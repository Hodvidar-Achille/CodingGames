package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

/*
 *
 */
public class _Day09_Test extends AbstractTestForAdventOfCode {

    protected _Day09_Test() {
        super(new _Day09());
    }

    @Override
    protected int getExpectedResult() {
        return -1;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 127"
    })
    void checkInvalidValueForFiVeNumberForSum(final int numberOfTheTest, final int expectedResult) throws FileNotFoundException {
        assertThat(new _Day09().readValuesAndLookForInvalid(getScanner(numberOfTheTest), 5)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @MethodSource("generateData")
    void checkCreateSubSumList(final int numberOfTheTest, final int[] expectedResult) throws FileNotFoundException {
        assertThat(new _Day09().readValuesAndLookForInvalid(getScanner(numberOfTheTest), 5)).isEqualTo(expectedResult);
    }

    static Stream<Arguments> generateData() {
        return Stream.of(
                Arguments.of(
                        Arrays.asList(1, 2, 3, 4, 5),
                        Arrays.asList(
                                Arrays.asList(3, 4, 5, 6),
                                Arrays.asList(3, 4, 5, 6),
                                Arrays.asList(3, 4, 5, 6),
                                Arrays.asList(3, 4, 5, 6),
                                Arrays.asList(3, 4, 5, 6),
                                Arguments.of(2, "bar", Arrays.asList("x", "y", "z"))
                        )));

    }

}
