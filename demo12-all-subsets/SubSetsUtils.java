package com.qbq.test01;

import java.util.ArrayList;
import java.util.List;

public class SubSetsUtils {

	public static <T, E> List<CartesianElement<T, E>> cartesianProducts(SubSet<T> sub1, SubSet<E> sub2) {
		if (sub1 == null || sub2 == null) {
			throw new RuntimeException("collection can not be null or empty");
		}
		List<List<T>> d1 = sub1.getAll();
		List<List<E>> d2 = sub2.getAll();
		long d1Size = d1.size();
		long d2Size = d2.size();
		List<CartesianElement<T, E>> cartesianElements = new ArrayList<>();
		for (int i = 0; i < d1Size; i++) {
			for (int j = 0; j < d2Size; j++) {
				cartesianElements.add(new CartesianElement<>(d1.get(i), d2.get(j)));
			}
		}
		return cartesianElements;
	}

	public static class CartesianElement<T, E> {
		private List<T> left;
		private List<E> right;

		private CartesianElement(List<T> left, List<E> right) {
			this.left = left;
			this.right = right;
		}

		public List<T> getLeft() {
			return left;
		}

		public List<E> getRight() {
			return right;
		}

		@Override
		public String toString() {
			return "[left:" + left + " - right:" + right + "]";
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((left == null) ? 0 : left.hashCode());
			result = prime * result + ((right == null) ? 0 : right.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			CartesianElement other = (CartesianElement) obj;
			if (left == null) {
				if (other.left != null)
					return false;
			} else if (!left.equals(other.left))
				return false;
			if (right == null) {
				if (other.right != null)
					return false;
			} else if (!right.equals(other.right))
				return false;
			return true;
		}

	}

}
