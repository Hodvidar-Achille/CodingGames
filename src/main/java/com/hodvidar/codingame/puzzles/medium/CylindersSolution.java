package com.hodvidar.codingame.puzzles.medium;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

// Copy from Solution class at
// https://www.codingame.com/ide/puzzle/cylinders
class CylindersSolution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String[] result = new String[n];
        DecimalFormat formatter = new DecimalFormat("0.000", DecimalFormatSymbols.getInstance(Locale.ENGLISH));
        formatter.setRoundingMode(RoundingMode.HALF_EVEN);
        for (int i = 0; i < n; i++) {
            String line = in.nextLine();
            int[] lineArray = extractNumber(line).stream().mapToInt(Double::intValue).toArray();
            // We remove the first element, not a circle radius
            int[] circleRadius = Arrays.copyOfRange(lineArray, 1, lineArray.length);
            result[i] = formatter.format(getMinLength(circleRadius));
        }

        String answer = result[0];
        for (int i = 1; i < n; i++) {
            answer += "\n" + result[i];
        }

        System.out.println(answer);
    }

    private static final int NUMBER_OF_TRIES = 30000;

    public static List<Double> extractNumber(final String s) {
        final List<Double> numbers = new ArrayList<>();
        final Pattern p = Pattern.compile("-?\\d+(\\.\\d+)?");
        final Matcher m = p.matcher(s);
        while (m.find()) {
            final String a = m.group();
            final Double b = Double.parseDouble(a);
            numbers.add(b);
        }
        return numbers;
    }

    private static double getMinLength(final int[] circlesRadius) {
        final Optional<Double> lengthQuick = getLengthQuick(circlesRadius);
        if (lengthQuick.isPresent()) {
            return lengthQuick.get();
        }
        final Map<String, Double> shuffleResults = new HashMap<>();
        final List<Integer> circlesRadiusList = Arrays.stream(circlesRadius).boxed().collect(Collectors.toList());
        for (int i = 0; i < NUMBER_OF_TRIES; i++) {
            Collections.shuffle(circlesRadiusList);
            final String circleRadiusListString = circlesRadiusList.stream()
                    .map(String::valueOf)
                    .collect(Collectors.joining(","));
            if (shuffleResults.containsKey(circleRadiusListString)) {
                continue;
            }
            shuffleResults.put(circleRadiusListString,
                    CylindersSolution.FordCircleContainerBuilder
                            .getFordCircleContainer(circlesRadiusList.stream().mapToInt(it -> it).toArray()).getMaxX());
        }
        final Map.Entry<String, Double> minResult = shuffleResults.entrySet().stream()
                .min(Comparator.comparingDouble(Map.Entry::getValue))
                .orElseThrow();
        System.err.println("For Input: "
                + Arrays.stream(circlesRadius).boxed().collect(Collectors.toList()).stream()
                .map(String::valueOf).collect(Collectors.joining(","))
                + "\nMin result found: " + minResult.getValue()
                + "\n for radius in this order: " + minResult.getKey()
                + "\nnumber of tries: " + shuffleResults.size());
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


    public static class FordCircleContainerBuilder {

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


        private static Circle createNewCircle(final List<Circle> existingCircles,
                                              final Circle closeCircle,
                                              final boolean createNewToTheRight,
                                              final double radiusOfNewCircle) {
            Circle possibleNewCircle = createNewCircle(closeCircle, createNewToTheRight, radiusOfNewCircle);
            if (isCircleTooMuchOnTheLeft(possibleNewCircle)) {
                possibleNewCircle = new Circle(new Point(radiusOfNewCircle, radiusOfNewCircle), radiusOfNewCircle);
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


        private static double getHorizontalLengthBetween2FordCirclesCenter(final double circleRadius1,
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

    static class FordCircleContainer {

        private final List<Circle> circles;

        public FordCircleContainer(final List<Circle> circles) {
            this.circles = circles;
        }

        public double getMaxX() {
            if (circles.isEmpty()) {
                return 0;
            }
            return circles.stream().mapToDouble(c -> c.getEastPoint().getX()).max().getAsDouble();
        }
    }

    static class Circle {
        /**
         * Coordinate X,Y of the circle's center.
         */
        public final Point center;
        /**
         * unit : u.
         */
        public final double radius;

        public Circle(final double radius) {
            this.center = new Point(0, 0);
            this.radius = radius;
        }

        public Circle(final Point center, final double radius) {
            this.center = center;
            this.radius = radius;
        }


        public boolean isOverLapping(final Circle c) {
            return getDistance(this.getCenter(), c.getCenter())
                    < this.getRadius() + c.getRadius();
        }

        private double getDistance(final Point p1, final Point p2) {
            final double x = Math.pow((p1.x - p2.x), 2);
            final double y = Math.pow((p1.y - p2.y), 2);

            return Math.sqrt(x + y);
        }

        public Point getCenter() {
            return this.center;
        }

        public double getRadius() {
            return this.radius;
        }

        public Point getNorthPoint() {
            return new Point(this.center.x, this.center.y + this.radius);
        }

        public Point getEastPoint() {
            return new Point(this.center.x + this.radius, this.center.y);
        }

        public Point getSouthPoint() {
            return new Point(this.center.x, this.center.y - this.radius);
        }

        public Point getWestPoint() {
            return new Point(this.center.x - this.radius, this.center.y);
        }
    }

    static class Point {
        public final double x;
        public final double y;

        public Point(final double x, final double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(final Object obj) {
            if (obj == null)
                return false;
            if (!(obj instanceof Point))
                return false;

            final Point p = (Point) obj;
            return this.x == p.x && this.y == p.y;
        }

        @Override
        public int hashCode() {
            return (int) (31 * this.x + 89 * this.y);
        }

        @Override
        public String toString() {
            return "(" + this.x + "; " + this.y + ")";
        }

        public double getX() {
            return this.x;
        }

        public double getY() {
            return this.y;
        }
    }
}
