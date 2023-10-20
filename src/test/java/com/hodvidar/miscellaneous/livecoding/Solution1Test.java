package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution1Test {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "abcdee | e",
            "aaaaaaazzzzzzz | a"
    })
    void getResult(final String input, final String expectedResult) {
        assertThat(Solution1.getResult(input)).isEqualTo(expectedResult);
    }

    @Test
    @Disabled
    void test() {
        final char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase().toCharArray();
        for (final int i : alphabet) {
            System.out.println("" + i);
        }
    }
}
