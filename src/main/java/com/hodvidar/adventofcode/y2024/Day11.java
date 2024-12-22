package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day11 extends AbstractAdventOfCode2024 {

    protected static long countStone(final String stones) {
        return stones.chars().filter(ch -> ch == ' ').count() + 1;
    }

    protected static String performOneBlink(final String stones) {
        final String[] stoneArray = stones.split(" "); // Split input into individual stones
        final List<String> result = new ArrayList<>();

        for (final String stone : stoneArray) {
            final double number = Double.parseDouble(stone);
            // Apply transformation rules
            if (number == 0) {
                result.add("1"); // Rule 1: Replace 0 with 1
                continue;
            }
            final String numberStr = String.format("%.0f", number);
            if (numberStr.length() % 2 == 0) {
                // Rule 2: Split stone into two parts if even digits
                final int mid = numberStr.length() / 2;
                final String left = numberStr.substring(0, mid);
                final String right = numberStr.substring(mid);
                result.add(String.format("%.0f", Double.parseDouble(left)));
                result.add(String.format("%.0f", Double.parseDouble(right)));
                continue;
            }
            // Rule 3: Multiply by 2024
            result.add(String.format("%.0f", (number * 2024)));
        }

        return String.join(" ", result); // Combine transformed stones into a single string
    }

    /**
     * Slow for more than 30 iterations see Day11p2
     **/
    protected static String performBlinks(String newLine, final int numberOfBlinks) {
        for (int i = 0; i < numberOfBlinks; i++) {
            //System.out.println("Loop number:" + (i+1));
            newLine = performOneBlink(newLine);
        }
        return newLine;
    }

    @Override
    public int getDay() {
        return 11;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        String newLine = sc.nextLine();
        newLine = performBlinks(newLine, 25);
        return countStone(newLine);
    }
}
