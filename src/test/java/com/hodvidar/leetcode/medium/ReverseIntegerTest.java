package com.hodvidar.leetcode.medium;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class ReverseIntegerTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0 | 0",
            "1 | 1",
            "12 | 21",
            "123 | 321",
            "1234 | 4321",
            "12345 | 54321",
            "123456 | 654321",
            "1234567 | 7654321",
            "12345678 | 87654321",
            "123456789 | 987654321",
            "1234567890 | 987654321",
            "-1 | -1",
            "-12 | -21",
            "-123 | -321",
            "-1234 | -4321",
            "-12345 | -54321",
            "-123456 | -654321",
            "-1234567 | -7654321",
            "-12345678 | -87654321",
            "-123456789 | -987654321",
            "-1234567890 | -987654321",
            "2147483647 | 0",
            "-2147483648 | 0",
            "2147483646 | 0",
            "2147483640 | 463847412",
            "-2147483640 | -463847412"
    })
    void intToRoman(final int number, final int expected) {
        Assertions.assertThat(ReverseInteger.reverse(number))
                .isEqualTo(expected);
    }
}
