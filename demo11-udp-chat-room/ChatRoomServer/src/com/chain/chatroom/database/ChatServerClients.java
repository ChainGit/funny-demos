package com.chain.chatroom.database;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.chain.chatroom.domain.Client;
import com.chain.chatroom.domain.Msg;

public class ChatServerClients {

	private volatile Map<Long, Client> clients;

	// 已有用户的数量
	private volatile int count;

	private Class<Client> c = Client.class;

	public ChatServerClients() {
		clients = new ConcurrentHashMap<>();
	}

	public Client get(long id) {
		return clients.get(id);
	}

	public Client addClient(Msg msg) {
		int clientIp = msg.getIp();
		int clientPort = msg.getPort();
		String clientName = (String) msg.getData();
		long id = System.currentTimeMillis();
		while (exist(id))
			id = (long) (Math.random() * 10_0000_0000 + ADMIN_ID + 1);
		Client c = new Client(id);
		c.setIp(clientIp);
		if (clientName == null)
			clientName = "client_" + id;
		c.setName(clientName);
		c.setPort(clientPort);
		clients.put(id, c);
		synchronized (c) {
			if (exist(c.getId())) {
				++count;
				return c;
			}
			return null;
		}
	}

	public boolean exist(Long id) {
		return clients.containsKey(id);
	}

	public boolean exist(Client c) {
		return exist(c.getId());
	}

	public Collection<Client> values() {
		return clients.values();
	}

	public List<String> names() {
		synchronized (c) {
			List<String> names = new ArrayList<>();
			for (Client c : values())
				if (!isAdmin(c))
					names.add(c.getName());
			return names;
		}
	}

	public boolean isEmpty() {
		synchronized (c) {
			return count == 0;
		}
	}

	public static final Long ADMIN_ID = 10000L;
	public static final String ADMIN_NAME = "System";

	private volatile Client admin;

	public void refreshAdmin() {
		synchronized (c) {
			if (admin == null) {
				admin = new Client(ADMIN_ID);
				admin.setName(ADMIN_NAME);
				clients.put(ADMIN_ID, admin);
			}
			admin.online();
		}
	}

	public boolean refreshClient(Msg msg) {
		long clientId = msg.getId();
		if (clientId == -1)
			return false;
		if (clientId == ADMIN_ID) {
			refreshAdmin();
			return true;
		}
		int clientIp = msg.getIp();
		int clientPort = msg.getPort();
		Client c = clients.get(clientId);
		if (c != null) {
			c.online();
			if (c.getIp() != clientIp)
				c.setIp(clientIp);
			if (c.getPort() != clientPort)
				c.setPort(clientPort);
			return true;
		}
		return false;
	}

	public Client deleteClient(Client c) {
		return deleteClient(c.getId());
	}

	public Client deleteClient(Long id) {
		if (id == -1 || id == ADMIN_ID)
			return null;
		synchronized (c) {
			Client remove = clients.remove(id);
			if (remove != null) {
				--count;
				return remove;
			}
			return null;
		}
	}

	public boolean isAdmin(Client c) {
		return c.getId() == ADMIN_ID;
	}

	public int size() {
		return count;
	}

}
