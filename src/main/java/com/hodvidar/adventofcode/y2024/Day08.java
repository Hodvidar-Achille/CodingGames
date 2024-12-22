package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Day08 extends AbstractAdventOfCode2024 {

    protected static final char ANTINODE = '#';
    protected static final char EMPTY = '.';

    private static boolean isInBounds(final char[][] grid, final int row, final int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

    protected static long CountAntinodes(final char[][] gridOfAntinodes) {
        return Arrays.stream(gridOfAntinodes).flatMapToInt(row -> new String(row).chars()).filter(ch -> ch == ANTINODE).count();
    }

    protected void addAntinodes(final int i, final int j, final char[][] gridCopy, final int k, final int l) {
        // Calculate the distance between antennas
        final int deltaRow = k - i;
        final int deltaCol = l - j;

        // Add antinodes (#) at both ends if they are in bounds
        final int antinode1Row = i - deltaRow;
        final int antinode1Col = j - deltaCol;
        if (isInBounds(gridCopy, antinode1Row, antinode1Col)) {
            gridCopy[antinode1Row][antinode1Col] = ANTINODE;
        }

        final int antinode2Row = k + deltaRow;
        final int antinode2Col = l + deltaCol;
        if (isInBounds(gridCopy, antinode2Row, antinode2Col)) {
            gridCopy[antinode2Row][antinode2Col] = ANTINODE;
        }
    }

    private void searchForAntennaAndCreateAntinodes(final char[][] grid, final char[][] gridCopy) {
        final int rows = grid.length;
        final int cols = grid[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final char antenna = grid[i][j];
                // Skip non-antenna characters
                if (antenna == EMPTY || antenna == ANTINODE) {
                    continue;
                }
                // Search for pairs of the same antenna
                searchForSameSignAntenna(i, rows, cols, j, grid, antenna, gridCopy);
            }
        }
    }

    private void searchForSameSignAntenna(final int i, final int rows, final int cols, final int j, final char[][] grid, final char antenna, final char[][] gridCopy) {
        for (int k = i; k < rows; k++) {
            for (int l = 0; l < cols; l++) {
                // Skip the same position and non-matching antennas
                if (k == i && l <= j) {
                    continue;
                }
                if (grid[k][l] != antenna) {
                    continue;
                }
                addAntinodes(i, j, gridCopy, k, l);
            }
        }
    }

    @Override
    public int getDay() {
        return 8;
    }

    @Override
    public double getResultDouble(final Scanner sc) {

        final List<char[]> gridList = new ArrayList<>();
        // Build initialGridOfAntennas row by row
        while (sc.hasNext()) {
            final String line = sc.nextLine();
            gridList.add(line.toCharArray());
        }
        final char[][] initialGridOfAntennas = gridList.toArray(new char[0][0]);
        final char[][] gridOfAntinodes = Day06p2.copyGrid(initialGridOfAntennas);

        // Process the initialGridOfAntennas to find pairs of antennas
        searchForAntennaAndCreateAntinodes(initialGridOfAntennas, gridOfAntinodes);

        // Count all '#' in gridOfAntinodes using a stream
        final long hashCount = CountAntinodes(gridOfAntinodes);
        return (double) hashCount;
    }

    protected void clearLocalVariables() {
        // empty
    }

}

