package com.qbq.test01;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.qbq.test01.SubSet.SubSetType;
import com.qbq.test01.SubSetsUtils.CartesianElement;

public class MainTest {

	public static void main(String[] args) {
		List<String> lst1 = new ArrayList<>();
		lst1.add("满1000减200");
		lst1.add("满500减100");

		SubSet<String> subSet1 = new SubSet<>(lst1, SubSetType.NORMAL_SUBSET);
		System.out.println(subSet1.getTotalNumber());
		System.out.println(subSet1.getAll());

		List<String> lst2 = new ArrayList<>();
		lst2.add("满10件商品再打8折");
		lst2.add("满5件商品再打9折");

		SubSet<String> subSet2 = new SubSet<>(lst2, SubSetType.NORMAL_SUBSET);
		System.out.println(subSet2.getTotalNumber());
		System.out.println(subSet2.getAll());

		List<CartesianElement<String, String>> cartesianProducts = SubSetsUtils.cartesianProducts(subSet1, subSet2);
		System.out.println(cartesianProducts.size());
		System.out.println(cartesianProducts);

		List<List<CartesianElement<String, String>>> doubleProducts = new ArrayList<>();
		for (CartesianElement<String, String> e : cartesianProducts) {
			List<String> left = e.getLeft();
			List<String> right = e.getRight();
			if (left.size() <= 0 || right.size() <= 0) {
				continue;
			}
			doubleProducts.add(SubSetsUtils.cartesianProducts(new SubSet<>(left, SubSetType.NORMAL_SUBSET),
					new SubSet<>(right, SubSetType.NORMAL_SUBSET)));
		}

		Set<CartesianElement<String, String>> sets = new HashSet<>();
		for (List<CartesianElement<String, String>> lst : doubleProducts) {
			for (CartesianElement<String, String> ele : lst) {
				sets.add(ele);
			}
		}
		System.out.println(sets.size());
		System.out.println("------------------");
		for (CartesianElement<String, String> ele : sets) {
			System.out.println(ele.getLeft() + " + " + ele.getRight());
		}
		System.out.println("------------------");
	}

}
