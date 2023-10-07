package com.hodvidar.leetcode.easy;

import com.hodvidar.utils.regex.NumberExtractor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RemoveDuplicateFromSortedArrayTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[1, 1, 2] | 2 | [1, 2]",
            "[0, 0, 1, 1, 1, 2, 2, 3, 3, 4] | 5 | [0, 1, 2, 3, 4]",
            "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1] | 1 | [1]",
            "[1, 1, 1, 1, 1, 1, 1, 1, 1, 2] | 2 | [1, 2]"
    })
    void removeDuplicates(final String numbersArrayStr, final int expected, final String expectedArrayStr) {
        final int[] nums = NumberExtractor.getArray(numbersArrayStr);
        final int actualLength = RemoveDuplicateFromSortedArray.removeDuplicates(nums);
        Assertions.assertThat(actualLength).isEqualTo(expected);
        final int[] expectedArray = NumberExtractor.getArray(expectedArrayStr);
        for (int i = 0; i < actualLength; i++) {
            Assertions.assertThat(nums[i]).isEqualTo(expectedArray[i]);
        }
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[1, 1, 2] | 2 | [1, 2]",
            "[0, 0, 1, 1, 1, 2, 2, 3, 3, 4] | 5 | [0, 1, 2, 3, 4]",
            "[1, 1, 1, 1, 1, 1, 1, 1, 1, 1] | 1 | [1]",
            "[1, 1, 1, 1, 1, 1, 1, 1, 1, 2] | 2 | [1, 2]"
    })
    void removeDuplicatesBetter(final String numbersArrayStr, final int expected, final String expectedArrayStr) {
        final int[] nums = NumberExtractor.getArray(numbersArrayStr);
        final int actualLength = RemoveDuplicateFromSortedArray.removeDuplicatesBetter(nums);
        Assertions.assertThat(actualLength).isEqualTo(expected);
        final int[] expectedArray = NumberExtractor.getArray(expectedArrayStr);
        for (int i = 0; i < actualLength; i++) {
            Assertions.assertThat(nums[i]).isEqualTo(expectedArray[i]);
        }
    }
}
