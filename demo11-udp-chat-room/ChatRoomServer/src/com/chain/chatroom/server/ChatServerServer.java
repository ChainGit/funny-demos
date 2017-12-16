package com.chain.chatroom.server;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.chain.chatroom.database.ChatServerClients;
import com.chain.chatroom.domain.Client;
import com.chain.chatroom.domain.Msg;
import com.chain.chatroom.domain.Msg.Type;
import com.chain.chatroom.domain.MsgMaker;
import com.chain.chatroom.register.ChatServerRegister;
import com.chain.chatroom.service.ChatServerServerService;
import com.chain.chatroom.utils.ChatUtils;

public class ChatServerServer {

	private static Logger logger = LoggerFactory.getLogger(ChatServerServer.class);

	private ChatServerRegister register;

	private DatagramSocket datagramSocket;

	private int serverIp;
	private int serverPort;

	private SocketAddress serverSocketAddress;

	private Queue<Msg> msgSendQueue;
	private Queue<Msg> msgRecvQueue;
	private Queue<Msg> msgRecvEchoQueue;

	private ChatServerServerService serverService;

	private volatile ChatServerServer server;

	private ChatServerServerSendThread serverSendThread;
	private ChatServerServerRecvThread serverRecvThread;
	private ChatServerServerRecvEchoHandleThread serverRecvEchoHandleThread;
	private List<ChatServerServerRecvHandleThread> serverRecvHandleThreadList;

	private ChatServerServerClientMonitorThread serverClientMonitorThread;

	private ChatServerClients db;

	private static final int SLEEP_TIME = 100;
	private static final int RETRY_TIMES = 3;

	private ReentrantLock recvLock = new ReentrantLock();
	private ReentrantLock sendLock = new ReentrantLock();

	private ReentrantLock msgSendQueueLock = new ReentrantLock();
	private ReentrantLock msgRecvQueueLock = new ReentrantLock();
	private ReentrantLock msgRecvEchoQueueLock = new ReentrantLock();

	private Condition msgSendQueueCondition;
	private Condition msgRecvQueueCondition;
	private Condition msgRecvEchoQueueCondition;

	private MsgMaker msgMaker;

	private boolean closed;

	public ChatServerRegister getRegister() {
		return register;
	}

	public ChatServerServer(ChatServerRegister r) {
		this.register = r;
	}

	private void init() {
		boolean status = false;
		logger.info("chat-server server init");
		serverIp = register.getProperties().getServerIp();
		serverPort = register.getProperties().getServerPort();
		serverSocketAddress = new InetSocketAddress(ChatUtils.intIp2Str(serverIp), serverPort);
		logger.info("server ip is " + ChatUtils.intIp2Str(serverIp) + ", port is " + serverPort);
		try {
			datagramSocket = new DatagramSocket(serverSocketAddress);
			msgSendQueue = new ConcurrentLinkedQueue<>();
			msgRecvQueue = new ConcurrentLinkedQueue<>();
			msgRecvEchoQueue = new ConcurrentLinkedQueue<>();
			msgSendQueueCondition = msgSendQueueLock.newCondition();
			msgRecvQueueCondition = msgRecvQueueLock.newCondition();
			msgRecvEchoQueueCondition = msgRecvEchoQueueLock.newCondition();
			serverRecvHandleThreadList = new LinkedList<>();
			db = new ChatServerClients();
			register.setDatabase(db);
			msgMaker = new MsgMaker(true);
			register.setMsgMaker(msgMaker);
			serverService = new ChatServerServerService(server = this);
			register.setServerService(serverService);
			status = true;
		} catch (SocketException e) {
			logger.error("chat-server server init exception", e);
			throw new RuntimeException("chat-server server init exception", e);
		} finally {
			logger.info("chat-server server init exit with " + status);
		}
	}

