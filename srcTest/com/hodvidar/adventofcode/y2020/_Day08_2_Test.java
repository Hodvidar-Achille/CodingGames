package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day08_2_Test extends AbstractTestForAdventOfCode {

    public _Day08_2_Test() {
        super(new _Day08_2());
    }

    @Override
    protected int getExpectedResult() {
        return 640;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "2 = 15",
            "1 = 8"
    })
    void checkGetAccumulatorValue(int numberOfTheTest, int expectedResult) throws FileNotFoundException {
        Scanner sc = getScanner(numberOfTheTest);
        int result = new _Day08_2().getAccumulatorValueAfterFix(sc);
        assertThat(result).isEqualTo(expectedResult);
    }
}