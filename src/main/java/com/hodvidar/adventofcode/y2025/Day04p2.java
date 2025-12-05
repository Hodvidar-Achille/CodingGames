package com.hodvidar.adventofcode.y2025;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.hodvidar.utils.geometry.Point;

public class Day04p2 extends Day04 {

    private static final char EMPTY = '.';

    @Override
    protected double countIsolatedRolls(final List<List<Character>> rollsGrid) {
        if (rollsGrid == null || rollsGrid.isEmpty()) {
            return 0.0;
        }
        Set<Point> rollsToRemove = new HashSet<>();
        final int rows = rollsGrid.size();
        final int cols = rollsGrid.getFirst().size();
        int accessibleCount = 0;
        // Scan the whole rollsGrid
        accessibleCount = getAccessibleCount(rollsGrid, rows, cols, accessibleCount, rollsToRemove);
        while(!rollsToRemove.isEmpty()) {
            // Remove the previously found accessible rolls
            for(final Point p : rollsToRemove) {
                rollsGrid.get((int) p.x).set((int) p.y, EMPTY);
            }
            final Set<Point> newRollsToRemove = new HashSet<>();
            // Instead of going through all the grid again we only check the 8 neighbors of the previously removed rolls
            for(final Point p : rollsToRemove) {
                for (final int[] d : DIRS) {
                    final int nr = (int) (p.x + d[0]);
                    final int nc = (int) (p.y + d[1]);
                    // Skip positions that are outside the rollsGrid (edge handling)
                    if (nr < 0 || nr >= rows || nc < 0 || nc >= cols) {
                        continue;
                    }
                    // Not already checked
                    final Point newPoint = new Point(nr, nc);
                    if(newRollsToRemove.contains(newPoint)) {
                        continue;
                    }
                    if (rollsGrid.get(nr).get(nc) == ROLL) {
                        if (isAccessible(rollsGrid, nr, nc, rows, cols)) {
                            accessibleCount++;
                            newRollsToRemove.add(newPoint);
                        }
                    }
                }
            }
            rollsToRemove = newRollsToRemove;
        }
        return accessibleCount;
    }

    private static int getAccessibleCount(List<List<Character>> rollsGrid, int rows, int cols, int accessibleCount, Set<Point> rollsToRemove) {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (isAccessible(rollsGrid, r, c, rows, cols)) {
                    accessibleCount++;
                    rollsToRemove.add(new Point(r, c));
                }
            }
        }
        return accessibleCount;
    }
}
