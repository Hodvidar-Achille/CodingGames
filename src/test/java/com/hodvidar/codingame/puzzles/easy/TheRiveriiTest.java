package com.hodvidar.codingame.puzzles.easy;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class TheRiveriiTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "20 | false",
            "13 | true",
            "1 | false",
            "2 | false",
            "19 | true",
            "18 | false",
            "44 | true",
            "45 | true",
            "11145 | false",
            "89696 | true",
            // "1234567890 | false" // Take too much time
    })
    void findRivers(final long inputValue, final boolean expected) {
        assertThat(TheRiverii.findRivers(inputValue) >= 2).isEqualTo(expected);
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 2",
            "2 | 4",
            "19 | 29",
            "18 | 27",
            "44 | 52",
            "45 | 54",
            "46 | 56",
            "111 | 114",
            "1234567890 | 1234567935"
    })
    void nextNumber(final long inputValue, final long expected) {
        assertThat(TheRiverii.nextNumber(inputValue)).isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 1",
            "2 | 2",
            "19 | 10",
            "18 | 9",
            "44 | 8",
            "45 | 9",
            "46 | 10",
            "111 | 3",
            "1234567890 | 45"
    })
    void sumDigits(final long inputValue, final long expected) {
        assertThat(TheRiverii.sumDigits(inputValue)).isEqualTo(expected);
    }

}
