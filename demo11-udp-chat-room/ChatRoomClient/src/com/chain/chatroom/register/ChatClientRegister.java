package com.chain.chatroom.register;

import com.chain.chatroom.common.ChatClientProperties;
import com.chain.chatroom.database.ChatClientStatus;
import com.chain.chatroom.domain.MsgMaker;
import com.chain.chatroom.server.ChatClientServer;
import com.chain.chatroom.service.ChatClientGuiService;
import com.chain.chatroom.service.ChatClientServerService;

public class ChatClientRegister {

	private ChatClientGuiService guiService;
	private ChatClientServerService serverService;

	private ChatClientStatus chatClientStatus;

	private ChatClientServer server;

	public ChatClientServer getServer() {
		return server;
	}

	public void setServer(ChatClientServer server) {
		assertNull(this.server);
		this.server = server;
	}

	private MsgMaker msgMaker;

	public ChatClientStatus getChatClientStatus() {
		return chatClientStatus;
	}

	public MsgMaker getMsgMaker() {
		return msgMaker;
	}

	public void setChatClientStatus(ChatClientStatus chatClientStatus) {
		assertNull(this.chatClientStatus);
		this.chatClientStatus = chatClientStatus;
	}

	public ChatClientServerService getServerService() {
		return serverService;
	}

	public void setServerService(ChatClientServerService serverService) {
		assertNull(this.serverService);
		this.serverService = serverService;
	}

	private ChatClientProperties properties;

	public ChatClientProperties getProperties() {
		return properties;
	}

	public ChatClientGuiService getGuiService() {
		return guiService;
	}

	public void setGuiService(ChatClientGuiService guiService) {
		assertNull(this.guiService);
		this.guiService = guiService;
	}

	private void assertNull(Object obj) {
		if (obj != null)
			throw new RuntimeException("object can only be set once");
	}

	public void setProperties(ChatClientProperties properties) {
		assertNull(this.properties);
		this.properties = properties;
	}

	public void setMsgMaker(MsgMaker msgMaker) {
		assertNull(this.msgMaker);
		this.msgMaker = msgMaker;
	}

}
