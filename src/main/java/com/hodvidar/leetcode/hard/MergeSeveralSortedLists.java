package com.hodvidar.leetcode.hard;

import com.hodvidar.leetcode.utils.ListNode;
import org.springframework.lang.Nullable;

// https://leetcode.com/problems/merge-k-sorted-lists/
public class MergeSeveralSortedLists {

    public static ListNode mergeKLists(final ListNode[] lists) {
        ListNode resultRoot = null;
        ListNode current = null;

        while (true) {
            final int[] minValueAndIndex = getMinimumValueAndItsIndex(lists);
            if (minValueAndIndex == null)
                break;
            final ListNode newNode = new ListNode(minValueAndIndex[0]);
            if (current == null) {
                resultRoot = newNode;
                current = newNode;
            } else {
                current.next = newNode;
                current = newNode;
            }
            lists[minValueAndIndex[1]] = lists[minValueAndIndex[1]].next;
        }
        return resultRoot;
    }

    @Nullable
    private static int[] getMinimumValueAndItsIndex(final ListNode[] lists) {
        int minValue = Integer.MAX_VALUE;
        int index = -1;
        for (int i = 0; i < lists.length; i++) {
            if (lists[i] == null)
                continue;
            if (lists[i].val < minValue) {
                minValue = lists[i].val;
                index = i;
            }
        }
        if (index == -1)
            return null;
        return new int[]{minValue, index};
    }
}
