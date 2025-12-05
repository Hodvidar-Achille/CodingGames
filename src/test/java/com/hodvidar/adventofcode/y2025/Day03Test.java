package com.hodvidar.adventofcode.y2025;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day03Test extends AbstractTestForAdventOfCode2025 {

    protected Day03Test() {
        super(new Day03());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "987654321111111 | 98",
            "811111111111119 | 89",
            "234234234234278 | 78",
            "818181911112111 | 92",
            "1119 | 19",
            "192 | 92",
            "82 | 82"
    })
    void checkMiddle(final String line, final double expectedResult) {
        final double result = testedClass.getDigitFromLine(line);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Override
    protected double getExpectedResultDouble() {
        return 16887;
    }

    @Override
    protected int getExpectedTestResult() {
        return 357;
    }
}