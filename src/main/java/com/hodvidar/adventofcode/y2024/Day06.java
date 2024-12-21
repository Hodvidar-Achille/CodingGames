package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day06 extends AbstractAdventOfCode2024 {

    protected static List<Cell> orderedCells = new ArrayList<>();
    protected char[][] grid;
    protected Cell[][] pathGrid;

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

    /**
     * returns array of 2
     **/
    protected static int[] findStartingPoint(final char[][] initialGrid) {
        final int[] result = new int[2]; // [row, col]

        for (int i = 0; i < initialGrid.length; i++) {
            for (int j = 0; j < initialGrid[0].length; j++) {
                final char c = initialGrid[i][j];
                if (c == '<' || c == '>' || c == 'v' || c == '^') {
                    result[0] = i;
                    result[1] = j;
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * returns array of 3 $
     **/
    private static int[] findStartingPointForStep1(final char[][] initialGrid) {
        final int[] result = new int[3]; // [row, col, directionIndex]
        Direction currentDirection = null;

        final int[] initialCoordinates = findStartingPoint(initialGrid);
        final char c = initialGrid[initialCoordinates[0]][initialCoordinates[1]];
        currentDirection = switch (c) {
            case '^' -> Direction.NORTH;
            case '>' -> Direction.EAST;
            case 'v' -> Direction.SOUTH;
            case '<' -> Direction.WEST;
            default -> currentDirection;
        };
        if (currentDirection != null) {
            result[0] = initialCoordinates[0];
            result[1] = initialCoordinates[1];
            result[2] = currentDirection.ordinal();
        }
        if (currentDirection == null) {
            throw new IllegalStateException("No starting position found in the grid.");
        }
        return result;
    }

    protected static Cell[][] calculatePath(final char[][] grid, final boolean updateList) {
        final int rows = grid.length;
        final int cols = grid[0].length;
        final Cell[][] pathGrid = new Cell[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                pathGrid[i][j] = new Cell(i, j);
            }
        }

        final int[] start = findStartingPointForStep1(grid);
        int row = start[0];
        int col = start[1];
        Direction currentDirection = Direction.values()[start[2]];

        int step = 1;
        AddStep(row, col, step, currentDirection, pathGrid, updateList);
        step++;

        while (true) {
            final int nextRow = row + currentDirection.getRowDelta();
            final int nextCol = col + currentDirection.getColDelta();

            // Check if the next step is out of bounds
            if (nextRow < 0 || nextRow >= rows || nextCol < 0 || nextCol >= cols) {
                break;
            }

            // Check if the next cell is an obstacle
            if (grid[nextRow][nextCol] == '#' || grid[nextRow][nextCol] == 'O') {
                currentDirection = currentDirection.turnRight();
                continue;
            }

            // Mark the current cell and move, check for loop
            if (AddStep(nextRow, nextCol, step, currentDirection, pathGrid, updateList)) {
                return null; // Loop detected
            }
            step++;
            row = nextRow;
            col = nextCol;
        }

        return pathGrid;
    }

    private static boolean AddStep(final int row,
                                   final int col,
                                   final int step,
                                   final Direction currentDirection,
                                   final Cell[][] pathGrid,
                                   final boolean updateList) {
        final Cell currentCell = pathGrid[row][col];
        if (updateList) {
            orderedCells.add(currentCell);
        }
        return currentCell.addStep(step, currentDirection);
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        buildGrid(sc);
        return countNonZeroCells(pathGrid);
    }

    protected void buildGrid(final Scanner sc) {
        final List<char[]> gridList = new ArrayList<>();
        orderedCells.clear();

        // Build grid row by row
        while (sc.hasNext()) {
            final String line = sc.nextLine();
            gridList.add(line.toCharArray());
        }

        // Finalize grid
        grid = gridList.toArray(new char[0][0]);

        // Create the path grid, fill orderedCells list
        pathGrid = calculatePath(grid, true);
    }

    @Override
    public int getDay() {
        return 6;
    }

    protected enum Direction {
        NORTH(-1, 0, '^'),
        EAST(0, 1, '>'),
        SOUTH(1, 0, 'v'),
        WEST(0, -1, '<');

        private final int rowDelta;
        private final int colDelta;
        private final char symbol;

        Direction(final int rowDelta, final int colDelta, final char symbol) {
            this.rowDelta = rowDelta;
            this.colDelta = colDelta;
            this.symbol = symbol;
        }

        public int getRowDelta() {
            return rowDelta;
        }

        public int getColDelta() {
            return colDelta;
        }

        public char getSymbol() {
            return symbol;
        }

        public Direction turnRight() {
            return values()[(this.ordinal() + 1) % values().length];
        }
    }

    protected static class Cell {
        protected final List<Integer> steps = new ArrayList<>();
        protected final List<Direction> directions = new ArrayList<>();
        private final int row;
        private final int col;

        public Cell(final int row, final int col) {
            this.row = row;
            this.col = col;
        }

        public boolean addStep(final int step, final Direction direction) {
            if (directions.contains(direction)) {
                return true; // Loop detected
            }
            steps.add(step);
            directions.add(direction);
            return false;
        }

        public Direction getDirectionForStep(final int step) {
            final int index = steps.indexOf(step);
            if (index == -1) {
                throw new IllegalStateException("Step " + step + "not present in the current Cell");
            }
            return directions.get(index);
        }

        public int getRow() {
            return row;
        }

        public int getCol() {
            return col;
        }

        @Override
        public String toString() {
            return "Steps: " + steps + ", Directions: " + directions;
        }
    }

}
