package com.chain.part2.demo00.studentsyscli;

import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ѧ����Ϣ����ϵͳ <b>������</b> <br>
 * StuSysTool�ṩ��StuSysData�ķ���,��һЩ��������
 * 
 * @version 20170108
 * @author Chain
 *
 */
public class StuSysTool {

	private static StuSysTool tool;

	private static StuSysData data;
	private static Scanner in;

	private StuSysTool() {

	}

	public static StuSysTool getInstance() {
		checkData();
		if (tool == null)
			tool = new StuSysTool();
		return tool;
	}

	public static void inital() {
		data = StuSysData.getInstance();
		getInstance();
		getScanner();
		data.load();
	}

	private static void checkData() {
		if (data == null)
			throw new RuntimeException("tool must inital first!");
	}

	public void getDataNum() {
		System.out.print("��������(��): " + data.getDataNum());
	}

	private void printSet(TreeSet<Student> tset) {
		if (tset != null) {
			System.out.println("ѧ��\t����\t����\t����\t��ѧ\tӢ��\t�ܷ�");
			for (Student s : tset)
				System.out.println(s.toString());
		}
	}

	public void showData() {
		System.out.println("֧�ֵ���ʾ���ݷ�ʽ:");
		System.out.println("0) ����                1) Ĭ��(ѧ��)    2) ���ܷ� ");
		System.out.println("3) ��ѧ��            4) ������                  5) ������ ");
		System.out.println("6) �����ĳɼ�    7) ����ѧ�ɼ�          8) ��Ӣ��ɼ� ");
		System.out.print("�����뷽ʽ: ");
		int sel = getScanner().nextInt();
		if (sel < 0 || sel > 8)
			System.out.println("����������.");
		procShowData(sel);
	}

	public void addData() {
		System.out.println("������ѧ����Ϣ(����over����): ");
		System.out.println("(��: 10000,zhansan,20,89,91,93)");
		String s = null;
		while (!(s = in.next()).equals("over"))
			data.add(s);
		System.out.println("�������.");
	}

	static enum Match {
		MATCH_SEARCH, MATCH_MODIFY, MATCH_DELETE,
	}

	private void intputMatch(Match mal) {
		System.out.print("������Ҫ");
		switch (mal) {
		case MATCH_SEARCH:
			System.out.print("����");
			break;
		case MATCH_DELETE:
			System.out.print("ɾ��");
			break;
		case MATCH_MODIFY:
			System.out.print("�޸�");
			break;
		}
		System.out.println("������: ������: 1000?,zhan?an,2?,??,,8?)");
		System.out.print("> ");
	}

	private TreeSet<Student> getMatchedStudent() {
		String input = getScanner().next();
		// System.out.println(input);
		String[] spart = input.split(",");
		if (spart.length != 6) {
			System.out.println("�����ʽ����.");
			return null;
		}
		TreeSet<Student> tset = new TreeSet<>();
		for (Iterator<Student> it = data.getIterator(); it.hasNext();)
			tset.add(it.next());
		// ����������������н�������
		TreeSet<Student> set = new TreeSet<>(tset);
		for (int i = 0; i < spart.length; i++) {
			String sp = spart[i];
			if (sp.length() < 1)
				continue;
			if (i != 1)
				sp = sp.replaceAll("[?]", "[0-9]*");
			else
				sp = sp.replaceAll("[?]", "[a-zA-Z]*");
			sp = "^" + sp;
			sp += "$";
			// System.out.println(sp);
			Pattern p = Pattern.compile(sp);
			for (Student s : tset) {
				Matcher m = null;
				switch (i) {
				case 0:
					m = p.matcher(String.valueOf(s.getId()));
					break;
				case 1:
					m = p.matcher(s.getName());
					break;
				case 2:
					m = p.matcher(String.valueOf(s.getAge()));
					break;
				case 3:
					m = p.matcher(String.valueOf(s.getLesson().getChinese()));
					break;
				case 4:
					m = p.matcher(String.valueOf(s.getLesson().getMath()));
					break;
				case 5:
					m = p.matcher(String.valueOf(s.getLesson().getEnglish()));
					break;
				default:
					m = p.matcher(s.toString());
					break;
				}
				if (!m.matches())
					set.remove(s);
			}
		}
		if (set.size() > 0)
			return set;
		else
			return null;
	}

	public void searchData() {
		intputMatch(Match.MATCH_SEARCH);
		searchDataRe();
	}

	private TreeSet<Student> searchDataRe() {
		TreeSet<Student> setp = getMatchedStudent();
		if (setp == null) {
			System.out.println("��ƥ������.");
			return null;
		} else
			System.out.println("���ҽ��:");
		printSet(setp);
		return setp;
	}

