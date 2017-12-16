package com.chain.part2.demo00.studentsyscli;

/**
 * 
 * Student类，学生类，继承Person类，内部包含Lesson类 <br>
 * 学生信息包含：<i>学号，姓名，年龄，语文成绩，数学成绩，英文成绩</i>
 * 
 * @version 20170108
 * @author Chain
 *
 */
public class Student extends Person implements Comparable<Student> {

	private int id;
	private Lesson le;

	/**
	 * Student类的构造函数
	 * 
	 * @param id
	 *            学生学号，长度必须为5
	 * @param name
	 *            学生姓名，长度范围[1,10]
	 * @param age
	 *            学生年龄，范围[0,99]
	 * @param chinese
	 *            语文成绩，范围[0,100]
	 * @param math
	 *            数学成绩，范围[0,100]
	 * @param english
	 *            英语成绩，范围[0,100]
	 */
	public Student(int id, String name, int age, int chinese, int math, int english) {
		super(name, age);
		setId(id);
		setLesson(chinese, math, english);
	}

	/**
	 * 检查学号是否合法，长度必须是5
	 * 
	 * @param id
	 */
	private void checkId(int id) {
		if (id < 10000 || id > 99999)
			throw new RuntimeException("the length of id must be 5");
	}

	/**
	 * 获得学生的学号
	 * 
	 * @return 学号
	 */
	public int getId() {
		return id;
	}

	/**
	 * 设置学生学号
	 * 
	 * @param id
	 *            学号，长度必须是5
	 */
	public void setId(int id) {
		checkId(id);
		this.id = id;
	}

	/**
	 * 获得学生的课程成绩
	 * 
	 * @return
	 */
	public Lesson getLesson() {
		return le;
	}

	/**
	 * 获得学生的课程成绩，以数组形式返回
	 * 
	 * @return 整型数组，依次为<i>语文，数学，英语各科成绩和总成绩</i>
	 */
	public int[] getScores() {
		return new int[] { le.getChinese(), le.getMath(), le.getEnglish(), le.getSum() };
	}

	/**
	 * 设置学生的课程成绩
	 * 
	 * @param chinese
	 *            语文成绩，范围[0,100]
	 * @param math
	 *            数学成绩，范围[0,100]
	 * @param english
	 *            英语成绩，范围[0,100]
	 */
	public void setLesson(int chinese, int math, int english) {
		// 单例设计模式
		if (le == null)
			this.le = new Lesson(chinese, math, english);
		else {
			le.setChinese(chinese);
			le.setMath(math);
			le.setEnglish(english);
		}
	}

	public Student clone() {
		return new Student(getId(), getName(), getAge(), getLesson().getChinese(), getLesson().getMath(),
				getLesson().getEnglish());
	}

	@Override
	public String toString() {
		return String.valueOf(getId()) + '\t' + getName() + '\t' + getAge() + '\t' + getLesson().toString();
	}

	@Override
	public int hashCode() {
		return getId() + getName().hashCode() + getAge() + getLesson().hashCode();
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Student))
			return false;

		return compareTo((Student) o) == 0 ? true : false;
	}

	@Override
	public int compareTo(Student otherStu) {
		int oid = otherStu.getId();
		String oname = otherStu.getName();
		int oage = otherStu.getAge();
		Lesson ole = otherStu.getLesson();

		if (id == oid)
			return 0;

		if (id == oid && getName().equals(oname) && getAge() == oage && le.equals(ole))
			return 0;

		if (oid != id)
			return id - oid;
		if (!getName().equals(oname))
			return getName().compareTo(oname);
		if (getAge() != oage)
			return getAge() - oage;
		return le.compareTo(ole);
	}
}
