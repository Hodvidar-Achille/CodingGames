package com.hodvidar.adventofcode.y2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day02p2Test extends AbstractTestForAdventOfCode2024 {

    protected Day02p2Test() {
        super(new Day02p2());
    }

    @Override
    protected int getExpectedResult() {
        return 601;
    }

    @Override
    protected int getExpectedTestResult() {
        return 4;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "10 12 14 16 19 19 26 | 0",
            "10 12 14 16 19 19 | 1",
            "1 2 | 1",
            "67 65 68 71 72 73 74 | 1"
    })
    void checkMiddle(final String line, final double expectedResult) {
        final double result = testedClass.getDigitFromLine(line);
        assertThat(result).isEqualTo(expectedResult);
    }

}
