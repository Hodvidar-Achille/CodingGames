package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class Day01p2Test extends AbstractTestForAdventOfCode2023 {
    protected Day01p2Test() {
        super(new Day01p2());
    }

    @Override
    protected int getExpectedResult() {
        return 54504;
    }

    @Override
    protected int getNumberOfTheTest() {
        return 2;
    }

    @Override
    protected int getExpectedTestResult() {
        return 281;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "eightwothree = e8t2ot3e",
            "oneighthree = o1e8t3e"
    })
    void transformString_dirtyWithOverLapping(final String input, final String expected) {
        assertThat(Day01p2.transformString_dirtyWithOverLapping(input)).isEqualTo(expected);
    }
}