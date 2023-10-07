package com.hodvidar.formation.algorithms;

import com.hodvidar.utils.regex.NumberExtractor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class SortingTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[] | []",
            "[1] | [1]",
            "[1, 2] | [1, 2]",
            "[2, 1] | [1, 2]",
            "[1, 1] | [1, 1]",
            "[9, 8, 7, 6, 5, 4, 3, 2, 1] | [1, 2, 3, 4, 5, 6, 7, 8, 9]",
            "[-1, -2, -3, -4, -5, -6, -7, -8, -9] | [-9, -8, -7, -6, -5, -4, -3, -2, -1]",
            "[9, 8, 7, 6, 5, 5, 5, 4, 3, 2, 1] | [1, 2, 3, 4, 5, 5, 5, 6, 7, 8, 9]"
    })
    void removeDuplicatesBetter(final String numbersArrayStr, final String expectedArrayStr) {
        final int[] nums = NumberExtractor.getArray(numbersArrayStr);
        final int[] sortedArray = Sorting.quickSort(nums);
        final int[] expectedArray = NumberExtractor.getArray(expectedArrayStr);
        Assertions.assertThat(sortedArray).isEqualTo(expectedArray);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[] | []",
            "[1] | [1]",
            "[1, 2] | [1, 2]",
            "[2, 1] | [1, 2]",
            "[1, 1] | [1, 1]",
            "[9, 8, 7, 6, 5, 4, 3, 2, 1] | [1, 2, 3, 4, 5, 6, 7, 8, 9]",
            "[-1, -2, -3, -4, -5, -6, -7, -8, -9] | [-9, -8, -7, -6, -5, -4, -3, -2, -1]",
            "[9, 8, 7, 6, 5, 5, 5, 4, 3, 2, 1] | [1, 2, 3, 4, 5, 5, 5, 6, 7, 8, 9]"
    })
    void mergeSort(final String numbersArrayStr, final String expectedArrayStr) {
        final int[] nums = NumberExtractor.getArray(numbersArrayStr);
        final int[] sortedArray = Sorting.mergeSort(nums);
        final int[] expectedArray = NumberExtractor.getArray(expectedArrayStr);
        Assertions.assertThat(sortedArray).isEqualTo(expectedArray);
    }


    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[] | []",
            "[1] | [1]",
            "[1, 2] | [1, 2]",
            "[2, 1] | [1, 2]",
            "[1, 1] | [1, 1]",
            "[9, 8, 7, 6, 5, 4, 3, 2, 1] | [1, 2, 3, 4, 5, 6, 7, 8, 9]",
            "[-1, -2, -3, -4, -5, -6, -7, -8, -9] | [-9, -8, -7, -6, -5, -4, -3, -2, -1]",
            "[9, 8, 7, 6, 5, 5, 5, 4, 3, 2, 1] | [1, 2, 3, 4, 5, 5, 5, 6, 7, 8, 9]"
    })
    void heapSort(final String numbersArrayStr, final String expectedArrayStr) {
        final int[] nums = NumberExtractor.getArray(numbersArrayStr);
        final int[] sortedArray = Sorting.heapSort(nums);
        final int[] expectedArray = NumberExtractor.getArray(expectedArrayStr);
        Assertions.assertThat(sortedArray).isEqualTo(expectedArray);
    }
}
