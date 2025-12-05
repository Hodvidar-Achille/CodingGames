package com.hodvidar.adventofcode.y2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day04 extends AbstractAdventOfCode2025 {

    protected static final char ROLL = '@';
    // Directions for the eight neighbors (row‑offset, col‑offset)
    protected static final int[][] DIRS = { { -1, -1 }, { -1, 0 }, { -1, 1 }, //
                                            { 0, -1 }, /*       */ { 0, 1 }, //
                                            { 1, -1 }, { 1, 0 }, { 1, 1 } };
    private final List<List<Character>> grid = new ArrayList<>();

    protected static boolean isAccessible(final List<List<Character>> rollsGrid, final int x, final int y, final int maxX, final int maxY) {
        // Only interested in roll cells
        if (rollsGrid.get(x).get(y) != ROLL) {
            return false;
        }
        int neighbourRolls = 0;
        // Look at the eight possible neighbours
        for (final int[] d : DIRS) {
            final int nr = x + d[0];
            final int nc = y + d[1];
            // Skip positions that are outside the rollsGrid (edge handling)
            if (nr < 0 || nr >= maxX || nc < 0 || nc >= maxY) {
                continue;
            }
            if (rollsGrid.get(nr).get(nc) == ROLL) {
                neighbourRolls++;
                // Early exit – we already know this roll is *not* accessible
                if (neighbourRolls >= 4) {
                    return false;
                }
            }
        }
        return true;
    }

    protected double countIsolatedRolls(final List<List<Character>> rollsGrid) {
        if (rollsGrid == null || rollsGrid.isEmpty()) {
            return 0.0;
        }
        final int rows = rollsGrid.size();
        final int cols = rollsGrid.getFirst().size();
        int accessibleCount = 0;
        // Scan the whole rollsGrid
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (isAccessible(rollsGrid, r, c, rows, cols)) {
                    accessibleCount++;
                }
            }
        }
        return accessibleCount;
    }

    @Override
    public void digestLine(final String line) {
        final List<Character> row = new ArrayList<>();
        for (final char c : line.toCharArray()) {
            row.add(c);
        }
        this.grid.add(row);
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        grid.clear();
        while (sc.hasNextLine()) {
            this.digestLine(sc.nextLine());
        }
        return countIsolatedRolls(grid);
    }
}
