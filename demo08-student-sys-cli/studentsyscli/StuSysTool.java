package com.chain.part2.demo00.studentsyscli;

import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 学生信息管理系统 <b>工具类</b> <br>
 * StuSysTool提供对StuSysData的访问,和一些其他方法
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
		System.out.print("已有数据(条): " + data.getDataNum());
	}

	private void printSet(TreeSet<Student> tset) {
		if (tset != null) {
			System.out.println("学号\t姓名\t年龄\t语文\t数学\t英语\t总分");
			for (Student s : tset)
				System.out.println(s.toString());
		}
	}

	public void showData() {
		System.out.println("支持的显示数据方式:");
		System.out.println("0) 返回                1) 默认(学号)    2) 按总分 ");
		System.out.println("3) 按学号            4) 按姓名                  5) 按年龄 ");
		System.out.println("6) 按语文成绩    7) 按数学成绩          8) 按英语成绩 ");
		System.out.print("请输入方式: ");
		int sel = getScanner().nextInt();
		if (sel < 0 || sel > 8)
			System.out.println("序号输入错误.");
		procShowData(sel);
	}

	public void addData() {
		System.out.println("请输入学生信息(输入over结束): ");
		System.out.println("(例: 10000,zhansan,20,89,91,93)");
		String s = null;
		while (!(s = in.next()).equals("over"))
			data.add(s);
		System.out.println("输入结束.");
	}

	static enum Match {
		MATCH_SEARCH, MATCH_MODIFY, MATCH_DELETE,
	}

	private void intputMatch(Match mal) {
		System.out.print("请输入要");
		switch (mal) {
		case MATCH_SEARCH:
			System.out.print("查找");
			break;
		case MATCH_DELETE:
			System.out.print("删除");
			break;
		case MATCH_MODIFY:
			System.out.print("修改");
			break;
		}
		System.out.println("的数据: （例如: 1000?,zhan?an,2?,??,,8?)");
		System.out.print("> ");
	}

	private TreeSet<Student> getMatchedStudent() {
		String input = getScanner().next();
		// System.out.println(input);
		String[] spart = input.split(",");
		if (spart.length != 6) {
			System.out.println("输入格式错误.");
			return null;
		}
		TreeSet<Student> tset = new TreeSet<>();
		for (Iterator<Student> it = data.getIterator(); it.hasNext();)
			tset.add(it.next());
		// 根据输入的条件进行交集查找
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
			System.out.println("无匹配数据.");
			return null;
		} else
			System.out.println("查找结果:");
		printSet(setp);
		return setp;
	}

	public void deleteData() {
		intputMatch(Match.MATCH_DELETE);
		TreeSet<Student> setp = searchDataRe();
		if (setp != null) {
			String str = null;
			System.out.printf("请输入要删除的学生学号[剩余%d条](输入a全部删除,输入e结束删除): ", setp.size());
			while ((!(str = StuSysTool.getScanner().next().toLowerCase()).equals("e")) && setp.size() != 0) {
				if (str.contains("a")) {
					System.out.print("确认删除全部数据?(y/n)");
					if (StuSysTool.getScanner().next().toLowerCase().contains("y")) {
						data.deleteAll(setp);
						break;
					} else
						System.out.println("操作取消.");
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
						System.out.println("未找到数据 " + id + ",删除失败.");
					else {
						System.out.println("删除数据 " + s.getId() + " 成功.");
						if (s != null)
							setp.remove(s);
					}
				}
				System.out.printf("请输入要删除的学生学号[还剩%d条]: ", setp.size());
			}
			System.out.println("操作结束.");
		}
	}

	public void modifyData() {
		System.out.println("学号不可修改,如需修改学号,请先删除数据后再重新添加!");
		intputMatch(Match.MATCH_MODIFY);
		TreeSet<Student> setp = searchDataRe();
		if (setp != null) {
			String str = null;
			System.out.print("请输入要修改的学生学号(输入e结束): ");
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
					System.out.println("修改数据 " + s.getId() + " 成功.");
				else
					System.out.println("数据 " + id + " 未修改.");
				System.out.print("请输入要修改的学生学号: ");
			}
			System.out.println("操作结束");
		}
	}

	private void modifyData(Student s) {
		System.out.println("请输入要修改的学生 " + s.getId() + " 信息: ");
		System.out.println("1) 姓名  2) 年龄 3)语文成绩  4) 数学成绩  5)英语成绩 ");
		System.out.print("> ");
		int sel = in.nextInt();
		StringBuilder selb = new StringBuilder("请输入新的");
		switch (sel) {
		case 1:
			selb.append("姓名: ");
			break;
		case 2:
			selb.append("年龄: ");
			break;
		case 3:
			selb.append("语文成绩: ");
			break;
		case 4:
			selb.append("数学成绩: ");
			break;
		case 5:
			selb.append("英语成绩: ");
			break;
		default:
			System.out.println("序号输入错误.");
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
			System.out.println("序号输入错误.");
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
			System.out.println("操作取消.");
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
