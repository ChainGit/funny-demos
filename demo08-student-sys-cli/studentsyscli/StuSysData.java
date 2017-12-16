package com.chain.part2.demo00.studentsyscli;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Comparator;
import java.util.Iterator;
import java.util.TreeSet;

/**
 * 学生信息管理系统 <b>数据类</b> <br>
 * StuSysData内的方法均只能被StuSysTool调用
 * 
 * @version 20170110
 * @author Chain
 *
 */
public class StuSysData {

	private static StuSysData data;
	private TreeSet<Student> set;
	private BufferedWriter bufw;

	// In windows
	private static final String DATA_PATH = "E:\\Special\\Tests\\stusys.dat";
	// private static final String DATA_PATH = "stusys.dat";

	private StuSysData() {
		if (set == null)
			set = new TreeSet<>();
	}

	public static StuSysData getInstance() {
		if (data == null)
			data = new StuSysData();
		return data;
	}

	public Iterator<Student> getIterator() {
		return set.iterator();
	}

	private void createWriter() {
		try {
			bufw = new BufferedWriter(new FileWriter(DATA_PATH));
		} catch (Exception e) {
			e.printStackTrace();
			closeWriter();
		}
	}

	private void closeWriter() {
		try {
			if (bufw != null)
				bufw.close();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	private void loadReader() {
		BufferedReader bufr = null;
		try {
			bufr = new BufferedReader(new FileReader(DATA_PATH));
			String buf = null;
			while ((buf = bufr.readLine()) != null)
				add(getStuByString(buf));
		} catch (Exception e) {
			throw new RuntimeException("load data err");
		} finally {
			try {
				if (bufr != null)
					bufr.close();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	public void load() {
		if (new File(DATA_PATH).exists())
			loadReader();
		createWriter();
	}

	public void save() {
		if (bufw == null)
			return;

		try {
			for (Student s : set) {
				bufw.write(parseStuToString(s));
				bufw.newLine();
			}
			bufw.flush();
			System.out.println("保存数据成功.");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void saveAndExit() {
		try {
			save();
		} finally {
			closeWriter();
			System.out.println("系统已退出.");
		}
	}

	public void add(Student s) {
		if (s != null)
			set.add(s);
	}

	public void add(String s) {
		add(getStuByString(s));
	}

	public boolean delete(Student s) {
		if (set.contains(s))
			return set.remove(s);
		return false;
	}

	public boolean deleteAll(TreeSet<Student> setp) {
		if (setp == null)
			return false;
		for (Student s : setp) {
			if (set.contains(s))
				set.remove(s);
		}
		return true;
	}

	public int getDataNum() {
		return set.size();
	}

	private Student getStuByString(String s) {
		if (s == null)
			throw new RuntimeException("string is null");

		String sp[] = s.split(",");
		if (sp.length < 6)
			return null;

		return new Student(Integer.parseInt(sp[0]), sp[1], Integer.parseInt(sp[2]), Integer.parseInt(sp[3]),
				Integer.parseInt(sp[4]), Integer.parseInt(sp[5]));
	}

	private String parseStuToString(Student s) {
		int[] scores = s.getScores();
		String split = ",";
		return new StringBuilder().append(s.getId()).append(split).append(s.getName()).append(split).append(s.getAge())
				.append(split).append(scores[0]).append(split).append(scores[1]).append(split).append(scores[2])
				.toString();
	}

	class CompStudent {

		class CompById implements Comparator<Student> {

			@Override
			public int compare(Student o1, Student o2) {
				return compareInt(o1.getId(), o2.getId());
			}

		}

		class CompByDefault implements Comparator<Student> {

			@Override
			public int compare(Student o1, Student o2) {
				return o1.compareTo(o2);
			}

		}

		class CompByName implements Comparator<Student> {

			@Override
			public int compare(Student o1, Student o2) {
				String t1 = o1.getName();
				String t2 = o2.getName();
				if (t1.equals(t2))
					return 1;
				else
					return t1.compareTo(t2);
			}

		}

		class CompByAge implements Comparator<Student> {

			@Override
			public int compare(Student o1, Student o2) {
				return compareInt(o1.getAge(), o2.getAge());
			}

		}

		class CompByChinese implements Comparator<Student> {

			@Override
			public int compare(Student o1, Student o2) {
				return compareInt(o2.getLesson().getChinese(), o1.getLesson().getChinese());
			}

		}

		class CompByMath implements Comparator<Student> {

			@Override
			public int compare(Student o1, Student o2) {
				return compareInt(o2.getLesson().getMath(), o1.getLesson().getMath());
			}

		}

		class CompByEnglish implements Comparator<Student> {

			@Override
			public int compare(Student o1, Student o2) {
				return compareInt(o2.getLesson().getEnglish(), o1.getLesson().getEnglish());
			}

		}

		class CompBySum implements Comparator<Student> {

			@Override
			public int compare(Student o1, Student o2) {
				return compareInt(o2.getLesson().getSum(), o1.getLesson().getSum());
			}
		}

		private int compareInt(int t1, int t2) {
			if (t1 == t2)
				return 1;
			else
				return t1 - t2;
		}

	}
}
