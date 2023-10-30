package com.hodvidar.leetcode.medium;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

class FourSumTest {

    @Test
    void fourSum_basic() {
        final int[] nums = new int[]{1, 0, -1, 0, -2, 2};
        final int target = 0;
        final List<List<Integer>> expected = List.of(List.of(-2, -1, 1, 2),
                List.of(-2, 0, 0, 2),
                List.of(-1, 0, 0, 1));
        final List<List<Integer>> actual = FourSum.fourSum(nums, target);
        Assertions.assertThat(actual).isEqualTo(expected);
    }

    @Test
    void fourSum_edgeCase1() {
        final int[] nums = new int[]{1000000000, 1000000000, 1000000000, 1000000000};
        final int target = -294967296;
        final List<List<Integer>> expected = Collections.emptyList();
        final List<List<Integer>> actual = FourSum.fourSum(nums, target);
        Assertions.assertThat(actual).isEqualTo(expected);
    }
}
