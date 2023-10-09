package com.hodvidar.leetcode.hard;

import com.hodvidar.utils.list.IntegerArrayConverter;
import com.hodvidar.utils.regex.NumberExtractor;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.converter.ConvertWith;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

// TODO replace tests by one parametrized test
class MedianOfTwoSortedArraysTest {


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[1, 3] | [2] | 2.0",
            "[1, 2] | [3, 4] | 2.5",
            "[0] | [0] | 0.0",
            "[] | [2] | 2.0",
            "[3] | [] | 3.0",
            "[3, 5, 6, 7] | [] | 5.5",
            "[3, 5, 6] | [] | 5",
            "[1, 2, 3, 4, 5] | [11, 12, 13, 14, 15] | 8.0",
            "[11, 12, 13, 14, 15] | [1, 2, 3, 4, 5] | 8.0",
            "[1, 5, 9, 13, 17] | [2, 6, 10, 14, 18] | 9.5",
            "[1, 5, 9, 13, 17] | [2, 6, 10, 14] | 9.0",
            "[0, 0, 0, 0, 0] | [-1, 0, 0, 0, 0, 0, 1] | 0.0",
            "[1, 3] | [2, 7] | 2.5",
            "[0, 0] | [0, 0] | 0.0",
            "[3] | [-2, -1] | -1.0",
            "[2, 3] | [1] | 2.0",
            "[3] | [1, 2] | 2.0",
            "[4] | [1, 2, 3] | 2.5",
            "[4, 5, 6] | [1, 2, 3] | 3.5"
    })
    void testMedianOfTwoSortedArrays(final String numbersArrayStrOne,
                                     final String numbersArrayStrTwo,
                                     final double expected) {
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(
                NumberExtractor.getArray(numbersArrayStrOne),
                NumberExtractor.getArray(numbersArrayStrTwo)))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[1, 3] | [2] | 2.0",
            "[1, 2] | [3, 4] | 2.5",
            "[0] | [0] | 0.0",
            "[] | [2] | 2.0",
            "[3] | [] | 3.0",
            "[3, 5, 6, 7] | [] | 5.5",
            "[3, 5, 6] | [] | 5",
            "[1, 2, 3, 4, 5] | [11, 12, 13, 14, 15] | 8.0",
            "[11, 12, 13, 14, 15] | [1, 2, 3, 4, 5] | 8.0",
            "[1, 5, 9, 13, 17] | [2, 6, 10, 14, 18] | 9.5",
            "[1, 5, 9, 13, 17] | [2, 6, 10, 14] | 9.0",
            "[0, 0, 0, 0, 0] | [-1, 0, 0, 0, 0, 0, 1] | 0.0",
            "[1, 3] | [2, 7] | 2.5",
            "[0, 0] | [0, 0] | 0.0",
            "[3] | [-2, -1] | -1.0",
            "[2, 3] | [1] | 2.0",
            "[3] | [1, 2] | 2.0",
            "[4] | [1, 2, 3] | 2.5",
            "[4, 5, 6] | [1, 2, 3] | 3.5"
    })
    void testMedianOfTwoSortedArrays(@ConvertWith(IntegerArrayConverter.class) final int[] nums1,
                                     @ConvertWith(IntegerArrayConverter.class) final int[] nums2,
                                     final double expected) {
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2))
                .isEqualTo(expected);
    }
}
