package com.hodvidar.utils.geometry.fordcircle;

import com.hodvidar.utils.geometry.Circle;
import com.hodvidar.utils.geometry.Point;
import com.hodvidar.utils.number.DoubleFormater;
import com.hodvidar.utils.regex.NumberExtractor;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class FordCircleContainerBuilderTest {

    private static List<Circle> getFordCirclesFromString(final String circles) {
        return getFordCirclesFromNumbers(NumberExtractor.extractNumber(circles));
    }

    private static List<Circle> getFordCirclesFromNumbers(final List<Double> numbers) {
        if (numbers.isEmpty()) {
            return Collections.emptyList();
        }
        if (numbers.size() % 2 != 0) {
            throw new IllegalArgumentException("The number of numbers must be a multiple of 3.");
        }
        final int size = numbers.size();
        final List<Circle> circles = new ArrayList<>(size / 2);
        for (int i = 0; i < size; i += 2) {
            circles.add(new Circle(new Point(numbers.get(i), numbers.get(i + 1)), numbers.get(i + 1)));
        }
        return circles;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[{1.0, 1.0}] | {1.0, 1.0} | 1 | 3.0",
            "[{10.0, 10.0}] | {10.0, 10.0} | 1 | 16.325",
            "[{1.0, 1.0}] | {1.0, 1.0} | 10 | 10",
            "[{10.0, 10.0}] | {10.0, 10.0} | 10 | 30",
            "[{10.0, 10.0}, {16.325, 1.0}] | {16.325, 1.0} | 1 | 18.325",
            "[{10.0, 10.0}, {16.325, 1.0}] | {16.325, 1.0} | 10 | 30",
            "[{1.0, 1.0}] | {1.0, 1.0} | 431 | 431"
    })
    void createNewCircle(final String circles,
                         final String circle,
                         final int radiusOfNewCircle,
                         final double expectedXcoordinateOfNewCircle) {
        final List<Circle> existingCircles = getFordCirclesFromString(circles);
        final Circle closeCircle = getFordCirclesFromString(circle).get(0);
        final Circle newCircle = FordCircleContainerBuilder.createNewCircle(
                existingCircles,
                closeCircle,
                true,
                radiusOfNewCircle);
        assertThat(DoubleFormater.roundTo3rdDecimal(newCircle.getCenter().getX()))
                .isEqualTo(expectedXcoordinateOfNewCircle);
    }
}