	public void start() {
		boolean status = false;
		try {
			init();

			logger.info("chat-server server start");

			serverSendThread = new ChatServerServerSendThread("Thread-Server-Send");
			serverRecvThread = new ChatServerServerRecvThread("Thread-Server-Recv");
			serverRecvEchoHandleThread = new ChatServerServerRecvEchoHandleThread("Thread-Server-Recv-Echo-Handle");
			for (int i = (int) (Math.random() * 3 + 1); i > -1; i--)
				serverRecvHandleThreadList.add(new ChatServerServerRecvHandleThread("Thread-Server-Recv-Handle-" + i));

			serverClientMonitorThread = new ChatServerServerClientMonitorThread("Thread-Server-Client-Monitor");

			logger.info("chat-server server recv handle process create amount: " + serverRecvHandleThreadList.size());

			serverSendThread.start();
			serverRecvThread.start();
			serverRecvEchoHandleThread.start();
			for (ChatServerServerRecvHandleThread t : serverRecvHandleThreadList)
				t.start();

			serverClientMonitorThread.start();

			for (boolean ready = false; !ready;) {
				ready = true;
				for (ChatServerServerRecvHandleThread t : serverRecvHandleThreadList)
					if (!t.isReady()) {
						ready = false;
						Thread.sleep(SLEEP_TIME);
						break;
					}
			}

			while (!(serverSendThread.isReady() && serverRecvThread.isReady() && serverRecvEchoHandleThread.isReady()
					&& serverClientMonitorThread.isReady()))
				Thread.sleep(SLEEP_TIME);

			Thread.sleep(SLEEP_TIME * 5);
			logger.info("chat-server server start success...");

			serverSendThread.join();
			serverRecvThread.join();
			serverRecvEchoHandleThread.join();
			for (ChatServerServerRecvHandleThread t : serverRecvHandleThreadList)
				t.join();

			serverClientMonitorThread.join();

			status = true;

			logger.info("chat-server server is closing...");
		} catch (Exception e) {
			logger.error("chat-server server start exception", e);
			throw new RuntimeException("chat-server server start exception", e);
		} finally {
			server.close();
			logger.info("chat-server server start exit with " + status);
		}
	}

	public void close() {
		if (closed)
			return;

		serverSendThread.close();
		serverRecvThread.close();
		serverRecvEchoHandleThread.close();
		for (ChatServerServerRecvHandleThread t : serverRecvHandleThreadList)
			t.close();
		serverClientMonitorThread.close();
		datagramSocket.close();

		closed = true;
	}

	public void commit(Msg msg) {
		int tryTimes = 0;
		while (tryTimes++ < RETRY_TIMES)
			try {
				msgSendQueue.add(msg);
				try {
					msgSendQueueLock.lock();
					msgSendQueueCondition.signal();
					logger.info("chat-server msg send-work signal");
				} finally {
					msgSendQueueLock.unlock();
				}
				logger.info("chat-server server commit-work add msg to send queue success");
				return;
			} catch (Exception e) {
				logger.error("chat-server server commit-work exception", e);
				continue;
			}

		if (tryTimes >= RETRY_TIMES) {
			logger.error(
					"chat-server server commit has tried to add this msg to send queue for " + RETRY_TIMES + " times");
			throw new RuntimeException(
					"chat-server server commit has tried to add this msg to send queue for " + RETRY_TIMES + " times");
		}
	}

	private class ChatServerServerSendThread extends Thread {

		private volatile boolean status = true;

		public ChatServerServerSendThread(String string) {
			super(string);
		}

		@Override
		public void run() {
			boolean runStatus = false;
			logger.info("chat-server server send process start");
			try {
				while (status) {
					try {
						Msg msg = msgSendQueue.poll();

						if (msg == null) {
							try {
								msgSendQueueLock.lock();
								logger.info("chat-server send-work is sleeping");
								msgSendQueueCondition.await();
								logger.info("chat-server send-work has been waked up");
							} finally {
								msgSendQueueLock.unlock();
							}
							continue;
						}

						int tryTimes = 0;
						while (tryTimes++ < RETRY_TIMES)
							try {
								if (send(msg)) {
									logger.info("chat-server send msg: " + msg);
									break;
								}
							} catch (Exception e) {
								logger.error("chat-server server try-send-work exception", e);
								continue;
							}

						if (tryTimes >= RETRY_TIMES)
							logger.error("chat-server server send-work has tried to send this msg for " + RETRY_TIMES
									+ " times");

					} catch (Exception e) {
						logger.error("chat-server server send-work exception", e);
						continue;
					}
				}
				runStatus = true;
			} catch (Exception e) {
				logger.error("chat-server server send process exception", e);
				throw new RuntimeException("chat-server server send process exception", e);
			} finally {
				close();
				logger.info("chat-server server send process exit with " + runStatus);
			}
		}

