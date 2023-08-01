package com.hodvidar.codingame.puzzles.medium;

import com.hodvidar.utils.geometry.FordCircleContainer;
import com.hodvidar.utils.geometry.FordCircleContainerBuilder;
import com.hodvidar.utils.list.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

import static com.hodvidar.utils.geometry.FordCircleContainerBuilder.getHorizontalLengthBetween2FordCirclesCenter;

// https://www.codingame.com/training/medium/cylinders
// https://www.codingame.com/ide/puzzle/cylinders
public class Cylinders {

    private Cylinders() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Returns the length the given circles took on the X axis when each circle touch the X axis (Y=0) with their bottom
     * point.
     *
     * @param circlesRadius array of radius for a series of Ford circles
     * @return the maximum length the circles took on the X axis
     */
    public static double getLength(final int[] circlesRadius) {
        final Optional<Double> lengthQuick = getLengthQuick(circlesRadius);
        if (lengthQuick.isPresent()) {
            return lengthQuick.get();
        }
        final FordCircleContainer container = FordCircleContainerBuilder.getFordCircleContainer(circlesRadius);
        return container.getMaxX();
    }


    public static double getMinLength(final int[] circlesRadius) {
        final Optional<Double> lengthQuick = getLengthQuick(circlesRadius);
        if (lengthQuick.isPresent()) {
            return lengthQuick.get();
        }
        final long startTime = System.currentTimeMillis();
        final Map<String, Double> shuffleResults = new HashMap<>();
        final List<List<Integer>> allPossiblePermutations = new ArrayList<>();
        ListUtils.collectAllPermutation(allPossiblePermutations, circlesRadius.length, circlesRadius);
        for (List<Integer> aPermutation : allPossiblePermutations) {
            final String circleRadiusListString = aPermutation.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            if (shuffleResults.containsKey(circleRadiusListString)) {
                continue;
            }
            shuffleResults.put(circleRadiusListString,
                    FordCircleContainerBuilder.
                            getFordCircleContainer(aPermutation.stream().mapToInt(it -> it).toArray())
                            .getMaxX());
        }
        final Map.Entry<String, Double> minResult = shuffleResults.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .orElseThrow();
        final long endTime = System.currentTimeMillis();
        print(circlesRadius, startTime, shuffleResults, minResult, endTime);
        return minResult.getValue();
    }

    private static void print(int[] circlesRadius, long startTime, Map<String, Double> shuffleResults, Map.Entry<String, Double> minResult, long endTime) {
        System.err.println("For Input: "
                + Arrays.stream(circlesRadius).boxed().collect(Collectors.toList()).stream()
                .map(String::valueOf).collect(Collectors.joining(","))
                + "\nMin result found: " + minResult.getValue()
                + "\nfor radius in this order: " + minResult.getKey()
                + "\nnumber of tries: " + shuffleResults.size()
                + "\nTime: " + (endTime - startTime) + "ms");
    }

    public static double getMinLengthUsingSuffle(final int[] circlesRadius, int numberOfTries) {
        final Optional<Double> lengthQuick = getLengthQuick(circlesRadius);
        if (lengthQuick.isPresent()) {
            return lengthQuick.get();
        }
        final long startTime = System.currentTimeMillis();
        final Map<String, Double> shuffleResults = new HashMap<>();
        final List<Integer> circlesRadiusList = Arrays.stream(circlesRadius).boxed().collect(Collectors.toList());
        for (int i = 0; i < numberOfTries; i++) {
            Collections.shuffle(circlesRadiusList);
            final String circleRadiusListString = circlesRadiusList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            if (shuffleResults.containsKey(circleRadiusListString)) {
                continue;
            }
            shuffleResults.put(circleRadiusListString,
                    FordCircleContainerBuilder.
                            getFordCircleContainer(circlesRadiusList.stream().mapToInt(it -> it).toArray())
                            .getMaxX());
        }
        final Map.Entry<String, Double> minResult = shuffleResults.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .orElseThrow();
        final long endTime = System.currentTimeMillis();
        print(circlesRadius, startTime, shuffleResults, minResult, endTime);
        return minResult.getValue();
    }

    private static Optional<Double> getLengthQuick(final int[] circlesRadius) {
        if (circlesRadius.length == 0) {
            return Optional.of(0.0);
        }
        if (Arrays.stream(circlesRadius).anyMatch(d -> d <= 0)) {
            throw new IllegalArgumentException("Radius must be positive.");
        }
        if (circlesRadius.length == 1) {
            return Optional.of(circlesRadius[0] * 2.0);
        }
        if (Arrays.stream(circlesRadius).allMatch(d -> d == circlesRadius[0])) {
            return Optional.of(Arrays.stream(circlesRadius).sum() * 2.0);
        }
        return Optional.empty();
    }

    private static double getMaxXLengthTakenBy2FordCircles(final int circleRadius1, final int circleRadius2) {
        final double maxLength = Math.max(circleRadius1, circleRadius2);
        final double minLength = Math.min(circleRadius1, circleRadius2);
        final double middleLength = getHorizontalLengthBetween2FordCirclesCenter(maxLength, minLength);
        final double maxXLength = Math.max(middleLength + minLength, maxLength);
        return maxLength + maxXLength;
    }


}
