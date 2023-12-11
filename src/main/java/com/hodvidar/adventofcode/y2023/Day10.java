package com.hodvidar.adventofcode.y2023;

import com.hodvidar.utils.geometry.Point;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static com.hodvidar.adventofcode.y2023.Day10.Pipe.getPipe;

public class Day10 extends AbstractAdventOfCode2023 {

    protected PipeNetwork network;

    @Override
    public int getDay() {
        return 10;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        network = getNetwork();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        return network.getMaxDistance();
    }

    protected PipeNetwork getNetwork() {
        return new PipeNetwork();
    }

    @Override
    protected void digestLine(final String line) {
        network.addLine(line);
    }

    protected class PipeNetwork {

        public static final char START = 'S';

        private final List<String> lines = new ArrayList<>();
        protected char[][] map;

        protected PipeNavigator navigator1;
        protected PipeNavigator navigator2;


        public void addLine(final String line) {
            lines.add(line);
        }

        public void buildMap() {
            map = lines.stream().map(String::toCharArray).toArray(char[][]::new);
        }

        public int getMaxDistance() {
            if (map == null) {
                buildMap();
            }
            final Point start = findStart();
            final Direction[] startDirection = getStartDirection(start);
            navigator1 = getNavigator(start, startDirection[0]);
            navigator2 = getNavigator(start, startDirection[1]);
            while (!(navigator1.getX() == navigator2.getX() && navigator1.getY() == navigator2.getY())) {
                navigator1.goToNext();
                navigator2.goToNext();
            }
            return navigator1.getCurrentDistance();
        }

        protected PipeNavigator getNavigator(final Point start, final Direction from) {
            return new PipeNavigator(this, start, from);
        }

        private Direction[] getStartDirection(final Point start) {
            final Direction[] startDirection = new Direction[2];
            int index = 0;
            // West ?
            if (start.x > 0) {
                final Pipe westPipe = Pipe.getPipe(map[(int) start.y][(int) start.x - 1]);
                if (westPipe == Pipe.HORIZONTAL || westPipe == Pipe.CORNER_NE || westPipe == Pipe.CORNER_SE) {
                    startDirection[index] = Direction.EAST;
                    index++;
                }
            }
            // North ?
            if (start.y > 0) {
                final Pipe northPipe = Pipe.getPipe(map[(int) start.y - 1][(int) start.x]);
                if (northPipe == Pipe.VERTICAL || northPipe == Pipe.CORNER_SW || northPipe == Pipe.CORNER_SE) {
                    startDirection[index] = Direction.SOUTH;
                    index++;
                }
            }
            if (index == 2) {
                return startDirection;
            }
            // East ?
            if (start.x < map[0].length - 1) {
                final Pipe eastPipe = Pipe.getPipe(map[(int) start.y][(int) start.x + 1]);
                if (eastPipe == Pipe.HORIZONTAL || eastPipe == Pipe.CORNER_NW || eastPipe == Pipe.CORNER_SW) {
                    startDirection[index] = Direction.WEST;
                    index++;
                }
            }
            if (index == 2) {
                return startDirection;
            }
            // South ?
            if (start.y < map.length - 1) {
                final Pipe southPipe = Pipe.getPipe(map[(int) start.y + 1][(int) start.x]);
                if (southPipe == Pipe.VERTICAL || southPipe == Pipe.CORNER_NW || southPipe == Pipe.CORNER_NE) {
                    startDirection[index] = Direction.NORTH;
                }
            }
            return startDirection;
        }


        private Point findStart() {
            for (int i = 0; i < map.length; i++) {
                for (int j = 0; j < map[i].length; j++) {
                    if (map[i][j] == START) {
                        return new Point(j, i);
                    }
                }
            }
            throw new IllegalStateException("No start found");
        }
    }

    protected class PipeNavigator {

        protected final char[][] map;
        protected int x;
        protected int y;

        private Direction from;

        private int currentDistance = 1;


        public PipeNavigator(final PipeNetwork network, final Point start, final Direction from) {
            this.map = network.map;
            this.x = (int) start.x;
            this.y = (int) start.y;
            this.from = from;
            applyDirection(from.getOpposite());
        }

        public void goToNext() {
            final char c = map[y][x];
            final Direction next = from.getNext(c);
            applyDirection(next);
            from = next.getOpposite();
            currentDistance++;
        }

        private void applyDirection(final Direction next) {
            switch (next) {
                case NORTH -> y--;
                case SOUTH -> y++;
                case EAST -> x++;
                case WEST -> x--;
            }
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getCurrentDistance() {
            return currentDistance;
        }


    }

    protected enum Direction {
        NORTH, SOUTH, EAST, WEST;

        public Direction getNext(final char c) {
            return getPipe(c).getNext(this);
        }

        public Direction getOpposite() {
            return switch (this) {
                case NORTH -> SOUTH;
                case SOUTH -> NORTH;
                case EAST -> WEST;
                case WEST -> EAST;
            };
        }
    }

    protected enum Pipe {
        VERTICAL(), HORIZONTAL(), CORNER_NE(), CORNER_NW(), CORNER_SW(), CORNER_SE(), NONE();

        public static Pipe getPipe(final char c) {
            return switch (c) {
                case '|' -> VERTICAL;
                case '-' -> HORIZONTAL;
                case 'L' -> CORNER_NE;
                case 'J' -> CORNER_NW;
                case '7' -> CORNER_SW;
                case 'F' -> CORNER_SE;
                default -> NONE;
            };
        }

        public Direction getNext(final Direction from) {
            return switch (this) {
                case VERTICAL -> from == Direction.NORTH ? Direction.SOUTH : Direction.NORTH;
                case HORIZONTAL -> from == Direction.EAST ? Direction.WEST : Direction.EAST;
                case CORNER_NE -> from == Direction.NORTH ? Direction.EAST : Direction.NORTH;
                case CORNER_NW -> from == Direction.NORTH ? Direction.WEST : Direction.NORTH;
                case CORNER_SW -> from == Direction.SOUTH ? Direction.WEST : Direction.SOUTH;
                case CORNER_SE -> from == Direction.SOUTH ? Direction.EAST : Direction.SOUTH;
                case NONE -> throw new IllegalStateException("No next direction for NONE");
            };
        }


    }
}
