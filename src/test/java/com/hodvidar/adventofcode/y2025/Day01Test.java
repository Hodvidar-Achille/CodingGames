package com.hodvidar.adventofcode.y2025;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day01Test extends AbstractTestForAdventOfCode2025 {

    protected Day01Test() {
        super(new Day01());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "L68 | 82",
            "R68 | 18",
            "R50 | 0",
            "L50 | 0"
    })
    void checkMiddle(final String line, final double expectedResult) {
        ((Day01) testedClass).resetState();
        final double result = testedClass.getDigitFromLine(line);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Override
    protected int getExpectedResult() {
        return 1177;
    }

    @Override
    protected int getExpectedTestResult() {
        return 3;
    }
}