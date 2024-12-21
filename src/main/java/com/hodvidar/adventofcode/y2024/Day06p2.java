package com.hodvidar.adventofcode.y2024;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day06p2 extends Day06 {

    private static char[][] copyGrid(final char[][] grid) {
        final int rows = grid.length;
        final char[][] newGrid = new char[rows][];
        for (int i = 0; i < rows; i++) {
            newGrid[i] = Arrays.copyOf(grid[i], grid[i].length);
        }
        return newGrid;
    }

    private static int simulateObstacles(final char[][] initialGrid,
                                         final List<Cell> orderedSteps) {
        final int rows = initialGrid.length;
        final int cols = initialGrid[0].length;

        int loopCount = 0;
        final char[][] gridWithObstacles = copyGrid(initialGrid);

        // FORBIDDEN TWO PLACES TO PUT AN OBSTACLE:
        final int[] start = findStartingPoint(initialGrid);
        final int initialGuardRow = start[0];
        final int initialGuardCol = start[1];

        for (final Cell currentCell : orderedSteps) {
            final int obstacleRow = currentCell.getRow();
            final int obstacleCol = currentCell.getCol();


            // Check bounds for obstacle placement
            if (obstacleRow < 0 || obstacleRow >= rows
                    || obstacleCol < 0 || obstacleCol >= cols) {
                continue; // out of bonds
            }

            if (initialGrid[obstacleRow][obstacleCol] == '#') {
                continue; // already and obstacle
            }

            if (gridWithObstacles[obstacleRow][obstacleCol] == 'O') {
                continue; // obstacle already added
            }

            if (obstacleRow == initialGuardRow && obstacleCol == initialGuardCol) {
                continue; // we ignore obstacle that will be put on the guard
            }

            final char[][] modifiedGrid = copyGrid(initialGrid);
            modifiedGrid[obstacleRow][obstacleCol] = 'O'; // add obstacle
            final Cell[][] newPathGrid = calculatePath(modifiedGrid, false);
            // Check if a loop was detected
            if (newPathGrid == null) {
                loopCount++;
                gridWithObstacles[obstacleRow][obstacleCol] = 'O';
            }
        }
        return loopCount;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        buildGrid(sc);
        return simulateObstacles(grid, orderedCells);
    }
}
