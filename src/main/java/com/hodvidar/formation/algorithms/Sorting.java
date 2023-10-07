package com.hodvidar.formation.algorithms;

import java.util.Arrays;
import java.util.stream.IntStream;

public class Sorting {

    public static int[] quickSort(final int[] arrayToSort) {
        if (arrayToSort == null || arrayToSort.length < 2) {
            return arrayToSort;
        }
        final int pivot = arrayToSort[arrayToSort.length / 2];
        final int[] less = Arrays.stream(arrayToSort).filter(i -> i < pivot).toArray();
        final int[] equal = Arrays.stream(arrayToSort).filter(i -> i == pivot).toArray();
        final int[] greater = Arrays.stream(arrayToSort).filter(i -> i > pivot).toArray();
        return IntStream.concat(IntStream.concat(
                                Arrays.stream(quickSort(less)),
                                Arrays.stream(equal)),
                        Arrays.stream(quickSort(greater)))
                .toArray();
    }

    public static int[] mergeSort(final int[] arrayToSort) {
        if (arrayToSort == null || arrayToSort.length < 2) {
            return arrayToSort;
        }
        final int[] left = Arrays.copyOfRange(arrayToSort, 0, arrayToSort.length / 2);
        final int[] right = Arrays.copyOfRange(arrayToSort, arrayToSort.length / 2, arrayToSort.length);
        return merge(mergeSort(left), mergeSort(right));
    }

    private static int[] merge(final int[] left, final int[] right) {
        final int[] result = new int[left.length + right.length];
        int leftIndex = 0;
        int rightIndex = 0;
        int resultIndex = 0;
        while (leftIndex < left.length && rightIndex < right.length) {
            if (left[leftIndex] < right[rightIndex]) {
                result[resultIndex++] = left[leftIndex++];
            } else {
                result[resultIndex++] = right[rightIndex++];
            }
        }
        while (leftIndex < left.length) {
            result[resultIndex++] = left[leftIndex++];
        }
        while (rightIndex < right.length) {
            result[resultIndex++] = right[rightIndex++];
        }
        return result;
    }

    // harder to understand
    public static int[] heapSort(final int[] arrayToSort) {
        final int size = arrayToSort.length;
        for (int i = size / 2 - 1; i >= 0; i--) {
            heapify(arrayToSort, size, i);
        }
        for (int i = size - 1; i > 0; i--) {
            final int temp = arrayToSort[0];
            arrayToSort[0] = arrayToSort[i];
            arrayToSort[i] = temp;
            heapify(arrayToSort, i, 0);
        }
        return arrayToSort;
    }

    private static void heapify(final int[] array, final int size, final int index) {
        int largest = index;
        final int left = 2 * index + 1;
        final int right = 2 * index + 2;
        if (left < size && array[left] > array[largest]) {
            largest = left;
        }
        if (right < size && array[right] > array[largest]) {
            largest = right;
        }
        if (largest != index) {
            final int swap = array[index];
            array[index] = array[largest];
            array[largest] = swap;
            heapify(array, size, largest);
        }
    }
}
