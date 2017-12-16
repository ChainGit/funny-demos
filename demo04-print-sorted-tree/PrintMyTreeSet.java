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
 * 横向打印 排序二叉树MyTreeSet
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

		f = new Frame("横向打印排序二叉树");
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

		btnp = new Button("打印(回车)");
		btnp.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				printTree();
			}
		});
		f.add(btnp);

		btnr = new Button("清除(R)");
		btnr.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				resetTree();
			}
		});
		f.add(btnr);

		ta = new TextArea(25, 50);
		ta.setText("支持多个数据的同时输入,输入时请用空格隔开." + newLine + "注意:" + newLine + "1. 重复数据将被忽略." + newLine
				+ "2. 输入数据最大不能超过2147483647");
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
	// 存储根节点
	private MyNode rootNode = null;
	// 存储节点数量
	private int num = 0;
	// 用于存储树的高度
	private int treeDegree = 0;
	// 用于将树转为二维数组
	private int index = 0;
	// 用于打印树
	private boolean[] info = null;
	// 用于构建info
	private int parentNodeNum = 0;
	// 每一层的宽度,用于打印
	private int[] width = null;

	// 从rootNode节点出发,找到addNode的插入位置,并插入
	public void add(MyNode addNode) {
		if (rootNode == null) {
			rootNode = addNode;
			num++;
			return;
		}

		MyNode currentNode = rootNode;
		int nodeDegree = 0;
		// 不存在重复数据且一定能插入
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
				// 还是考虑下重复的情况
				return;
			}
		}
	}

	// 将树按后序遍历转为二维数组,每行数组信息依次为节点数据,节点所在的高度,节点的类型(孤立,父,子,均有)
	private int[][] toArray() {
		int[][] dat = new int[num][3];
		width = new int[treeDegree + 1];
		postOrder(dat, rootNode);
		return dat;
	}

	// 后序遍历
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

	// 获得整数的宽度
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
		// 初始化info,需要在toArray之后
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

// 为了操作方便,没有设计setters/getters,直接public
class MyNode {
	public int data = -1;
	public int degree = 0;
	public MyNode parent = null;
	public MyNode lchild = null;
	public MyNode rchild = null;

	public MyNode(int data) {
		this.data = data;
	}

	// 0:孤立的点
	// 1:父节点
	// 2:子节点
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
