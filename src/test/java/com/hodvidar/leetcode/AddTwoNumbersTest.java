package com.hodvidar.leetcode;

import com.hodvidar.leetcode.utils.ListNode;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AddTwoNumbersTest {

    /**
     * Input: (2 -> 4 -> 3) + (5 -> 6 -> 4) Output: 7 -> 0 -> 8 Explanation: 342 + 465 = 807.
     */
    @Test
    public void test1() {
        final ListNode l1 = new ListNode(2);
        final ListNode l1_2 = new ListNode(4);
        final ListNode l1_3 = new ListNode(3);

        l1.next = l1_2;
        l1_2.next = l1_3;

        final ListNode l2 = new ListNode(5);
        final ListNode l2_2 = new ListNode(6);
        final ListNode l2_3 = new ListNode(4);

        l2.next = l2_2;
        l2_2.next = l2_3;

        final AddTwoNumbers s = new AddTwoNumbers();
        final ListNode result = s.addTwoNumbers(l1, l2);

        assertThat(result.val).isEqualTo(7);
        assertThat(result.next.val).isEqualTo(0);
        assertThat(result.next.next.val).isEqualTo(8);
    }
}
