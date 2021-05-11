package com.hodvidar.miscellaneous.livecoding;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class SlidingAverageTest {

    @ParameterizedTest
    @MethodSource("generateData_Array")
    public void getResultTest_several(int[] array,
                                      int windowSize,
                                      double[] expected) {
        assertThat(SlidingAverage.getSlidingAverage(array, windowSize)).containsExactly(expected);
    }

    @Test
    public void getResultTest() {
        int[] array = new int[]{5, 3, 7, 2, 10};
        int size = 3;
        double[] expected = new double[]{5, 4, (19 / 3d), 6, 10};
        assertThat(SlidingAverage.getSlidingAverage(array, size)).containsExactly(expected);
    }

    private static Stream<Arguments> generateData_Array() {
        return Stream.of(
                Arguments.of(
                        new int[]{1},
                        1,
                        new double[]{1}
                ),
                Arguments.of(
                        new int[]{1, 2},
                        1,
                        new double[]{1, 2}
                ),
                Arguments.of(
                        new int[]{1, 2},
                        2,
                        new double[]{1.5, 2}
                ),
                Arguments.of(
                        new int[]{1, 2, 3},
                        2,
                        new double[]{1.5, 2.5, 3}
                ),
                Arguments.of(
                        new int[]{1, 2, 3},
                        3,
                        new double[]{2, 2.5, 3}
                ),
                Arguments.of(
                        new int[]{5, 3, 7, 2, 10},
                        3,
                        new double[]{5, 4, (19 / 3d), 6, 10}
                ),
                Arguments.of(
                        new int[]{5, 3, 7, 2, 10},
                        5,
                        new double[]{(27 / 5d), (22 / 4d), (19 / 3d), 6, 10}
                ),
                Arguments.of(
                        new int[]{5, 3, 7, 2, 10, 100, 1000},
                        3,
                        new double[]{5, 4, (19 / 3d), (112 / 3d), 370, 550, 1000}
                )
        );
    }
}
