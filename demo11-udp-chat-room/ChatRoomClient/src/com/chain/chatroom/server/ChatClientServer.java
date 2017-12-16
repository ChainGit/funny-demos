package com.chain.chatroom.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chain.chatroom.database.ChatClientStatus;
import com.chain.chatroom.domain.Msg;
import com.chain.chatroom.domain.MsgMaker;
import com.chain.chatroom.register.ChatClientRegister;
import com.chain.chatroom.service.ChatClientGuiService;
import com.chain.chatroom.service.ChatClientServerService;
import com.chain.chatroom.utils.ChatUtils;

public class ChatClientServer {

	private static Logger logger = LoggerFactory.getLogger(ChatClientServer.class);

	private ChatClientRegister register;

	private ChatClientGuiService guiService;

	private DatagramSocket datagramSocket;

	private int serverIp;
	private int serverPort;

	private SocketAddress serverSocketAddress;

	private int clientIp;
	private int clientPort;

	private SocketAddress clientSocketAddress;

	private BlockingQueue<Msg> msgSendQueue;
	private BlockingQueue<Msg> msgRecvQueue;

	private ChatClientServerService serverService;

	private volatile ChatClientServer server;

	private ChatClientServerSendThread serverSendThread;
	private ChatClientServerRecvThread serverRecvThread;
	private ChatClientServerRecvHandleThread serverRecvHandleThread;
	private ChatClientServerEchoSendThread serverEchoSendThread;

	private ChatClientStatus clientStatus;

	public static final int SLEEP_TIME = 100;
	public static final int RETRY_TIMES = 3;

	private MsgMaker msgMaker;

	private boolean closed;

	public ChatClientServer(ChatClientRegister register) {
		this.register = register;
		this.guiService = register.getGuiService();
	}

	private void init() {
		boolean status = false;
		logger.info("chat client server init");
		guiService.init();
		serverIp = register.getProperties().getServerIp();
		serverPort = register.getProperties().getServerPort();
		serverSocketAddress = new InetSocketAddress(ChatUtils.intIp2Str(serverIp), serverPort);
		logger.info("server ip is " + ChatUtils.intIp2Str(serverIp) + ", port is " + serverPort);
		try {
			datagramSocket = new DatagramSocket();
			clientSocketAddress = datagramSocket.getLocalSocketAddress();
			clientIp = ChatUtils.strIp2int(datagramSocket.getLocalAddress().getHostAddress());
			clientPort = datagramSocket.getLocalPort();
			logger.info("client ip is " + ChatUtils.intIp2Str(clientIp) + ", port is " + clientPort);
			msgSendQueue = new LinkedBlockingQueue<>();
			msgRecvQueue = new LinkedBlockingQueue<>();
			clientStatus = new ChatClientStatus();
			clientStatus.setIp(clientIp);
			clientStatus.setPort(clientPort);
			register.setChatClientStatus(clientStatus);
			msgMaker = new MsgMaker(false);
			register.setMsgMaker(msgMaker);
			serverService = new ChatClientServerService(server = this);
			register.setServerService(serverService);
			status = true;
		} catch (SocketException e) {
			logger.error("chat client server init exception", e);
			throw new RuntimeException("chat client server init exception", e);
		} finally {
			logger.info("chat client server init exit with " + status);
		}
	}

	public void start() {
		boolean status = false;
		try {
			init();

			logger.info("chat client server start");

			guiService.start();

			serverSendThread = new ChatClientServerSendThread("Thread-Client-Send");
			serverRecvThread = new ChatClientServerRecvThread("Thread-Client-Recv");
			serverRecvHandleThread = new ChatClientServerRecvHandleThread("Thread-Client-Recv-Handle");

			serverSendThread.start();
			serverRecvThread.start();
			serverRecvHandleThread.start();

			while (!(serverSendThread.isReady() && serverRecvThread.isReady() && serverRecvHandleThread.isReady()))
				Thread.sleep(SLEEP_TIME);

			if (!serverService.tryLogin() && clientStatus.isLogout()) {
				guiService.disable();
				guiService.errorDialog("login failed");
				return;
			}

			serverEchoSendThread = new ChatClientServerEchoSendThread("Thread-Client-Echo-Send");
			serverEchoSendThread.start();

			while (!serverEchoSendThread.isReady())
				Thread.sleep(SLEEP_TIME);

			guiService.ready();

			logger.info("chat client server start success...");

			serverSendThread.join();
			serverRecvThread.join();
			serverRecvHandleThread.join();
			serverEchoSendThread.join();

			status = true;

			logger.info("chat client server is closing...");
		} catch (Exception e) {
			logger.error("chat client server start exception", e);
			throw new RuntimeException("chat client server start exception", e);
		} finally {
			server.close();
			logger.info("chat client server start exit with " + status);
		}
	}

