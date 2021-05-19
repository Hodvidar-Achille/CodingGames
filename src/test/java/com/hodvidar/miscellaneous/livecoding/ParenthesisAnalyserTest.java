package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ParenthesisAnalyserTest {


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            " | true",
            "() | true",
            "(()) | true",
            "()() | true",
            "(()()) | true",
            "( | false",
            ") | false",
            "(((())) | false",
            ")()( | false",
            ")))((( | false"
    })
    public void isValid(final String inputValue, final boolean expected) {
        assertThat(ParenthesisAnalyser.isValid(inputValue)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            " | 0",
            "() | 2",
            "(()) | 4",
            "()() | 2",
            "(()()) | 6",
            "( | 0",
            ") | 0",
            "(((())) | 6",
            ")()( | 2",
            ")))((( | 0",
            "))))))((()())())))()))) | 10",
            "()()() | 2",
            "()()()()() | 2",
            ")))))((((()()()()))))(((((()) | 16"
    })
    public void isValid(final String inputValue, final int expected) {
        assertThat(ParenthesisAnalyser.longestValidSubString(inputValue)).isEqualTo(expected);
        assertThat(ParenthesisAnalyser.longestValidSubString2(inputValue)).isEqualTo(expected);
    }
}
