package com.chain.chatroom.register;

import com.chain.chatroom.common.ChatServerProperties;
import com.chain.chatroom.database.ChatServerClients;
import com.chain.chatroom.domain.MsgMaker;
import com.chain.chatroom.server.ChatServerServer;
import com.chain.chatroom.service.ChatServerServerService;

public class ChatServerRegister {

	private ChatServerServerService serverService;
	private ChatServerClients database;

	private ChatServerProperties properties;

	private MsgMaker msgMaker;

	private ChatServerServer server;

	public ChatServerServer getServer() {
		return server;
	}

	public void setServer(ChatServerServer server) {
		assertNull(this.server);
		this.server = server;
	}

	public ChatServerServerService getServerService() {
		return serverService;
	}

	public void setServerService(ChatServerServerService serverService) {
		assertNull(this.serverService);
		this.serverService = serverService;
	}

	public ChatServerProperties getProperties() {
		return properties;
	}

	private void assertNull(Object obj) {
		if (obj != null)
			throw new RuntimeException("object can only be set once");
	}

	public void setProperties(ChatServerProperties properties) {
		assertNull(this.properties);
		this.properties = properties;
	}

	public ChatServerClients getDatabase() {
		return database;
	}

	public void setDatabase(ChatServerClients database) {
		assertNull(this.database);
		this.database = database;
	}

	public void setMsgMaker(MsgMaker msgMaker) {
		assertNull(this.msgMaker);
		this.msgMaker = msgMaker;
	}

	public MsgMaker getMsgMaker() {
		return msgMaker;
	}
}
