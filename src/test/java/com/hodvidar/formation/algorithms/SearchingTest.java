package com.hodvidar.formation.algorithms;

import com.hodvidar.utils.regex.NumberExtractor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SearchingTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[] | 1 | -1",
            "[1] | 1 | 0",
            "[1, 2] | 1 | 0",
            "[1, 2] | 2 | 1",
            "[1, 2, 3, 4, 6, 7, 8, 9, 10] | 1 | 0",
            "[1, 2, 3, 4, 6, 7, 8, 9, 10] | 7 | 5",
            "[1, 654, 7689, 9000, 100000] | 9000 | 3"
    })
    void binarySearch(final String numbersArrayStr, final int value, final int expected) {
        Assertions.assertThat(Searching.binarySearch(
                        NumberExtractor.getArray(numbersArrayStr),
                        value))
                .isEqualTo(expected);
    }
}
