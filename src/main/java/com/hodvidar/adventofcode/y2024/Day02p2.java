package com.hodvidar.adventofcode.y2024;

import java.util.Arrays;

public class Day02p2 extends Day02 {

    @Override
    public double getDigitFromLine(final String line) {
        final String[] parts = line.trim().split("\\s+");
        final int[] numbers = Arrays.stream(parts)
                .mapToInt(Integer::parseInt)
                .toArray();
        return isSafe(numbers, 2);
    }
}
