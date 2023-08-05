package com.hodvidar.codingame.puzzles.medium;

import com.hodvidar.utils.number.DoubleFormater;
import com.hodvidar.utils.regex.NumberExtractor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

// https://www.codingame.com/training/medium/cylinders
// https://www.codingame.com/ide/puzzle/cylinders
class CylindersTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 2.0",
            "2 | 4.0",
            "3 | 6.0",
            "4 | 8.0",
            "5 | 10.0",
            "10 | 20.0",
            "1000 | 2000.0"
    })
    void boxWidthForOneCylinder(final int circleRadius, final double expected) {
        Assertions.assertThat(Cylinders.getLength(new int[]{circleRadius}))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 1 | 4.0",
            "2 | 2 | 8.0",
            "3 | 3 | 12.0",
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
            "[1, 1] | 4.0",
            "[2, 2] | 8.0",
            "[3, 3] | 12.0",
            "[500, 1] | 1000.0",
            "[580, 431] | 2010.960",
            "[431, 580] | 2010.960",
            "[431, 1, 580] | 2010.960", // small circles should be ignored
            "[1, 431, 580] | 2010.960",
            "[2, 1, 2] | 9.657",
            "[3, 3, 3, 3] | 24.000",
            "[900, 970, 567, 965] | 6696.314",
            "[380, 3, 150, 93, 10, 542, 135] | 2218.738",
            "[3, 380, 135, 150, 542, 93, 10] | 2229.884",
            "[13, 8, 2, 1, 3, 1, 5, 2] | 63.949",
            "[2, 3, 8, 1, 13, 1, 5, 2] | 61.542",
            "[1, 2, 3, 4, 5, 6, 7, 8, 9] | 89.453",
            "[5, 7, 2, 9, 3, 8, 4, 6, 1] | 80.103"

    })
    void boxWidthForSeveralCylinders(final String cylindersArray,
                                     final double expected) {
        Assertions.assertThat(DoubleFormater.roundTo3rdDecimal(
                        Cylinders.getLength(NumberExtractor.getArray(cylindersArray))))
                .isEqualTo(expected);
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[1, 2, 3, 4, 5, 6, 7, 8, 9] | 80.103",
            "[380, 3, 150, 93, 10, 542, 135] | 2163.508",
            "[3, 380, 135, 150, 542, 93, 10] | 2163.508",
            "[13, 8, 2, 1, 3, 1, 5, 2] | 53.811",
            "[2, 3, 8, 1, 13, 1, 5, 2] | 53.811",
            "[4137, 183, 51, 96, 109, 99, 183, 10] | 8274.000",
            "[3, 4, 3, 3, 200, 2, 400, 4, 11] | 1165.685",
            "[739, 211, 458, 965, 253, 492, 587, 174] | 6719.488",
            "[279, 496, 796, 644, 602, 765, 723, 715, 85] | 9713.968",
            "[442, 721, 165, 672, 157, 346, 696, 767, 269] | 7472.313"
    })
    void boxWidthForSeveralCylindersOptimizedUsingShuffle(final String cylindersArray,
                                                          final double expected) {
        Cylinders.VERBOSE = false;
        Assertions.assertThat(DoubleFormater.roundTo3rdDecimal(
                        Cylinders.getMinLengthUsingShuffle(NumberExtractor.getArrayOfDouble(cylindersArray),
                                10000)))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[1, 2, 3, 4, 5, 6, 7, 8, 9] | 80.103",
            "[380, 3, 150, 93, 10, 542, 135] | 2163.508",
            "[3, 380, 135, 150, 542, 93, 10] | 2163.508",
            "[13, 8, 2, 1, 3, 1, 5, 2] | 53.811",
            "[2, 3, 8, 1, 13, 1, 5, 2] | 53.811",
            "[4137, 183, 51, 96, 109, 99, 183, 10] | 8274.000",
            "[3, 4, 3, 3, 200, 2, 400, 4, 11] | 1165.685",
            "[739, 211, 458, 965, 253, 492, 587, 174] | 6719.488",
            "[279, 496, 796, 644, 602, 765, 723, 715, 85] | 9713.968",
            "[442, 721, 165, 672, 157, 346, 696, 767, 269] | 7472.313"
    })
    void boxWidthForSeveralCylindersOptimizedUsingBrutForceAsString(final String cylindersArray,
                                                                    final String expected) {
        Cylinders.VERBOSE = true;
        final DecimalFormat formatter = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        formatter.setRoundingMode(RoundingMode.HALF_EVEN);
        Assertions.assertThat(
                        formatter.format(Cylinders.getMinLengthUsingBrutForce(NumberExtractor.getArrayOfDouble(cylindersArray))))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[1, 2, 3, 4, 5, 6, 7, 8, 9] | 80.103",
            "[380, 3, 150, 93, 10, 542, 135] | 2163.508",
            "[3, 380, 135, 150, 542, 93, 10] | 2163.508",
            "[13, 8, 2, 1, 3, 1, 5, 2] | 53.811",
            "[2, 3, 8, 1, 13, 1, 5, 2] | 53.811",
            "[4137, 183, 51, 96, 109, 99, 183, 10] | 8274.000",
            "[3, 4, 3, 3, 200, 2, 400, 4, 11] | 1165.685",
            "[739, 211, 458, 965, 253, 492, 587, 174] | 6719.488",
            "[279, 496, 796, 644, 602, 765, 723, 715, 85] | 9713.968",
            "[442, 721, 165, 672, 157, 346, 696, 767, 269] | 7472.313"
    })
    void boxWidthForSeveralCylindersOptimizedUsingBigToSmallAsString(final String cylindersArray,
                                                                     final String expected) {
        Cylinders.VERBOSE = true;
        final DecimalFormat formatter = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        formatter.setRoundingMode(RoundingMode.HALF_EVEN);
        Assertions.assertThat(formatter.format(Cylinders.getMinLengthUsingOptimization(NumberExtractor.getArray(cylindersArray))))
                .isEqualTo(expected);
    }
}
