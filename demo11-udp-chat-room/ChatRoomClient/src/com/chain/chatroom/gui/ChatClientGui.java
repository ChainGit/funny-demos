package com.chain.chatroom.gui;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.WindowConstants;
import javax.swing.border.LineBorder;

import com.chain.chatroom.register.ChatClientRegister;

public class ChatClientGui {

	public JFrame frame;
	public JTextArea msgTextArea, lstTextArea, inputArea;
	public JButton sendBtn;
	public JLabel msgLabel;

	private ChatClientRegister register;

	public ChatClientGui(ChatClientRegister register) {
		this.register = register;
		buildGui();
		eventGui();
	}

	private void eventGui() {
		sendBtn.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				register.getGuiService().sendInput();
			}
		});

		inputArea.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER)
					register.getGuiService().sendInput();
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!(e.isControlDown() && e.getKeyCode() == KeyEvent.VK_ENTER))
					register.getGuiService().checkInput();
			}

		});

	}

	private static final int WIDTH = 600;
	private static final int HEIGHT = 500;

	// 注意各种set的先后顺序
	private void buildGui() {
		frame = new JFrame();
		frame.setTitle("聊天室客户端");
		frame.setSize(WIDTH, HEIGHT);
		frame.setLayout(null);
		frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setLocationRelativeTo(null);

		LineBorder border = new LineBorder(new java.awt.Color(127, 157, 185), 1, false);

		msgTextArea = new JTextArea();
		msgTextArea.setAutoscrolls(true);
		msgTextArea.setLineWrap(true);
		msgTextArea.setWrapStyleWord(true);
		msgTextArea.setEditable(false);
		msgTextArea.setBorder(border);
		msgTextArea.setSize((int) (WIDTH * 0.7), (int) (HEIGHT * 0.7));
		JScrollPane msgTextAreaPane = new JScrollPane(msgTextArea);
		msgTextAreaPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		msgTextAreaPane.setAutoscrolls(true);
		msgTextAreaPane.setBounds(0, 0, (int) (WIDTH * 0.7), (int) (HEIGHT * 0.7));
		frame.add(msgTextAreaPane);

		lstTextArea = new JTextArea();
		lstTextArea.setEditable(false);
		lstTextArea.setLineWrap(true);
		lstTextArea.setWrapStyleWord(true);
		lstTextArea.setBorder(border);
		lstTextArea.setSize((int) (WIDTH * 0.3), (int) (HEIGHT * 0.7));
		JScrollPane lstTextAreaPane = new JScrollPane(lstTextArea);
		lstTextAreaPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		lstTextAreaPane.setBounds((int) (WIDTH * 0.7), 0, (int) (WIDTH * 0.3), (int) (HEIGHT * 0.7));
		frame.add(lstTextAreaPane);

		inputArea = new JTextArea();
		inputArea.setLineWrap(true);
		inputArea.setWrapStyleWord(true);
		inputArea.setBorder(border);
		inputArea.setSize(WIDTH, (int) (HEIGHT * 0.18));
		JScrollPane inputAreaPane = new JScrollPane(inputArea);
		inputAreaPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		inputAreaPane.setBounds(0, (int) (HEIGHT * 0.7), WIDTH, (int) (HEIGHT * 0.18));
		frame.add(inputAreaPane);

		sendBtn = new JButton("发  送");
		sendBtn.setBorder(border);
		sendBtn.setBounds((int) (WIDTH * 0.83), (int) (HEIGHT * 0.88), (int) (WIDTH * 0.16), (int) (HEIGHT * 0.06));
		frame.add(sendBtn);

		msgLabel = new JLabel();
		msgLabel.setText("欢迎使用");
		msgLabel.setBounds((int) (WIDTH * 0.02), (int) (HEIGHT * 0.88), (int) (WIDTH * 0.6), (int) (HEIGHT * 0.06));
		frame.add(msgLabel);

		frame.setVisible(true);
	}

	public void setMsgLabel(String msg) {
		msgLabel.setText(msg);
	}

}
