package com.chain.chatroom.domain;

import com.chain.chatroom.domain.Msg.Type;

public class MsgMaker {

	private boolean isServer;

	public MsgMaker(boolean isServer) {
		this.isServer = isServer;
	}

	public Msg login() {
		return isServer ? Msg.of(Type.SERVER_RESPONSE_LOGIN) : Msg.of(Type.CLIENT_REQUEST_LOGIN);
	}

	public Msg login(Object data) {
		return login().setData(data);
	}

	public Msg login(long id, Object data) {
		return login(data).setId(id);
	}

	public Msg logout() {
		return isServer ? Msg.of(Type.SERVER_RESPONSE_LOGOUT) : Msg.of(Type.CLIENT_REQUEST_LOGOUT);
	}

	public Msg logout(Object data) {
		return logout().setData(data);
	}

	public Msg logout(long id, Object data) {
		return logout(data).setId(id);
	}

	public Msg list() {
		return isServer ? Msg.of(Type.SERVER_RESPONSE_LIST) : Msg.of(Type.CLIENT_REQUEST_LIST);
	}

	public Msg list(Object data) {
		return list().setData(data);
	}

	public Msg list(long id, Object data) {
		return list(data).setId(id);
	}

	public Msg group() {
		return isServer ? Msg.of(Type.SERVER_RESPONSE_GROUP) : Msg.of(Type.CLIENT_REQUEST_GROUP);
	}

	public Msg group(Object data) {
		return group().setData(data);
	}

	public Msg group(long id, Object data) {
		return group(data).setId(id);
	}

	public Msg echo() {
		return isServer ? Msg.of(Type.ECHO) : Msg.of(Type.ECHO);
	}

	public Msg echo(Object data) {
		return echo().setData(data);
	}

	public Msg echo(long id, Object data) {
		return echo(data).setId(id);
	}

	public Msg input() {
		return isServer ? Msg.of(Type.SERVER_RESPONSE_INPUT) : Msg.of(Type.CLIENT_REQUEST_INPUT);
	}

	public Msg input(Object data) {
		return input().setData(data);
	}

	public Msg input(long id, Object data) {
		return input(data).setId(id);
	}

}
