package com.chain.chatroom.domain;

public class GroupMsg {

	private GroupMsg() {
		this.time = System.currentTimeMillis();
	}

	private long time;
	private String name;
	private Object content;

	public static GroupMsg of(long time, String name, Object content) {
		GroupMsg msg = new GroupMsg();
		msg.time = time;
		msg.name = name;
		msg.content = content;
		return msg;
	}

	public static GroupMsg of(String name, Object content) {
		GroupMsg msg = new GroupMsg();
		msg.name = name;
		msg.content = content;
		return msg;
	}

	public long getTime() {
		return time;
	}

	public String getName() {
		return name;
	}

	public Object getContent() {
		return content;
	}

}
