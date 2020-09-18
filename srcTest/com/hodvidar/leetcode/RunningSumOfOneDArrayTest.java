package com.hodvidar.leetcode;

import org.junit.Assert;
import org.junit.jupiter.api.Test;


public class RunningSumOfOneDArrayTest {

	@Test
	void should_not_change_an_array_of_one() {
		int[] nums = {1};
		RunningSumOfOneDArray s = new RunningSumOfOneDArray();
		int[] sums = s.runningSum(nums.clone());

		Assert.assertArrayEquals(nums, sums);
	}

	@Test
	void should_change_an_array_of_five() {
		int[] nums = {1, 2, 3, 4, 5};
		RunningSumOfOneDArray s = new RunningSumOfOneDArray();
		int[] sums = s.runningSum(nums.clone());

		int[] expected = {1, 3, 6, 10, 15};

		Assert.assertArrayEquals(expected, sums);
	}
}
