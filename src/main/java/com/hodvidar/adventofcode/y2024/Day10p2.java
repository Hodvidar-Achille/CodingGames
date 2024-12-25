package com.hodvidar.adventofcode.y2024;

public class Day10p2 extends Day10 {


    /**
     * Calculates the sum of trailhead ratings for all trailheads on the map.
     */
    @Override
    protected int calculateTrailheadScores(final int[][] map) {
        int totalRating = 0;

        for (int row = 0; row < map.length; row++) {
            for (int col = 0; col < map[0].length; col++) {
                if (map[row][col] == 0) {
                    totalRating += calculateTrailheadRating(map, row, col);
                }
            }
        }

        return totalRating;
    }

    /**
     * Calculates the rating of a specific trailhead at (startRow, startCol).
     * The rating is the number of distinct hiking trails starting from this position.
     */
    private int calculateTrailheadRating(final int[][] map, final int startRow, final int startCol) {
        return countDistinctTrails(map, startRow, startCol, 0, new boolean[map.length][map[0].length]);
    }

    /**
     * Recursively counts distinct trails starting from the current position.
     */
    private int countDistinctTrails(final int[][] map, final int row, final int col, final int currentHeight, final boolean[][] visited) {
        // Base case: Out of bounds or already visited or invalid step
        if (row < 0 || row >= map.length || col < 0 || col >= map[0].length || visited[row][col]) {
            return 0;
        }

        final int height = map[row][col];
        if (height != currentHeight) {
            return 0; // Not the expected height in the trail
        }

        // Mark the current position as visited
        visited[row][col] = true;

        // If we've reached height 9, this is a valid trail
        if (height == 9) {
            visited[row][col] = false; // Unmark for other trails
            return 1;
        }

        // Explore all 4 directions
        final int trails = countDistinctTrails(map, row - 1, col, currentHeight + 1, visited) + // Up
                countDistinctTrails(map, row + 1, col, currentHeight + 1, visited) + // Down
                countDistinctTrails(map, row, col - 1, currentHeight + 1, visited) + // Left
                countDistinctTrails(map, row, col + 1, currentHeight + 1, visited);  // Right

        // Unmark the current position before backtracking
        visited[row][col] = false;

        return trails;
    }
}
