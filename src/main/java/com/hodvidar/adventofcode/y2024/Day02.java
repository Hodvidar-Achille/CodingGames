package com.hodvidar.adventofcode.y2024;

import java.util.*;

public class Day02 extends AbstractAdventOfCode2024 {

    @Override
    public int getDay() {
        return 2;
    }

    @Override
    public double getDigitFromLine(final String line) {
        final String[] parts = line.trim().split("\\s+");
        final int[] numbers = Arrays.stream(parts)
                .mapToInt(Integer::parseInt)
                .toArray();
        return isSafe(numbers);
    }

    protected int isSafe(final int[] numbers) {
        return isSafe(numbers, 1);
    }

    protected int isSafe(final int[] numbers, final int tries) {
        int n1 = numbers[0];
        int n2 = numbers[1];
        int diff = n1 - n2;
        int absDiff = Math.abs(diff);
        if (absDiff > 3 || absDiff < 1) {
            return checkForRetries(numbers, 0, tries);
        }
        final boolean isIncreaseConstant = diff < 0;
        for (int i = 1; i < numbers.length - 1; i++) {
            n1 = numbers[i];
            n2 = numbers[i + 1];
            diff = n1 - n2;
            absDiff = Math.abs(diff);
            if (absDiff > 3 || absDiff < 1) {
                return checkForRetries(numbers, i, tries);
            }
            final boolean isIncrease = diff < 0;
            if (isIncreaseConstant != isIncrease) {
                return checkForRetries(numbers, i, tries);
            }
        }
        return 1;
    }

    private int checkForRetries(final int[] numbers, final int index, final int tries) {
        if (tries == 1) {
            return 0; // No more retries, it was the last one
        }
        final int resultForRemovingCurrentIndex = isSafe(removeElementAtIndex(numbers, index), tries - 1);
        if (resultForRemovingCurrentIndex == 1) {
            return 1;
        }
        return isSafe(removeElementAtIndex(numbers, index + 1), tries - 1);
    }

    public static int[] removeElementAtIndex(final int[] numbers, final int indexToRemove) {
        if (indexToRemove < 0 || indexToRemove >= numbers.length) {
            throw new IllegalArgumentException("Index out of bounds: " + indexToRemove);
        }

        final int[] result = new int[numbers.length - 1];
        for (int i = 0, j = 0; i < numbers.length; i++) {
            if (i != indexToRemove) {
                result[j++] = numbers[i];
            }
        }
        return result;
    }
}
