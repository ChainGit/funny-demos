package com.chain.chatroom.test;

import org.junit.Test;

import com.chain.chatroom.domain.Msg;
import com.chain.chatroom.domain.Msg.Type;
import com.chain.chatroom.utils.ChatUtils;

public class ChatClientTest {

	@Test
	public void test1() throws Exception {
		Msg msg = Msg.of(Type.ECHO);
		System.out.println(msg);
		byte[] bytes = ChatUtils.writeToBytes(msg);
		System.out.println(new String(bytes));
		msg = ChatUtils.readFromBytes(bytes);
		System.out.println(msg);
	}

	@Test
	public void test2() {
		String sip = "192.168.10.188";
		int iip = ChatUtils.strIp2int(sip);
		System.out.println(iip);
		sip = ChatUtils.intIp2Str(iip);
		System.out.println(sip);
	}

}
