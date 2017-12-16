package com.chain.part2.demo00.studentsyscli;

/**
 * Lesson类，课程类，包含语文，数学，英语的成绩，<b>自动</b>求出总分
 * 
 * @version 20170108
 * @author Chain
 *
 */
public class Lesson implements Comparable<Lesson> {

	private int chinese;
	private int math;
	private int english;

	private int sum;

	/**
	 * Lesson类的构造函数
	 * 
	 * @param c
	 *            语文成绩，范围[0,100]
	 * @param m
	 *            数学成绩，范围[0,100]
	 * @param e
	 *            英语成绩，范围[0,100]
	 */
	public Lesson(int c, int m, int e) {
		setChinese(c);
		setMath(m);
		setEnglish(e);
	}

	/**
	 * 检查成绩是否合法，范围[0,100]
	 * 
	 * @param x
	 *            成绩
	 */
	private void checkScore(int x) {
		if (x < 0 || x > 100)
			throw new RuntimeException("score should be in [0,100]");
	}

	/**
	 * 获得英语成绩
	 * 
	 * @return 英语成绩
	 */
	public int getEnglish() {
		return english;
	}

	/**
	 * 设置英语成绩
	 * 
	 * @param english
	 *            英语成绩，范围[0,100]
	 */
	public void setEnglish(int english) {
		checkScore(english);
		this.english = english;
		setSum();
	}

	/**
	 * 获得数学成绩
	 * 
	 * @return 数学成绩
	 */
	public int getMath() {
		return math;
	}

	/**
	 * 设置数学成绩
	 * 
	 * @param math
	 *            数学成绩，范围[0,100]
	 */
	public void setMath(int math) {
		checkScore(math);
		this.math = math;
		setSum();
	}

	/**
	 * 获得语文成绩
	 * 
	 * @return 语文成绩
	 */
	public int getChinese() {
		return chinese;
	}

	/**
	 * 设置语文成绩
	 * 
	 * @param chinese
	 *            语文成绩，范围[0,100]
	 */
	public void setChinese(int chinese) {
		checkScore(chinese);
		this.chinese = chinese;
		setSum();
	}

	/**
	 * 获得总分
	 * 
	 * @return 总分
	 */
	public int getSum() {
		return sum;
	}

	/**
	 * 设置总分，自动设置，不可手动设置
	 * 
	 */
	private void setSum() {
		this.sum = getChinese() + getEnglish() + getMath();
	}

	@Override
	public String toString() {
		return String.valueOf(getChinese()) + '\t' + String.valueOf(getMath()) + '\t' + String.valueOf(getEnglish())
				+ '\t' + String.valueOf(getSum());
	}

	@Override
	public int hashCode() {
		return (getChinese() << 4) + (getMath() << 2) + (getEnglish() << 1);
	}

	@Override
	public boolean equals(Object o) {
		if (!(o instanceof Lesson))
			return false;

		return compareTo((Lesson) o) == 0 ? true : false;
	}

	@Override
	public int compareTo(Lesson otherLes) {
		if (getChinese() == otherLes.getChinese() && getMath() == otherLes.getMath()
				&& getEnglish() == otherLes.getEnglish())
			return 0;
		return getSum() - otherLes.getSum();
	}

}
