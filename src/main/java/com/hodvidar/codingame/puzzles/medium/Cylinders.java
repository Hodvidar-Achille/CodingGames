package com.hodvidar.codingame.puzzles.medium;

import com.hodvidar.utils.geometry.Circle;
import com.hodvidar.utils.geometry.fordcircle.FordCircleContainer;
import com.hodvidar.utils.geometry.fordcircle.FordCircleContainerBuilder;
import com.hodvidar.utils.list.ListUtils;

import java.util.*;
import java.util.stream.Collectors;

// https://www.codingame.com/training/medium/cylinders
// https://www.codingame.com/ide/puzzle/cylinders
public class Cylinders {

    public static boolean VERBOSE = false;

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
        final double[] circlesRadiusAsDouble = ListUtils.arrayAsDoubles(circlesRadius);
        final Optional<Double> lengthQuick = getLengthQuick(circlesRadiusAsDouble);
        if (lengthQuick.isPresent()) {
            return lengthQuick.get();
        }
        final FordCircleContainer container = FordCircleContainerBuilder.getFordCircleContainerWithoutOptimization(circlesRadiusAsDouble);
        return container.getMaxX();
    }


    public static double getMinLengthUsingOptimization(final int[] circlesRadius) {
        final double[] circlesRadiusAsDouble = ListUtils.arrayAsDoubles(circlesRadius);
        final Optional<Double> lengthQuick = getLengthQuick(circlesRadiusAsDouble);
        if (lengthQuick.isPresent()) {
            return lengthQuick.get();
        }
        final long startTime = System.currentTimeMillis();
        final FordCircleContainer optimizedContainer = FordCircleContainerBuilder.getFordCircleContainerWithOptimization(circlesRadiusAsDouble);
        final Map<String, Double> results = new HashMap<>();
        final String circleRadiusListString = getStringFromList(optimizedContainer.getCircles().stream()
                .map(Circle::getRadius)
                .collect(Collectors.toList()));
        results.put(circleRadiusListString, optimizedContainer.getMaxX());
        final Map.Entry<String, Double> minResult = results.entrySet().iterator().next();
        final long endTime = System.currentTimeMillis();
        print(circlesRadiusAsDouble, startTime, results, minResult, endTime);
        return minResult.getValue();
    }

    public static double getMinLengthUsingBrutForce(final double[] circlesRadius) {
        final Optional<Double> lengthQuick = getLengthQuick(circlesRadius);
        if (lengthQuick.isPresent()) {
            return lengthQuick.get();
        }
        final long startTime = System.currentTimeMillis();
        final Map<String, Double> results = new HashMap<>();
        final List<List<Double>> allPossiblePermutations = new ArrayList<>();
        ListUtils.collectAllPermutation(allPossiblePermutations, circlesRadius.length, circlesRadius);
        for (final List<Double> aPermutation : allPossiblePermutations) {
            final String circleRadiusListString = getStringFromList(aPermutation);
            if (results.containsKey(circleRadiusListString)) {
                continue;
            }
            results.put(circleRadiusListString,
                    FordCircleContainerBuilder.
                            getFordCircleContainerWithoutOptimization(aPermutation.stream().mapToDouble(d -> d).toArray())
                            .getMaxX());
        }
        final Map.Entry<String, Double> minResult = results.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .orElseThrow();
        final long endTime = System.currentTimeMillis();
        print(circlesRadius, startTime, results, minResult, endTime);
        return minResult.getValue();
    }

    private static String getStringFromList(final List<Double> aListOfNumbers) {
        return aListOfNumbers.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    public static double getMinLengthUsingShuffle(final double[] circlesRadius, final int numberOfTries) {
        final Optional<Double> lengthQuick = getLengthQuick(circlesRadius);
        if (lengthQuick.isPresent()) {
            return lengthQuick.get();
        }
        final long startTime = System.currentTimeMillis();
        final Map<String, Double> shuffleResults = new HashMap<>();
        final List<Double> circlesRadiusList = Arrays.stream(circlesRadius).boxed().collect(Collectors.toList());
        for (int i = 0; i < numberOfTries; i++) {
            Collections.shuffle(circlesRadiusList);
            final String circleRadiusListString = getStringFromList(circlesRadiusList);
            if (shuffleResults.containsKey(circleRadiusListString)) {
                continue;
            }
            final FordCircleContainer container = FordCircleContainerBuilder.
                    getFordCircleContainerWithoutOptimization(circlesRadiusList.stream().mapToDouble(d -> d).toArray());
            shuffleResults.put(circleRadiusListString, container.getMaxX());
        }
        final Map.Entry<String, Double> minResult = shuffleResults.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .orElseThrow();
        final long endTime = System.currentTimeMillis();
        print(circlesRadius, startTime, shuffleResults, minResult, endTime);
        return minResult.getValue();
    }

    private static Optional<Double> getLengthQuick(final double[] circlesRadius) {
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

    private static void print(final double[] circlesRadius, final long startTime, final Map<String, Double> results, final Map.Entry<String, Double> minResult, final long endTime) {
        if (!VERBOSE) {
            return;
        }
        final Map<String, Double> allMinResults = results.entrySet().stream()
                .filter(e -> e.getValue().equals(minResult.getValue()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
        System.err.println("For Input: "
                + Arrays.stream(circlesRadius).boxed().collect(Collectors.toList()).stream()
                .map(String::valueOf).collect(Collectors.joining(", "))
                + "\nMin result found: " + minResult.getValue()
                + "\nfor radius in this order: " + String.join(" | ", allMinResults.keySet()
                .stream().map(String::valueOf).collect(Collectors.joining(", ")))
                + "\nnumber of tries: " + results.size()
                + "\nTime: " + (endTime - startTime) + "ms");
    }
}
