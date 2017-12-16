package com.chain.c001;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.ComponentOrientation;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextArea;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.TextEvent;
import java.awt.event.TextListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;

/**
 * 大数值高精度计算器
 * 
 * 1.支持输入数据最长长度为30
 * 
 * 2.支持连续加减乘除
 * 
 * @author Chain
 * @version 20170125
 *
 */
public class BigNumCalc {

	// 操作标志,不能随意修改
	enum Calcdo {
		ZERO, ONE, TWO, THREE, FOUR, FIVE, SIX, SEVEN, EIGHT, NINE, PERIOD, ADD, SUBTRACT, MULTIPLY, DIVIDE, DELETE, OPPOSITE, CLEAR, EQUALS,
	};

	private Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9, btndot, btnadd, btnsub, btnmul, btndiv,
			btnmin, btncls, btnbak, btnequ;

	private Frame f;
	private TextArea ta;
	private TextField tf;

	private Panel pup, pdown;

	// 调试模式
	private boolean isDebug = false;

	// 输入A
	private byte[] a;
	// 用于加减乘除的中间数组E
	private byte[] e;
	// 输入B
	private byte[] b;
	// 结果C
	private byte[] c;
	// 引用a/b/c/e
	private byte[] x;

	// 当前输入的是A、B
	private boolean which;

	// 输入A是否有输入
	private boolean isArrAChanged;

	// 造作的类型：加0、减1、乘2、除3、无-1
	private byte kind;

	private static String newline = null;

	public BigNumCalc() {
		clearResetAll();
		init();
	}

	private void init() {
		initTextArea();
		initTextAreaContent();
		initTextField();
		initTextFieldContent();
		initButton();
		initButtonContent();
		initPanel();
		initPanelContent();
		initFrame();
		initFrameContent();

		f.setVisible(true);
	}

