package com.hodvidar.leetcode;

import com.hodvidar.leetcode.utils.ListNode;

/**
* Definition for singly-linked list.
* public class ListNode {
*     int val;
*     ListNode next;
*     ListNode(int x) { val = x; }
* }
*/
// https://leetcode.com/problems/add-two-numbers/
public final class AddTwoNumbers
{
	public ListNode addTwoNumbers(ListNode l1, ListNode l2)
	{
		ListNode l3 = addTwoNumbersRecursive(l1, l2, null, false);
		return l3;
	}

	private ListNode addTwoNumbersRecursive(ListNode l1, ListNode l2, ListNode l3, boolean previous)
	{
		// 1) check if finish
		if ((l1 == null && l2 == null))
		{
			if (previous)
				l3 = new ListNode(1);
			return l3;
		}

		// 1) add l1 and l2 into l3
		int n1 = l1 == null ? 0 : l1.val;
		int n2 = l2 == null ? 0 : l2.val;
		int[] n3 = getSumOfDigits(n1, n2);
		int[] n3Bis;
		if (previous)
		{
			n3Bis = getSumOfDigits(n3[1], 1);
			n3[1] = n3Bis[1];
			if (n3Bis[0] == 1)
				n3[0] = 1;
		}
		l3 = new ListNode(n3[1]);
		previous = (n3[0] == 1);
		ListNode next1 = l1 == null ? null : l1.next;
		ListNode next2 = l2 == null ? null : l2.next;
		l3.next = addTwoNumbersRecursive(next1, next2, l3.next, previous);
		return l3;
	}

	/** returns an array A of int of size 2, A[0] is 0 or 1, A[1] is the unit
	 * example: 2 + 2 = 4 -> [0, 4] / 8 + 7 = 15 -> [1, 5]
	**/
	private int[] getSumOfDigits(int a, int b)
	{
		if (a > 9 || b > 9)
			throw new IllegalArgumentException("Arguments must each be less than 10");
		int u = (a + b) % 10;
		int d = ((a + b) >= 10) ? 1 : 0;
		return new int[] { d, u };
	}

}