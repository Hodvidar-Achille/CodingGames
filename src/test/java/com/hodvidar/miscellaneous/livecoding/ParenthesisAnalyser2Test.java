package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class ParenthesisAnalyser2Test {

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "() = true",
            "{} = true",
            "[] = true",
            "{}[]() = true",
            "([{}]) = true",
            "{}((()))[[]] = true",
            "{(([][][]))()} = true",
            "{}} = false",
            "{{} = false",
            "()) = false",
            "()) = false",
            "[]] = false",
            "[[] = false",
            "(((([]))))} = false",
            "(()()(){}{}{}[][][]) = true",
            "(()()(){}{}{}[][][] = false",
    })
    public void isValid(final String input, final boolean expectedResult) {
        assertThat(ParenthesisAnalyser2.isValid(input)).isEqualTo(expectedResult);
    }
}
