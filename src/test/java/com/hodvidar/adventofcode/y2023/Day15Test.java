package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Day15Test extends AbstractTestForAdventOfCode2023 {

    protected Day15Test() {
        super(new Day15());
    }

    @Override
    protected int getNumberOfTheTest() {
        return 1;
    }

    @Override
    protected double getExpectedTestResultDouble() {
        return 1320d;
    }

    @Override
    protected double getExpectedResultDouble() {
        return 513643d;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "HASH | 52"
    })
    void numberOfPermutations(final String input, final int expectedResult) {
        assertThat(new Day15().hashAlgorithm(input)).isEqualTo(expectedResult);
    }
}