	private void initTextFieldContent() {
		tf.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});
	}

	private void initTextField() {
		tf = new TextField("0");
		tf.setEditable(false);
		tf.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
	}

	private void initTextArea() {
		ta = new TextArea(null, 20, 30, TextArea.SCROLLBARS_NONE);
		ta.setComponentOrientation(ComponentOrientation.RIGHT_TO_LEFT);
		ta.setEditable(false);
	}

	private void initTextAreaContent() {
		ta.addTextListener(new TextListener() {

			@Override
			public void textValueChanged(TextEvent e) {

			}
		});

		ta.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});
	}

	private void initButton() {
		btn0 = new Button("0");
		btn1 = new Button("1");
		btn2 = new Button("2");
		btn3 = new Button("3");
		btn4 = new Button("4");
		btn5 = new Button("5");
		btn6 = new Button("6");
		btn7 = new Button("7");
		btn8 = new Button("8");
		btn9 = new Button("9");
		btndot = new Button(".");
		btnadd = new Button("+");
		btnsub = new Button("-");
		btnmul = new Button("*");
		btndiv = new Button("/");
		btnmin = new Button("R");
		btncls = new Button("C");
		btnbak = new Button("<");
		btnequ = new Button("=");
	}

	private void initButtonContent() {
		btn0.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btn0.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_0);
			}

		});

		btn1.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btn1.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_1);
			}

		});

		btn2.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btn2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_2);
			}

		});

		btn3.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btn3.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_3);
			}

		});

		btn4.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btn4.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_4);
			}

		});

		btn5.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btn5.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_5);
			}

		});

		btn6.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btn6.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_6);
			}

		});

		btn7.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btn7.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_7);
			}

		});

		btn8.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btn8.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_8);
			}

		});

		btn9.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btn9.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_9);
			}

		});

		btnadd.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btnadd.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_PLUS);
			}

		});

		btnsub.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btnsub.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_MINUS);
			}

		});

		btnmul.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {

				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btnmul.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_MULTIPLY);
			}

		});

		btndiv.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btndiv.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_SLASH);
			}

		});

		btnmin.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btnmin.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_R);
			}

		});

		btnequ.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btnequ.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_EQUALS);
			}

		});

		btncls.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btncls.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_C);
			}

		});

		btnbak.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btnbak.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_BACK_SPACE);
			}

		});

		btndot.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				procKeyOrMousePress(getPressedKeyCode(e));
			}
		});

		btndot.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procKeyOrMousePress(KeyEvent.VK_PERIOD);
			}

		});

	}

	private void initPanelContent() {

	}

	private void initPanel() {
		pup = new Panel(new BorderLayout());
		pdown = new Panel(new GridLayout(4, 5));

		pup.add(ta, BorderLayout.CENTER);
		pup.add(tf, BorderLayout.SOUTH);

		pdown.add(btn7);
		pdown.add(btn8);
		pdown.add(btn9);
		pdown.add(btndiv);
		pdown.add(btnbak);
		pdown.add(btn4);
		pdown.add(btn5);
		pdown.add(btn6);
		pdown.add(btnmul);
		pdown.add(btncls);
		pdown.add(btn1);
		pdown.add(btn2);
		pdown.add(btn3);
		pdown.add(btnsub);
		pdown.add(btnequ);
		pdown.add(btnmin);
		pdown.add(btn0);
		pdown.add(btndot);
		pdown.add(btnadd);

	}

	private void initFrameContent() {
		f.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private void initFrame() {
		f = new Frame("大数值高精度计算器");
		f.setBounds(600, 150, 400, 500);
		f.setLayout(new GridLayout(2, 1));
		f.add(pup);
		f.add(pdown);
	}

	public static void main(String[] args) {
		newline = System.getProperty("line.sperator");
		if (newline == null)
			newline = "\n";
		new BigNumCalc();
	}

	// 处理不同按键组合但具有相同的功能
	private int getPressedKeyCode(KeyEvent e) {
		int k = e.getKeyCode();
		if (e.isShiftDown() && k == KeyEvent.VK_EQUALS)
			return KeyEvent.VK_PLUS;
		else if (e.isShiftDown() && k == KeyEvent.VK_8)
			return KeyEvent.VK_MULTIPLY;
		else if (k == KeyEvent.VK_DELETE)
			return KeyEvent.VK_BACK_SPACE;
		return k;
	}

	private void procKeyOrMousePress(int i) {
		switch (i) {
		case KeyEvent.VK_PLUS:
			procKind(Calcdo.ADD);
			break;
		case KeyEvent.VK_MINUS:
			procKind(Calcdo.SUBTRACT);
			break;
		case KeyEvent.VK_MULTIPLY:
			procKind(Calcdo.MULTIPLY);
			break;
		case KeyEvent.VK_SLASH:
			procKind(Calcdo.DIVIDE);
			break;
		case KeyEvent.VK_C:
			procKind(Calcdo.CLEAR);
			break;
		case KeyEvent.VK_ENTER:
			procKind(Calcdo.EQUALS);
			break;
		case KeyEvent.VK_EQUALS:
			procKind(Calcdo.EQUALS);
			break;
		case KeyEvent.VK_BACK_SPACE:
			procInput(Calcdo.DELETE);
			break;
		case KeyEvent.VK_R:
			procInput(Calcdo.OPPOSITE);
			break;
		case KeyEvent.VK_PERIOD:
			procInput(Calcdo.PERIOD);
			break;
		case KeyEvent.VK_0:
			procInput(Calcdo.ZERO);
			break;
		case KeyEvent.VK_1:
			procInput(Calcdo.ONE);
			break;
		case KeyEvent.VK_2:
			procInput(Calcdo.TWO);
			break;
		case KeyEvent.VK_3:
			procInput(Calcdo.THREE);
			break;
		case KeyEvent.VK_4:
			procInput(Calcdo.FOUR);
			break;
		case KeyEvent.VK_5:
			procInput(Calcdo.FIVE);
			break;
		case KeyEvent.VK_6:
			procInput(Calcdo.SIX);
			break;
		case KeyEvent.VK_7:
			procInput(Calcdo.SEVEN);
			break;
		case KeyEvent.VK_8:
			procInput(Calcdo.EIGHT);
			break;
		case KeyEvent.VK_9:
			procInput(Calcdo.NINE);
			break;
		default:
			break;
		}
	}

	private void procInput(Calcdo c) {
		if (x == a)
			isArrAChanged = true;

		byte input = (byte) c.ordinal();
		if (input == (byte) Calcdo.DELETE.ordinal()) {
			moveArrayHeadOrBack(false);
			showArrayInTextField();
			return;
		} else if (input == (byte) Calcdo.OPPOSITE.ordinal()) {
			x[0] = (byte) (~x[0] & 0x01);
			showArrayInTextField();
			return;
		}

		if (checkArrayIsFull())
			return;

		if (input == (byte) Calcdo.PERIOD.ordinal()) {
			if (!checkIfHasPeriod()) {
				x[1] = 1;
				moveArrayHeadOrBack(true);
				x[x.length - 1] = -1;
			}
		} else {
			moveArrayHeadOrBack(true);
			x[x.length - 1] = input;
		}

		showArrayInTextField();
	}

	// 在TextField里打印数组
	private void showArrayInTextField() {
		StringBuilder sb = new StringBuilder();
		int k = 0;
		if (x[0] != 0)
			sb.append("- ");
		int zelen = getZeroLength();
		for (int i = zelen; i < x.length; i++, k++) {
			if (!checkIfHasPeriod() && k != 0 && k % 3 == 0)
				sb.append(' ');
			if (x[i] != -1)
				sb.append(x[i]);
			else
				sb.append('.');
		}
		if (zelen == x.length)
			sb.append(0);
		tf.setText(sb.toString());
	}

	private int getZeroLength() {
		return getZeroLength(x);
	}

	// 获得数组非零时的下标
	private int getZeroLength(byte[] x) {
		int m = 2;
		for (; m < x.length; m++)
			if (x[m] != 0)
				break;
		return m;
	}

	private void moveArrayHeadOrBack(boolean d, byte[] x) {
		// d为真则整体向前移动一位,d为假则整体向后移动一位
		int len = x.length;
		if (d) {
			for (int i = 2; i < len - 1; i++)
				x[i] = x[i + 1];
			x[len - 1] = 0;
		} else {
			for (int i = len - 1; i > 2; i--)
				x[i] = x[i - 1];
			x[2] = 0;
		}
	}

	private void moveArrayHeadOrBack(boolean d) {
		moveArrayHeadOrBack(d, x);
	}

	private boolean checkArrayIsFull() {
		// 第一位是符号位,第二位记录是否有小数点
		return x[2] != 0 ? true : false;
	}

	private void procKind(Calcdo c) {
		int n = c.ordinal();
		if (n == Calcdo.CLEAR.ordinal()) {
			clearResetAll();
		} else if (n == Calcdo.EQUALS.ordinal()) {
			calcValue();
		} else {
			boolean isApty = isArrayEmpty(a);
			boolean isBpty = isArrayEmpty(b);
			boolean isEpty = isArrayEmpty(e);
			if (isArrAChanged)
				copyArrToE(a);
			if (!isEpty && !isBpty && kind != -1)
				calcValue();
			which = false;
			x = b;
			if (n == Calcdo.ADD.ordinal())
				kind = 0;
			else if (n == Calcdo.SUBTRACT.ordinal())
				kind = 1;
			else if (n == Calcdo.MULTIPLY.ordinal())
				kind = 2;
			else if (n == Calcdo.DIVIDE.ordinal())
				kind = 3;
		}
	}

	private void calcValue() {
		switch (kind) {
		case 0:
			calcAdd();
			break;
		case 1:
			calcSub();
			break;
		case 2:
			calcMul();
			break;
		case 3:
			calcDiv();
			break;
		default:
			return;
		}
		printValueInTextArea();
		copyArrToE(c);
		clearForNext();
	}

	// 复制数组到中间数组E
	private void copyArrToE(byte[] arr) {
		e = Arrays.copyOf(arr, arr.length);
	}

	// 计算除法的预先处理
	private void calcDiv() {
		if ((e[0] == 0 && b[0] == 0) || (e[0] == 1 && b[0] == 1))
			calcPosNumDiv(false);
		else
			calcPosNumDiv(true);
	}

	// 计算乘法的预先处理
	private void calcMul() {
		if ((e[0] == 0 && b[0] == 0) || (e[0] == 1 && b[0] == 1))
			calcPosNumMul(false);
		else
			calcPosNumMul(true);
	}

	// 计算减法的预先处理
	private void calcSub() {
		if (e[0] == 0) {
			if (b[0] == 0) {
				calcPosNumSub(false);
			} else {
				calcPosNumAdd(false);
			}
		} else {
			if (b[0] == 0) {
				calcPosNumAdd(true);
			} else {
				calcPosNumSub(true);
			}
		}
	}

	// 计算加法的预先处理
	private void calcAdd() {
		if (e[0] == 0) {
			if (b[0] == 0) {
				calcPosNumAdd(false);
			} else {
				calcPosNumSub(false);
			}
		} else {
			if (b[0] == 0) {
				calcPosNumSub(true);
			} else {
				calcPosNumAdd(true);
			}
		}
	}

	// 计算非负实数加法
	private void calcPosNumAdd(boolean opp) {
		// 分为三段：空+整数部分+小数部分
		int lene = e.length;
		int lenb = b.length;
		int lenc = c.length;
		int startpose = getZeroLength(e);// zeroprtlene+2
		int startposb = getZeroLength(b);// zeroprtlenb+2
		int periodpose = getThePostionOfPeriod(e);// decprtlene
		int periodposb = getThePostionOfPeriod(b);// decprtlenb
		int intprtlene = lene - startpose - periodpose;
		int intprtlenb = lenb - startposb - periodposb;
		int lenmax = lene > lenb ? lene : lenb;
		int lenmin = lene > lenb ? lenb : lene;
		int startlenmax = startpose > startposb ? startpose : startposb;
		int startlenmin = startpose > startposb ? startposb : startpose;
		int perprtlenmax = periodpose > periodposb ? periodpose : periodposb;
		int perprtlenmin = periodpose > periodposb ? periodposb : periodpose;
		int intprtlenmax = intprtlene > intprtlenb ? intprtlene : intprtlenb;
		int intprtlenmin = intprtlene > intprtlenb ? intprtlenb : intprtlene;
		int r = 0;
		int posc = lenc - 1;
		for (int i = perprtlenmax; i > perprtlenmin; i--, posc--) {
			byte t = perprtlenmax == periodpose ? e[i + lene - periodpose - 1] : b[i + lenb - periodposb - 1];
			if (t == -1)
				break;
			c[posc] = t;
		}
		for (int i = perprtlenmin; i > 1; i--, posc--) {
			byte t1 = e[i + lene - periodpose - 1];
			byte t2 = b[i + lenb - periodposb - 1];
			byte t = (byte) (t1 + t2 + r);
			c[posc] = (byte) (t % 10);
			r = t / 10;
		}
		if (perprtlenmax > 0) {
			c[posc--] = -1;
			c[1] = 1;
		}
		for (int i = intprtlenmax; i > -1; i--, posc--) {
			byte t = 0;
			if (i >= intprtlenmax - intprtlenmin) {
				t = (byte) (r + (intprtlenmax == intprtlene
						? ((i + startpose - 1 > 1 ? e[i + startpose - 1] : 0)
								+ (i + startposb - intprtlenmax + intprtlenmin - 1 > 1
										? b[i + startposb - intprtlenmax + intprtlenmin - 1] : 0))
						: ((i + startposb - 1 > 1 ? b[i + startposb - 1] : 0)
								+ (i + startpose - intprtlenmax + intprtlenmin - 1 > 1
										? e[i + startpose - intprtlenmax + intprtlenmin - 1] : 0))));
			} else
				t = (byte) (r + (intprtlenmax == intprtlene ? (i + startpose - 1 > 1 ? e[i + startpose - 1] : 0)
						: (i + startposb - 1 > 1 ? b[i + startposb - 1] : 0)));
			c[posc] = (byte) (t % 10);
			r = t / 10;
		}
		while (r != 0) {
			c[posc] = (byte) (r % 10);
			r = r / 10;
			posc--;
		}
		if (opp)
			c[0] = (byte) (~c[0] & 0x01);
	}

	// 计算非负实数减法
	private void calcPosNumSub(boolean opp) {
		// 分为三段：空+整数部分+小数部分
		int lene = e.length;
		int lenb = b.length;
		int lenc = c.length;
		int startpose = getZeroLength(e);// zeroprtlene+2
		int startposb = getZeroLength(b);// zeroprtlenb+2
		int periodpose = getThePostionOfPeriod(e);// decprtlene
		int periodposb = getThePostionOfPeriod(b);// decprtlenb
		int intprtlene = lene - startpose - periodpose;
		int intprtlenb = lenb - startposb - periodposb;
		int lenmax = lene > lenb ? lene : lenb;
		int lenmin = lene > lenb ? lenb : lene;
		int startlenmax = startpose > startposb ? startpose : startposb;
		int startlenmin = startpose > startposb ? startposb : startpose;
		int perprtlenmax = periodpose > periodposb ? periodpose : periodposb;
		int perprtlenmin = periodpose > periodposb ? periodposb : periodpose;
		int intprtlenmax = intprtlene > intprtlenb ? intprtlene : intprtlenb;
		int intprtlenmin = intprtlene > intprtlenb ? intprtlenb : intprtlene;
		int posc = lenc - 1;
		byte[] m = null;
		byte[] n = null;
		// 保证被减数大于减数部分
		if (intprtlene == intprtlenb) {
			for (int i = 0; i < intprtlene; i++) {
				byte elee = e[i + startpose];
				byte eleb = b[i + startposb];
				if (elee != eleb) {
					if (elee > eleb) {
						m = Arrays.copyOf(e, lene);
						n = Arrays.copyOf(b, lenb);
					} else if (elee < eleb) {
						m = Arrays.copyOf(b, lenb);
						n = Arrays.copyOf(e, lene);
						opp = !opp;
					}
					break;
				}
			}
			if (m == null && n == null) {
				for (int i = 0; i < perprtlenmin; i++) {
					byte elee = e[i + startpose + intprtlene];
					byte eleb = b[i + startposb + intprtlenb];
					if (elee != eleb) {
						if (elee > eleb) {
							m = Arrays.copyOf(e, lene);
							n = Arrays.copyOf(b, lenb);
						} else if (elee < eleb) {
							m = Arrays.copyOf(b, lenb);
							n = Arrays.copyOf(e, lene);
							opp = !opp;
						}
						break;
					}
				}
			}
			if (m == null && n == null) {
				if (perprtlenmax == periodpose) {
					m = Arrays.copyOf(e, lene);
					n = Arrays.copyOf(b, lenb);
				} else {
					m = Arrays.copyOf(b, lenb);
					n = Arrays.copyOf(e, lene);
					opp = !opp;
				}
			}
		} else if (intprtlene > intprtlenb) {
			m = Arrays.copyOf(e, lene);
			n = Arrays.copyOf(b, lenb);
		} else if (intprtlene < intprtlenb) {
			m = Arrays.copyOf(b, lenb);
			n = Arrays.copyOf(e, lene);
			opp = !opp;
		}
		// 补全小数部分较短的那一个
		int lenm = m.length;
		int lenn = n.length;
		int periodposm = getThePostionOfPeriod(m);
		int periodposn = getThePostionOfPeriod(n);
		int startposm = getZeroLength(m);// zeroprtlenm+2
		int startposn = getZeroLength(n);// zeroprtlenn+2
		int intprtlenm = lenm - startposm - periodposm;
		int intprtlenn = lenn - startposn - periodposn;
		if (perprtlenmax > 0) {
			byte[] tpmin = new byte[perprtlenmax - 1];
			for (int i = 0; i < perprtlenmax - 1; i++)
				if (i < perprtlenmin - 1)
					tpmin[i] = perprtlenmin == periodposn ? n[i + lenn - periodposn + 1] : m[i + lenm - periodposm + 1];
				else
					tpmin[i] = 0;
			for (int i = perprtlenmax - 1; i > 0; i--, posc--) {
				byte t1 = perprtlenmax == periodposm ? m[i + lenm - periodposm] : tpmin[i - 1];
				byte t2 = perprtlenmax == periodposn ? n[i + lenn - periodposn] : tpmin[i - 1];
				byte t = (byte) (t1 - t2);
				if (t < 0) {
					if (i > 1)
						if (perprtlenmax == periodposm)
							m[i - 1 + lenm - periodposm] -= (byte) 1;
						else
							tpmin[i - 2] -= (byte) 1;
					else
						m[lenm - periodposm - 1] -= (byte) 1;
					t1 += (byte) 10;
					t = (byte) (t1 - t2);
				}
				c[posc] = (byte) (t % 10);
			}
			c[posc--] = -1;
			c[1] = 1;
		}
		for (int i = intprtlenmax - 1; i > -1; i--, posc--) {
			byte t1 = m[i + startposm];
			byte t2 = 0;
			if (i > intprtlenmax - intprtlenmin - 1)
				t2 = n[i + startposn - intprtlenmax + intprtlenmin];
			byte t = (byte) (t1 - t2);
			if (t < 0) {
				m[i - 1 + startposm] -= (byte) 1;
				t1 += 10;
				t = (byte) (t1 - t2);
			}
			c[posc] = (byte) (t % 10);
		}
		if (opp)
			c[0] = (byte) (~c[0] & 0x01);
	}

	// 计算非负实数乘法
	private void calcPosNumMul(boolean opp) {
		int lene = e.length;
		int lenb = b.length;
		int lenc = c.length;
		int startpose = getZeroLength(e);// zeroprtlene+2
		int startposb = getZeroLength(b);// zeroprtlenb+2
		int periodpose = getThePostionOfPeriod(e);// decprtlene
		int periodposb = getThePostionOfPeriod(b);// decprtlenb
		int intprtlene = lene - startpose - periodpose;
		int intprtlenb = lenb - startposb - periodposb;
		int lenmax = lene > lenb ? lene : lenb;
		int lenmin = lene > lenb ? lenb : lene;
		int startlenmax = startpose > startposb ? startpose : startposb;
		int startlenmin = startpose > startposb ? startposb : startpose;
		int perprtlenmax = periodpose > periodposb ? periodpose : periodposb;
		int perprtlenmin = periodpose > periodposb ? periodposb : periodpose;
		int intprtlenmax = intprtlene > intprtlenb ? intprtlene : intprtlenb;
		int intprtlenmin = intprtlene > intprtlenb ? intprtlenb : intprtlene;
		int posc = lenc - 1;
		// 忽略小数点进行比较,将大数值放在前面
		int lenenoper = lene - startpose - (periodpose == 0 ? 0 : 1);
		int lenbnoper = lenb - startposb - (periodposb == 0 ? 0 : 1);
		int lenmaxnoper = lenenoper > lenbnoper ? lenenoper : lenbnoper;
		int lenminnoper = lenenoper > lenbnoper ? lenbnoper : lenenoper;
		byte[] te = new byte[lenenoper];
		byte[] tb = new byte[lenbnoper];
		int kk = 0;
		for (int i = kk; i < lenenoper && kk < lene; i++, kk++) {
			byte t = e[kk + startpose];
			if (t == -1)
				if (kk < lene - 1)
					t = e[++kk + startpose];
				else
					break;
			te[i] = t;
		}
		kk = 0;
		for (int i = kk; i < lenbnoper && kk < lenb; i++, kk++) {
			byte t = b[kk + startposb];
			if (t == -1)
				if (kk < lenb - 1)
					t = b[++kk + startposb];
				else
					break;
			tb[i] = t;
		}
		byte[] m = null;
		byte[] n = null;
		if (lenenoper == lenbnoper) {
			int i = startpose;
			for (; i < lenenoper; i++) {
				byte t1 = te[i];
				byte t2 = tb[i];

				if (t1 > t2) {
					m = Arrays.copyOf(te, lenenoper);
					n = Arrays.copyOf(tb, lenbnoper);
					break;
				} else if (t1 < t2) {
					m = Arrays.copyOf(tb, lenbnoper);
					n = Arrays.copyOf(te, lenenoper);
					break;
				}

			}

			if (m == null && n == null) {
				m = Arrays.copyOf(te, lenenoper);
				n = Arrays.copyOf(tb, lenbnoper);
			}
		} else if (lenenoper > lenbnoper) {
			m = Arrays.copyOf(te, lenenoper);
			n = Arrays.copyOf(tb, lenbnoper);
		} else {
			m = Arrays.copyOf(tb, lenbnoper);
			n = Arrays.copyOf(te, lenenoper);
		}
		// 根据乘数和被乘数确定中间存储每次按位乘的结果
		int stolen = lenmaxnoper + (lenminnoper << 1);
		byte[] sto = new byte[stolen];
		for (int i = 0; i < lenminnoper; i++) {
			byte muln = n[lenminnoper - i - 1];
			byte r = 0;
			int k = sto.length - 1;
			for (int j = 0; j < i && k > -1; j++, k--) {
				if (!isArrayEnough(sto)) {
					int oldlen = sto.length;
					sto = doubleArray(sto);
					k += sto.length - oldlen;
				}
				sto[k] = 0;
			}
			for (int j = lenmaxnoper - 1; j > -1 && k > -1; j--, k--) {
				if (!isArrayEnough(sto)) {
					int oldlen = sto.length;
					sto = doubleArray(sto);
					k += sto.length - oldlen;
				}
				byte mulm = m[j];
				byte t = (byte) (mulm * muln + r);
				sto[k] = (byte) (t % 10);
				r = (byte) (t / 10);
			}
			while (r != 0) {
				if (!isArrayEnough(sto)) {
					int oldlen = sto.length;
					sto = doubleArray(sto);
					k += sto.length - oldlen;
				}
				sto[k--] = (byte) (r % 10);
				r /= 10;
			}
			if (!isArrayEnough(c)) {
				int oldlen = c.length;
				c = doubleArray(c);
				posc += c.length - oldlen;
			}
			calcIntNumAdd(sto, c.clone());
			setArrayEmpty(sto);
		}
		// 插入小数点
		int perposum = ((periodpose - 1) > -1 ? (periodpose - 1) : 0) + ((periodposb - 1) > -1 ? (periodposb - 1) : 0);
		if (perposum > 0) {
			c = Arrays.copyOf(c, c.length + 1);
			for (int i = c.length - 1; i > c.length - perposum - 1; i--) {
				if (!isArrayEnough(c)) {
					int oldlen = c.length;
					c = doubleArray(c);
					posc += c.length - oldlen;
				}
				c[i] = c[i - 1];
			}
			c[c.length - perposum - 1] = -1;
			c[1] = 1;
		}
		if (opp)
			c[0] = (byte) (~c[0] & 0x01);
	}

	// 计算正整数加法
	private void calcIntNumAdd(byte[] a, byte[] b) {
		int lena = a.length;
		int lenb = b.length;
		int lenc = c.length;
		int posc = lenc - 1;
		int lenmax = lena > lenb ? lena : lenb;
		int lenmin = lena > lenb ? lenb : lena;
		byte r = 0;
		for (int i = lenmax - 1; i > -1 && posc > -1; i--, posc--) {
			if (!isArrayEnough(c)) {
				int oldlen = c.length;
				c = doubleArray(c);
				posc += c.length - oldlen;
			}
			byte t = 0;
			if (i >= lenmax - lenmin)
				t = (byte) (r + ((byte) ((lenmax == lena ? a[i] : b[i])
						+ (lenmax == lena ? b[i - lenmax + lenmin] : a[i - lenmax + lenmin]))));
			else
				t = (byte) (r + (lenmax == lena ? a[i] : b[i]));
			c[posc] = (byte) (t % 10);
			r = (byte) (t / 10);
		}
		while (r != 0 && posc > -1) {
			if (!isArrayEnough(c)) {
				int oldlen = c.length;
				c = doubleArray(c);
				posc += c.length - oldlen;
			}
			c[posc--] = (byte) (r % 10);
			r /= 10;
		}
	}

	// 计算非负实数除法
	private void calcPosNumDiv(boolean opp) {
		int lene = e.length;
		int lenb = b.length;
		int lenc = c.length;
		int startpose = getZeroLength(e);// zeroprtlene+2
		int startposb = getZeroLength(b);// zeroprtlenb+2
		int periodpose = getThePostionOfPeriod(e);// decprtlene
		int periodposb = getThePostionOfPeriod(b);// decprtlenb
		int intprtlene = lene - startpose - periodpose;
		int intprtlenb = lenb - startposb - periodposb;
		int lenmax = lene > lenb ? lene : lenb;
		int lenmin = lene > lenb ? lenb : lene;
		int startlenmax = startpose > startposb ? startpose : startposb;
		int startlenmin = startpose > startposb ? startposb : startpose;
		int perprtlenmax = periodpose > periodposb ? periodpose : periodposb;
		int perprtlenmin = periodpose > periodposb ? periodposb : periodpose;
		int intprtlenmax = intprtlene > intprtlenb ? intprtlene : intprtlenb;
		int intprtlenmin = intprtlene > intprtlenb ? intprtlenb : intprtlene;
		int posc = lenc - 1;
		// 将数据提取出来,并去除小数点对齐
		byte[] m = new byte[lene - startpose - (periodpose != 0 ? 1 : 0) + (perprtlenmax == periodpose ? 0
				: ((perprtlenmax - 1 > -1 ? perprtlenmax - 1 : 0) - (perprtlenmin - 1 > -1 ? perprtlenmin - 1 : 0)))];
		byte[] n = new byte[lenb - startposb - (periodposb != 0 ? 1 : 0) + (perprtlenmax == periodpose
				? ((perprtlenmax - 1 > -1 ? perprtlenmax - 1 : 0) - (perprtlenmin - 1 > -1 ? perprtlenmin - 1 : 0))
				: 0)];
		for (int i = startpose, j = 0; i < lene; i++)
			if (e[i] != -1)
				m[j++] = e[i];
		for (int i = startposb, j = 0; i < lenb; i++)
			if (b[i] != -1)
				n[j++] = b[i];

		// long为4字节,最大为9223372036854775807
		if (n.length > 18) {
			ta.append(newline);
			ta.append("错误: 被除数过大!");
			ta.append(newline);
			ta.append(newline);
			return;
		}

		// 转化除数为long
		long divn = parseByteArrayToLong(n);
		if (divn == 0) {
			ta.append(newline);
			ta.append("错误: 除数不能为零!");
			ta.append(newline);
			ta.append(newline);
			return;
		}

		// 计算除法
		long divz = 0;
		for (int i = 0; i < m.length && lenc - getZeroLength(c) < 62; i++) {
			long divt = m[i];
			divz = divz * 10 + divt;
			long divx = (long) (divz / divn);
			moveArrayHeadOrBack(true, c);
			c[lenc - 1] = (byte) divx;
			long divy = divx * divn;
			divz = divz - divy;
			if (divz != 0 && i == m.length - 1) {
				m = Arrays.copyOf(m, m.length + 1);
				if (c[1] == 0) {
					moveArrayHeadOrBack(true, c);
					c[lenc - 1] = -1;
					c[1] = 1;
				}
			}
		}

		if (opp)
			c[0] = (byte) (~c[0] & 0x01);
	}

	// 将byte数组存储的数值转化为long
	private long parseByteArrayToLong(byte[] n) {
		long s = 0;
		long b = 1;
		for (int i = n.length - 1; i > -1; i--) {
			s += n[i] * b;
			b *= 10;
		}
		return s;
	}

	// 在TextArea里打印当前计算的结果
	private void printValueInTextArea() {
		int lene = e.length - 2 - getZeroLength(e);
		int lenb = b.length - 2 - getZeroLength(b);
		int lenc = c.length - 2 - getZeroLength(c);
		int t = lene < lenb ? lenb : lene;
		int lemax = lenc > t ? lenc : t;
		int thePosOfPerE = getThePostionOfPeriod(e);
		int lenep = lene - thePosOfPerE;
		int thePosOfPerB = getThePostionOfPeriod(b);
		int lenbp = lenb - thePosOfPerB;
		int thePosOfPerC = getThePostionOfPeriod(c);
		int lencp = lenc - thePosOfPerC;
		int tp = lenep < lenbp ? lenbp : lenep;
		int lemaxp = lencp > tp ? lencp : tp;
		int tpemax = thePosOfPerE < thePosOfPerB ? thePosOfPerB : thePosOfPerE;
		int leemax = thePosOfPerC > tpemax ? thePosOfPerC : tpemax;
		showArrayInTextArea(e, lemaxp);
		showArrayInTextArea(b, lemaxp);
		StringBuilder sb = new StringBuilder();
		int letop = lemaxp + 6 + leemax;
		for (int i = 0; i < letop; i++)
			sb.append('-');
		sb.append(newline);
		ta.append(sb.toString());
		showArrayInTextArea(c, lemaxp);
		ta.append(newline);
	}

	// 在TextArea里打印数组
	private void showArrayInTextArea(byte[] p, int le) {
		StringBuilder sb = new StringBuilder();
		if (p == b) {
			switch (kind) {
			case 0:
				sb.append('+');
				break;
			case 1:
				sb.append('-');
				break;
			case 2:
				sb.append('*');
				break;
			case 3:
				sb.append('/');
				break;
			default:
				sb.append(' ');
				break;
			}
		} else
			sb.append(' ');
		sb.append(' ');
		for (int i = 0; i < le - p.length + 2 + getZeroLength(p) + getThePostionOfPeriod(p); i++)
			sb.append(' ');
		if (p[0] == 0)
			sb.append(' ');
		else
			sb.append('-');
		sb.append(' ');
		boolean isZero = true;
		for (int i = 2; i < p.length; i++) {
			byte t = p[i];
			if (isZero && t != 0)
				isZero = false;
			if (!isZero)
				if (t == -1 && p[1] != 0)
					sb.append('.');
				else
					sb.append(t);
			else if (i == p.length - 1)
				sb.setCharAt(sb.length() - 1, '0');
		}
		ta.append(sb.toString());
		ta.append(newline);

		printArray(p);
	}

	// 获得小数点的位置(从后往前)
	private int getThePostionOfPeriod(byte[] b) {
		for (int i = 2; i < b.length; i++)
			if (b[i] == -1)
				return b.length - i;
		return 0;
	}

	// 检查是否是实数还是整数
	private boolean checkIfHasPeriod() {
		x[1] = 0;
		for (int i = 2; i < x.length; i++)
			if (x[i] == -1)
				x[1] = 1;
		return x[1] == 0 ? false : true;
	}

	// 清空并复位
	private void clearResetAll() {
		clearForNext();
		// 初始化用于连续加减乘除的中间变量数组
		e = new byte[3];
		if (ta != null)
			ta.setText(null);
		if (tf != null)
			tf.setText("0");
		System.gc();
	}

	// 清空相关结果
	private void clearForNext() {
		// 输入A初始化为null
		if (a == null)
			a = new byte[32];
		else
			setArrayEmpty(a);
		// 输入B限定在最长为30位,一位符号位,一位空闲位
		if (b == null)
			b = new byte[32];
		else
			setArrayEmpty(b);
		// 结果C初始限定为64位,包含符号位
		c = new byte[64];
		// 初始输入为A
		which = true;
		// 输入的引用为A
		x = a;
		// 初始为无操作
		kind = -1;
		// 输入A初始化为无输入
		isArrAChanged = false;
	}

	// 清空数组
	private void setArrayEmpty(byte[] a) {
		for (int i = 0; i < a.length; i++)
			a[i] = 0;
	}

	// 数组是否是空的并创建数组
	private boolean isArrayEmpty(byte[] a) {
		if (a == null)
			return true;
		else
			for (int i = 0; i < a.length; i++)
				if (a[i] != 0)
					return false;
		return true;
	}

	// 数组是否空间足够
	private boolean isArrayEnough(byte[] c) {
		return getZeroLength(c) > 10 ? true : false;
	}

	// 扩大数组一倍
	private byte[] doubleArray(byte[] b) {
		int len = b.length;
		byte[] bn = new byte[len << 1];
		bn[0] = b[0];
		bn[1] = b[1];
		for (int i = 2; i < len; i++)
			bn[len + i] = b[i];
		b = null;
		if (bn.length > 256) {
			ta.append(newline);
			ta.append("警告: 当前计算位数已经很大,具有崩溃和误差可能!");
			ta.append(newline);
		}
		System.gc();
		return bn;
	}

	// 打印数组
	private void printArray(byte[] s) {
		if (!isDebug)
			return;
		System.out.println();
		for (int i = 0; i < s.length; i++)
			System.out.print(s[i]);
		System.out.println();
	}

}
