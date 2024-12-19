package com.hodvidar.leetcode.medium;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class LetterCombinationsOfPhoneNumberTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "23 | 9",
            "2 | 3",
            "23456789 | 11664"
    })
    void testLetterCombinations(final String input, final int expected) {
        final List<String> combinations = LetterCombinationsOfPhoneNumber.letterCombinations(input);
        // Number of possible combinations
        assertThat(combinations.size()).isEqualTo(expected);
    }
}
