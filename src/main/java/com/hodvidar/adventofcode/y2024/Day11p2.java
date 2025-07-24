package com.hodvidar.adventofcode.y2024;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day11p2 extends Day11 {

    private static final Map<String, Map<Integer, Double>> SCORE_BY_STONE_BY_ITERATION = new HashMap<>();

    protected static double performBlinkRecursivelyAndCountStones(final String stones, final int numberOfIterationLeft) {
        if (numberOfIterationLeft == 0) {
            return countStone(stones);
        }
        if (SCORE_BY_STONE_BY_ITERATION.containsKey(stones) && SCORE_BY_STONE_BY_ITERATION.get(stones).containsKey(numberOfIterationLeft)) {
            return SCORE_BY_STONE_BY_ITERATION.get(stones).get(numberOfIterationLeft);
        }
        // We want to iterate on each stone one by one:
        final String[] stoneArray = stones.split(" ");
        final Map<String, Integer> StonesRepetition = new HashMap<>();
        final Map<String, Double> StonesScores = new HashMap<>();
        for (final String stone : stoneArray) {
            StonesRepetition.put(stone, StonesRepetition.getOrDefault(stone, 0) + 1);
            // Compute score only if not already present in StonesScores
            if (!StonesScores.containsKey(stone)) {
                final String newStones = performOneBlink(stone);
                StonesScores.put(stone, performBlinkRecursivelyAndCountStones(newStones, numberOfIterationLeft - 1));
            }
        }
        final double totalScore = StonesScores.entrySet().stream()
                .mapToDouble(entry -> entry.getValue() * StonesRepetition.getOrDefault(entry.getKey(), 1))
                .sum();
        SCORE_BY_STONE_BY_ITERATION.computeIfAbsent(stones, k -> new HashMap<>()).put(numberOfIterationLeft, totalScore);
        return totalScore;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        SCORE_BY_STONE_BY_ITERATION.clear();
        return performBlinkRecursivelyAndCountStones(sc.nextLine(), 75);
    }
}
