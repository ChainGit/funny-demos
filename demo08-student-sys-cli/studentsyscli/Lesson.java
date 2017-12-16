package com.chain.part2.demo00.studentsyscli;

/**
 * Lesson�࣬�γ��࣬�������ģ���ѧ��Ӣ��ĳɼ���<b>�Զ�</b>����ܷ�
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
	 * Lesson��Ĺ��캯��
	 * 
	 * @param c
	 *            ���ĳɼ�����Χ[0,100]
	 * @param m
	 *            ��ѧ�ɼ�����Χ[0,100]
	 * @param e
	 *            Ӣ��ɼ�����Χ[0,100]
	 */
	public Lesson(int c, int m, int e) {
		setChinese(c);
		setMath(m);
		setEnglish(e);
	}

	/**
	 * ���ɼ��Ƿ�Ϸ�����Χ[0,100]
	 * 
	 * @param x
	 *            �ɼ�
	 */
	private void checkScore(int x) {
		if (x < 0 || x > 100)
			throw new RuntimeException("score should be in [0,100]");
	}

	/**
	 * ���Ӣ��ɼ�
	 * 
	 * @return Ӣ��ɼ�
	 */
	public int getEnglish() {
		return english;
	}

	/**
	 * ����Ӣ��ɼ�
	 * 
	 * @param english
	 *            Ӣ��ɼ�����Χ[0,100]
	 */
	public void setEnglish(int english) {
		checkScore(english);
		this.english = english;
		setSum();
	}

	/**
	 * �����ѧ�ɼ�
	 * 
	 * @return ��ѧ�ɼ�
	 */
	public int getMath() {
		return math;
	}

	/**
	 * ������ѧ�ɼ�
	 * 
	 * @param math
	 *            ��ѧ�ɼ�����Χ[0,100]
	 */
	public void setMath(int math) {
		checkScore(math);
		this.math = math;
		setSum();
	}

	/**
	 * ������ĳɼ�
	 * 
	 * @return ���ĳɼ�
	 */
	public int getChinese() {
		return chinese;
	}

	/**
	 * �������ĳɼ�
	 * 
	 * @param chinese
	 *            ���ĳɼ�����Χ[0,100]
	 */
	public void setChinese(int chinese) {
		checkScore(chinese);
		this.chinese = chinese;
		setSum();
	}

	/**
	 * ����ܷ�
	 * 
	 * @return �ܷ�
	 */
	public int getSum() {
		return sum;
	}

	/**
	 * �����ܷ֣��Զ����ã������ֶ�����
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
