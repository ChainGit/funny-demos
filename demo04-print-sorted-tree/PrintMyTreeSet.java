package com.chain.c002;

import java.awt.Button;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * 
 * �����ӡ ���������MyTreeSet
 * 
 * @author Chain
 * @version 20170213
 *
 */
public class PrintMyTreeSet {

	private Frame f;
	private TextField tf;
	private Button btnp, btnr;
	private TextArea ta;

	private String newLine;

	public PrintMyTreeSet() {
		init();
	}

	private void init() {
		newLine = System.getProperty("line.sperator");
		if (newLine == null)
			newLine = "\n";

		f = new Frame("�����ӡ���������");
		f.setBounds(400, 100, 400, 500);
		f.setLayout(new FlowLayout());
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		tf = new TextField(30);
		tf.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				if (keyCode == KeyEvent.VK_ENTER)
					printTree();
				else if (keyCode == KeyEvent.VK_R) {
					e.consume();
					resetTree();
				} else if ((keyCode < KeyEvent.VK_0 || keyCode > KeyEvent.VK_9) && keyCode != KeyEvent.VK_SPACE)
					e.consume();
			}
		});
		f.add(tf);

		btnp = new Button("��ӡ(�س�)");
		btnp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				printTree();
			}
		});
		f.add(btnp);

		btnr = new Button("���(R)");
		btnr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetTree();
			}
		});
		f.add(btnr);

		ta = new TextArea(25, 50);
		ta.setText("֧�ֶ�����ݵ�ͬʱ����,����ʱ���ÿո����." + newLine + "ע��:" + newLine + "1. �ظ����ݽ�������." + newLine
				+ "2. ������������ܳ���2147483647");
		f.add(ta);

		f.setVisible(true);
	}

	public static void main(String[] args) {
		new PrintMyTreeSet();
	}

	private void printTree() {
		String instr = tf.getText();
		String[] sbuf = instr.split(" ");
		MyTreeSet tree = new MyTreeSet();
		for (int i = 0; i < sbuf.length; i++)
			if (sbuf[i].length() < 10)
				tree.add(new MyNode(new Integer(sbuf[i])));

		ta.setText(tree.toString());
	}

	private void resetTree() {
		tf.setText(null);
		ta.setText(null);
	}
}

class MyTreeSet {
	// �洢���ڵ�
	private MyNode rootNode = null;
	// �洢�ڵ�����
	private int num = 0;
	// ���ڴ洢���ĸ߶�
	private int treeDegree = 0;
	// ���ڽ���תΪ��ά����
	private int index = 0;
	// ���ڴ�ӡ��
	private boolean[] info = null;
	// ���ڹ���info
	private int parentNodeNum = 0;
	// ÿһ��Ŀ��,���ڴ�ӡ
	private int[] width = null;

	// ��rootNode�ڵ����,�ҵ�addNode�Ĳ���λ��,������
	public void add(MyNode addNode) {
		if (rootNode == null) {
			rootNode = addNode;
			num++;
			return;
		}

		MyNode currentNode = rootNode;
		int nodeDegree = 0;
		// �������ظ�������һ���ܲ���
		while (true) {
			MyNode nextNode = null;
			if (addNode.data < currentNode.data) {
				nextNode = currentNode.lchild;
				nodeDegree++;
				if (nextNode == null) {
					addNode.parent = currentNode;
					addNode.degree = nodeDegree;
					currentNode.lchild = addNode;
					num++;
					if (treeDegree < nodeDegree)
						treeDegree = nodeDegree;
					return;
				} else
					currentNode = nextNode;
			} else if (addNode.data > currentNode.data) {
				nextNode = currentNode.rchild;
				nodeDegree++;
				if (nextNode == null) {
					addNode.parent = currentNode;
					addNode.degree = nodeDegree;
					currentNode.rchild = addNode;
					num++;
					if (treeDegree < nodeDegree)
						treeDegree = nodeDegree;
					return;
				} else
					currentNode = nextNode;
			} else {
				// ���ǿ������ظ������
				return;
			}
		}
	}

	// �������������תΪ��ά����,ÿ��������Ϣ����Ϊ�ڵ�����,�ڵ����ڵĸ߶�,�ڵ������(����,��,��,����)
	private int[][] toArray() {
		int[][] dat = new int[num][3];
		width = new int[treeDegree + 1];
		postOrder(dat, rootNode);
		return dat;
	}

	// �������
	private void postOrder(int[][] dat, MyNode currentNode) {
		if (currentNode == null)
			return;
		postOrder(dat, currentNode.rchild);
		int data = currentNode.data;
		dat[index][0] = data;
		int currentDegree = currentNode.degree;
		dat[index][1] = currentDegree;
		int dataWidth = getDataWidth(data);
		int oldWidth = width[currentDegree];
		int kind = currentNode.getNodeKind();
		dat[index++][2] = kind;
		if (kind == 1 || kind == 3)
			parentNodeNum++;
		if (oldWidth < dataWidth)
			width[currentDegree] = dataWidth;
		postOrder(dat, currentNode.lchild);
	}

	// ��������Ŀ��
	private int getDataWidth(int data) {
		return String.valueOf(data).length();
	}

	@Override
	public String toString() {
		String newline = System.getProperty("line.sperator");
		String leftStr = "|-";
		String rightStr = "-|";
		if (newline == null)// default Windows
			newline = "\n";
		// System.out.println("degree: " + treeDegree + " node: " + num);
		int[][] dat = toArray();
		// ��ʼ��info,��Ҫ��toArray֮��
		if (info == null)
			info = new boolean[parentNodeNum];
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < num; i++) {
			int tmpData = dat[i][0];
			int tmpDegree = dat[i][1];
			int tmpKind = dat[i][2];
			if (tmpKind < 2) {
				sb.append(tmpData);
				sb.append(rightStr);
				sb.append(newline);
				continue;
			} else {
				int rootWidth = width[0];
				for (int j = 0; j <= rootWidth; j++)
					sb.append('.');
			}
			for (int j = 1; j < tmpDegree; j++) {
				if (info[j - 1])
					sb.append('|');
				else
					sb.append('.');

				int tmpWidth = width[j] + 2;
				for (int k = 0; k < tmpWidth; k++)
					sb.append('.');
			}
			if (tmpKind > 1) {
				sb.append(leftStr);
				sb.append(tmpData);
				int blank = width[tmpDegree] - getDataWidth(tmpData);
				for (int j = 0; j < blank; j++)
					sb.append(' ');
			}
			if (tmpKind == 3)
				sb.append(rightStr);
			if (tmpKind == 1 || tmpKind == 3)
				info[tmpDegree] = true;
			if (tmpKind > 1)
				info[tmpDegree - 1] = !info[tmpDegree - 1];
			sb.append(newline);
		}
		return sb.toString();
	}

}

// Ϊ�˲�������,û�����setters/getters,ֱ��public
class MyNode {
	public int data = -1;
	public int degree = 0;
	public MyNode parent = null;
	public MyNode lchild = null;
	public MyNode rchild = null;

	public MyNode(int data) {
		this.data = data;
	}

	// 0:�����ĵ�
	// 1:���ڵ�
	// 2:�ӽڵ�
	// 3:1+2
	public int getNodeKind() {
		int kind = 0;
		if (parent != null)
			kind = 2;
		if (lchild != null || rchild != null)
			kind += 1;
		return kind;
	}
}
