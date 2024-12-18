package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day05_Test extends AbstractTestForAdventOfCode2020 {

    public _Day05_Test() {
        super(new _Day05());
    }

    @Override
    protected int getExpectedResult() {
        return 922;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0 | 10 | true | 5",
            "0 | 10 | false | 5",
            "5 | 10 | true | 8",
            "5 | 10 | false | 7"
    })
    void checkMiddle(final int min, final int max, final boolean roundCeil, final int expectedResult) throws FileNotFoundException {
        final int result = _Day05.getMiddle(min, max, roundCeil);
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0 | 10 | B | 5 | 10",
            "0 | 10 | F | 0 | 5"
    })
    void checkNewRange(final int min, final int max, final char partition, final int expectedMin, final int expectedMax)
            throws FileNotFoundException {
        final int[] range = new int[]{min, max};
        final int[] result = _Day05.getNewRange(partition, range);
        final int[] expectedResult = new int[]{expectedMin, expectedMax};
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "0 | 127 | FBFBBFF | 44",
            "0 | 7 | RLR | 5",
            "0 | 127 | FFFFFFF | 0",
            "0 | 127 | BBBBBBB | 127",
            "0 | 7 | LLL | 0",
            "0 | 7 | RRR | 7"
    })
    void checkFinalPosition(final int min, final int max, final String spacePartitioning, final int expectedResult)
            throws FileNotFoundException {
        final int result = _Day05.getFinalPosition(spacePartitioning, min, max);
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "FBFBBFFRLR | 357",
            "FFFFFFFLLL | 0",
            "FFFFFFFRRR | 7",
            "FFFFFFBLLL | 8",
            "FFFFFFBLLB | 9",
            "BBBBBBBLLL | 1016",
            "BBBBBBBRRR | 1023",
            "BFFFBBFRRR | 567",
            "FFFBBBFRRR | 119",
            "BBFFBBFRLL | 820",
    })
    void checkFinalPositionCode(final String spacePartitioning, final int expectedResult) throws FileNotFoundException {
        final int result = _Day05.getFinalPositionCode(spacePartitioning);
        assertThat(result).isEqualTo(expectedResult);
    }

}
