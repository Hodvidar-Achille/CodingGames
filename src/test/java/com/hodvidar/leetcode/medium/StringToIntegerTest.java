package com.hodvidar.leetcode.medium;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class StringToIntegerTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "| 0",
            "42 | 42", // Valid positive integer
            "-42 | -42", // Valid negative integer
            "   42 | 42", // Leading whitespace
            "42abc | 42", // Trailing characters
            "abc42 | 0", // Leading characters
            "2147483648 | " + Integer.MAX_VALUE, // Overflow
            "-2147483649 | " + Integer.MIN_VALUE, // Underflow
            "00042 | 42", // Leading zeros
            "   -42 | -42", // Leading sign
            "000-42 | 0",
            "21474836482147483648 | " + Integer.MAX_VALUE,
            "-21474836482147483648 | " + Integer.MIN_VALUE
    })
    void myAtoi(final String input, final int expected) {
        Assertions.assertThat(StringToInteger.myAtoi(input))
                .isEqualTo(expected);
    }
}
