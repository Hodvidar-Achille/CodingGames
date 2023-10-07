package com.hodvidar.formation.algorithms;

public class Searching {

    public static int binarySearch(final int[] sortedArray, final int value) {
        if (sortedArray == null || sortedArray.length == 0) {
            return -1;
        }
        int lowIndex = 0;
        int middleIndex = 0;
        int maxIndex = sortedArray.length - 1;
        while (lowIndex <= maxIndex) {
            middleIndex = (maxIndex + lowIndex) / 2;
            if (sortedArray[middleIndex] < value) {
                lowIndex = middleIndex + 1;
            } else if (sortedArray[middleIndex] > value) {
                maxIndex = middleIndex - 1;
            } else {
                return middleIndex;
            }
        }
        return -1;
    }
}
