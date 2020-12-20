package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day03_2_Test  extends AbstractTestForAdventOfCode {
    @Override
    protected int getDay() {
        return 3;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 1 | 1 | 2",
            "1 | 1 | 3 | 7",
            "1 | 1 | 5 | 3",
            "1 | 1 | 7 | 4",
            "1 | 2 | 1 | 2"
    })
    void shouldFindResultInSmallNumberPool(int numberOfTheTest, int ordinateIncrement, int abscissaIncrement, int expectedResult) throws FileNotFoundException {
        Scanner sc = getScanner(numberOfTheTest);
        int result = new _Day03_2().countTrees(sc, ordinateIncrement, abscissaIncrement);
        assertThat(result).isEqualTo(expectedResult);
    }
}
