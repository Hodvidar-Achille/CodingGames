package com.hodvidar.adventofcode.y2025;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day03p2Test extends AbstractTestForAdventOfCode2025 {

    protected Day03p2Test() {
        super(new Day03p2());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "987654321111111 | 987654321111",
            "811111111111119 | 811111111119",
            "234234234234278 | 434234234278",
            "818181911112111 | 888911112111",
            "123456789123 | 123456789123"
    })
    void checkMiddle(final String line, final double expectedResult) {
        final double result = testedClass.getDigitFromLine(line);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Override
    protected double getExpectedResultDouble() {
        return 167302518850275d;
    }

    @Override
    protected double getExpectedTestResultDouble() {
        return 3121910778619d;
    }
}