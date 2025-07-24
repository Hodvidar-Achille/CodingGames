package com.hodvidar.utils.geometry.fordcircle;

import com.hodvidar.utils.geometry.Point;
import com.hodvidar.utils.number.DoubleFormater;
import com.hodvidar.utils.regex.NumberExtractor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FordCircleIntervalTest {

    private static List<FordCircle> getFordCirclesFromString(final String circles) {
        return getFordCirclesFromNumbers(NumberExtractor.extractNumber(circles));
    }

    private static List<FordCircle> getFordCirclesFromNumbers(final List<Double> numbers) {
        if (numbers.isEmpty()) {
            return Collections.emptyList();
        }
        if (numbers.size() % 2 != 0) {
            throw new IllegalArgumentException("The number of numbers must be a multiple of 3.");
        }
        final int size = numbers.size();
        final List<FordCircle> circles = new ArrayList<>(size / 2);
        for (int i = 0; i < size; i += 2) {
            circles.add(new FordCircle(new Point(numbers.get(i), numbers.get(i + 1)), numbers.get(i + 1)));
        }
        return circles;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[{7.0, 7.0}, {23.0, 9.0}] | 1.977"
    })
    void maxCircleRadiusPossibleForTwoCircle(final String circles,
                                             final double expectedMiddleRadius) {
        final List<FordCircle> existingCircles = getFordCirclesFromString(circles);
        final FordCircle c1 = existingCircles.get(0);
        final FordCircle c2 = existingCircles.get(1);
        final FordCircleInterval interval = new FordCircleInterval(c1, c2);
        assertThat(DoubleFormater.roundTo3rdDecimal(interval.maxCircleRadiusPossible()))
                .isEqualTo(expectedMiddleRadius);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[{6.0, 6.0} | 1.115"
    })
    void maxCircleRadiusPossibleForOneCircle(final String circle,
                                             final double expectedMiddleRadius) {
        final FordCircle c1 = getFordCirclesFromString(circle).get(0);
        final FordCircleInterval interval = new FordCircleInterval(c1, null);
        assertThat(DoubleFormater.roundTo3rdDecimal(interval.maxCircleRadiusPossible()))
                .isEqualTo(expectedMiddleRadius);
    }
}
