package com.hodvidar.adventofcode.y2023;

import com.hodvidar.utils.geometry.Point;
import com.hodvidar.utils.list.ArrayUtils;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.hodvidar.adventofcode.y2023.Day16.Direction.*;

public class Day16 extends AbstractAdventOfCode2023 {

    protected final List<String> lines = new ArrayList<>();
    protected Grid grid;

    @Override
    public int getDay() {
        return 16;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        lines.clear();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        grid = new Grid(lines);
        return grid.getNumberOfLightedSpaces(0, 0, EAST);
    }

    protected static class Grid {
        public static final boolean LIGHTED_SPACE = true;

        private final Mirror[][] mirrors;
        private final boolean[][] space;

        private final boolean[][][] lightBeamPoints;

        private final List<LightBeam> newLightBeams = new ArrayList<>();


        protected Grid(final List<String> givenCave) {
            final int width = givenCave.get(0).length();
            final int height = givenCave.size();
            mirrors = new Mirror[height][width];
            space = new boolean[height][width];
            lightBeamPoints = new boolean[height][width][4];
            for (int y = 0; y < height; y++) {
                final String line = givenCave.get(y);
                for (int x = 0; x < width; x++) {
                    final char c = line.charAt(x);
                    mirrors[y][x] = Mirror.getMirror(c);
                }
            }
        }

        public int getNumberOfLightedSpaces(final int firstLightBeamCoordinateX,
                                            final int firstLightBeamCoordinateY,
                                            final Direction firstLightBeamDirection) {
            final LightBeam firstLightBeam = new LightBeam(this, firstLightBeamCoordinateX, firstLightBeamCoordinateY, firstLightBeamDirection);
            startLightBean(firstLightBeam);
            return countLightedSpaces();
        }

        private void startLightBean(final LightBeam firstLightBeam) {
            ArrayUtils.resetToFalse(lightBeamPoints);
            ArrayUtils.resetToFalse(space);
            List<LightBeam> lightBeams = new ArrayList<>();
            lightBeams.add(firstLightBeam);
            updateGrid(lightBeams);
            while (!lightBeams.isEmpty()) {
                lightBeams.forEach(LightBeam::moveOneStep);
                lightBeams.addAll(newLightBeams);
                updateGrid(lightBeams);
                newLightBeams.clear();
                lightBeams = lightBeams.stream().filter(lb -> !lb.isFinished()).collect(Collectors.toList());
            }
        }

        private int countLightedSpaces() {
            return (int) Arrays.stream(space)
                    .flatMapToLong(row -> IntStream.range(0, row.length).mapToLong(i -> row[i] ? 1 : 0))
                    .filter(value -> value == 1)
                    .count();
        }

        private void updateGrid(final List<LightBeam> lightBeams) {
            lightBeams.stream().filter(lb -> !lb.isOut()).forEach(lb -> updateGrid(lb.currentX, lb.currentY));
        }

        private void updateGrid(final int x, final int y) {
            space[y][x] = LIGHTED_SPACE;
        }


        private Mirror getMirror(final int x, final int y) {
            return mirrors[y][x];
        }

        protected boolean isLooping(final LightBeamPoint lightBeamPoint) {
            return lightBeamPoints
                    [lightBeamPoint.getSimpleY()]
                    [lightBeamPoint.getSimpleX()]
                    [lightBeamPoint.getDirection().getIndex()];
        }

        protected void addLightBeamPoint(final LightBeamPoint lightBeamPoint) {
            lightBeamPoints
                    [lightBeamPoint.getSimpleY()]
                    [lightBeamPoint.getSimpleX()]
                    [lightBeamPoint.getDirection().getIndex()] = true;
        }
    }

    private static class LightBeam {

        private final Grid grid;
        private final int maxY;
        private final int maxX;

        private int currentX;
        private int currentY;
        private Direction currentDirection;

        private LightBeamPoint currentPoint;

        public LightBeam(final Grid grid, final int x, final int y, final Direction direction) {
            this.grid = grid;
            this.maxY = grid.mirrors.length - 1;
            this.maxX = grid.mirrors[0].length - 1;
            this.currentDirection = direction;
            this.currentPoint = new LightBeamPoint(x, y, direction);
            this.currentX = x;
            this.currentY = y;
        }

        public void moveOneStep() {
            grid.addLightBeamPoint(this.currentPoint);
            final Mirror mirror = grid.getMirror(currentX, currentY);
            switch (mirror) {
                case EMPTY_SPACE:
                    moveOneStepInDirection();
                    break;
                case SPLITTER_NORTH_SOUTH, SPLITTER_EAST_WEST:
                    handleSplitter(mirror);
                    moveOneStepInDirection();
                    break;
                case MIRROR_NORTH_EAST_SOUTH_WEST:
                    handleMirrorOne();
                    moveOneStepInDirection();
                    break;
                case MIRROR_NORTH_WEST_SOUTH_EAST:
                    handleMirrorTwo();
                    moveOneStepInDirection();
                    break;
                default:
                    throw new IllegalStateException("Unknown mirror: " + mirror);
            }
        }

