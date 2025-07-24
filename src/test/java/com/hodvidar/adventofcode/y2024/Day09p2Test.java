package com.hodvidar.adventofcode.y2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day09p2Test extends AbstractTestForAdventOfCode2024 {

    public Day09p2Test() {
        super(new Day09p2());
    }

    @Override
    protected double getExpectedResultDouble() {
        return 6547228115826d;
    }

    @Override
    protected int getExpectedTestResult() {
        return 2858;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2333133121414131402 | 00992111777.44.333....5555.6666.....8888.."
    })
    void compactDiskMap(final String line, final String expectedResult) {
        final String result = ((Day09) testedClass).compactDiskMapIntoString(
                ((Day09) testedClass).transformDiskMap(line));
        assertThat(result).isEqualTo(expectedResult);
    }
}
