package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class _Day01_Test extends AbstractTestForAdventOfCode2023 {
    protected _Day01_Test() {
        super(new _Day01());
    }

    @Override
    protected int getExpectedResult() {
        return 54597;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 142"
    })
    void shouldFindResultInSmallNumberPool(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScanner(numberOfTheTest);
        final int result = testedClass.getResult(sc);
        assertThat(result).isEqualTo(expectedResult);
    }
}
