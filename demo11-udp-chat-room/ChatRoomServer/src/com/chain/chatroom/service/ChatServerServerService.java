package com.chain.chatroom.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chain.chatroom.database.ChatServerClients;
import com.chain.chatroom.domain.Client;
import com.chain.chatroom.domain.GroupMsg;
import com.chain.chatroom.domain.Msg;
import com.chain.chatroom.domain.MsgMaker;
import com.chain.chatroom.register.ChatServerRegister;
import com.chain.chatroom.server.ChatServerServer;

public class ChatServerServerService {

	private static Logger logger = LoggerFactory.getLogger(ChatServerServerService.class);

	private volatile ChatServerServer server;

	private ChatServerRegister register;

	private ChatServerClients db;

	private MsgMaker msgMaker;

	public ChatServerServerService(ChatServerServer server) {
		this.server = server;
		this.register = server.getRegister();
		this.db = register.getDatabase();
		this.msgMaker = register.getMsgMaker();
	}

	public void handle(Msg msg) {
		logger.info("msg start to handle");
		if (doEcho(msg)) {
			switch (msg.getType()) {
			case CLIENT_REQUEST_LOGIN:
				doRequestLogin(msg);
				break;
			case CLIENT_REQUEST_LOGOUT:
				doRequestLogout(msg);
				break;
			case CLIENT_REQUEST_GROUP:
				doRequestGroup(msg);
				break;
			case CLIENT_REQUEST_INPUT:
				doRequestInput(msg);
				break;
			case CLIENT_REQUEST_LIST:
				doRequestList(msg);
				break;
			case ECHO:
			default:
			}
		}
		logger.info("msg handle success");
	}

	private void doRequestGroup(Msg msg) {

	}

	private void doRequestInput(Msg msg) {
		GroupMsg groupMsg = GroupMsg.of(db.get(msg.getId()).getName(), msg.getData());
		broadcast(msgMaker.group(groupMsg));
	}

	public boolean doEcho(Msg msg) {
		if (msg.getId() == -1)
			return true;
		boolean refresh = db.refreshClient(msg);
		if (!refresh && !db.exist(msg.getId())) {
			server.commit(msgMaker.logout().setIp(msg.getIp()).setPort(msg.getPort()));
			return false;
		}
		return refresh;
	}

	private void doRequestLogout(Msg msg) {
		long id = msg.getId();
		if (id == -1)
			return;
		Client client = db.deleteClient(id);
		if (client != null) {
			server.commit(msgMaker.logout().setIp(msg.getIp()).setPort(msg.getPort()));
			broadcast(msgMaker
					.groupOffline(GroupMsg.of(ChatServerClients.ADMIN_NAME, "[" + client.getName() + "] is offline")));
		}
	}

	private void doRequestLogin(Msg msg) {
		Client client = db.addClient(msg);
		if (client != null) {
			server.commit(msgMaker.login(new Object[] { client.getId(), client.getName() }).setIp(msg.getIp())
					.setPort(msg.getPort()));
			broadcast(msgMaker
					.groupOnline(GroupMsg.of(ChatServerClients.ADMIN_NAME, "[" + client.getName() + "] is online")));
		}
	}

	private void doRequestList(Msg msg) {
		server.commit(msgMaker.list(db.names()).setIp(msg.getIp()).setPort(msg.getPort()));
	}

	public void monitor() {
		db.refreshAdmin();

		if (db.isEmpty())
			return;

		List<Msg> msgList = new ArrayList<>();
		List<Client> removeList = new ArrayList<>();
		for (Client c : db.values())
			if (c.isOffline())
				removeList.add(c);

		for (Client c : removeList) {
			String name = c.getName();
			if (db.deleteClient(c) != null) {
				msgList.add(
						msgMaker.groupOffline(GroupMsg.of(ChatServerClients.ADMIN_NAME, "[" + name + "] is offline")));
				server.commit(msgMaker.logout().setIp(c.getIp()).setPort(c.getPort()));
			}
		}

		for (Msg m : msgList)
			broadcast(m);

		logger.info("current logined: " + db.size());
	}

	private void broadcast(Msg m) {
		List<Msg> msgList = new ArrayList<>();
		for (Client c : db.values())
			if (!db.isAdmin(c))
				msgList.add(Msg.of(m.getType(), m.getData()).setIp(c.getIp()).setPort(c.getPort()));

		for (Msg ms : msgList)
			server.commit(ms);

		if (msgList.size() > 0)
			logger.info("broadcast a msg");
	}

}
