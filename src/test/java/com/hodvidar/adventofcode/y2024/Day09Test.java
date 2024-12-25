package com.hodvidar.adventofcode.y2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day09Test extends AbstractTestForAdventOfCode2024 {

    public Day09Test() {
        super(new Day09());
    }

    @Override
    protected double getExpectedResultDouble() {
        return 6519155389266d;
    }

    @Override
    protected int getExpectedTestResult() {
        return 1928;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "12345 | 0..111....22222",
            "2333133121414131402 | 00...111...2...333.44.5555.6666.777.888899",
            "90909 | 000000000111111111222222222",
            "1111111111111111111111111 | 0.1.2.3.4.5.6.7.8.9.10.11.12"
    })
    void transformDiskMap(final String line, final String expectedResult) {
        final String result = ((Day09) testedClass).transformDiskMapInToString(line);
        assertThat(result).isEqualTo(expectedResult);
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "12345 | 022111222......",
            "2333133121414131402| 0099811188827773336446555566..............",
            "90909 | 000000000111111111222222222",
            "1111111111111111111111111 | 0121112103948576............"
    })
    void compactDiskMap(final String line, final String expectedResult) {
        final String result = ((Day09) testedClass).compactDiskMapIntoString(
                ((Day09) testedClass).transformDiskMap(line));
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "2333133121414131402.............. | 1928",
            "000000000 | 0",
            "90909 | 513"
    })
    void compactDiskMap(final String line, final double expectedResult) {
        final double result = ((Day09) testedClass).calculateChecksum(
                ((Day09) testedClass).compactDiskMap(
                        ((Day09) testedClass).transformDiskMap(line)));
        assertThat(result).isEqualTo(expectedResult);
    }
}