	public void close() {
		if (closed)
			return;

		clientStatus.toClose();

		for (int times = 0; times < RETRY_TIMES; times++) {
			serverService.preLogout();
			if (clientStatus.isLogin()) {
				try {
					Thread.sleep(SLEEP_TIME * 20);
				} catch (InterruptedException e) {
					logger.error("chat client server close exception", e);
					throw new RuntimeException("chat client server close exception", e);
				}
				continue;
			}
			break;
		}

		serverSendThread.close();
		serverRecvThread.close();
		serverRecvHandleThread.close();
		if (serverEchoSendThread != null)
			serverEchoSendThread.close();

		datagramSocket.close();

		closed = true;
	}

	public void commit(Msg msg) {
		int tryTimes = 0;
		while (tryTimes++ < RETRY_TIMES)
			try {
				msgSendQueue.put(msg);
				logger.info("chat client server commit-work add msg to send queue success");
				return;
			} catch (Exception e) {
				logger.error("chat client server commit-work exception", e);
				continue;
			}

		if (tryTimes >= RETRY_TIMES) {
			logger.error(
					"chat client server commit has tried to add this msg to send queue for " + RETRY_TIMES + " times");
			throw new RuntimeException(
					"chat client server commit has tried to add this msg to send queue for " + RETRY_TIMES + " times");
		}
	}

	private class ChatClientServerSendThread extends Thread {

		private volatile boolean status = true;

		public ChatClientServerSendThread(String string) {
			super(string);
		}

		@Override
		public void run() {
			boolean runStatus = false;
			logger.info("chat client server send process start");
			try {
				while (status) {
					try {
						Msg msg = msgSendQueue.take();
						if (msg == null) {
							Thread.sleep(SLEEP_TIME);
							continue;
						}

						int tryTimes = 0;
						while (tryTimes++ < RETRY_TIMES)
							try {
								if (send(msg)) {
									logger.info("chat client send msg: " + msg);
									break;
								}
							} catch (Exception e) {
								logger.error("chat client server try-send-work exception", e);
								continue;
							}

						if (tryTimes >= RETRY_TIMES)
							logger.error("chat client server send-work has tried to send this msg for " + RETRY_TIMES
									+ " times");

					} catch (Exception e) {
						logger.error("chat client server send-work exception", e);
						continue;
					}
				}
				runStatus = true;
			} catch (Exception e) {
				logger.error("chat client server send process exception", e);
				throw new RuntimeException("chat client server send process exception", e);
			} finally {
				close();
				logger.info("chat client server send process exit with " + runStatus);
			}
		}

		private boolean send(Msg msg) {
			try {
				synchronized (ChatClientServerSendThread.class) {
					if (!datagramSocket.isClosed()) {
						msg.setIp(serverIp);
						msg.setPort(serverPort);
						msg.setId(clientStatus.getId());
						byte[] data = msg.toBytes();
						logger.info("msg will be sent to " + serverSocketAddress);
						DatagramPacket dp = new DatagramPacket(data, 0, data.length, serverSocketAddress);
						datagramSocket.send(dp);
						logger.info("chat client server send a msg");
						return true;
					}
				}
			} catch (Exception e) {
				if (e.getMessage() == null || !e.getMessage().endsWith("closed")) {
					logger.error("chat client server send exception", e);
					throw new RuntimeException("chat client server send exception", e);
				} else {
					close();
					logger.info("chat client server datagram socket has CLOSED");
				}
			}
			return false;
		}

		public boolean isReady() {
			return status;
		}

		public void close() {
			status = false;
		}

	}

	private class ChatClientServerRecvThread extends Thread {

		private volatile boolean status = true;

		private static final int MSG_MAX_DATA_SIZE = 32;

		private static final long ADMIN_ID = 10000L;

		public ChatClientServerRecvThread(String string) {
			super(string);
		}

