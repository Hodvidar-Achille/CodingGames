package com.hodvidar.adventofcode.y2025;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day01p2Test extends AbstractTestForAdventOfCode2025 {

    protected Day01p2Test() {
        super(new Day01p2());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "L49 | 1 | 0",
            "L549 | 1 | 5",
            "R49 | 99 | 0",
            "R549 | 99 | 5",
            "L68 | 82 |  1",
            "L168 | 82 | 2",
            "L1068 | 82 | 11",
            "R68 | 18 |  1",
            "R168 | 18 | 2",
            "R1068 | 18 | 11",
            "R50 | 0 | 1",
            "R150 | 0 | 2",
            "R1050 | 0 | 11",
            "L50 | 0 | 1",
            "L150 | 0 | 2",
            "L1050 | 0 | 11"
    })
    void checkMiddle(final String line, final double expectedResult, final int expectedCounter) {
        ((Day01p2) testedClass).resetState();
        final double result = testedClass.getDigitFromLine(line);
        assertThat(result).isEqualTo(expectedResult);
        assertThat(((Day01p2) testedClass).counter).isEqualTo(expectedCounter);
    }

    @Override
    protected int getExpectedResult() {
        return 6768;
    }

    @Override
    protected int getExpectedTestResult() {
        return 6;
    }
}
