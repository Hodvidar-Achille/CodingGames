package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class _Day02_Test extends AbstractTestForAdventOfCode2023 {
    protected _Day02_Test() {
        super(new _Day02());
    }

    @Override
    protected int getExpectedResult() {
        return 1734;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 8"
    })
    void shouldFindResultInSmallNumberPool(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScanner(numberOfTheTest);
        final int result = testedClass.getResult(sc);
        assertThat(result).isEqualTo(expectedResult);
    }
}
