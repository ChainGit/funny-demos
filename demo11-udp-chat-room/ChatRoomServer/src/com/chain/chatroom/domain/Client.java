package com.chain.chatroom.domain;

import com.chain.chatroom.utils.ChatUtils;

public class Client {

	private volatile int ip = -1;
	private volatile int port = -1;

	private volatile long id = -1;
	private volatile String name;

	// 发出的ECHO信息没有响应的次数
	private volatile int count;

	// 最后一次响应ECHO的时间
	private volatile long last = -1;

	private Class<Client> c = Client.class;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		synchronized (c) {
			this.name = name;
		}
	}

	public void loseAgain() {
		synchronized (c) {
			if (count < MAX_LOSE_TIMES)
				++count;
			else if (count > MAX_LOSE_TIMES)
				offline();
		}
	}

	private static final int MAX_LOSE_TIMES = 3;

	public boolean isOnline() {
		return !isOffline();
	}

	public static final long MAX_LOSE_SECONDS = 10;

	public boolean isOffline() {
		synchronized (c) {
			if (ChatUtils.earlierSeconds(last) >= MAX_LOSE_SECONDS)
				loseAgain();

			return count == MAX_LOSE_TIMES;
		}
	}

	public Client(long id) {
		this.id = id;
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

	public long getLast() {
		return last;
	}

	public void online() {
		synchronized (c) {
			this.count = 0;
			this.last = System.currentTimeMillis();
		}
	}

	public void offline() {
		synchronized (c) {
			this.count = MAX_LOSE_TIMES;
		}
	}

	public long getId() {
		return id;
	}

	@Override
	public String toString() {
		return "Client [ip=" + ip + ", port=" + port + ", id=" + id + ", name=" + name + ", count=" + count + ", last="
				+ last + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Client other = (Client) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
