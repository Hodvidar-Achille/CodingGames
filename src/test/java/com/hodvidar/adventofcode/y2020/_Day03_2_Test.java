package com.hodvidar.adventofcode.y2020;

import com.hodvidar.adventofcode.AbstractTestForAdventOfCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day03_2_Test extends AbstractTestForAdventOfCode2020 {

    public _Day03_2_Test() {
        super(new _Day03_2());
    }

    @Override
    protected int getExpectedResult() {
        return 0;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 1 | 1 | 2",
            "1 | 1 | 3 | 7",
            "1 | 1 | 5 | 3",
            "1 | 1 | 7 | 4",
            "1 | 2 | 1 | 2"
    })
    void shouldFindResultInSmallNumberPool(final int numberOfTheTest, final int ordinateIncrement, final int abscissaIncrement, final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScanner(numberOfTheTest);
        final int result = new _Day03_2().countTrees(sc, ordinateIncrement, abscissaIncrement);
        assertThat(result).isEqualTo(expectedResult);
    }
}