        private void moveOneStepInDirection() {
            if (isAgainstWall()) {
                return;
            }
            switch (currentDirection) {
                case NORTH -> currentY--;
                case SOUTH -> currentY++;
                case WEST -> currentX--;
                case EAST -> currentX++;
            }
            currentPoint = new LightBeamPoint(currentX, currentY, currentDirection);
        }

        private void handleSplitter(final Mirror splitter) {
            if (splitter == Mirror.SPLITTER_NORTH_SOUTH
                    && (currentDirection == EAST || currentDirection == WEST)) {
                createAnotherLightBeam(SOUTH);
                currentDirection = NORTH;
            }
            if (splitter == Mirror.SPLITTER_EAST_WEST
                    && (currentDirection == NORTH || currentDirection == SOUTH)) {
                createAnotherLightBeam(EAST);
                currentDirection = WEST;
            }
        }

        private void createAnotherLightBeam(final Direction direction) {
            final LightBeam newLightBeam = new LightBeam(grid, currentX, currentY, direction);
            newLightBeam.moveOneStep();
            grid.newLightBeams.add(newLightBeam);
        }

        private void handleMirrorOne() {
            handleMirror(EAST, SOUTH, WEST, NORTH);
        }

        private void handleMirrorTwo() {
            handleMirror(WEST, NORTH, EAST, SOUTH);
        }

        private void handleMirror(final Direction direction,
                                  final Direction direction2,
                                  final Direction direction3,
                                  final Direction direction4) {
            if (currentDirection == NORTH) {
                currentDirection = direction;
                return;
            }
            if (currentDirection == WEST) {
                currentDirection = direction2;
                return;
            }
            if (currentDirection == SOUTH) {
                currentDirection = direction3;
                return;
            }
            if (currentDirection == EAST) {
                currentDirection = direction4;
            }
        }

        public boolean isFinished() {
            return isAgainstWall() || isLooping();
        }

        private boolean isAgainstWall() {
            if (isOut()) {
                return true;
            }
            final Mirror mirror = grid.getMirror(currentX, currentY);
            if (mirror == Mirror.EMPTY_SPACE || mirror == Mirror.SPLITTER_NORTH_SOUTH) {
                if (currentDirection == NORTH && currentY == 0) {
                    return true;
                }
                if (currentDirection == SOUTH && currentY == maxY) {
                    return true;
                }
            }
            if (mirror == Mirror.EMPTY_SPACE || mirror == Mirror.SPLITTER_EAST_WEST) {
                if (currentDirection == WEST && currentX == 0) {
                    return true;
                }
                return currentDirection == EAST && currentX == maxX;
            }
            return false;
        }

        private boolean isOut() {
            return currentX < 0 || currentX > maxX || currentY < 0 || currentY > maxY;
        }

        public boolean isLooping() {
            return grid.isLooping(this.currentPoint);
        }
    }

    protected static class LightBeamPoint extends Point {
        private final Direction direction;

        private LightBeamPoint(final int x, final int y, final Direction direction) {
            super(x, y);
            this.direction = direction;
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof final LightBeamPoint other)) {
                return false;
            }
            return this.x == other.x && this.y == other.y && this.direction == other.direction;
        }

        @Override
        public int hashCode() {
            return Objects.hash(this.x, this.y, this.direction);
        }

        @Override
        public String toString() {
            return "{" + x + "; " + y + "; " + direction + '}';
        }

        public int getSimpleX() {
            return (int) x;
        }

        public int getSimpleY() {
            return (int) y;
        }

        public Direction getDirection() {
            return direction;
        }
    }

    public enum Direction {
        NORTH, SOUTH, WEST, EAST;

        public int getIndex() {
            return switch (this) {
                case NORTH -> 0;
                case SOUTH -> 1;
                case WEST -> 2;
                case EAST -> 3;
            };
        }
    }

    private enum Mirror {
        SPLITTER_NORTH_SOUTH,
        SPLITTER_EAST_WEST,
        MIRROR_NORTH_EAST_SOUTH_WEST,
        MIRROR_NORTH_WEST_SOUTH_EAST,
        EMPTY_SPACE;

        public static Mirror getMirror(final char c) {
            return switch (c) {
                case '|' -> SPLITTER_NORTH_SOUTH;
                case '-' -> SPLITTER_EAST_WEST;
                case '/' -> MIRROR_NORTH_EAST_SOUTH_WEST;
                case '\\' -> MIRROR_NORTH_WEST_SOUTH_EAST;
                case '.' -> EMPTY_SPACE;
                default -> throw new IllegalArgumentException("Unknown mirror: " + c);
            };
        }
    }

}
