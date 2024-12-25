package com.hodvidar.adventofcode.y2024;

import java.util.*;

public class Day10 extends AbstractAdventOfCode2024 {
    // Directions for North, South, East, West
    private static final int[][] DIRECTIONS = {
            {-1, 0}, // North
            {1, 0},  // South
            {0, -1}, // West
            {0, 1}   // East
    };

    protected int calculateTrailheadScores(final int[][] map) {
        final int rows = map.length;
        final int cols = map[0].length;
        int totalScore = 0;

        // Find all trailheads (positions with height 0)
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (map[r][c] == 0) {
                    totalScore += bfs(map, r, c);
                }
            }
        }

        return totalScore;
    }

    protected int bfs(final int[][] map, final int startRow, final int startCol) {
        final int rows = map.length;
        final int cols = map[0].length;
        final boolean[][] visited = new boolean[rows][cols];
        final Queue<int[]> queue = new LinkedList<>();
        final Set<String> reachableNines = new HashSet<>();

        // Initialize BFS
        queue.offer(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;

        while (!queue.isEmpty()) {
            final int[] current = queue.poll();
            final int currentRow = current[0];
            final int currentCol = current[1];
            final int currentHeight = map[currentRow][currentCol];

            // Check if current position is height 9
            if (currentHeight == 9) {
                reachableNines.add(currentRow + "," + currentCol); // Mark as reachable
                continue;
            }

            // Explore neighbors
            for (final int[] direction : DIRECTIONS) {
                final int newRow = currentRow + direction[0];
                final int newCol = currentCol + direction[1];

                // Validate neighbor
                if (newRow >= 0 && newRow < rows && newCol >= 0 && newCol < cols
                        && !visited[newRow][newCol]
                        && map[newRow][newCol] == currentHeight + 1) {
                    visited[newRow][newCol] = true;
                    queue.offer(new int[]{newRow, newCol});
                }
            }
        }

        // Return the count of unique reachable 9s
        return reachableNines.size();
    }

    @Override
    public int getDay() {
        return 10;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        final List<int[]> gridList = new ArrayList<>();

        // Build the grid row by row from the Scanner input
        while (sc.hasNext()) {
            final String line = sc.nextLine();
            // Convert the line into an array of integers
            final int[] row = line.chars()
                    .map(Character::getNumericValue)
                    .toArray();
            gridList.add(row);
        }

        // Convert the List<int[]> to a 2D array
        final int[][] map = gridList.toArray(new int[0][]);

        // Example: Call the calculateTrailheadScores method (or another method you define)
        return calculateTrailheadScores(map);
    }

}
