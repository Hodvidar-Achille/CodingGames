package com.hodvidar.coderbyte.hard;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class BracketCombinationsTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 1",
            "2 | 2",
            "3 | 5",
            "4 | 14",
            "5 | 42",
            "6 | 132",
            "7 | 429",
            "8 | 1430",
            "9 | 4862",
            "10 | 16796"
    })
    void testMedianOfTwoSortedArrays(final int num, final int expected) {
        assertThat(BracketCombinations.bracketCombinations(num)).isEqualTo(expected);
    }
}
