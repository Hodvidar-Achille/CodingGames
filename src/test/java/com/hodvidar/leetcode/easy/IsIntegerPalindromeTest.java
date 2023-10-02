package com.hodvidar.leetcode.easy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IsIntegerPalindromeTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0 | true",
            "1 | true",
            "9 | true",
            "10 | false",
            "11 | true",
            "12 | false",
            "121 | true",
            "122 | false",
            "1110111 | true",
            "-1 | false",
    })
    void longestCommonPrefix(final int number, final boolean expected) {
        Assertions.assertThat(IsIntegerPalindrome.isPalindrome(number))
                .isEqualTo(expected);
    }
}
