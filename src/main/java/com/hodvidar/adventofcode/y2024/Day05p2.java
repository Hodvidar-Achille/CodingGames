package com.hodvidar.adventofcode.y2024;

import java.util.*;

public class Day05p2 extends Day05 {


    private static String fixOrder(final String line, final Set<String> incorrectOrders) {
        final String[] numbers = line.split(",");
        final List<String> numberList = new ArrayList<>(Arrays.asList(numbers));

        boolean updated = false;
        do {
            final List<List<String>> pairs = generateAllPairs(String.join(",", numberList));
            updated = isUpdated(incorrectOrders, pairs, numberList);
        } while (updated);

        return String.join(",", numberList);
    }

    private static boolean isUpdated(final Set<String> incorrectOrders, final List<List<String>> pairs, final List<String> numberList) {
        for (final List<String> pairList : pairs) {
            for (final String pair : pairList) {
                if (incorrectOrders.contains(pair)) {
                    final String[] parts = pair.split("\\|");
                    final String first = parts[0];
                    final String second = parts[1];

                    // Swap to correct order
                    final int indexFirst = numberList.indexOf(first);
                    final int indexSecond = numberList.indexOf(second);
                    if (indexFirst < indexSecond) {
                        Collections.swap(numberList, indexFirst, indexSecond);
                        return true;
                    }
                }
            }
        }
        return false;
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
            if (!checkLineOrder(listOfListOFPairs)) {
                final String lineFixed = fixOrder(line, incorrectOrders);
                counter += Integer.parseInt(extractMiddleNumber(lineFixed));
            }
        }
        return counter;
    }
}
