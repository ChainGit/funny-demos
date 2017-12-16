package com.chain.easyshop.extra;

public class MyInteger {

	private int value;

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MyInteger [value=").append(value).append("]");
		return builder.toString();
	}

}
