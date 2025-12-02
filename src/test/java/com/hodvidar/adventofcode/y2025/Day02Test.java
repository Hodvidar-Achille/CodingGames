package com.hodvidar.adventofcode.y2025;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day02Test extends AbstractTestForAdventOfCode2025 {

    protected Day02Test() {
        super(new Day02());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "11 | true",
            "111 | false",
            "1111 | true",
            "11111 | false",
            "112112 | true",
            "1234567812345678 | true"
    })
    void checkMiddle(final Double line, final boolean expectedResult) {
        final boolean result = ((Day02) testedClass).isInvalid(line);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Override
    protected double getExpectedResultDouble() {
        return 24043483400d;
    }

    @Override
    protected int getExpectedTestResult() {
        return 1227775554;
    }
}