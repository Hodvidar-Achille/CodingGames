package com.hodvidar.adventofcode.y2024;

import java.util.List;
import java.util.Scanner;

public class Day04p2 extends Day04 {

    public static int countPatterns(final List<List<Character>> grid) {
        final int rows = grid.size();
        final int cols = grid.getFirst().size();
        int count = 0;

        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (grid.get(row).get(col) == 'A') {
                    if (isPatternMatch(grid, row, col)) {
                        count++;
                    }
                }
            }
        }

        return count;
    }

    private static boolean isPatternMatch(final List<List<Character>> grid, final int row, final int col) {
        final int rows = grid.size();
        final int cols = grid.get(0).size();

        // Check boundary conditions
        if (row == 0 || row == rows - 1 || col == 0 || col == cols - 1) {
            return false;
        }

        // Get the characters at the corners
        final char topLeft = grid.get(row - 1).get(col - 1);
        final char bottomRight = grid.get(row + 1).get(col + 1);
        final char topRight = grid.get(row - 1).get(col + 1);
        final char bottomLeft = grid.get(row + 1).get(col - 1);

        // Define valid patterns
        final boolean diagonal1Valid = (topLeft == 'M' && bottomRight == 'S') || (topLeft == 'S' && bottomRight == 'M');
        final boolean diagonal2Valid = (topRight == 'M' && bottomLeft == 'S') || (topRight == 'S' && bottomLeft == 'M');

        // Check if any valid pattern matches
        return diagonal1Valid && diagonal2Valid;
    }


    @Override
    public double getResultDouble(final Scanner sc) {
        grid.clear();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        return countPatterns(grid);
    }
}
