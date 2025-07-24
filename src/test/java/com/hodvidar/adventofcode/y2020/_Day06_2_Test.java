package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day06_2_Test extends AbstractTestForAdventOfCode2020 {

    public _Day06_2_Test() {
        super(new _Day06_2());
    }

    @Override
    protected int getExpectedResult() {
        return 3158;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "AAA#AAA#AAA | 1",
            "a#b#c | 0",
            "a#a | 1",
            "abcx#abcy#abcz | 3",
            "abcde | 5",
            "b | 1",
            "ab#ac | 1",
            "ab#ab#ab#a | 1",
            "abcde#abcd#abc#ab | 2"
    })
    void checkCountCommonLettersInGroup(final String code, final int expectedResult) throws FileNotFoundException {
        new _Day06_2();
        final int result = _Day06_2.countCommonLettersInGroup(code);
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 6"
    })
    void shouldFindResultInSmallNumberPool(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScanner(numberOfTheTest);
        new _Day06_2();
        final int result = _Day06_2.countUniqueLetterByGroup(sc);
        assertThat(result).isEqualTo(expectedResult);
    }

}