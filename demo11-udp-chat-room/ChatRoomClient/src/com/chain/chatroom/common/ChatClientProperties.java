package com.chain.chatroom.common;

import java.util.Properties;

import com.chain.chatroom.utils.ChatUtils;

public class ChatClientProperties {

	private Properties prop;

	public ChatClientProperties(Properties prop) {
		this.prop = prop;
	}

	private int serverIp = -1;
	private int serverPort = -1;

	public Properties getProperties() {
		return prop;
	}

	public int getServerIp() {
		if (serverIp == -1)
			serverIp = Integer.valueOf(ChatUtils.strIp2int(prop.getProperty("server.ip")));
		return serverIp;
	}

	public int getServerPort() {
		if (serverPort == -1)
			serverPort = Integer.valueOf(prop.getProperty("server.port"));
		return serverPort;
	}

}