	public void deleteData() {
		intputMatch(Match.MATCH_DELETE);
		TreeSet<Student> setp = searchDataRe();
		if (setp != null) {
			String str = null;
			System.out.printf("������Ҫɾ����ѧ��ѧ��[ʣ��%d��](����aȫ��ɾ��,����e����ɾ��): ", setp.size());
			while ((!(str = StuSysTool.getScanner().next().toLowerCase()).equals("e")) && setp.size() != 0) {
				if (str.contains("a")) {
					System.out.print("ȷ��ɾ��ȫ������?(y/n)");
					if (StuSysTool.getScanner().next().toLowerCase().contains("y")) {
						data.deleteAll(setp);
						break;
					} else
						System.out.println("����ȡ��.");
				} else {
					int id = Integer.parseInt(str);
					boolean isFind = false;
					Student s = null;
					for (Iterator<Student> it = setp.iterator(); it.hasNext();) {
						s = it.next();
						if (s.getId() == id) {
							isFind = data.delete(s);
							break;
						}
					}
					if (!isFind)
						System.out.println("δ�ҵ����� " + id + ",ɾ��ʧ��.");
					else {
						System.out.println("ɾ������ " + s.getId() + " �ɹ�.");
						if (s != null)
							setp.remove(s);
					}
				}
				System.out.printf("������Ҫɾ����ѧ��ѧ��[��ʣ%d��]: ", setp.size());
			}
			System.out.println("��������.");
		}
	}

	public void modifyData() {
		System.out.println("ѧ�Ų����޸�,�����޸�ѧ��,����ɾ�����ݺ����������!");
		intputMatch(Match.MATCH_MODIFY);
		TreeSet<Student> setp = searchDataRe();
		if (setp != null) {
			String str = null;
			System.out.print("������Ҫ�޸ĵ�ѧ��ѧ��(����e����): ");
			while (!(str = StuSysTool.getScanner().next().toLowerCase()).equals("e")) {
				int id = Integer.parseInt(str);
				Student s = null;
				boolean isChanged = false;
				for (Iterator<Student> it = setp.iterator(); it.hasNext();) {
					s = it.next();
					if (s.getId() == id) {
						Student sp = s.clone();
						modifyData(s);
						// System.out.println(sp);
						// System.out.println(s);
						if (sp != null && (!sp.toString().equals(s.toString()))) {
							data.delete(sp);
							data.add(s);
							isChanged = true;
						}
						break;
					}
				}
				if (isChanged)
					System.out.println("�޸����� " + s.getId() + " �ɹ�.");
				else
					System.out.println("���� " + id + " δ�޸�.");
				System.out.print("������Ҫ�޸ĵ�ѧ��ѧ��: ");
			}
			System.out.println("��������");
		}
	}

	private void modifyData(Student s) {
		System.out.println("������Ҫ�޸ĵ�ѧ�� " + s.getId() + " ��Ϣ: ");
		System.out.println("1) ����  2) ���� 3)���ĳɼ�  4) ��ѧ�ɼ�  5)Ӣ��ɼ� ");
		System.out.print("> ");
		int sel = in.nextInt();
		StringBuilder selb = new StringBuilder("�������µ�");
		switch (sel) {
		case 1:
			selb.append("����: ");
			break;
		case 2:
			selb.append("����: ");
			break;
		case 3:
			selb.append("���ĳɼ�: ");
			break;
		case 4:
			selb.append("��ѧ�ɼ�: ");
			break;
		case 5:
			selb.append("Ӣ��ɼ�: ");
			break;
		default:
			System.out.println("����������.");
			return;
		}
		if (selb.length() > 5)
			System.out.print(selb.toString());

		String inputs = null;
		int inputi = -1;
		if (sel != 1)
			inputi = in.nextInt();
		else
			inputs = in.next();

		switch (sel) {
		case 1:
			s.setName(inputs);
			break;
		case 2:
			s.setAge(inputi);
			break;
		case 3:
			s.getLesson().setChinese(inputi);
			break;
		case 4:
			s.getLesson().setMath(inputi);
			break;
		case 5:
			s.getLesson().setEnglish(inputi);
			break;
		default:
			System.out.println("����������.");
			return;
		}
	}

	public void exit() {
		data.saveAndExit();
		in.close();
	}

	public void save() {
		data.save();
	}

	private void procShowData(int sel) {
		TreeSet<Student> tset = null;
		StuSysData.CompStudent comp = data.new CompStudent();

		switch (sel) {
		case 0:
			System.out.println("����ȡ��.");
			break;
		case 1:
			tset = new TreeSet<>(comp.new CompByDefault());
			break;
		case 2:
			tset = new TreeSet<>(comp.new CompBySum());
			break;
		case 3:
			tset = new TreeSet<>(comp.new CompById());
			break;
		case 4:
			tset = new TreeSet<>(comp.new CompByName());
			break;
		case 5:
			tset = new TreeSet<>(comp.new CompByAge());
			break;
		case 6:
			tset = new TreeSet<>(comp.new CompByChinese());
			break;
		case 7:
			tset = new TreeSet<>(comp.new CompByMath());
			break;
		case 8:
			tset = new TreeSet<>(comp.new CompByEnglish());
			break;
		default:
			tset = null;
		}

		if (tset != null) {
			for (Iterator<Student> it = data.getIterator(); it.hasNext();)
				tset.add(it.next());

			printSet(tset);
		}
	}

	public static Scanner getScanner() {
		if (in == null)
			in = new Scanner(System.in);
		return in;
	}

}
