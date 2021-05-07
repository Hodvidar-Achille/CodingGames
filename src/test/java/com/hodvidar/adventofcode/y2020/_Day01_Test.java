package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day01_Test extends AbstractTestForAdventOfCode {

    public _Day01_Test() {
        super(new _Day01());
    }

    @Override
    protected int getExpectedResult() {
        return 388075;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 514579",
            "2 = 1020100"
    })
    void shouldFindResultInSmallNumberPool(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScanner(numberOfTheTest);
        final int result = testedClass.getResult(sc);
        assertThat(result).isEqualTo(expectedResult);
    }

}
