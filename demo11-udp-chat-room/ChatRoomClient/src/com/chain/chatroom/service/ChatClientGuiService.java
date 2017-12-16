package com.chain.chatroom.service;

import java.util.List;

import javax.swing.JOptionPane;

import com.chain.chatroom.domain.GroupMsg;
import com.chain.chatroom.gui.ChatClientGui;
import com.chain.chatroom.register.ChatClientRegister;
import com.chain.chatroom.utils.ChatUtils;

public class ChatClientGuiService {

	// private static Logger logger =
	// LoggerFactory.getLogger(ChatClientGuiService.class);

	private ChatClientGui gui;

	private volatile ChatClientRegister register;

	public ChatClientGuiService(ChatClientGui gui, ChatClientRegister register) {
		this.gui = gui;
		this.register = register;
	}

	public void init() {
		gui.inputArea.setText("");
		gui.lstTextArea.setText("");
		gui.msgTextArea.setText("");
		gui.inputArea.setEnabled(false);
		gui.sendBtn.setEnabled(false);
		gui.setMsgLabel("正在初始化...");
	}

	public void start() {
		gui.inputArea.setEnabled(false);
		gui.sendBtn.setEnabled(false);
		gui.setMsgLabel("正在登陆到服务器...");
	}

	public void ready() {
		gui.inputArea.setEnabled(true);
		gui.sendBtn.setEnabled(true);
		gui.setMsgLabel("登陆服务器成功...");
		resetInput();
	}

	private static final String NL = System.getProperty("line.separator");

	public void recvMsg() {
		gui.setMsgLabel("成功接受消息...");
	}

	public void sendMsg() {
		gui.setMsgLabel("成功发送消息...");
	}

	public void disable() {
		gui.inputArea.setEnabled(false);
		gui.sendBtn.setEnabled(false);
		gui.setMsgLabel("客户端暂时不可用...");
	}

	public void errorDialog(String content) {
		JOptionPane.showMessageDialog(gui.frame, content == null ? "error" : content, "Error",
				JOptionPane.ERROR_MESSAGE);
	}

	public void massageDialog(String content) {
		JOptionPane.showMessageDialog(gui.frame, content == null ? "massage" : content, "Massage",
				JOptionPane.INFORMATION_MESSAGE);
	}

	public void addGroupMsg(GroupMsg groupMsg) {
		gui.msgTextArea.append(ChatUtils.toFormatDataTimeString(groupMsg.getTime()) + " " + groupMsg.getName() + ":"
				+ NL + groupMsg.getContent() + NL);
		gui.msgTextArea.setCaretPosition(gui.msgTextArea.getDocument().getLength());
		gui.frame.setVisible(true);
	}

	public void list(List<?> list) {
		StringBuffer sb = new StringBuffer();
		for (Object s : list) {
			sb.append(s);
			sb.append(NL);
		}
		gui.lstTextArea.setText(sb.toString());
	}

	private volatile int headLength;

	public void sendInput() {
		String input = gui.inputArea.getText();
		if (input.length() > headLength) {
			register.getServer().commit(register.getMsgMaker().input(input.substring(headLength)));
			resetInput();
		}
	}

	public void checkInput() {
		int inputlength = gui.inputArea.getText().length();
		if (inputlength < headLength)
			resetInput();
	}

	private void resetInput() {
		String headStr = register.getChatClientStatus().getName() + ": ";
		headLength = headStr.length();
		gui.inputArea.setText(headStr);
	}

}
