package com.chain.chatroom.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chain.chatroom.server.ChatClientServer;

public class ChatClientServerThread extends Thread {

	private static Logger logger = LoggerFactory.getLogger(ChatClientServerThread.class);

	private volatile ChatClientServer server;

	public ChatClientServerThread(String string, ChatClientServer server) {
		super(string);
		this.server = server;
	}

	@Override
	public void run() {
		logger.info("chat client server process start");
		boolean status = false;
		try {
			server.start();
			status = true;
		} catch (Exception e) {
			logger.error("something bad happened exception", e);
			server.getRegister().getGuiService().errorDialog("error detail: " + e.getMessage());
		} finally {
			server.close();
			logger.info("chat client server process exit with " + status);
		}
	}

}
