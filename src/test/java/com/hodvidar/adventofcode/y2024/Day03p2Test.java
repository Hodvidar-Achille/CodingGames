package com.hodvidar.adventofcode.y2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day03p2Test extends AbstractTestForAdventOfCode2024 {

    protected Day03p2Test() {
        super(new Day03p2());
    }

    @Override
    protected int getNumberOfTheTest() {
        return 2;
    }

    // Input file need to be of one line only (instead of the 6 given lines)
    @Override
    protected int getExpectedResult() {
        return 83595109;
    }

    @Override
    protected int getExpectedTestResult() {
        return 48;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "don't()mul(2,2) | 0",
            "don't()do()mul(2,2) | 4",
            "mul(3,3)don't()mul(2,2) | 9",
    })
    void checkMiddle(final String line, final double expectedResult) {
        final double result = testedClass.getDigitFromLine(line);
        assertThat(result).isEqualTo(expectedResult);
    }

}
