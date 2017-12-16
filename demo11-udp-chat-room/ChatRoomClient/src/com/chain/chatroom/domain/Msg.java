package com.chain.chatroom.domain;

import com.chain.chatroom.utils.ChatUtils;

public class Msg {

	public static enum Type {
		ECHO,
		// 客户端
		CLIENT_REQUEST_LOGIN, CLIENT_REQUEST_LOGOUT, CLIENT_REQUEST_LIST, CLIENT_REQUEST_GROUP, CLIENT_REQUEST_INPUT,
		// 服务器
		SERVER_RESPONSE_LOGIN, SERVER_RESPONSE_LOGOUT, SERVER_RESPONSE_LIST, SERVER_RESPONSE_GROUP, SERVER_RESPONSE_INPUT,
	};

	private int ip = -1;
	private int port = -1;
	private long id = -1;
	private long time = -1;
	private Type type = Type.ECHO;
	private Object data;

	protected Msg() {
		time = System.currentTimeMillis();
	}

	public int getIp() {
		return ip;
	}

	public Msg setIp(int ip) {
		this.ip = ip;
		return this;
	}

	public int getPort() {
		return port;
	}

	public Msg setPort(int port) {
		this.port = port;
		return this;
	}

	public long getId() {
		return id;
	}

	public Msg setId(long id) {
		this.id = id;
		return this;
	}

	public long getTime() {
		return time;
	}

	public Type getType() {
		return type;
	}

	public Object getData() {
		return data;
	}

	protected Msg setData(Object data) {
		this.data = data;
		return this;
	}

	public static Msg of(Type type) {
		Msg msg = new Msg();
		msg.type = type;
		return msg;
	}

	public static Msg of(Type type, Object data) {
		Msg msg = Msg.of(type);
		msg.data = data;
		return msg;
	}

	public static Msg of(long id, Type type, Object data) {
		Msg msg = Msg.of(type, data);
		msg.id = id;
		return msg;
	}

	public static Msg of(byte[] data) throws Exception {
		return ChatUtils.readFromBytes(data);
	}

	@Override
	public String toString() {
		try {
			return ChatUtils.writeToString(this);
		} catch (Exception e) {
			throw new RuntimeException("msg to string");
		}
	}

	public byte[] toBytes() throws Exception {
		return ChatUtils.writeToBytes(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + (int) (time ^ (time >>> 32));
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Msg other = (Msg) obj;
		if (id != other.id)
			return false;
		if (time != other.time)
			return false;
		if (type != other.type)
			return false;
		return true;
	}

}
