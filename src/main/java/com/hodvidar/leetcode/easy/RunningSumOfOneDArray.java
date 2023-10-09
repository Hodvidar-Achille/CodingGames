package com.hodvidar.leetcode.easy;

// https://leetcode.com/problems/running-sum-of-1d-array/
public class RunningSumOfOneDArray {

    public int[] runningSum(final int[] nums) {
        final int[] sums = new int[nums.length];
        sums[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sums[i] = sums[i - 1] + nums[i];
        }
        return sums;
    }

}
