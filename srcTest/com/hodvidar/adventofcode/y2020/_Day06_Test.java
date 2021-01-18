package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day06_Test extends AbstractTestForAdventOfCode {

    public _Day06_Test() {
        super(new _Day06());
    }

    @Override
    protected int getExpectedResult() {
        return 6297;
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "AAA | 1",
            "abcde | 5",
            "a a | 1",
            "abcxabcyabcz | 6"
    })
    void checkCountUniqueCharacters(String code, int expectedResult) throws FileNotFoundException {
        int result = new _Day06().countUniqueCharacters(code);
        assertThat(result).isEqualTo(expectedResult);
    }

}
