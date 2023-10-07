package com.hodvidar.leetcode.medium;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class IntegerToRomanTest {

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
            "MMMCMXCIX | 3999",
            "MCMXLIV | 1944",
            "MMMM | 4000",
            "MMMMM | 5000",
            "MMMMMM | 6000",
            "MMMMMMM | 7000",
            "MMMMMMMM | 8000",
            "MMMMMMMMM | 9000"
    })
    void intToRoman(final String romanNumber, final int number) {
        Assertions.assertThat(IntegerToRoman.intToRoman(number))
                .isEqualTo(romanNumber);
    }

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
            "MMMCMXCIX | 3999",
            "MCMXLIV | 1944",
            "MMMM | 4000",
            "MMMMM | 5000",
            "MMMMMM | 6000",
            "MMMMMMM | 7000",
            "MMMMMMMM | 8000",
            "MMMMMMMMM | 9000"
    })
    void intToRomaOptimized(final String romanNumber, final int number) {
        Assertions.assertThat(IntegerToRoman.intToRomaOptimized(number))
                .isEqualTo(romanNumber);
    }
}
