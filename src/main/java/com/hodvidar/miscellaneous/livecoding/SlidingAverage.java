package com.hodvidar.miscellaneous.livecoding;

import com.hodvidar.utils.list.ListUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Sliding average for an array with a given window size for the number of
 * Inputs: A = [5, 3, 7, 2, 3], W = 3
 * Result: [5, 4, ...]
 **/
public class SlidingAverage {

    // 0(n)
    public static double[] getSlidingAverage(final int[] array, final int windowSize) {
        if (windowSize < 1) {
            throw new IllegalArgumentException("window size should be > 1");
        }
        final List<Double> averages = new ArrayList<>();
        final int maxLength = array.length;
        int currentSum = getSumOfFirstArrayElementsInWindow(array, windowSize);
        double actualWindowSize;
        averages.add(currentSum / (double) Math.min(windowSize, maxLength));
        for (int i = 1; i < maxLength; i++) {
            actualWindowSize = Math.min(maxLength - i, windowSize);
            currentSum -= array[i - 1];
            if (windowSize + i <= maxLength) {
                currentSum += array[i + windowSize - 1];
            }
            averages.add(currentSum / actualWindowSize);
        }
        return ListUtils.listOfDoubleToArray(averages);
    }

    private static int getSumOfFirstArrayElementsInWindow(final int[] array, final int windowSize) {
        return Arrays.stream(array).limit(windowSize).sum();
    }
}
