package com.hodvidar.hackerrank;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static com.hodvidar.hackerrank.ArrayHourglass.getHighestHourglassSum;
import static com.hodvidar.utils.list.ListUtils.array2DToList;
import static org.assertj.core.api.Assertions.assertThat;

class ArrayHoursGlassTest {

    private static final int[][] valueForTest1 =
            new int[][]{
                    {-9, -9, -9, 1, 1, 1},
                    {0, -9, 0, 4, 3, 2},
                    {-9, -9, -9, 1, 2, 3},
                    {0, 0, 8, 6, 6, 0},
                    {0, 0, 0, -2, 0, 0},
                    {0, 0, 1, 2, 4, 0},
            };

    private static final int[][] valueForTest2 =
            new int[][]{
                    {1, 1, 1, 0, 0, 0},
                    {0, 1, 0, 0, 0, 0},
                    {1, 1, 1, 0, 0, 0},
                    {0, 0, 2, 4, 4, 0},
                    {0, 0, 0, 2, 0, 0},
                    {0, 0, 1, 2, 4, 0},
            };

    private static Stream<Arguments> generateData_Array() {
        return Stream.of(
                Arguments.of(
                        valueForTest1,
                        28
                ),
                Arguments.of(
                        valueForTest2,
                        19
                )
        );
    }

    private static Stream<Arguments> generateData_List() {
        return Stream.of(
                Arguments.of(
                        array2DToList(valueForTest1),
                        28
                ),
                Arguments.of(
                        array2DToList(valueForTest2),
                        19
                )
        );
    }

    @ParameterizedTest
    @MethodSource("generateData_Array")
    void check_reverse_array(final int[][] originalArray, final int expectedSum) {
        assertThat(getHighestHourglassSum(originalArray)).isEqualTo(expectedSum);
    }

    @ParameterizedTest
    @MethodSource("generateData_List")
    void check_reverse_list(final List<List<Integer>> originalList, final int expectedSum) {
        assertThat(getHighestHourglassSum(originalList)).isEqualTo(expectedSum);
    }
}
