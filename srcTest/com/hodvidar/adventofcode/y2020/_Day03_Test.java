package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day03_Test extends AbstractTestForAdventOfCode {
    @Override
    protected int getDay() {
        return 3;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 7",
            "2 = 1",
            "3 = 3"
    })
    void shouldFindResultInSmallNumberPool(int numberOfTheTest, int expectedResult) throws FileNotFoundException {
        Scanner sc = getScanner(numberOfTheTest);
        int result = new _Day03().countTrees(sc, 1, 3);
        assertThat(result).isEqualTo(expectedResult);
    }
}
