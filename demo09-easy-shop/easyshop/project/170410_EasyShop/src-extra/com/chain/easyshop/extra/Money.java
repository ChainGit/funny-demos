package com.chain.easyshop.extra;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 金额类
 */
public class Money implements Serializable {

	private static final long serialVersionUID = -4084883380962759807L;

	private BigDecimal value;

	public static final int DEFAULT_SCALE = 10;

	private int scale = DEFAULT_SCALE;

	public Money(double value) {
		setValue(value);
	}

	public Money(String value) {
		setValue(value);
	}

	public Money(String value, int scale) {
		setValue(value, scale);
	}

	public Money(double value, int scale) {
		setValue(value, scale);
	}

	/**
	 * 取得BigDecimal的值
	 * 
	 * @return
	 */
	public BigDecimal getValue() {
		return this.value;
	}

	public void setValue(double value, int scale) {
		setValue(value);
		setScale(scale);
	}

	public void setValue(double value) {
		this.value = new BigDecimal(Double.toString(value));
	}

	public void setValue(String value, int scale) {
		setValue(value);
		setScale(scale);
	}

	public void setValue(String value) {
		this.value = new BigDecimal(value);
	}

	/**
	 * 两个double类型的数值相加
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public double add(double v1, double v2) {
		Money a1 = new Money(v1);
		Money a2 = new Money(v2);
		return add(a1, a2);
	}

	/**
	 * 两数相除
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public double div(double v1, double v2) {
		Money a1 = new Money(v1);
		Money a2 = new Money(v2);
		return this.div(a1, a2);
	}

	/**
	 * 相减
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public double sub(double v1, double v2) {
		Money a1 = new Money(v1);
		Money a2 = new Money(v2);
		return this.sub(a1, a2);
	}

	/**
	 * 相乘
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public double mul(double v1, double v2) {
		Money a1 = new Money(v1);
		Money a2 = new Money(v2);
		return this.mul(a1, a2);
	}

	/**
	 * 两个Money类型的数据进行相加
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public double add(Money v1, Money v2) {
		return v1.getValue().add(v2.getValue()).doubleValue();
	}

	/**
	 * 两个Money类型变量相除
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public double div(Money v1, Money v2) {
		if (scale < 0)
			throw new IllegalArgumentException("精度指定错误,请指定一个大于等于0的精度");
		return v1.getValue().divide(v2.getValue(), scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 两数相乘
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public double mul(Money v1, Money v2) {
		return v1.getValue().multiply(v2.getValue()).doubleValue();
	}

	/**
	 * 两数相减
	 * 
	 * @param v1
	 * @param v2
	 * @return
	 */
	public double sub(Money v1, Money v2) {
		return v1.getValue().subtract(v2.getValue()).doubleValue();
	}

	/**
	 * 返回value的浮点数值
	 * 
	 * @return
	 */
	public double doubleValue() {
		return this.getValue().doubleValue();
	}

	public String stringValue() {
		return this.getValue().toPlainString();
	}

	/**
	 * 设置精度
	 * 
	 * @param scale
	 */
	public void setScale(int scale) {
		this.scale = scale;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Money [value=").append(value).append(", scale=").append(scale).append("]");
		return builder.toString();
	}

}
