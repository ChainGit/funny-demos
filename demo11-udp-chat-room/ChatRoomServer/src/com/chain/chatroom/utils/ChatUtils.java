package com.chain.chatroom.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.chain.chatroom.domain.Msg;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ChatUtils {

	public static int strIp2int(String sip) {
		String[] ips = sip.split("\\.");
		int ip = 0;
		for (int i = 0; i < ips.length; i++)
			ip += Integer.valueOf(ips[i]) << ((3 - i) << 3);
		return ip;
	}

	public static String intIp2Str(int ip) {
		String[] ips = new String[4];
		for (int i = 0; i < ips.length; i++)
			ips[i] = String.valueOf((ip >>> ((3 - i) << 3)) & 0xff);
		return String.join(".", ips);
	}

	public static byte[] intIp2Bytes(int ip) {
		byte[] bips = new byte[4];
		bips[0] = (byte) ((ip >>> 24) & 0xff);
		bips[1] = (byte) ((ip >>> 16) & 0xff);
		bips[2] = (byte) ((ip >>> 8) & 0xff);
		bips[3] = (byte) (ip & 0xff);
		return bips;
	}

	public static int bytesIp2int(byte[] bips) {
		if (bips == null || bips.length < 4)
			throw new RuntimeException("byte array length should be 4");
		int ip = 0;
		for (int i = 0; i < 4; i++)
			ip += bips[i] << ((3 - i) << 3);
		return ip;
	}

	public static long earlierSeconds(long earlier) {
		long current = System.currentTimeMillis();
		return (current - earlier) / 1000;
	}

	public static String toFormatDataTimeString(long time) {
		return formatter.format(LocalDateTime.ofInstant(new Date(time).toInstant(), ZoneId.systemDefault()));
	}

	private static DateTimeFormatter formatter;

	private static ObjectMapper objectMapper;

	static {
		objectMapper = new ObjectMapper();
		formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");
	}

	public static Msg readFromBytes(byte[] bytes) throws Exception {
		return objectMapper.readValue(bytes, Msg.class);
	}

	public static String writeToString(Msg msg) throws Exception {
		return objectMapper.writeValueAsString(msg);
	}

	public static byte[] writeToBytes(Msg msg) throws Exception {
		return objectMapper.writeValueAsBytes(msg);
	}
}
