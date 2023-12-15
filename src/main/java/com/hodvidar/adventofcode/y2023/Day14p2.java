package com.hodvidar.adventofcode.y2023;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Day14p2 extends Day14 {

    private static final int ONE_BILLION = 1_000_000_000;

    @Override
    public double getResultDouble(final Scanner sc) {
        initGrid(sc);
        return performCyclesAndRetrieveLastLoad(ONE_BILLION);
    }

    private double performCyclesAndRetrieveLastLoad(final int numberOfCycles) {
        final List<String> grids = new ArrayList<>();
        final List<Double> loads = new ArrayList<>();
        do {
            printGrid();
            tiltGridToNorth();
            printGrid();
            tiltGridToWest();
            printGrid();
            tiltGridToSouth();
            printGrid();
            tiltGridToEast();
            printGrid();
            grids.add(gridToSingleString());
            loads.add(getSumOfRoundRockLoad());
        } while (!isCycleRepeating(grids));
        final int last = grids.size() - 1;
        final int first = grids.indexOf(grids.get(last));
        return loads.get(first + (numberOfCycles - 1 - first) % (last - first));
    }

    private void printGrid() {
        if (!VERBOSE) {
            return;
        }
        System.out.println("-------- GRID:---------");
        for (final char[] line : grid) {
            System.out.println(line);
        }
    }

    private String gridToSingleString() {
        final StringBuilder sb = new StringBuilder();
        for (final char[] line : grid) {
            sb.append(line);
        }
        return sb.toString();
    }

    private boolean isCycleRepeating(final List<String> grids) {
        return grids.size() != new HashSet<>(grids).size();
    }

    private void tiltGridToWest() {
        for (int j = 1; j < grid[0].length; j++) {
            for (int i = 0; i < grid.length; i++) {
                moveRoundRockToWest(i, j);
            }
        }
    }

    private void tiltGridToSouth() {
        for (int i = grid.length - 2; i >= 0; i--) {
            for (int j = 0; j < grid[0].length; j++) {
                moveRoundRockToSouth(i, j);
            }
        }
    }

    private void tiltGridToEast() {
        for (int j = grid[0].length - 2; j >= 0; j--) {
            for (int i = 0; i < grid.length; i++) {
                moveRoundRockToEast(i, j);
            }
        }
    }

    protected void moveRoundRockToWest(final int i, final int j) {
        if (grid[i][j] != ROUND_ROCK) {
            return;
        }
        if (j == 0) {
            return;
        }
        if (grid[i][j - 1] == ROUND_ROCK) {
            return;
        }
        if (grid[i][j - 1] == SQUARE_ROCK) {
            return;
        }
        grid[i][j - 1] = ROUND_ROCK;
        grid[i][j] = EMPTY_SPACE;
        moveRoundRockToWest(i, j - 1);
    }

    protected void moveRoundRockToSouth(final int i, final int j) {
        if (grid[i][j] != ROUND_ROCK) {
            return;
        }
        if (i == grid.length - 1) {
            return;
        }
        if (grid[i + 1][j] == ROUND_ROCK) {
            return;
        }
        if (grid[i + 1][j] == SQUARE_ROCK) {
            return;
        }
        grid[i + 1][j] = ROUND_ROCK;
        grid[i][j] = EMPTY_SPACE;
        moveRoundRockToSouth(i + 1, j);
    }

    protected void moveRoundRockToEast(final int i, final int j) {
        if (grid[i][j] != ROUND_ROCK) {
            return;
        }
        if (j == grid[0].length - 1) {
            return;
        }
        if (grid[i][j + 1] == ROUND_ROCK) {
            return;
        }
        if (grid[i][j + 1] == SQUARE_ROCK) {
            return;
        }
        grid[i][j + 1] = ROUND_ROCK;
        grid[i][j] = EMPTY_SPACE;
        moveRoundRockToEast(i, j + 1);
    }
}
