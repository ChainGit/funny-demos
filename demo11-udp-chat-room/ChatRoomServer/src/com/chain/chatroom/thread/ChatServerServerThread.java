package com.chain.chatroom.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chain.chatroom.server.ChatServerServer;

public class ChatServerServerThread extends Thread {

	private static Logger logger = LoggerFactory.getLogger(ChatServerServerThread.class);

	private ChatServerServer server;

	public ChatServerServerThread(String string, ChatServerServer chatServerServer) {
		super(string);
		this.server = chatServerServer;
	}

	@Override
	public void run() {
		logger.info("chat-server server process start");
		boolean status = false;
		try {
			server.start();
			status = true;
		} catch (Exception e) {
			logger.error("something bad happened exception", e);
		} finally {
			server.close();
			logger.info("chat-server server process exit with " + status);
		}
	}

}
