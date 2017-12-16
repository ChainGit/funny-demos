package com.chain.chatroom.database;

import com.chain.chatroom.utils.ChatUtils;

public class ChatClientStatus {

	public static final int LOGIN = 1;
	public static final int LOGOUT = 0;
	public static final int MAX_LOSE_SECONDS = 10;

	private volatile int status = ChatClientStatus.LOGOUT;

	private volatile boolean closing;

	private volatile long id = -1;
	private volatile String name;

	private volatile int ip;
	private volatile int port;

	private volatile long last;

	public boolean isOnline() {
		return ChatUtils.earlierSeconds(last) <= MAX_LOSE_SECONDS;
	}

	public void online() {
		synchronized (c) {
			this.last = System.currentTimeMillis();
		}
	}

	private Class<ChatClientStatus> c = ChatClientStatus.class;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		synchronized (c) {
			this.name = name;
		}
	}

	public int getStatus() {
		return status;
	}

	public int getIp() {
		return ip;
	}

	public void setIp(int ip) {
		synchronized (c) {
			this.ip = ip;
		}
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		synchronized (c) {
			this.port = port;
		}
	}

	public long getId() {
		return id;
	}

	private void setStatus(int status) {
		synchronized (c) {
			if (status < -1 || status > 1)
				throw new RuntimeException("illegal client status");
			this.status = status;
		}
	}

	public void toLogin(long id) {
		synchronized (c) {
			if (id == -1)
				throw new RuntimeException("illegal id");
			else if (this.id != -1)
				throw new RuntimeException("already logined");
			this.id = id;
			this.closing = false;
			online();
			setStatus(LOGIN);
		}
	}

	public void toLogout() {
		synchronized (c) {
			this.id = -1;
			this.name = null;
			this.status = LOGOUT;
		}
	}

	public boolean isLogin() {
		synchronized (c) {
			return status == LOGIN;
		}
	}

	public boolean isLogout() {
		synchronized (c) {
			return status == LOGOUT;
		}
	}

	@Override
	public String toString() {
		return "ChatClientStatus [id=" + id + ", name=" + name + ", ip=" + ip + ", port=" + port + "]";
	}

	public boolean isClosing() {
		return closing;
	}

	public void toClose() {
		synchronized (c) {
			toLogout();
			this.closing = true;
		}
	}

}
