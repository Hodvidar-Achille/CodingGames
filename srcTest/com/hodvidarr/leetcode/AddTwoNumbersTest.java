package com.hodvidarr.leetcode;

import com.hodvidarr.leetcode.utils.ListNode;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class AddTwoNumbersTest {
	
	/**
	 * 	Input: (2 -> 4 -> 3) + (5 -> 6 -> 4)
		Output: 7 -> 0 -> 8
		Explanation: 342 + 465 = 807.
	 */
	@Test
	public void test1()
	{
		ListNode l1 = new ListNode(2);
		ListNode l1_2 = new ListNode(4);
		ListNode l1_3 = new ListNode(3);
		
		l1.next = l1_2;
		l1_2.next = l1_3;
		
		ListNode l2 = new ListNode(5);
		ListNode l2_2 = new ListNode(6);
		ListNode l2_3 = new ListNode(4);
		
		l2.next = l2_2;
		l2_2.next = l2_3;

		AddTwoNumbers s = new AddTwoNumbers();
		ListNode result = s.addTwoNumbers(l1, l2);
		
		Assert.assertEquals(7, result.val);
		Assert.assertEquals(0, result.next.val);
		Assert.assertEquals(8, result.next.next.val);
	}
}
