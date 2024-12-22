package com.hodvidar.adventofcode.y2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11p2Test extends AbstractTestForAdventOfCode2024 {

    public Day11p2Test() {
        super(new Day11p2());
    }

    @Override
    protected double getExpectedResultDouble() {
        return 284973560658514d;
    }

    @Override
    protected double getExpectedTestResultDouble() {
        return 65601038650482d;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "125 17 | 55312"
    })
    void check25Blinks(final String stones, final int expectedResult) {
        assertThat(Day11p2.performBlinkRecursivelyAndCountStones(stones, 25))
                .isEqualTo(expectedResult);
    }

}
