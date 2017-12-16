import java.awt.Button;
import java.awt.Choice;
import java.awt.FlowLayout;
import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;

public class ConvertNum {

	private static final String[] xxpy = new String[] { "ling", "yi", "er", "san", "si", "wu", "liu", "qi", "ba",
			"jiu" };

	private static final String[] dxpy = new String[] { "LING", "YI", "ER", "SAN", "SI", "WU", "LIU", "QI", "BA",
			"JIU" };

	private static final String[] xxhz = new String[] { "��", "һ", "��", "��", "��", "��", "��", "��", "��", "��" };

	private static final String[] dxsz = new String[] { "��", "Ҽ", "��", "��", "��", "��", "½", "��", "��", "��" };

	private static final String[] xxpydw = { "", "shi", "bai", "qian", "", "wan", "yi" };// ge��""

	private static final String[] dxpydw = { "", "SHI", "BAI", "QIAN", "", "WAN", "YI" };// ge��""

	private static final String[] xxhzdw = { "", "ʮ", "��", "ǧ", "", "��", "��" };// ��λ��""

	private static final String[] dxhzdw = { "", "ʰ", "��", "Ǫ", "", "��", "��" };// 10,11,12,13,14,15,16

	private String[] its = { "��д��ʽ", "Сд��ʽ", "Сдƴ��", "��дƴ��", };

	private static String[] nums = null;
	private static String[] units = null;

	private Frame frm;
	private Button btn;
	private Choice cio;
	private TextField tfdi, tfdo;

	private LinkedList<Byte> dlst;

	public ConvertNum() {
		nums = dxsz;
		units = dxhzdw;

		init();
	}

	private void init() {
		initFrame();
		initFrameEvent();
		initComponents();
		initComponentsEvent();
		frm.setVisible(true);
	}

	private void initComponentsEvent() {
		tfdi.addKeyListener(new KeyAdapter() {

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER)
					procConvert();
			}
		});

		btn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				procConvert();
			}
		});

		cio.addItemListener(new ItemListener() {

			@Override
			public void itemStateChanged(ItemEvent e) {
				String str = (String) e.getItem();
				if (str.equals(its[0])) {
					nums = dxsz;
					units = dxhzdw;
					print(dlst);
				} else if (str.equals(its[1])) {
					nums = xxhz;
					units = xxhzdw;
					print(dlst);
				} else if (str.equals(its[2])) {
					nums = xxpy;
					units = xxpydw;
					print(dlst);
				} else {
					nums = dxpy;
					units = dxpydw;
					print(dlst);
				}
			}

		});
	}

	private void initComponents() {
		tfdi = new TextField(70);
		frm.add(tfdi);

		btn = new Button("ת��");
		frm.add(btn);

		cio = new Choice();
		for (String s : its)
			cio.addItem(s);
		frm.add(cio);

		tfdo = new TextField(65);
		frm.add(tfdo);
	}

	private void initFrameEvent() {
		frm.addWindowListener(new WindowAdapter() {

			@Override
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
	}

	private void initFrame() {
		frm = new Frame("ת���������Ϊ�ı�");
		frm.setLayout(new FlowLayout());
		frm.setBounds(100, 200, 600, 110);
	}

	public static void main(String[] args) {
		new ConvertNum();
	}

	private void procConvert() {
		String input = tfdi.getText();
		if (input == null || input.length() < 1)
			return;

		char[] arr = input.toCharArray();

		arr = preProcess(arr);
		if (arr == null)
			return;

		LinkedList<Byte> dlst = change(arr);

		print(dlst);
	}

	private char[] preProcess(char[] arr) {
		// �жϳ���
		int len = arr.length;
		if (len > 12) {
			tfdo.setText("�������,���֧��12λ!");
			return null;
		}

		// �ж��Ƿ�������
		for (int i = 0; i < len; i++)
			if (arr[i] < 48 || arr[i] > 57) {
				tfdo.setText("�������������!");
				return null;
			}

		// ȥ����ͷ����
		boolean isZeroStart = arr[0] == 48 ? true : false;
		int m = 0;
		for (; isZeroStart && m < len; m++) {
			if (arr[m] == 48)
				continue;
			else
				break;
		}

		// ��������������
		arr = Arrays.copyOfRange(arr, m, len);
		len -= m;

		// ��ӡ����Ԥ����������ֵ
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < len; i++) {
			sb.append(arr[i]);
			if (i < len - 1 && (len - i - 1) % 4 == 0)
				sb.append(",");
		}
		tfdi.setText(sb.toString());
		return arr;
	}

	private LinkedList<Byte> change(char[] arr) {
		// ��������ֵת��Ϊ�м�����
		dlst = new LinkedList<>();
		int last = arr.length - 1;
		for (int i = last; i > -1; i--) {
			int p = last - i;
			if (p % 4 == 0)
				dlst.add((byte) (14 + p / 4));
			int tmp = arr[i] - 48;
			if (tmp != 0)
				dlst.add((byte) (10 + p % 4));
			// �������������
			dlst.add((byte) tmp);
		}

		Collections.reverse(dlst);

		// ȥ��"10"����ʮ����һʮ
		if (dlst.get(0) == 1 && dlst.get(1) == 11)
			dlst.remove(0);

		// �������0�ȱ��-1
		for (int i = 0; i < dlst.size(); i++)
			if (dlst.get(i) == 0) {
				int j = i + 1;
				for (; j < dlst.size() && dlst.get(j) == 0; j++)
					dlst.set(j, (byte) -1);
				if (dlst.get(j) > 13)
					dlst.set(i, (byte) -1);
				i = j;
			}

		// ɾ�����е�-1
		int len = dlst.size();
		boolean isClear = false;
		while (!isClear) {
			isClear = true;
			for (int i = 0; i < len; i++)
				if (dlst.get(i) == -1) {
					dlst.remove(i);
					isClear = false;
					len--;
				}
		}

		// ɾ��û�������""
		len = dlst.size();
		isClear = false;
		while (!isClear) {
			isClear = true;
			for (int i = 0; i < len; i++) {
				int b = dlst.get(i);
				if (b == 10 || b == 14) {
					isClear = false;
					dlst.remove(i);
					len--;
				}
			}
		}

		// ɾ����������һ��
		len = dlst.size();
		for (int i = 0; i < len; i++)
			if (dlst.get(i) == 16 && dlst.get(i + 1) == 15) {
				dlst.remove(i + 1);
				break;
			}

		// ɾ������ͷ
		if (dlst.get(0) == 16 || dlst.get(0) == 15)
			dlst.remove(0);

		// tfdo.setText(dlst);

		return dlst;
	}

	private void print(LinkedList<Byte> arr) {
		if (arr == null)
			return;

		StringBuilder sb = new StringBuilder();

		int last = arr.size() - 1;
		for (int i = 0; i < last; i++) {
			int da = arr.get(i);
			if (da == 10 || da == 14 || da == -1)
				continue;
			else if (da < 10)
				sb.append(nums[da]);
			else
				sb.append(units[da - 10]);
			if (da != 14 || da != 10)
				sb.append(" ");
		}

		int tmp = arr.get(last);
		if (tmp < 10)
			sb.append(nums[tmp]);
		else
			sb.append(units[tmp - 10]);

		tfdo.setText(sb.toString());
	}

}
