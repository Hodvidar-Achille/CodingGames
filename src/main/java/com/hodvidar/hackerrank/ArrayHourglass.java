package com.hodvidar.hackerrank;

import java.util.List;

import static com.hodvidar.utils.list.ListUtils.listToArray2D;

/**
 * https://www.hackerrank.com/challenges/2d-array/problem
 * <p>
 * Given a 2D Array,
 * <p>
 * :
 * <p>
 * 1 1 1 0 0 0
 * 0 1 0 0 0 0
 * 1 1 1 0 0 0
 * 0 0 0 0 0 0
 * 0 0 0 0 0 0
 * 0 0 0 0 0 0
 * <p>
 * An hourglass in
 * is a subset of values with indices falling in this pattern in
 * <p>
 * 's graphical representation:
 * <p>
 * a b c
 * d
 * e f g
 * <p>
 * There are
 * hourglasses in . An hourglass sum is the sum of an hourglass' values. Calculate the hourglass sum for every hourglass in , then print the maximum hourglass sum. The array will always be .
 */
public class ArrayHourglass {

    public static int getHighestHourglassSum(final int[][] array2D) {
        if (array2D == null || array2D.length != 6 || array2D[0].length != 6) {
            throw new IllegalArgumentException("Expecting a 6x6 array");
        }
        int max = Integer.MIN_VALUE;
        for (int columnIndex = 0; columnIndex < 4; columnIndex++) {
            for (int rowIndex = 0; rowIndex < 4; rowIndex++) {
                final int sum = getSumForHourGlass(rowIndex, columnIndex, array2D);
                if (sum > max) {
                    max = sum;
                }
            }
        }
        return max;
    }

    public static int getHighestHourglassSum(final List<List<Integer>> list2D) {
        if (list2D == null || list2D.size() != 6 || list2D.get(0).size() != 6) {
            throw new IllegalArgumentException("Expecting a 6x6 array");
        }
        return getHighestHourglassSum(listToArray2D(list2D));
    }

    /**
     * sum of the 7 integers following the hourglass pattern:
     * a b c
     * d
     * e f g
     *
     * @param topLeftRowIndex    the Y index of a
     * @param topLeftColumnIndex the X index of b
     * @param array2D            the 2D array
     * @return the sum
     */
    private static int getSumForHourGlass(final int topLeftRowIndex,
                                          final int topLeftColumnIndex,
                                          final int[][] array2D) {
        return array2D[topLeftRowIndex][topLeftColumnIndex]
                + array2D[topLeftRowIndex][topLeftColumnIndex + 1]
                + array2D[topLeftRowIndex][topLeftColumnIndex + 2]
                + array2D[topLeftRowIndex + 1][topLeftColumnIndex + 1]
                + array2D[topLeftRowIndex + 2][topLeftColumnIndex]
                + array2D[topLeftRowIndex + 2][topLeftColumnIndex + 1]
                + array2D[topLeftRowIndex + 2][topLeftColumnIndex + 2];
    }
}
