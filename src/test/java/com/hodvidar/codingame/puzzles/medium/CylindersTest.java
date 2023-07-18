package com.hodvidar.codingame.puzzles.medium;

import com.hodvidar.utils.number.DoubleFormater;
import com.hodvidar.utils.regex.NumberExtractor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

// https://www.codingame.com/training/medium/cylinders
// https://www.codingame.com/ide/puzzle/cylinders
class CylindersTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 1.0",
            "2 | 2.0",
            "3 | 3.0",
            "4 | 4.0",
            "5 | 5.0",
            "10 | 10.0",
            "1000 | 1000.0"
    })
    void boxWidthForOneCylinder(final int circleRadius, final double expected) {
        Assertions.assertThat(Cylinders.getLength(new int[]{circleRadius}))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 1 | 2.0",
            "2 | 2 | 4.0",
            "3 | 3 | 6.0",
            "500 | 1 | 1000.0",
            "580 | 431 | 2010.960",
            "431 | 580 | 2010.960"
    })
    void boxWidthForTwoCylinders(final int circleRadius,
                                 final int circleRadius2,
                                 final double expected) {
        Assertions.assertThat(DoubleFormater.roundTo3rdDecimal(
                        Cylinders.getLength(new int[]{circleRadius, circleRadius2})))
                .isEqualTo(expected);
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[1, 1] | 2.0",
            "[2, 2] | 4.0",
            "[3, 3] | 6.0",
            "[500, 1] | 1000.0",
            "[580, 431] | 2010.960",
            "[431, 580] | 2010.960",
            "[3, 2, 1, 2] | 9.657",
            "[4, 3, 3, 3, 3] | 24.000",
            "[4, 900, 970, 567, 965] | 6696.314",
            "[7, 3, 380, 135, 150, 542, 93, 10], 2163.508",
            "[8, 13, 8, 2, 1, 3, 1, 5, 2] | 53.811"
    })
    void boxWidthForSeveralCylinders(final String cylindersArray,
                                     final double expected) {
        Assertions.assertThat(DoubleFormater.roundTo3rdDecimal(
                        Cylinders.getLength(NumberExtractor.getArray(cylindersArray))))
                .isEqualTo(expected);
    }
}
