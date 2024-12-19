package com.hodvidar.adventofcode.y2023;

import com.hodvidar.utils.geometry.Point;

import java.util.*;
import java.util.stream.IntStream;

public class Day11 extends AbstractAdventOfCode2023 {
    public static final double SPACE_EXPANSION_RATE = 2d;
    SpaceImage image;

    @Override
    public int getDay() {
        return 11;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        image = getSpaceImage();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        return image.computeTotalDistanceBetweenEachGalaxyPair();
    }

    protected SpaceImage getSpaceImage() {
        return new SpaceImage(SPACE_EXPANSION_RATE);
    }

    @Override
    protected void digestLine(final String line) {
        image.addLine(line);
    }

    protected static class SpaceImage {

        public static final char GALAXY = '#';
        private final double spaceExpansionRate;
        private final List<char[]> horizontalLines = new ArrayList<>();
        private final List<Double> horizontalLineIndexes = new ArrayList<>();
        private final List<char[]> verticalLines = new ArrayList<>();
        private final List<Double> verticalLineIndexes = new ArrayList<>();
        private final List<Point> galaxies = new ArrayList<>();
        private double currentHorizontalLineIndex = 0;
        private double currentVerticalLineIndex = 0;

        protected SpaceImage(final double spaceExpansionRate) {
            this.spaceExpansionRate = spaceExpansionRate;
        }

        private static Point findNearestPoint(final Point current, final Set<Point> unvisited) {
            Point nearest = null;
            double nearestDistance = Double.MAX_VALUE;
            for (final Point point : unvisited) {
                final double distance = current.getManhattanDistanceTo(point);
                if (distance < nearestDistance) {
                    nearest = point;
                    nearestDistance = distance;
                }
            }
            return nearest;
        }

        public void addLine(final String line) {
            final char[] chars = line.toCharArray();
            horizontalLines.add(chars);
            if (IsLineEmptySpace(chars)) {
                currentHorizontalLineIndex += spaceExpansionRate;
            } else {
                currentHorizontalLineIndex += 1;
            }
            horizontalLineIndexes.add(currentHorizontalLineIndex);
        }

        public double computeTotalDistanceBetweenEachGalaxyPair() {
            buildVerticalLines();
            buildGalaxyPositions();
            return computeTotalDistance(galaxies);
        }

        public double computeTotalDistance(final List<Point> points) {
            double totalDistance = 0;
            for (int i = 0; i < points.size(); i++) {
                for (int j = i + 1; j < points.size(); j++) {
                    final Point p1 = points.get(i);
                    final Point p2 = points.get(j);
                    totalDistance += p1.getManhattanDistanceTo(p2);
                }
            }
            return totalDistance;
        }

        private void buildVerticalLines() {
            if (!verticalLines.isEmpty()) {
                return;
            }
            final int nbLines = horizontalLines.size();
            final int nbChars = horizontalLines.get(0).length;
            final char[][] ninetyDegreesPivotImage = new char[nbChars][nbLines];
            for (int i = 0; i < nbLines; i++) {
                final char[] horizontalLine = horizontalLines.get(i);
                for (int j = 0; j < nbChars; j++) {
                    ninetyDegreesPivotImage[j][i] = horizontalLine[j];
                }
            }
            for (int i = 0; i < nbChars; i++) {
                final char[] verticalLine = ninetyDegreesPivotImage[i];
                verticalLines.add(verticalLine);
                if (IsLineEmptySpace(verticalLine)) {
                    currentVerticalLineIndex += spaceExpansionRate;
                } else {
                    currentVerticalLineIndex += 1;
                }
                verticalLineIndexes.add(currentVerticalLineIndex);
            }
        }

        private void buildGalaxyPositions() {
            if (!galaxies.isEmpty()) {
                return;
            }
            for (int y = 0; y < horizontalLines.size(); y++) {
                for (int x = 0; x < horizontalLines.get(y).length; x++) {
                    if (verticalLines.get(y)[x] == GALAXY) {
                        final double xIndex = horizontalLineIndexes.get(x);
                        final double yIndex = verticalLineIndexes.get(y);
                        galaxies.add(new Point(xIndex, yIndex));
                    }
                }
            }
        }

        private boolean IsLineEmptySpace(final char[] verticalLine) {
            return IntStream.range(0, verticalLine.length).allMatch(j -> verticalLine[j] == '.');
        }

        // ############ NOT USED ############
        public double computeShortestPathBetweenGalaxies() {
            buildVerticalLines();
            buildGalaxyPositions();
            return nearestNeighborPath(galaxies);
        }

        private double nearestNeighborPath(final List<Point> points) {
            final Set<Point> unvisited = new HashSet<>(points);
            Point current = points.get(0);
            unvisited.remove(current);
            double totalDistance = 0;
            while (!unvisited.isEmpty()) {
                final Point nearest = findNearestPoint(current, unvisited);
                totalDistance += current.getManhattanDistanceTo(nearest);
                current = nearest;
                unvisited.remove(nearest);
            }
            return totalDistance;
        }
    }
}
