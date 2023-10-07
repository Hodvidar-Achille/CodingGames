package com.hodvidar.leetcode.easy;

public class RemoveDuplicateFromSortedArray {

    public static int removeDuplicates(final int[] nums) {
        int length = 0;
        int currentUniqNumber = Integer.MIN_VALUE;
        for (int i = 0; i < nums.length; i++) {
            int currentNumber = nums[i];
            int j = i + 1;
            while (currentNumber <= currentUniqNumber) {
                if (j == nums.length) {
                    return length;
                }
                nums[i] = nums[j];
                currentNumber = nums[i];
                j++;
            }
            length++;
            currentUniqNumber = currentNumber;
        }
        return length;
    }

    public static int removeDuplicatesBetter(final int[] nums) {
        int j = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[j] = nums[i];
                j++;
            }
        }
        return j;
    }
}