		@Override
		public void run() {
			boolean runStatus = false;
			logger.info("chat client server recv start");
			try {
				byte[] data = new byte[1024 * MSG_MAX_DATA_SIZE];
				DatagramPacket dp = new DatagramPacket(data, data.length);
				while (status) {
					try {
						if (!recv(dp))
							continue;

						Msg msg = Msg.of(data);
						logger.info("chat client recv server msg: " + msg);

						int clientIpInMsg = msg.getIp();
						int clientPortInMsg = msg.getPort();
						if (clientIpInMsg == -1 || clientPortInMsg == -1 || msg.getId() != ADMIN_ID) {
							logger.warn("msg from server ip or port is illegal, or is not come from server");
							continue;
						}

						if (clientIp != clientIpInMsg) {
							clientStatus.setIp(clientIp = clientIpInMsg);
							String ips = ChatUtils.intIp2Str(clientIp);
							clientSocketAddress = new InetSocketAddress(ips, clientPort);
							logger.info("client ip and port has been changed to " + clientSocketAddress);
						}

						if (clientPort != clientPortInMsg) {
							clientStatus.setPort(clientPort = clientPortInMsg);
							clientSocketAddress = new InetSocketAddress(ChatUtils.intIp2Str(clientIp), clientPort);
							logger.info("client ip and port has been changed to " + clientSocketAddress);
						}

						int tryTimes = 0;
						while (tryTimes++ < RETRY_TIMES)
							try {
								msgRecvQueue.put(msg);
								logger.info("chat client recv server recv-work add msg to recv queue success");
								break;
							} catch (Exception e) {
								logger.error("chat client server try-recv-work exception", e);
								continue;
							}

						if (tryTimes >= RETRY_TIMES)
							logger.error("chat client server recv-work has tried to add this msg to recv queue for "
									+ RETRY_TIMES + " times");

					} catch (Exception e) {
						logger.error("chat client server recv-work exception", e);
						continue;
					}
				}
				runStatus = true;
			} catch (Exception e) {
				logger.error("chat client server recv exception", e);
				throw new RuntimeException("chat client server recv exception", e);
			} finally {
				close();
				logger.info("chat client server recv exit with " + runStatus);
			}
		}

		private boolean recv(DatagramPacket dp) {
			try {
				synchronized (ChatClientServerRecvThread.class) {
					if (!datagramSocket.isClosed()) {
						datagramSocket.receive(dp);
						SocketAddress sa = dp.getSocketAddress();
						if (!serverSocketAddress.equals(sa)) {
							logger.warn("chat client recv server socket address is illegal");
							return false;
						}
						logger.info("chat client server recv a msg from " + sa);
						return true;
					}
				}
			} catch (Exception e) {
				if (e.getMessage() == null || !e.getMessage().endsWith("closed")) {
					logger.error("chat client server recv exception", e);
					throw new RuntimeException("chat client server recv", e);
				} else {
					close();
					logger.info("chat client server datagram socket has CLOSED");
				}
			}
			return false;
		}

		public boolean isReady() {
			return status;
		}

		public void close() {
			status = false;
		}
	}

	private class ChatClientServerRecvHandleThread extends Thread {

		private volatile boolean status = true;

		public ChatClientServerRecvHandleThread(String string) {
			super(string);
		}

		@Override
		public void run() {
			boolean runStatus = false;
			logger.info("chat client server recv-handle process start");
			try {
				while (status) {
					try {
						Msg msg = msgRecvQueue.take();
						if (msg == null) {
							Thread.sleep(SLEEP_TIME);
							continue;
						}

						int tryTimes = 0;
						while (tryTimes++ < RETRY_TIMES)
							try {
								serverService.handle(msg);
								logger.info("chat client recv server recv-handle-work handle this msg success");
								break;
							} catch (Exception e) {
								logger.error("chat client server recv-handle-work exception", e);
								continue;
							}

						if (tryTimes >= RETRY_TIMES)
							logger.error("chat client server recv-handle-work has tried to handle this msg for "
									+ RETRY_TIMES + " times");

					} catch (Exception e) {
						logger.error("chat client server recv-handle-work exception", e);
						continue;
					}
				}
				runStatus = true;
			} catch (Exception e) {
				logger.error("chat client server recv-handle process exception", e);
				throw new RuntimeException("chat client server recv-handle process exception", e);
			} finally {
				close();
				logger.info("chat client server recv-handle process exit with " + runStatus);
			}
		}

		public boolean isReady() {
			return status;
		}

		public void close() {
			status = false;
		}

	}

	private class ChatClientServerEchoSendThread extends Thread {

		private volatile boolean status = true;

		public ChatClientServerEchoSendThread(String string) {
			super(string);
		}

		@Override
		public void run() {
			boolean runStatus = false;
			logger.info("chat client server echo-send process start");
			try {
				int t = 0;
				while (status) {
					try {
						Thread.sleep(SLEEP_TIME * ChatClientStatus.MAX_LOSE_SECONDS);
						if ((++t % 5) == 0) {
							t = 0;
							serverService.refresh();
						} else {
							serverService.doEcho();
						}
						if (!clientStatus.isOnline()) {
							clientStatus.toLogout();
							serverService.tryLoginWithExit();
						}
						Thread.sleep(SLEEP_TIME * ChatClientStatus.MAX_LOSE_SECONDS);
						logger.info("client refresh once");
					} catch (Exception e) {
						logger.error("chat client server echo-send-work exception", e);
						continue;
					}
				}
				runStatus = true;
			} catch (Exception e) {
				logger.error("chat client server echo-send process exception", e);
				throw new RuntimeException("chat client server echo-send process exception", e);
			} finally {
				close();
				logger.info("chat client server echo-send process exit with " + runStatus);
			}
		}

		public boolean isReady() {
			return status;
		}

		public void close() {
			status = false;
		}

	}

	public ChatClientRegister getRegister() {
		return register;
	}

}
