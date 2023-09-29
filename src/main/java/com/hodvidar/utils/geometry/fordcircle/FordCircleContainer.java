package com.hodvidar.utils.geometry.fordcircle;

import com.hodvidar.utils.geometry.Circle;
import com.hodvidar.utils.geometry.GeometryServices;
import com.hodvidar.utils.geometry.Point;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Ford Circles
 * All circles touch the axis y=0.
 * With the first circle touching the axis x=0.
 * With the following circles touching the previous one, never overlapping another circle.
 */
public class FordCircleContainer {

    private List<Circle> circles;

    /**
     * Ford circles unordered.
     */
    private final List<FordCircle> fordCircles;

    public FordCircleContainer(final List<Circle> circles) {
        this.circles = circles;
        this.fordCircles = new ArrayList<>();
    }

    public FordCircleContainer() {
        // For optimization build using addCircle(double)
        this.fordCircles = new ArrayList<>();
    }

    public void optimizeRadiusPosition(final List<Double> circleRadiusSortedBigToSmall) {
        final List<Double> circlesRadiusCopy = new ArrayList<>(circleRadiusSortedBigToSmall.size());
        circlesRadiusCopy.addAll(circleRadiusSortedBigToSmall);
        addCircle(circlesRadiusCopy.get(0));
        circlesRadiusCopy.remove(0);
        addCircle(circlesRadiusCopy.get(0));
        circlesRadiusCopy.remove(0);
        // add one of the remaining radius that will widen the less the current intervals
        while (!circlesRadiusCopy.isEmpty()) {
            double lowestDifferenceForCurrentAllRadius = Double.MAX_VALUE;
            Double radiusToAdd = null;
            int indexToRemove = 0;
            for (int i = 0; i < circlesRadiusCopy.size(); i++) {
                final Double currentRadius = circlesRadiusCopy.get(i);
                final double lowestDifferenceForCurrentRadius = getLowestDifferenceBetweenRadiusAndExistingInterval(currentRadius);
                System.err.println("lowestDifferenceForCurrentRadius (" + currentRadius + ") --> " + lowestDifferenceForCurrentRadius);
                if (lowestDifferenceForCurrentRadius < lowestDifferenceForCurrentAllRadius) {
                    lowestDifferenceForCurrentAllRadius = lowestDifferenceForCurrentRadius;
                    radiusToAdd = currentRadius;
                    indexToRemove = i;
                }
            }
            System.err.println("addCircle " + radiusToAdd);
            addCircle(radiusToAdd);
            circlesRadiusCopy.remove(indexToRemove);
            lowestDifferenceForCurrentAllRadius = Double.MAX_VALUE;
            radiusToAdd = null;
            indexToRemove = 0;
        }
    }

    private double getLowestDifferenceBetweenRadiusAndExistingInterval(final Double radius) {
        final List<FordCircleInterval> fordCircleIntervals = getIntervals();
        double minDifference = Double.MAX_VALUE;
        for (final FordCircleInterval interval : fordCircleIntervals) {
            final double difference = Math.abs(interval.maxCircleRadiusPossible() - radius);
            if (difference < minDifference) {
                minDifference = difference;
            }
        }
        return minDifference;
    }

    public void addCircle(final Double circleRadius) {
        if (fordCircles.isEmpty()) {
            final FordCircle firstCircle = new FordCircle(circleRadius);
            firstCircle.setPosition(0);
            fordCircles.add(firstCircle);
            return;
        }
        if (fordCircles.size() == 1) {
            final FordCircle secondCircle = new FordCircle(circleRadius);
            secondCircle.setPosition(1);
            fordCircles.add(secondCircle);
            return;
        }
        final int position = getPositionAndUpdateThem(circleRadius);
        final FordCircle newCircle = new FordCircle(circleRadius);
        newCircle.setPosition(position);
        fordCircles.add(newCircle);
    }

