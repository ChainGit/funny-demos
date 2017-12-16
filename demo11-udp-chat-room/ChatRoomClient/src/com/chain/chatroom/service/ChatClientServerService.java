package com.chain.chatroom.service;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chain.chatroom.database.ChatClientStatus;
import com.chain.chatroom.domain.GroupMsg;
import com.chain.chatroom.domain.Msg;
import com.chain.chatroom.domain.Msg.Type;
import com.chain.chatroom.domain.MsgMaker;
import com.chain.chatroom.register.ChatClientRegister;
import com.chain.chatroom.server.ChatClientServer;

public class ChatClientServerService {

	private static Logger logger = LoggerFactory.getLogger(ChatClientServerService.class);

	private volatile ChatClientServer server;

	private volatile ChatClientGuiService guiService;

	private MsgMaker msgMaker;

	private ChatClientStatus clientStatus;

	private ChatClientRegister register;

	public ChatClientServerService(ChatClientServer server) {
		this.server = server;
		this.register = server.getRegister();
		this.clientStatus = register.getChatClientStatus();
		this.msgMaker = register.getMsgMaker();
		this.guiService = register.getGuiService();
	}

	public void handle(Msg msg) {
		logger.info("msg start to handle");
		switch (msg.getType()) {
		case SERVER_RESPONSE_LOGIN:
			doResponseLogin(msg);
			break;
		case SERVER_RESPONSE_LOGOUT:
			doResponseLogout(msg);
			break;
		case SERVER_RESPONSE_GROUP:
			doResponseGroup(msg);
			break;
		case SERVER_RESPONSE_INPUT:
			doResponseInput(msg);
			break;
		case SERVER_RESPONSE_LIST:
			doResponseList(msg);
			break;
		case ECHO:
		default:
			doEcho();
		}
		logger.info("msg handle success");
	}

	public void doEcho() {
		server.commit(msgMaker.echo());
	}

	private void doResponseList(Msg msg) {
		logger.info("chat client do response list");
		clientStatus.online();
		guiService.list((List<?>) msg.getData());
	}

	private void doResponseInput(Msg msg) {
		logger.info("chat client do response input");
	}

	private void doResponseGroup(Msg msg) {
		logger.info("chat client do response group");
		@SuppressWarnings("unchecked")
		Map<String, Object> lst = (Map<String, Object>) msg.getData();
		GroupMsg groupMsg = GroupMsg.of((long) lst.get("time"), (String) lst.get("name"), lst.get("content"));
		guiService.addGroupMsg(groupMsg);
	}

	private void doResponseLogout(Msg msg) {
		logger.info("chat client do response logout");
		if (!clientStatus.isClosing()) {
			clientStatus.toLogout();
			try {
				tryLoginWithExit();
			} catch (InterruptedException e) {
				logger.error("do response logout exception", e);
				throw new RuntimeException("do response logout exception", e);
			}
		} else {
			clientStatus.toClose();
		}
	}

	private void doResponseLogin(Msg msg) {
		logger.info("chat client do response login");
		if (Type.SERVER_RESPONSE_LOGIN.equals(msg.getType())) {
			List<?> data = (List<?>) msg.getData();
			clientStatus.toLogin((long) data.get(0));
			clientStatus.setName((String) data.get(1));
			logger.info("client has logined, current info: " + clientStatus);
		}
	}

	public void preLogin() {
		Msg msg = msgMaker.login();
		server.commit(msg);
		logger.info("chat client try to do pre login");
	}

	public void preLogout() {
		Msg msg = msgMaker.logout();
		server.commit(msg);
		logger.info("chat client try to do pre logout");
	}

	public void refresh() {
		server.commit(msgMaker.list());
	}

	public void tryLoginWithExit() throws InterruptedException {
		if (!tryLogin()) {
			guiService.disable();
			guiService.errorDialog("connect to server failed");
			server.close();
		}
	}

	public boolean tryLogin() throws InterruptedException {
		int times = 0;
		int retryTimes = ChatClientServer.RETRY_TIMES;
		int sleepTime = ChatClientServer.SLEEP_TIME;
		w: for (; times < retryTimes; times++) {
			if (clientStatus.isLogout())
				preLogin();
			for (int i = 0; i < retryTimes; i++) {
				Thread.sleep(sleepTime * 10);
				if (clientStatus.isLogin())
					break w;
			}
		}
		return times < retryTimes;
	}
}
