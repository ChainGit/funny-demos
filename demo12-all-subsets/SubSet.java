package com.qbq.test01;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class SubSet<T> {

	private Object[] data;

	private int dataLen = -1;

	private long totalNumber = -1;

	private int type = -1;

	private static final int LONG_LEN = 64;

	public SubSet(Collection<T> col) {
		this(col, SubSetType.NORMAL_SUBSET);
	}

	public SubSet(Collection<T> col, SubSetType type) {
		if (col == null || col.size() <= 0) {
			throw new RuntimeException("collection can not be null or empty");
		}
		this.type = type.getType();
		this.data = reverse(col.toArray());
		this.dataLen = data.length;
		calcTotalNumber();
	}

	private void calcTotalNumber() {
		if (data == null || dataLen <= 0) {
			throw new RuntimeException("collection can not be null or empty");
		}
		long expectedNum = p2(dataLen);
		switch (type) {
		case NORMAL_SUBSET:
			this.totalNumber = expectedNum;
			break;
		case NON_EMPTY_SUBSET:
			this.totalNumber = expectedNum - 1;
			break;
		case PROPER_SUBSET:
			this.totalNumber = expectedNum - 1;
			break;
		case NON_EMPTY_AND_PROPER_SUBSET:
			this.totalNumber = expectedNum - 2;
			break;
		default:
			throw new RuntimeException("type is illegal");
		}
		if (totalNumber < 0) {
			throw new RuntimeException("total number is lower than 0");
		}
	}

	private long p2(int n) {
		if (n > LONG_LEN - 1) {
			throw new RuntimeException("collection size can not larger than 63");
		}
		return (long) Math.pow(2, n);
	}

	private Object[] reverse(Object[] array) {
		int arrayLen = array.length;
		int arrayMiddle = arrayLen >> 1;
		for (int idx = 0; idx < arrayMiddle; idx++) {
			Object temp = array[idx];
			array[idx] = array[arrayLen - idx - 1];
			array[arrayLen - idx - 1] = temp;
		}
		return array;
	}

	private T[] get(long index, final boolean ignoreIllegal) {
		if (index < 0) {
			throw new RuntimeException("index can not be lower than 0");
		}
		int[] intArray = new int[LONG_LEN];
		for (int idx = 0; idx < LONG_LEN; idx++) {
			intArray[idx] = (int) ((index >>> (LONG_LEN - idx - 1)) & 0x1);
		}
		for (int idx = 0; idx < LONG_LEN; idx++) {
			if (intArray[idx] != 0 || idx == LONG_LEN - 1) {
				intArray = Arrays.copyOfRange(intArray, idx, LONG_LEN);
				break;
			}
		}
		return get(intArray, ignoreIllegal);
	}

	public T[] get(String index) {
		return get(index, false);
	}

	private T[] get(String index, final boolean ignoreIllegal) {
		if (index == null || index.length() <= 0) {
			throw new RuntimeException("index can not be null or empty");
		}
		int[] bytes = convert2ByteArray(index);
		return get(bytes, ignoreIllegal);
	}

	private T[] get(int[] bytes, final boolean ignoreIllegal) {
		int oneNum = validataAndGetOneNum(bytes, ignoreIllegal);
		if (!ignoreIllegal && oneNum < 0) {
			throw new RuntimeException("type is not allow for this value");
		}
		if (ignoreIllegal && oneNum < 0) {
			return null;
		}
		Object[] sub = new Object[oneNum];
		int bytesLen = bytes.length;
		for (int idx = bytesLen - 1, subIdx = 0; idx > -1; idx--) {
			if (bytes[idx] == 1) {
				if (subIdx >= oneNum) {
					throw new RuntimeException("error process");
				}
				sub[subIdx++] = data[dataLen - bytesLen + idx];
			}
		}
		return (T[]) sub;
	}

	private int validataAndGetOneNum(int[] bytes, final boolean ignoreIllegal) {
		if (bytes == null || bytes.length <= 0) {
			throw new RuntimeException("index can not be null or empty");
		}
		int bytesLen = bytes.length;
		if (bytesLen > dataLen + 1) {
			throw new RuntimeException("index can not be larger than original collection data");
		}
		int oneNum = 0;
		for (int idx = 0; idx < bytesLen; idx++) {
			if (bytes[idx] == 1) {
				++oneNum;
			}
		}
		boolean isOk = true;
		switch (type) {
		case NORMAL_SUBSET:
			break;
		case NON_EMPTY_SUBSET:
			if (oneNum <= 0) {
				isOk = false;
			}
			break;
		case PROPER_SUBSET:
			if (oneNum == dataLen) {
				isOk = false;
			}
			break;
		case NON_EMPTY_AND_PROPER_SUBSET:
			if (oneNum == dataLen || oneNum <= 0) {
				isOk = false;
			}
			break;
		default:
			throw new RuntimeException("type is illegal");
		}
		if (!ignoreIllegal && !isOk) {
			throw new RuntimeException("type is not allow for this value");
		}
		if (ignoreIllegal && !isOk) {
			return -1;
		}
		return oneNum;
	}

	private int[] convert2ByteArray(String index) {
		if (index == null || index.length() <= 0) {
			throw new RuntimeException("index can not be null or empty");
		}
		char[] charArray = index.toCharArray();
		int arrayLen = charArray.length;
		int[] intArray = new int[arrayLen];
		for (int idx = 0; idx < arrayLen; idx++) {
			int element = charArray[idx] - '0';
			if (element < 0 || element > 1) {
				throw new RuntimeException("index is illegal, only can contains 0 or 1");
			}
			intArray[idx] = element;
		}
		return intArray;
	}

	public long getTotalNumber() {
		if (totalNumber < 0) {
			throw new RuntimeException("total number is lower than 0");
		}
		return this.totalNumber;
	}

	public List<List<T>> getAll() {
		List<List<T>> lst = new ArrayList<>();
		long expectedNum = p2(dataLen);
		for (int idx = 0; idx < expectedNum; idx++) {
			T[] subs = get(idx, true);
			if (subs == null) {
				continue;
			}
			lst.add(Arrays.asList(subs));
		}
		if (lst.size() != totalNumber) {
			throw new RuntimeException("error process");
		}
		return lst;
	}

	private static final int NORMAL_SUBSET = 0;
	private static final int NON_EMPTY_SUBSET = 1;
	private static final int PROPER_SUBSET = 2;
	private static final int NON_EMPTY_AND_PROPER_SUBSET = 3;

	public static enum SubSetType {

		/* 子集 */
		NORMAL_SUBSET(SubSet.NORMAL_SUBSET),
		/* 非空子集 */
		NON_EMPTY_SUBSET(SubSet.NON_EMPTY_SUBSET),
		/* 真子集 */
		PROPER_SUBSET(SubSet.PROPER_SUBSET),
		/* 非空真子集 */
		NON_EMPTY_AND_PROPER_SUBSET(SubSet.NON_EMPTY_AND_PROPER_SUBSET);

		private int type;

		private SubSetType(int type) {
			this.type = type;
		}

		public int getType() {
			return this.type;
		}
	}

}
