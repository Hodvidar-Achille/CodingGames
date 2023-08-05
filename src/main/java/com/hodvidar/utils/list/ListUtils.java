package com.hodvidar.utils.list;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ListUtils {

    private ListUtils() {
        throw new IllegalStateException("Utility class");
    }

    public static int[] reverse(final int[] array) {
        if (null == array || array.length < 2) {
            return array;
        }
        int temp;
        for (int i = 0; i < array.length / 2; i++) {
            temp = array[array.length - 1 - i];
            array[array.length - 1 - i] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public static double[] reverseDoubles(final double[] array) {
        if (null == array || array.length < 2) {
            return array;
        }
        double temp;
        for (int i = 0; i < array.length / 2; i++) {
            temp = array[array.length - 1 - i];
            array[array.length - 1 - i] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public static List<Integer> reverse(final List<Integer> list) {
        return arrayToList(reverse(listToArray(list)));
    }

    public static List<Double> reverseDouble(final List<Double> list) {
        return doubleArrayToList(reverseDoubles(doubleListToArray(list)));
    }

    public static List<Integer> arrayToList(final int[] array) {
        if (null == array) {
            return Collections.emptyList();
        }
        final List<Integer> integerArrayList = new ArrayList<>(array.length);
        for (final int i : array) {
            integerArrayList.add(i);
        }
        return integerArrayList;
    }


    public static List<Double> doubleArrayToList(final double[] array) {
        if (null == array) {
            return Collections.emptyList();
        }
        final List<Double> doubleArrayList = new ArrayList<>(array.length);
        for (final double i : array) {
            doubleArrayList.add(i);
        }
        return doubleArrayList;
    }

    public static List<List<Integer>> array2DToList(final int[][] array2D) {
        if (null == array2D) {
            return Collections.emptyList();
        }
        final List<List<Integer>> intList = new ArrayList<>(array2D.length);
        for (final int[] array : array2D) {
            intList.add(arrayToList(array));
        }
        return intList;
    }

    public static double[] doubleListToArray(final List<Double> list) {
        if (null == list) {
            return new double[0];
        }
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public static int[] listToArray(final List<Integer> list) {
        if (null == list) {
            return new int[0];
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static double[] listOfDoubleToArray(final List<Double> list) {
        if (null == list) {
            return new double[0];
        }
        return list.stream().mapToDouble(Double::doubleValue).toArray();
    }

    public static int[][] listToArray2D(final List<List<Integer>> list) {
        if (null == list) {
            return new int[0][0];
        }
        final int[][] array2D = new int[list.size()][];
        for (int i = 0; i < list.size(); i++) {
            final List<Integer> sublist = list.get(i);
            array2D[i] = listToArray(sublist);
        }
        return array2D;
    }

    /**
     * Returns -1 if value not in the array, otherwise returns the index of the value
     */
    public static int binarySearch(final int[] sortedArray, final int value) {
        return runBinarySearchIteratively(sortedArray, value, 0, sortedArray.length);
    }

    public static int binarySearch(final int[] sortedArray,
                                   final int value,
                                   final int lowIndex) {
        return runBinarySearchIteratively(sortedArray, value, lowIndex, sortedArray.length);
    }

    public static int runBinarySearchIteratively(final int[] sortedArray,
                                                 final int value,
                                                 int low,
                                                 int high) {
        int index = -1;
        while (low <= high) {
            final int mid = (low + high) / 2;
            if (sortedArray[mid] < value) {
                low = mid + 1;
            } else if (sortedArray[mid] > value) {
                high = mid - 1;
            } else if (sortedArray[mid] == value) {
                index = mid;
                break;
            }
        }
        return index;
    }


    public static double[] arrayAsDoubles(final int[] array) {
        return Arrays.stream(array).asDoubleStream().toArray();
    }


    /**
     * Returns a list of lists of all permutations of the given array.
     *
     * @param permutations the list to fill with all permutations
     * @param n            the length of the given array
     * @param elements     the given array
     */
    public static void collectAllPermutation(List<List<Double>> permutations,
                                             int n,
                                             double[] elements) {
        if (n == 1) {
            permutations.add(doubleArrayToList(elements));
        } else {
            for (int i = 0; i < n - 1; i++) {
                collectAllPermutation(permutations, n - 1, elements);
                if (n % 2 == 0) {
                    swap(elements, i, n - 1);
                } else {
                    swap(elements, 0, n - 1);
                }
            }
            collectAllPermutation(permutations, n - 1, elements);
        }
    }

    private static void swap(double[] input, int a, int b) {
        double tmp = input[a];
        input[a] = input[b];
        input[b] = tmp;
    }
}
