package com.chain.part2.demo00.studentsyscli;

/**
 * 
 * Student�࣬ѧ���࣬�̳�Person�࣬�ڲ�����Lesson�� <br>
 * ѧ����Ϣ������<i>ѧ�ţ����������䣬���ĳɼ�����ѧ�ɼ���Ӣ�ĳɼ�</i>
 * 
 * @version 20170108
 * @author Chain
 *
 */
public class Student extends Person implements Comparable<Student> {

	private int id;
	private Lesson le;

	/**
	 * Student��Ĺ��캯��
	 * 
	 * @param id
	 *            ѧ��ѧ�ţ����ȱ���Ϊ5
	 * @param name
	 *            ѧ�����������ȷ�Χ[1,10]
	 * @param age
	 *            ѧ�����䣬��Χ[0,99]
	 * @param chinese
	 *            ���ĳɼ�����Χ[0,100]
	 * @param math
	 *            ��ѧ�ɼ�����Χ[0,100]
	 * @param english
	 *            Ӣ��ɼ�����Χ[0,100]
	 */
	public Student(int id, String name, int age, int chinese, int math, int english) {
		super(name, age);
		setId(id);
		setLesson(chinese, math, english);
	}

	/**
	 * ���ѧ���Ƿ�Ϸ������ȱ�����5
	 * 
	 * @param id
	 */
	private void checkId(int id) {
		if (id < 10000 || id > 99999)
			throw new RuntimeException("the length of id must be 5");
	}

	/**
	 * ���ѧ����ѧ��
	 * 
	 * @return ѧ��
	 */
	public int getId() {
		return id;
	}

	/**
	 * ����ѧ��ѧ��
	 * 
	 * @param id
	 *            ѧ�ţ����ȱ�����5
	 */
	public void setId(int id) {
		checkId(id);
		this.id = id;
	}

	/**
	 * ���ѧ���Ŀγ̳ɼ�
	 * 
	 * @return
	 */
	public Lesson getLesson() {
		return le;
	}

	/**
	 * ���ѧ���Ŀγ̳ɼ�����������ʽ����
	 * 
	 * @return �������飬����Ϊ<i>���ģ���ѧ��Ӣ����Ƴɼ����ܳɼ�</i>
	 */
	public int[] getScores() {
		return new int[] { le.getChinese(), le.getMath(), le.getEnglish(), le.getSum() };
	}

	/**
	 * ����ѧ���Ŀγ̳ɼ�
	 * 
	 * @param chinese
	 *            ���ĳɼ�����Χ[0,100]
	 * @param math
	 *            ��ѧ�ɼ�����Χ[0,100]
	 * @param english
	 *            Ӣ��ɼ�����Χ[0,100]
	 */
	public void setLesson(int chinese, int math, int english) {
		// �������ģʽ
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
