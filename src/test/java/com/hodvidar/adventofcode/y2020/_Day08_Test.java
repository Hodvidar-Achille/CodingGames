package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day08_Test extends AbstractTestForAdventOfCode2020 {

    public _Day08_Test() {
        super(new _Day08());
    }

    @Override
    protected int getExpectedResult() {
        return 1528;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 5",
            "2 = 0"
    })
    void checkGetAccumulatorValue(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScanner(numberOfTheTest);
        final int result = new _Day08().getAccumulatorValue(sc);
        assertThat(result).isEqualTo(expectedResult);
    }
}