package com.hodvidar.leetcode.medium;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class FourSumTest {

    @Test
    void fourSum() {
        final int[] nums = new int[]{1, 0, -1, 0, -2, 2};
        final int target = 0;
        final List<List<Integer>> expected = List.of(List.of(-2, -1, 1, 2),
                List.of(-2, 0, 0, 2),
                List.of(-1, 0, 0, 1));
        final List<List<Integer>> actual = FourSum.fourSum(nums, target);
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
