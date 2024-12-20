package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day06 extends AbstractAdventOfCode2024 {

    private static Cell[][] calculatePath(final char[][] grid) {
        final int rows = grid.length;
        final int cols = grid[0].length;
        final Cell[][] pathGrid = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pathGrid[i][j] = new Cell();
            }
        }

        Direction currentDirection = null;
        int row = -1, col = -1;

        // Find the starting position and direction
        outer:
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final char c = grid[i][j];
                switch (c) {
                    case '^':
                        currentDirection = Direction.NORTH;
                        break;
                    case '>':
                        currentDirection = Direction.EAST;
                        break;
                    case 'v':
                        currentDirection = Direction.SOUTH;
                        break;
                    case '<':
                        currentDirection = Direction.WEST;
                        break;
                }
                if (currentDirection != null) {
                    row = i;
                    col = j;
                    break outer;
                }
            }
        }

        if (currentDirection == null) {
            throw new IllegalStateException("No starting position found in the grid.");
        }

        int step = 1;
        pathGrid[row][col].addStep(step++, currentDirection);

        while (true) {
            final int nextRow = row + currentDirection.getRowDelta();
            final int nextCol = col + currentDirection.getColDelta();

            // Check if the next step is out of bounds
            if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols) {
                break;
            }

            // Check if the next cell is an obstacle
            if (grid[nextRow][nextCol] == '#') {
                currentDirection = currentDirection.turnRight();
                continue;
            }

            // Mark the current cell and move
            pathGrid[nextRow][nextCol].addStep(step++, currentDirection);
            row = nextRow;
            col = nextCol;
        }

        return pathGrid;
    }

    private static int countNonZeroCells(final Cell[][] grid) {
        int count = 0;
        for (final Cell[] row : grid) {
            for (final Cell cell : row) {
                if (!cell.steps.isEmpty()) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        final List<char[]> gridList = new ArrayList<>();

        // Build grid row by row
        while (sc.hasNext()) {
            final String line = sc.nextLine();
            gridList.add(line.toCharArray());
        }

        // Finalize grid
        final char[][] grid = gridList.toArray(new char[0][0]);

        // Create the path grid
        final Cell[][] pathGrid = calculatePath(grid);

        // Count non-zero cells

        return countNonZeroCells(pathGrid);
    }

    @Override
    public int getDay() {
        return 6;
    }

    enum Direction {
        NORTH(-1, 0),
        EAST(0, 1),
        SOUTH(1, 0),
        WEST(0, -1);

        private final int rowDelta;
        private final int colDelta;

        Direction(final int rowDelta, final int colDelta) {
            this.rowDelta = rowDelta;
            this.colDelta = colDelta;
        }

        public int getRowDelta() {
            return rowDelta;
        }

        public int getColDelta() {
            return colDelta;
        }

        public Direction turnRight() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }

    static class Cell {
        private final List<Integer> steps = new ArrayList<>();
        private final List<Direction> directions = new ArrayList<>();

        public void addStep(final int step, final Direction direction) {
            steps.add(step);
            directions.add(direction);
        }

        @Override
        public String toString() {
            return "Steps: " + steps + ", Directions: " + directions;
        }
    }

}
