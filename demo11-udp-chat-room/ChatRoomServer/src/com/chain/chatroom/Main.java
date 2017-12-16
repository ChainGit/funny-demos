package com.chain.chatroom;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chain.chatroom.common.ChatServerProperties;
import com.chain.chatroom.register.ChatServerRegister;
import com.chain.chatroom.server.ChatServerServer;
import com.chain.chatroom.thread.ChatServerServerThread;

/**
 * Server
 * 
 * @author chain
 *
 */
public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	private ChatServerRegister register;

	private static Main m;

	private Main() {
		register = new ChatServerRegister();

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("conf/talkroom.properties"));
			register.setProperties(new ChatServerProperties(prop));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		boolean status = false;
		logger.info("chat-server main process start");
		try {
			m = new Main();

			ChatServerRegister r = m.register;

			ChatServerServer server = new ChatServerServer(r);

			r.setServer(server);

			ChatServerServerThread serverThread = new ChatServerServerThread("Thread-Server-Server", server);
			serverThread.start();

			serverThread.join();

			status = true;
		} catch (Exception e) {
			logger.error("something bad happened exception", e);
		} finally {
			logger.info("chat-server main process exit with " + status);
		}
	}
}