    private int getPositionAndUpdateThem(final Double circleRadius) {
        // find the best position, look for interval with the closest radius
        final List<FordCircleInterval> fordCircleIntervals = getIntervals();
        double minDistance = Double.MAX_VALUE;
        FordCircleInterval bestInterval = null;
        for (final FordCircleInterval interval : fordCircleIntervals) {
            final double difference = Math.abs(interval.maxCircleRadiusPossible() - circleRadius);
            if (difference < minDistance) {
                minDistance = difference;
                bestInterval = interval;
            }
        }
        assert bestInterval != null;
        // add the circle with the correct position using the interval
        // then update all positions affected
        final int position = bestInterval.getLeftCircle().isPresent() ?
                bestInterval.getLeftCircle().get().getPosition() + 1 :
                bestInterval.getRightCircle().get().getPosition();

        fordCircles.stream()
                .filter(c -> c.getPosition() >= position)
                .forEach(c -> c.setPosition(c.getPosition() + 1));
        System.err.println("Add new position for circle radius=" + circleRadius + " --> " + position + " (between circles "
                + intervalToString(bestInterval));
        return position;
    }

    private static String intervalToString(FordCircleInterval fordCircleInterval) {
        String r = "left: ";
        r += fordCircleInterval.getLeftCircle().isPresent() ?
                fordCircleInterval.getLeftCircle().get().getRadius()
                : "null";
        r += " | right: ";
        r += fordCircleInterval.getRightCircle().isPresent() ?
                fordCircleInterval.getRightCircle().get().getRadius()
                : "null";
        return r;
    }

    private List<FordCircleInterval> getIntervals() {
        final List<FordCircle> fordCirclesInOrder = getFordCirclesInOrder();
        final List<FordCircleInterval> intervals = new ArrayList<>();
        // first interval
        intervals.add(new FordCircleInterval(null, fordCirclesInOrder.get(0)));
        final int lastElementIndex = fordCirclesInOrder.size() - 1;
        for (int i = 0; i < lastElementIndex; i++) {
            final FordCircle firstCircle = fordCirclesInOrder.get(i);
            final FordCircle secondCircle = fordCirclesInOrder.get(i + 1);
            final FordCircleInterval interval = new FordCircleInterval(firstCircle, secondCircle);
            intervals.add(interval);
        }
        // last interval
        intervals.add(new FordCircleInterval(fordCirclesInOrder.get(lastElementIndex), null));
        intervals.forEach(FordCircleInterval::maxCircleRadiusPossible);
        return intervals;
    }

    private List<FordCircle> getFordCirclesInOrder() {
        return this.fordCircles.stream().sorted(Comparator.comparingInt(FordCircle::getPosition)).collect(Collectors.toList());
    }


    // to use after addCircle
    public void buildCirclesUsingFordCirclePositions() {
        final List<FordCircle> fordCirclesOrdered = getFordCirclesInOrder();
        final FordCircleContainer subContainer = FordCircleContainerBuilder.getFordCircleContainerWithoutOptimization(
                fordCirclesOrdered.stream()
                        .mapToDouble(FordCircle::getRadius)
                        .toArray());
        this.circles = subContainer.getCircles();
    }


    public double getMaxX() {
        if (circles.isEmpty()) {
            return 0;
        }
        return circles.stream().mapToDouble(c -> c.getEastPoint().getX()).max().getAsDouble();
    }

    public List<Circle> getCircles() {
        return new ArrayList<>(circles);
    }

    public String getCirclesAsString() {
        return getCircles().stream().map(String::valueOf).collect(Collectors.joining(","));
    }

    /**
     * Returns the sum of the angles between circles, one angle between two adjacent circle is the absolut angle from
     * the center of the first circle to the center of the next circle compared to the X axis.
     *
     * @return
     */
    public double getSumOfAnglesBetweenCircles() {
        double angleSum = 0.0;
        Circle previousCircle = null;
        for (final Circle c : circles) {
            if (previousCircle == null) {
                previousCircle = c;
                continue;
            }
            final double absolutAngle = Math.abs(
                    GeometryServices.getAngle(
                            previousCircle.getCenter(),
                            new Point(previousCircle.getCenter().getX(),
                                    previousCircle.getCenter().getY() - 1),
                            c.getCenter()));
            angleSum += absolutAngle;
            previousCircle = c;
        }
        return angleSum;
    }
}
