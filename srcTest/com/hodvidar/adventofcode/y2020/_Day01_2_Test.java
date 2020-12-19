package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day01_2_Test extends AbstractTestForAdventOfCode {

    @Override
    protected int getDay() {
        return 1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "3 = 1019090",
            "4 = 241861950",
            "5 = 2036160"
    })
    void shouldFindResultInSmallNumberPool(int numberOfTheTest, int expectedResult) throws FileNotFoundException {
        Scanner sc = getScanner(numberOfTheTest);
        int result = _Day01_2.getResult(sc);
        assertThat(result).isEqualTo(expectedResult);
    }

}
