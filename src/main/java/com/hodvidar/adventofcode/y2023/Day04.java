package com.hodvidar.adventofcode.y2023;

import java.util.Arrays;

public class Day04 extends AbstractAdventOfCode2023 {

    @Override
    public int getDay() {
        return 4;
    }

    @Override
    public double getDigitFromLine(final String line) {
        final String[] partsAfterCardNumber = line.split(": ")[1].split("\\|");
        final int[] winningNumbers = Arrays.stream(partsAfterCardNumber[0].trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        final int[] numbers = Arrays.stream(partsAfterCardNumber[1].trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        final int numberOfOccurrence = Arrays.stream(numbers).distinct().filter(n -> Arrays.stream(winningNumbers).anyMatch(n2 -> n2 == n)).toArray().length;
        return numberOfOccurrence == 0 ? 0 : (int) Math.pow(2, numberOfOccurrence - 1);
    }
}
