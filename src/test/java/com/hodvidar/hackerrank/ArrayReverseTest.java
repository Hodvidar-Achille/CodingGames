package com.hodvidar.hackerrank;

import org.eclipse.collections.api.factory.Lists;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.FileNotFoundException;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ArrayReverseTest {

    private static Stream<Arguments> generateData_Array() {
        return Stream.of(
                Arguments.of(
                        null,
                        null
                ),
                Arguments.of(
                        new int[]{},
                        new int[]{}
                ),
                Arguments.of(
                        new int[]{1},
                        new int[]{1}
                ),
                Arguments.of(
                        new int[]{1, 2},
                        new int[]{2, 1}
                ),
                Arguments.of(
                        new int[]{1, 2, 3, 4, 5},
                        new int[]{5, 4, 3, 2, 1}
                ),
                Arguments.of(
                        new int[]{0, 0, 0, 0},
                        new int[]{0, 0, 0, 0}
                ),
                Arguments.of(
                        new int[]{Integer.MAX_VALUE, 0, Integer.MIN_VALUE},
                        new int[]{Integer.MIN_VALUE, 0, Integer.MAX_VALUE}
                ),
                Arguments.of(
                        new int[]{Integer.MAX_VALUE, Integer.MIN_VALUE},
                        new int[]{Integer.MIN_VALUE, Integer.MAX_VALUE}
                )
        );
    }

    private static Stream<Arguments> generateData_List() {
        return Stream.of(
                Arguments.of(
                        null,
                        null
                ),
                Arguments.of(
                        Lists.immutable.empty(),
                        Lists.immutable.empty()
                ),
                Arguments.of(
                        Lists.immutable.of(1),
                        Lists.immutable.of(1)
                ),
                Arguments.of(
                        Lists.immutable.of(1, 2),
                        Lists.immutable.of(2, 1)
                ),
                Arguments.of(
                        Lists.immutable.of(1, 2, 3, 4, 5),
                        Lists.immutable.of(5, 4, 3, 2, 1)
                ),
                Arguments.of(
                        Lists.immutable.of(0, 0, 0, 0),
                        Lists.immutable.of(0, 0, 0, 0)
                ),
                Arguments.of(
                        Lists.immutable.of(Integer.MAX_VALUE, 0, Integer.MIN_VALUE),
                        Lists.immutable.of(Integer.MIN_VALUE, 0, Integer.MAX_VALUE)
                ),
                Arguments.of(
                        Lists.immutable.of(Integer.MAX_VALUE, Integer.MIN_VALUE),
                        Lists.immutable.of(Integer.MIN_VALUE, Integer.MAX_VALUE)
                )
        );
    }

    @ParameterizedTest
    @MethodSource("generateData_Array")
    void check_reverse_array(final int[] originalArray, final int[] expectedArray) throws FileNotFoundException {
        assertThat(ArrayReverse.reverse(originalArray)).isEqualTo(expectedArray);
    }

    @ParameterizedTest
    @MethodSource("generateData_List")
    void check_reverse_list(final List<Integer> originalList, final List<Integer> expectedList) throws FileNotFoundException {
        assertThat(ArrayReverse.reverse(originalList)).isEqualTo(expectedList);
    }
}
