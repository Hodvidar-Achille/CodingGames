package com.hodvidar.leetcode;

import com.hodvidar.leetcode.utils.ListNode;

/**
 * Definition for singly-linked list. public class ListNode { int val; ListNode next; ListNode(int
 * x) { val = x; } }
 */
// https://leetcode.com/problems/add-two-numbers/
public final class AddTwoNumbers {
	public ListNode addTwoNumbers(final ListNode l1, final ListNode l2) {
		final ListNode l3 = addTwoNumbersRecursive(l1, l2, null, false);
		return l3;
	}

	private ListNode addTwoNumbersRecursive(final ListNode l1, final ListNode l2, ListNode l3, boolean previous) {
		// 1) check if finish
		if ((l1 == null && l2 == null)) {
			if (previous)
				l3 = new ListNode(1);
			return l3;
		}

		// 1) add l1 and l2 into l3
		final int n1 = l1 == null ? 0 : l1.val;
		final int n2 = l2 == null ? 0 : l2.val;
		final int[] n3 = getSumOfDigits(n1, n2);
		final int[] n3Bis;
		if (previous) {
			n3Bis = getSumOfDigits(n3[1], 1);
			n3[1] = n3Bis[1];
			if (n3Bis[0] == 1)
				n3[0] = 1;
		}
		l3 = new ListNode(n3[1]);
		previous = (n3[0] == 1);
		final ListNode next1 = l1 == null ? null : l1.next;
		final ListNode next2 = l2 == null ? null : l2.next;
		l3.next = addTwoNumbersRecursive(next1, next2, l3.next, previous);
		return l3;
	}

	/**
	 * returns an array A of int of size 2, A[0] is 0 or 1, A[1] is the unit example: 2 + 2 = 4 ->
	 * [0, 4] / 8 + 7 = 15 -> [1, 5]
	 **/
	private int[] getSumOfDigits(final int a, final int b) {
		if (a > 9 || b > 9)
			throw new IllegalArgumentException("Arguments must each be less than 10");
		final int u = (a + b) % 10;
		final int d = ((a + b) >= 10) ? 1 : 0;
		return new int[] { d, u };
	}

}