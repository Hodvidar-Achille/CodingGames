package com.hodvidar.formation.algorithms;

import com.hodvidar.utils.regex.NumberExtractor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class TwoPointersTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[] | []",
            "[1] | [1]",
            "[1, 2] | [2, 1]",
            "[2, 1] | [1, 2]",
            "[1, 1] | [1, 1]",
            "[9, 8, 7, 6, 5, 4, 3, 2, 1] | [1, 2, 3, 4, 5, 6, 7, 8, 9]",
            "[-1, -2, -3, -4, -5, -6, -7, -8, -9] | [-9, -8, -7, -6, -5, -4, -3, -2, -1]",
            "[0, -1 , 1, -2, 2, -30, 30, -100, 100] | [100, -100, 30, -30, 2, -2, 1, -1, 0]",
    })
    void removeDuplicatesBetter(final String numbersArrayStr, final String expectedArrayStr) {
        final int[] nums = NumberExtractor.getArray(numbersArrayStr);
        final int[] reversedArray = TwoPointers.reverseIntArray(nums);
        final int[] expectedArray = NumberExtractor.getArray(expectedArrayStr);
        Assertions.assertThat(reversedArray).isEqualTo(expectedArray);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "babab | true",
            "bababa | false",
            "azerty | false",
            "a | true",
            "aa | true",
            "azertyytreza | true",
            "azertyaytreza | true",
            "azertyytrez | false",
    })
    void longestPalindrome(final String input, final boolean expected) {
        Assertions.assertThat(TwoPointers.isPalindrome(input))
                .isEqualTo(expected);
    }
}
