package com.hodvidar.leetcode.utils;

public class ListNodeUtils {

    public static ListNode getListNode(final int[] nums1) {
        if (nums1.length == 0)
            return null;
        final ListNode listNode = new ListNode(nums1[0]);
        ListNode currentNode = listNode;
        for (int i = 1; i < nums1.length; i++) {
            final ListNode child = new ListNode(nums1[i]);
            currentNode.next = child;
            currentNode = child;
        }
        return listNode;
    }
}
