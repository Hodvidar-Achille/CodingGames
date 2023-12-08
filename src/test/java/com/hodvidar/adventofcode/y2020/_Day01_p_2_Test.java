package com.hodvidar.adventofcode.y2020;

import com.hodvidar.adventofcode.AbstractTestForAdventOfCode;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.FileNotFoundException;
import java.util.Scanner;

import static org.assertj.core.api.Assertions.assertThat;

class _Day01_p_2_Test extends AbstractTestForAdventOfCode2020 {

    protected _Day01_p_2_Test() {
        super(new _Day01_2());
    }

    @Override
    protected int getExpectedResult() {
        return 293450526;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "3 = 1019090",
            "4 = 241861950",
            "5 = 2036160"
    })
    void shouldFindResultInSmallNumberPool(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        final Scanner sc = getScanner(numberOfTheTest);
        final int result = testedClass.getResult(sc);
        assertThat(result).isEqualTo(expectedResult);
    }

}
