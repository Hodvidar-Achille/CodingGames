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
        int n1 = numbers[0];
        int n2 = numbers[1];
        int diff = n1 - n2;
        int absDiff = Math.abs(diff);
        if(absDiff > 3 || absDiff < 1) {
            return 0; // unsafe
        }
        final boolean isIncreaseConstant = diff < 0;
        for(int i = 1; i < numbers.length - 1; i++) {
            n1 = numbers[i];
            n2 = numbers[i+1];
            diff = n1 - n2;
            absDiff = Math.abs(diff);
            if(absDiff > 3 || absDiff < 1) {
                return 0; // unsafe
            }
            boolean isIncrease = diff < 0;
            if(isIncreaseConstant != isIncrease) {
                return 0; // unsafe
            }
        }
        return 1; // safe
    }
}
