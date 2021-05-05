package com.hodvidar.leetcode;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

public class RunningSumOfOneDArrayTest {

    @Test
    void should_not_change_an_array_of_one() {
        final int[] nums = {1};
        final RunningSumOfOneDArray s = new RunningSumOfOneDArray();
        final int[] sums = s.runningSum(nums.clone());

        // Assert.assertArrayEquals(nums, sums);
        Assertions.assertThat(sums).isEqualTo(nums);
    }

    @Test
    void should_change_an_array_of_five() {
        final int[] nums = {1, 2, 3, 4, 5};
        final RunningSumOfOneDArray s = new RunningSumOfOneDArray();
        final int[] sums = s.runningSum(nums.clone());

        final int[] expected = {1, 3, 6, 10, 15};

        // Assert.assertArrayEquals(expected, sums);
        Assertions.assertThat(sums).isEqualTo(expected);
    }
}
