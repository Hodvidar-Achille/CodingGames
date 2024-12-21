package com.hodvidar.adventofcode.y2024;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day06p2 extends Day06 {

    private static char[][] copyGrid(final char[][] grid, final boolean removeGuard) {
        final int rows = grid.length;
        final char[][] newGrid = new char[rows][];
        for (int i = 0; i < rows; i++) {
            newGrid[i] = Arrays.copyOf(grid[i], grid[i].length);
            if (removeGuard) {
                for (int j = 0; j < newGrid[i].length; j++) {
                    if (newGrid[i][j] == '^' || newGrid[i][j] == '>' || newGrid[i][j] == 'v' || newGrid[i][j] == '<') {
                        newGrid[i][j] = '.'; // Remove the starting direction
                    }
                }
            }
        }
        return newGrid;
    }

    private static int simulateObstacles(final char[][] initialGrid,
                                         final List<Cell> orderedSteps) {
        final int rows = initialGrid.length;
        final int cols = initialGrid[0].length;

        int loopCount = 0;
        final char[][] gridWithObstacles = copyGrid(initialGrid, false);

        // FORBIDDEN TWO PLACES TO PUT AN OBSTACLE:
        final int[] start = findStartingPointForStep1(initialGrid);
        final int initialGuardRow = start[0];
        final int initialGuardCol = start[1];
        final Direction initialGuardDirection = Direction.values()[start[2]];
        final int firstStepGuardRow = initialGuardRow + initialGuardDirection.getRowDelta();
        final int firstStepGuardCol = initialGuardCol + initialGuardDirection.getColDelta();

        for (int i = 0; i < orderedSteps.size(); i++) {
            final Cell currentCell = orderedSteps.get(i);
            final int currentStep = i + 1;
            final Direction direction = currentCell.getDirectionForStep(currentStep);
            final char[][] modifiedGrid = copyGrid(initialGrid, true);
            final int currentCellRow = currentCell.getRow();
            final int currentCellCol = currentCell.getCol();
            modifiedGrid[currentCellRow][currentCellCol] = direction.getSymbol();
            final int obstacleRow = currentCellRow + direction.getRowDelta();
            final int obstacleCol = currentCellCol + direction.getColDelta();


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
            if (obstacleRow == firstStepGuardRow && obstacleCol == firstStepGuardCol) {
                continue; // we ignore obstacle that will be put exactly in front of the guard
            }

            modifiedGrid[obstacleRow][obstacleCol] = 'O';

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

        // Count non-zero cells
        return simulateObstacles(grid, orderedCells);
    }
}
