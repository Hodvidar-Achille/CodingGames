package com.hodvidar.adventofcode.y2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11Test extends AbstractTestForAdventOfCode2024 {

    public Day11Test() {
        super(new Day11());
    }

    @Override
    protected int getExpectedResult() {
        return 239714;
    }

    @Override
    protected int getExpectedTestResult() {
        return 55312;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "125 17 | 253000 1 7",
            "253000 1 7 | 253 0 2024 14168",
            "253 0 2024 14168 | 512072 1 20 24 28676032",
            "512072 1 20 24 28676032 | 512 72 2024 2 0 2 4 2867 6032",
            "512 72 2024 2 0 2 4 2867 6032 | 1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32",
            "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32 | 2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2"
    })
    void checkOneBlink(final String stones, final String nextIterationResult) {
        assertThat(Day11.performOneBlink(stones)).isEqualTo(nextIterationResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "125 17 | 2097446912 14168 4048 2 0 2 4 40 48 2024 40 48 80 96 2 8 6 7 6 0 3 2"
    })
    void checkSixBlinks(final String stones, final String nextIterationResult) {
        assertThat(Day11.performBlinks(stones, 6)).isEqualTo(nextIterationResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "125 17 | 2",
            "253000 1 7 | 3",
            "253 0 2024 14168 | 4",
            "512072 1 20 24 28676032 | 5",
            "512 72 2024 2 0 2 4 2867 6032 | 9",
            "1036288 7 2 20 24 4048 1 4048 8096 28 67 60 32 | 13"
    })
    void checkNumberOfStones(final String stones, final int numberOfStone) {
        assertThat(Day11.countStone(stones)).isEqualTo(numberOfStone);
    }
}
