package com.hodvidar.adventofcode.y2024;

import java.util.*;

public class Day05 extends AbstractAdventOfCode2024 {
    private final Set<String> incorrectOrders = new HashSet<>();

    public static List<List<String>> generateAllPairs(final String input) {
        final String[] numbers = input.split(",");
        final List<List<String>> result = new ArrayList<>();

        for (int i = 0; i < numbers.length; i++) {
            final List<String> currentList = new ArrayList<>();
            for (int j = i + 1; j < numbers.length; j++) {
                currentList.add(numbers[i] + "|" + numbers[j]);
            }
            if (!currentList.isEmpty()) {
                result.add(currentList);
            }
        }
        return result;
    }

    public static String extractMiddleNumber(final String input) {
        final String[] numbers = input.split(",");
        final int middleIndex = numbers.length / 2; // Integer division gives the middle index
        return numbers[middleIndex];
    }

    @Override
    public int getDay() {
        return 5;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        incorrectOrders.clear();
        boolean isFirstStep = true;
        double counter = 0;
        while (sc.hasNext()) {
            final String line = sc.nextLine();
            if (line.isBlank()) {
                isFirstStep = false;
                continue;
            }
            if (isFirstStep) {
                final String[] numbers = line.split("\\|");
                incorrectOrders.add(numbers[1] + "|" + numbers[0]);
                continue;
            }
            // second step
            final List<List<String>> listOfListOFPairs = generateAllPairs(line);
            if (checkLineOrder(listOfListOFPairs)) {
                counter += Integer.parseInt(extractMiddleNumber(line));
            }
        }

        return counter;
    }

    private boolean checkLineOrder(final List<List<String>> listOfListOFPairs) {
        for (final List<String> aListOfPairs : listOfListOFPairs) {
            for (final String pairOfNumbers : aListOfPairs) {
                if (incorrectOrders.contains(pairOfNumbers)) {
                    return false;
                }
            }
        }
        return true;
    }
}