		private boolean send(Msg msg) {
			try {
				sendLock.lock();
				if (!datagramSocket.isClosed()) {
					msg.setId(ChatServerClients.ADMIN_ID);
					byte[] data = msg.toBytes();
					int clientIp = msg.getIp();
					int clientPort = msg.getPort();
					if (clientIp == -1 || clientPort == -1) {
						logger.warn("msg to send ip or port is illegal");
						return false;
					}
					InetSocketAddress clientSocketAddress = new InetSocketAddress(ChatUtils.intIp2Str(clientIp),
							clientPort);
					logger.info("msg will be sent to " + clientSocketAddress);
					DatagramPacket dp = new DatagramPacket(data, 0, data.length, clientSocketAddress);
					datagramSocket.send(dp);
					logger.info("chat-server server send a msg");
					return true;
				}
			} catch (Exception e) {
				if (e.getMessage() == null || !e.getMessage().endsWith("closed")) {
					logger.error("chat-server server send exception", e);
					throw new RuntimeException("chat-server server send exception", e);
				} else {
					close();
					logger.info("chat-server server datagram socket has closed");
				}
			} finally {
				if (sendLock.isLocked())
					sendLock.unlock();
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

	private class ChatServerServerRecvThread extends Thread {

		private volatile boolean status = true;

		private static final int MSG_MAX_DATA_SIZE = 32;

		public ChatServerServerRecvThread(String string) {
			super(string);
		}

		@Override
		public void run() {
			boolean runStatus = false;
			logger.info("chat-server server recv start");
			try {
				byte[] data = new byte[1024 * MSG_MAX_DATA_SIZE];
				DatagramPacket dp = new DatagramPacket(data, data.length);
				while (status) {
					try {
						if (!recv(dp))
							continue;

						Msg msg = Msg.of(data);
						logger.info("chat-server recv client msg: " + msg);

						int serverIpInClientMsg = msg.getIp();
						int serverPortInClientMsg = msg.getPort();
						if (serverIpInClientMsg == -1 || serverPortInClientMsg != serverPort) {
							logger.warn("msg from client ip or port is illegal");
							continue;
						}

						// 打洞
						InetSocketAddress clientSocketAddress = (InetSocketAddress) dp.getSocketAddress();
						msg.setIp(ChatUtils.strIp2int(clientSocketAddress.getAddress().getHostAddress()));
						msg.setPort(clientSocketAddress.getPort());

						int tryTimes = 0;
						while (tryTimes++ < RETRY_TIMES)
							try {
								if (Type.ECHO.equals(msg.getType())) {
									msgRecvEchoQueue.add(msg);
									try {
										msgRecvEchoQueueLock.lock();
										msgRecvEchoQueueCondition.signal();
										logger.info("chat-server msg recv-echo-work signal");
									} finally {
										msgRecvEchoQueueLock.unlock();
									}
								} else {
									msgRecvQueue.add(msg);
									try {
										msgRecvQueueLock.lock();
										msgRecvQueueCondition.signal();
										logger.info("chat-server msg recv-work signal");
									} finally {
										msgRecvQueueLock.unlock();
									}
								}
								logger.info("chat-server server recv-work add msg to recv queue success");
								break;
							} catch (Exception e) {
								logger.error("chat-server server recv-work exception", e);
								continue;
							}

						if (tryTimes >= RETRY_TIMES)
							logger.error("chat-server server recv-work has tried to add this msg to recv queue for "
									+ RETRY_TIMES + " times");

					} catch (Exception e) {
						logger.error("chat-server server recv-work exception", e);
						continue;
					}
				}
				runStatus = true;
			} catch (Exception e) {
				logger.error("chat-server server recv exception", e);
				throw new RuntimeException("chat-server server recv exception", e);
			} finally {
				close();
				logger.info("chat-server server recv exit with " + runStatus);
			}
		}

		private boolean recv(DatagramPacket dp) {
			try {
				if ((recvLock.tryLock() || recvLock.tryLock(SLEEP_TIME / 50, TimeUnit.SECONDS))
						&& !datagramSocket.isClosed()) {
					datagramSocket.receive(dp);
					SocketAddress clientSocketAddress = (InetSocketAddress) dp.getSocketAddress();
					if (clientSocketAddress == null) {
						logger.warn("chat-server recv client socket address is illegal");
						return false;
					}
					logger.info("chat-server server recv a msg from " + clientSocketAddress);
					return true;
				}
			} catch (Exception e) {
				if (e.getMessage() == null || !e.getMessage().endsWith("closed")) {
					logger.error("chat-server server recv exception", e);
					throw new RuntimeException("chat-server server recv", e);
				} else {
					close();
					logger.info("chat-server server datagram socket has closed");
				}
			} finally {
				if (recvLock.isLocked())
					recvLock.unlock();
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

	private class ChatServerServerRecvEchoHandleThread extends Thread {

		private volatile boolean status = true;

		public ChatServerServerRecvEchoHandleThread(String string) {
			super(string);
		}

		@Override
		public void run() {
			boolean runStatus = false;
			logger.info("chat-server server recv-echo-handle process start");
			try {
				while (status) {
					try {
						Msg msg = msgRecvEchoQueue.poll();

						if (msg == null) {
							try {
								msgRecvEchoQueueLock.lock();
								logger.info("chat-server recv-echo-work is sleeping");
								msgRecvEchoQueueCondition.await();
								logger.info("chat-server recv-echo-work has been waked up");
							} finally {
								msgRecvEchoQueueLock.unlock();
							}
							continue;
						}

						int tryTimes = 0;
						while (tryTimes++ < RETRY_TIMES)
							try {
								serverService.doEcho(msg);
								logger.info("chat-server server recv-echo-handle-work handle msg success");
								break;
							} catch (Exception e) {
								logger.error("chat-server server recv-echo-handle-work try-to-handle exception", e);
								continue;
							}

						if (tryTimes >= RETRY_TIMES)
							logger.error(
									"chat-server server recv-echo-handle-work has tried to handle this echo msg for "
											+ RETRY_TIMES + " times");

					} catch (Exception e) {
						logger.error("chat-server server recv-echo-handle-work exception", e);
						continue;
					}
				}
				runStatus = true;
			} catch (Exception e) {
				logger.error("chat-server server recv-echo-handle process exception", e);
				throw new RuntimeException("chat-server server recv-echo-handle process exception", e);
			} finally {
				close();
				logger.info("chat-server server recv-echo-handle process exit with " + runStatus);
			}
		}

		public boolean isReady() {
			return status;
		}

		public void close() {
			status = false;
		}
	}

	private class ChatServerServerRecvHandleThread extends Thread {

		private volatile boolean status = true;

		public ChatServerServerRecvHandleThread(String string) {
			super(string);
		}

		@Override
		public void run() {
			boolean runStatus = false;
			logger.info("chat-server server recv-handle process start");
			try {
				while (status) {
					try {
						Msg msg = msgRecvQueue.poll();
						if (msg == null) {
							try {
								msgRecvQueueLock.lock();
								logger.info("chat-server recv-work is sleeping");
								msgRecvQueueCondition.await();
								logger.info("chat-server recv-work has been waked up");
							} finally {
								msgRecvQueueLock.unlock();
							}
							continue;
						}

						int tryTimes = 0;
						while (tryTimes++ < RETRY_TIMES)
							try {
								serverService.handle(msg);
								logger.info("chat-server server recv-handle-work handle msg success");
								break;
							} catch (Exception e) {
								logger.error("chat-server server recv-handle-work try-to-handle exception", e);
								continue;
							}

						if (tryTimes >= RETRY_TIMES)
							logger.error("chat-server server recv-handle-work has tried to handle this msg for "
									+ RETRY_TIMES + " times");

					} catch (Exception e) {
						logger.error("chat-server server recv-handle-work exception", e);
						continue;
					}
				}
				runStatus = true;
			} catch (Exception e) {
				logger.error("chat-server server recv-handle process exception", e);
				throw new RuntimeException("chat-server server recv-handle process exception", e);
			} finally {
				close();
				logger.info("chat-server server recv-handle process exit with " + runStatus);
			}
		}

		public boolean isReady() {
			return status;
		}

		public void close() {
			status = false;
		}

	}

	private class ChatServerServerClientMonitorThread extends Thread {

		private volatile boolean status = true;

		public ChatServerServerClientMonitorThread(String string) {
			super(string);
		}

		@Override
		public void run() {
			boolean runStatus = false;
			logger.info("chat-server server client monitor process start");
			try {
				while (status) {
					try {
						Thread.sleep(SLEEP_TIME * Client.MAX_LOSE_SECONDS * 20);

						synchronized (ChatServerServerClientMonitorThread.class) {
							serverService.monitor();
							logger.info("monitor once");
						}
					} catch (Exception e) {
						logger.error("chat-server server client-monitor-work exception", e);
						continue;
					}
				}
				runStatus = true;
			} catch (Exception e) {
				logger.error("chat-server server client monitor process exception", e);
				throw new RuntimeException("chat-server server client monitor process exception", e);
			} finally {
				close();
				logger.info("chat-serverserver client monitor process exit with " + runStatus);
			}
		}

		public boolean isReady() {
			return status;
		}

		public void close() {
			status = false;
		}

	}

}
