package com.chain.chatroom;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chain.chatroom.common.ChatClientProperties;
import com.chain.chatroom.gui.ChatClientGui;
import com.chain.chatroom.register.ChatClientRegister;
import com.chain.chatroom.server.ChatClientServer;
import com.chain.chatroom.service.ChatClientGuiService;
import com.chain.chatroom.thread.ChatClientServerThread;

/**
 * Client
 * 
 * @author chain
 *
 */
public class Main {

	private static Logger logger = LoggerFactory.getLogger(Main.class);

	private ChatClientRegister register;

	private static Main m;

	private Main() {
		register = new ChatClientRegister();

		Properties prop = new Properties();
		try {
			prop.load(new FileInputStream("conf/talkroom.properties"));
			register.setProperties(new ChatClientProperties(prop));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {
		boolean status = false;
		logger.info("chat client main process start");
		try {

			m = new Main();

			ChatClientRegister r = m.register;

			r.setGuiService(new ChatClientGuiService(new ChatClientGui(r), r));

			ChatClientServer server = new ChatClientServer(r);

			r.setServer(server);

			ChatClientServerThread serverThread = new ChatClientServerThread("Thread-Client-Server", server);
			serverThread.start();

			serverThread.join();

			status = true;
		} catch (Exception e) {
			logger.error("something bad happened exception", e);
		} finally {
			logger.info("chat client main process exit with " + status);
		}
	}

}
