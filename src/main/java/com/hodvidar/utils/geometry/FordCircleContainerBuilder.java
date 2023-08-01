package com.hodvidar.utils.geometry;

import org.assertj.core.util.VisibleForTesting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Builds Ford circles from given circles radius from left to right.
 * All circles touch the axis y=0.
 * With the first circle touching the axis x=0.
 * With the following circles touching the previous one, never overlapping another circle.
 */
public class FordCircleContainerBuilder {

    public static FordCircleContainer getFordCircleContainer(final int[] circlesRadius) {
        if (circlesRadius.length == 0) {
            return new FordCircleContainer(Collections.emptyList());
        }
        final int firstRadius = circlesRadius[0];
        final Circle firstCircle = new Circle(new Point(firstRadius, firstRadius), firstRadius);
        if (circlesRadius.length == 1) {
            return new FordCircleContainer(Collections.singletonList(firstCircle));
        }
        final List<Circle> circles = new ArrayList<>(circlesRadius.length);
        circles.add(firstCircle);
        for (int i = 1; i < circlesRadius.length; i++) {
            final int radius = circlesRadius[i];
            final Circle closeCircle = circles.get(i - 1);
            final Circle newCircle = createNewCircle(circles, closeCircle, true, radius);
            circles.add(newCircle);
        }
        return new FordCircleContainer(circles);
    }

    @VisibleForTesting
    static Circle createNewCircle(final List<Circle> existingCircles,
                                  final Circle closeCircle,
                                  final boolean createNewToTheRight,
                                  final double radiusOfNewCircle) {
        Circle possibleNewCircle = createNewCircle(closeCircle, createNewToTheRight, radiusOfNewCircle);
        if(isCircleTooMuchOnTheLeft(possibleNewCircle)) {
            possibleNewCircle =  new Circle(new Point(radiusOfNewCircle, radiusOfNewCircle), radiusOfNewCircle);
        }
        // check that an existing circle is not closer to this circle
        final List<Circle> circlesToConsider = existingCircles.stream()
                .filter(c -> (createNewToTheRight ? c.getCenter().getX() < closeCircle.getCenter().getX()
                        : c.getCenter().getX() > closeCircle.getCenter().getX()))
                .filter(c -> c.getRadius() > closeCircle.getRadius())
                .collect(Collectors.toList());
        for (final Circle aCircle : circlesToConsider) {
            if (aCircle.isOverLapping(possibleNewCircle)) {
                possibleNewCircle = createNewCircle(aCircle, createNewToTheRight, radiusOfNewCircle);
            }
        }
        return possibleNewCircle;
    }

    private static boolean isCircleTooMuchOnTheLeft(Circle possibleNewCircle) {
        return possibleNewCircle.getWestPoint().getX() < 0;
    }

    private static Circle createNewCircle(final Circle closeCircle,
                                          final boolean createNewToTheRight,
                                          final double radiusOfNewCircle) {
        final double horizontalLengthBetween2FordCirclesCenter =
                getHorizontalLengthBetween2FordCirclesCenter(closeCircle.getRadius(), radiusOfNewCircle);
        final double possibleXofNewCircle = closeCircle.getCenter().getX() +
                (createNewToTheRight ?
                        horizontalLengthBetween2FordCirclesCenter
                        : -horizontalLengthBetween2FordCirclesCenter);
        return new Circle(new Point(possibleXofNewCircle, radiusOfNewCircle), radiusOfNewCircle);
    }


    public static double getHorizontalLengthBetween2FordCirclesCenter(final double circleRadius1,
                                                                      final double circleRadius2) {
        if (circleRadius1 == circleRadius2) {
            return circleRadius1 + circleRadius2;
        }
        final double hypotenuse = circleRadius1 + circleRadius2;
        final double opposite = circleRadius1 - circleRadius2;
        final double sinusAngle = opposite / hypotenuse;
        final double angle = Math.asin(sinusAngle);
        final double adjacent = Math.cos(angle) * hypotenuse;
        return adjacent;
    }

}
