package com.hodvidar.adventofcode.y2023;

import java.util.*;

public class Day12 extends AbstractAdventOfCode2023 {

    private static final char WORKING_STATE = '.';
    private static final char UNKNOWN_STATE = '?';
    private static final char DAMAGED_STATE = '#';
    private final Map<SpringRow, Double> memoizationMap = new HashMap<>();

    @Override
    public int getDay() {
        return 12;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        memoizationMap.clear();
        double counter = 0;
        while (sc.hasNext()) {
            counter += getDigitFromLine(sc.nextLine());
        }
        return counter;
    }

    @Override
    public double getDigitFromLine(final String line) {
        final String[] SpringRow = line.split("\\s+");
        final String springCondition = "." + SpringRow[0] + ".";
        final List<Integer> damagedSpringGroups = Arrays.stream(SpringRow[1].split(",")).map(Integer::valueOf).toList();
        return countPermutations(springCondition, damagedSpringGroups);
    }


    protected double countPermutations(final String springCondition,
                                       final List<Integer> damagedSpringGroups) {
        if (springCondition.isBlank()) {
            // end of recursion
            return damagedSpringGroups.isEmpty() ? 1 : 0;
        }
        final SpringRow springRow = new SpringRow(springCondition, damagedSpringGroups);
        if (memoizationMap.containsKey(springRow)) {
            return memoizationMap.get(springRow);
        }
        final char individualState = springCondition.charAt(0);
        final var permutations = countPermutationsForGivenState(springCondition, damagedSpringGroups, individualState);
        memoizationMap.put(springRow, permutations);
        return permutations;
    }

    private double countPermutationsForGivenState(final String springCondition, final List<Integer> damagedSpringGroups, final char individualState) {
        return switch (individualState) {
            case WORKING_STATE -> countPermutationsForWorking(springCondition, 0, damagedSpringGroups);
            case UNKNOWN_STATE -> countPermutationsForUnknown(springCondition, damagedSpringGroups);
            case DAMAGED_STATE -> countPermutationsForDamaged(springCondition, damagedSpringGroups);
            default -> throw new IllegalStateException("Unexpected value: " + individualState);
        };
    }

    private double countPermutationsForWorking(final String springCondition,
                                               final int individualStateIndex,
                                               final List<Integer> damagedSpringGroups) {
        return countPermutations(springCondition.substring(individualStateIndex + 1), damagedSpringGroups);
    }

    private double countPermutationsForUnknown(final String springCondition,
                                               final List<Integer> damagedSpringGroups) {
        return countPermutations("." + springCondition.substring(1), damagedSpringGroups)
                + countPermutations("#" + springCondition.substring(1), damagedSpringGroups);
    }


    private double countPermutationsForDamaged(final String springCondition,
                                               final List<Integer> damagedSpringGroups) {
        if (damagedSpringGroups.isEmpty()) {
            return 0d;
        }
        final int numberOfDamagedSpringState = damagedSpringGroups.get(0);
        if (numberOfDamagedSpringState > springCondition.length()) {
            return 0d;
        }
        if (springCondition.chars().limit(numberOfDamagedSpringState).anyMatch(c -> c == WORKING_STATE)) {
            return 0d;
        }
        final List<Integer> subDamagedSpringGroups = damagedSpringGroups.subList(1, damagedSpringGroups.size());
        if (numberOfDamagedSpringState == springCondition.length()) {
            return subDamagedSpringGroups.isEmpty() ? 1 : 0;
        }
        final var individualState = springCondition.charAt(numberOfDamagedSpringState);
        return switch (individualState) {
            case WORKING_STATE ->
                    countPermutationsForWorking(springCondition, numberOfDamagedSpringState, subDamagedSpringGroups);
            case UNKNOWN_STATE ->
                    countPermutationsForWorking("." + springCondition, numberOfDamagedSpringState + 1, subDamagedSpringGroups);
            case DAMAGED_STATE -> 0d;
            default ->
                    throw new IllegalStateException("Unexpected value: " + springCondition.charAt(numberOfDamagedSpringState));
        };
    }

    private record SpringRow(String springCondition, List<Integer> damagedSpringGroups) {
    }
}
