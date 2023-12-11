package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

class Day10p2Test extends AbstractTestForAdventOfCode2023 {
    protected Day10p2Test() {
        super(new Day10p2());
    }

    @Override
    protected int getNumberOfTheTest() {
        return 2;
    }

    @Override
    protected double getExpectedResultDouble() {
        return 429d;
    }

    @Override
    protected double getExpectedTestResultDouble() {
        return 4d;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "3 = 8",
            "4 = 10",
    })
    void test(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        assertThat(new Day10p2().getResultDouble(getScanner(numberOfTheTest))).isEqualTo(expectedResult);
    }
}