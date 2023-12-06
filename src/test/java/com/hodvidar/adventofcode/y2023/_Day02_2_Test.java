package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class _Day02_2_Test extends AbstractTestForAdventOfCode2023 {

    public _Day02_2_Test() {
        super(new _Day02_2());
    }

    @Override
    protected int getExpectedResult() {
        return 70387;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "2 = 2286"
    })
    void shouldFindResultInSmallNumberPool(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScanner(numberOfTheTest);
        final int result = testedClass.getResult(sc);
        assertThat(result).isEqualTo(expectedResult);
    }
}
