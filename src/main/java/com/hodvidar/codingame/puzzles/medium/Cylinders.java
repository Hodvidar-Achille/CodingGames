package com.hodvidar.codingame.puzzles.medium;

import java.util.Arrays;

// https://www.codingame.com/training/medium/cylinders
// https://www.codingame.com/ide/puzzle/cylinders
public class Cylinders {

    private Cylinders() {
        throw new IllegalStateException("Utility class");
    }

    public static final double pi = 3.141;
    public static double getLength(final int[] circlesRadius) {
        if(circlesRadius.length == 0) {
            return 0.0;
        }
        if(Arrays.stream(circlesRadius).anyMatch(d -> d <= 0)) {
            throw new IllegalArgumentException("Radius must be positive.");
        }
        if(circlesRadius.length == 1) {
            return circlesRadius[0];
        }
        if(Arrays.stream(circlesRadius).allMatch(d -> d == circlesRadius[0])) {
            return Arrays.stream(circlesRadius).sum();
        }
        if(circlesRadius.length == 2) {
            final double leftLength = circlesRadius[0];
            final double rightLength = circlesRadius[1];
            final double maxLength = Math.max(leftLength, rightLength);
            final double minLength = Math.min(leftLength, rightLength);
            final double middleLength = getHorizontalLengthBetween2FordCirclesCenter(maxLength, minLength);
            return Math.max(
                    leftLength + middleLength + rightLength,
                    maxLength * 2);
        }
        return Arrays.stream(circlesRadius).sum();
    }

    public static double getHorizontalLengthBetween2FordCirclesCenter(final double circleRadius1,
                                                                       final double circleRadius2) {
        if(circleRadius1 == circleRadius2) {
            return circleRadius1 + circleRadius2;
        }
        final double hypotenuse = circleRadius1 + circleRadius2;
        final double opposite = circleRadius1 - circleRadius2;
        final double sinus = opposite / hypotenuse;
        final double angle = Math.asin(sinus);
        final double adjacent = Math.cos(angle) * hypotenuse;
        return adjacent;
    }

}
