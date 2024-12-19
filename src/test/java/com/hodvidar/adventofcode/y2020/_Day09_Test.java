package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/*
 *
 */
public class _Day09_Test extends AbstractTestForAdventOfCode2020 {

    protected _Day09_Test() {
        super(new _Day09());
    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(1, 5, 127),
                Arguments.of(2, 5, 25),
                Arguments.of(3, 5, 225),
                Arguments.of(4, 5, 320)
        );
    }

    @Override
    protected int getExpectedResult() {
        return 85848519;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 127"
    })
    void checkInvalidValueForFiVeNumberForSum(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        new _Day09();
        assertThat(_Day09.readValuesAndLookForInvalid(getScanner(numberOfTheTest), 5)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 5 | 127",
            "2 | 5 | 25",
            "3 | 5 | 225",
            "4 | 5 | 320",
    })
    void checkInvalidValueForFiVeNumberForSum(final int numberOfTheTest, final int numberOfNumberToUseForSums, final int expectedResult) throws FileNotFoundException {
        new _Day09();
        assertThat(_Day09.readValuesAndLookForInvalid(getScanner(numberOfTheTest), numberOfNumberToUseForSums)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("generateData")
    void checkInvalidValueForFiVeNumberForSum_method_source(final int numberOfTheTest, final int numberOfNumberToUseForSums, final int expectedResult) throws FileNotFoundException {
        new _Day09();
        assertThat(_Day09.readValuesAndLookForInvalid(getScanner(numberOfTheTest), 5)).isEqualTo(expectedResult);
    }

}
