package com.hodvidar.leetcode.easy;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class RomanToIntTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "I | 1",
            "II | 2",
            "III | 3",
            "IV | 4",
            "V | 5",
            "VI | 6",
            "X | 10",
            "L | 50",
            "C | 100",
            "D | 500",
            "M | 1000",
            "MM | 2000",
            "LVIII | 58",
            "MCMXCIV | 1994",
    })
    void longestCommonPrefix(final String romanNumber, final int expected) {
        Assertions.assertThat(RomanToInt.romanToInt(romanNumber))
                .isEqualTo(expected);
    }
}
