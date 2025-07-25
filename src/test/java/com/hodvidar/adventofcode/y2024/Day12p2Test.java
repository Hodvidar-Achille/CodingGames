package com.hodvidar.adventofcode.y2024;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class Day12p2Test extends AbstractTestForAdventOfCode2024 {


    public Day12p2Test() {
        super(new Day12p2());
    }

    @Override
    protected int getExpectedResult() {
        return 818286;
    }

    @Override
    protected int getExpectedTestResult() {
        return 1206;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "2 = 80",
            "3 = 236",
            "4 = 368",
            "5 = 436"
    })
    void shouldFindResultInSmallNumberPool(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScanner(numberOfTheTest);
        final int result = testedClass.getResult(sc);
        assertThat(result).isEqualTo(expectedResult);
    }
}
