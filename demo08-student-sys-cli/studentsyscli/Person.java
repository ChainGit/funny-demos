package com.chain.part2.demo00.studentsyscli;

/**
 * Person类，包含人的姓名和年龄
 * 
 * @version 20170108
 * @author Chain
 *
 */
public class Person {

	private String name;
	private int age;

	/**
	 * Person类的构造函数
	 * 
	 * @param n
	 *            姓名，长度范围[1,10]
	 * @param a
	 *            年龄，范围[1,100)
	 */
	public Person(String n, int a) {
		setName(n);
		setAge(a);
	}

	/**
	 * 检查姓名是否合法，长度范围[1,10]
	 * 
	 * @param n
	 *            姓名
	 */
	private void checkName(String n) {
		if (n == null | n.length() < 1 || n.length() > 10)
			throw new RuntimeException("person's name is null or empty，or the length of name is over 10");
	}

	/**
	 * 检查年龄是否合法，范围[1,100)
	 * 
	 * @param a
	 *            年龄
	 */
	private void checkAge(int a) {
		if (a < 1 || a > 99)
			throw new RuntimeException("person's age should lager than 0 and smaller than 100");
	}

	/**
	 * 获得姓名
	 * 
	 * @return 姓名
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置姓名
	 * 
	 * @param name
	 *            姓名，长度范围[1,10]
	 */
	public void setName(String name) {
		checkName(name);
		this.name = name;
	}

	/**
	 * 获得年龄
	 * 
	 * @return 年龄
	 */
	public int getAge() {
		return age;
	}

	/**
	 * 设置年龄
	 * 
	 * @param age
	 *            年龄，范围[1,100)
	 */
	public void setAge(int age) {
		checkAge(age);
		this.age = age;
	}

	@Override
	public String toString() {
		return getName() + '-' + String.valueOf(getAge());
	}
}
