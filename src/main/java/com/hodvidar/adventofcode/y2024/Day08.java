package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;


public class Day08 extends AbstractAdventOfCode2024 {


    @Override
    public int getDay() {
        return 8;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        final List<char[]> gridList = new ArrayList<>();
        // Build grid row by row
        while (sc.hasNext()) {
            final String line = sc.nextLine();
            gridList.add(line.toCharArray());
        }
        final char[][] grid = gridList.toArray(new char[0][0]);
        final char[][] gridCopy = Day06p2.copyGrid(grid);

        // Process the grid to find pairs of antennas
        final int rows = grid.length;
        final int cols = grid[0].length;

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                final char antenna = grid[i][j];
                // Skip non-antenna characters
                if (antenna == '.' || antenna == '#') {
                    continue;
                }

                // Search for pairs of the same antenna
                for (int k = i; k < rows; k++) {
                    for (int l = 0; l < cols; l++) {
                        // Skip the same position and non-matching antennas
                        if ((k == i && l <= j) || grid[k][l] != antenna) {
                            continue;
                        }

                        // Calculate the distance between antennas
                        final int deltaRow = k - i;
                        final int deltaCol = l - j;

                        // Add antinodes (#) at both ends if they are in bounds
                        final int antinode1Row = i - deltaRow;
                        final int antinode1Col = j - deltaCol;
                        if (isInBounds(gridCopy, antinode1Row, antinode1Col)) {
                            gridCopy[antinode1Row][antinode1Col] = '#';
                        }

                        final int antinode2Row = k + deltaRow;
                        final int antinode2Col = l + deltaCol;
                        if (isInBounds(gridCopy, antinode2Row, antinode2Col)) {
                            gridCopy[antinode2Row][antinode2Col] = '#';
                        }
                    }
                }
            }
        }

        // Count all '#' in gridCopy using a stream
        final long hashCount = Arrays.stream(gridCopy)
                .flatMapToInt(row -> new String(row).chars())
                .filter(ch -> ch == '#')
                .count();

        return (double) hashCount;
    }

    private boolean isInBounds(final char[][] grid, final int row, final int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }

}

