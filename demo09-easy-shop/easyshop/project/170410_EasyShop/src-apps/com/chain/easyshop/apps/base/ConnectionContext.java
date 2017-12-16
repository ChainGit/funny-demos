package com.chain.easyshop.apps.base;

import java.sql.Connection;

public class ConnectionContext {

	private ConnectionContext() {

	}

	private static ConnectionContext cc = new ConnectionContext();

	private ThreadLocal<Connection> tlc = new ThreadLocal<>();

	public static ConnectionContext getInstance() {
		return cc;
	}

	public void bindConnection(Connection conn) {
		tlc.set(conn);
	}

	public Connection getConnection() {
		return tlc.get();
	}

	public void removeConnection() {
		tlc.remove();
	}
}
