package com.hodvidar.codingame.puzzles.easy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class BracketsExtremeEditionTest {

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
            "(){}[] | true",
            "{([][])[]} | true",
            ")}][{( | false",
            "((([[[{{{ | false",
    })
    public void isExpressionOK(final String inputValue, final boolean expected) {
        assertThat(BracketsExtremeEdition.isExpressionOK(inputValue)).isEqualTo(expected);
        assertThat(BracketsExtremeEdition.isExpressionOK2(inputValue)).isEqualTo(expected);
    }
}
