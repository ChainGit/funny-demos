package com.chain.part2.demo00.studentsyscli;

/**
 * Person�࣬�����˵�����������
 * 
 * @version 20170108
 * @author Chain
 *
 */
public class Person {

	private String name;
	private int age;

	/**
	 * Person��Ĺ��캯��
	 * 
	 * @param n
	 *            ���������ȷ�Χ[1,10]
	 * @param a
	 *            ���䣬��Χ[1,100)
	 */
	public Person(String n, int a) {
		setName(n);
		setAge(a);
	}

	/**
	 * ��������Ƿ�Ϸ������ȷ�Χ[1,10]
	 * 
	 * @param n
	 *            ����
	 */
	private void checkName(String n) {
		if (n == null | n.length() < 1 || n.length() > 10)
			throw new RuntimeException("person's name is null or empty��or the length of name is over 10");
	}

	/**
	 * ��������Ƿ�Ϸ�����Χ[1,100)
	 * 
	 * @param a
	 *            ����
	 */
	private void checkAge(int a) {
		if (a < 1 || a > 99)
			throw new RuntimeException("person's age should lager than 0 and smaller than 100");
	}

	/**
	 * �������
	 * 
	 * @return ����
	 */
	public String getName() {
		return name;
	}

	/**
	 * ��������
	 * 
	 * @param name
	 *            ���������ȷ�Χ[1,10]
	 */
	public void setName(String name) {
		checkName(name);
		this.name = name;
	}

	/**
	 * �������
	 * 
	 * @return ����
	 */
	public int getAge() {
		return age;
	}

	/**
	 * ��������
	 * 
	 * @param age
	 *            ���䣬��Χ[1,100)
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
