package com.hodvidar.adventofcode.y2024;

import com.hodvidar.utils.geometry.Point;

import java.util.*;

public class Day12 extends AbstractAdventOfCode2024 {

    private static final int[][] DIRECTIONS = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

    @Override
    public double getResultDouble(final Scanner sc) {
        final char[][] grid = parseInput(sc);
        final List<Region> region = buildRegions(grid);
        return calculateTotalPrice(region);
    }

    protected char[][] parseInput(final Scanner sc) {
        final List<char[]> gridList = new ArrayList<>();
        while (sc.hasNext()) {
            gridList.add(sc.nextLine().toCharArray());
        }
        return gridList.toArray(new char[0][]);
    }

    protected double calculateTotalPrice(final List<Region> regions) {
        return regions.stream().mapToDouble(region -> region.getArea() * region.getPerimeter()).sum();
    }

    /**
     * Calculates the total price of fencing for all regions in the grid.
     */
    protected List<Region> buildRegions(final char[][] grid) {
        final int rows = grid.length;
        final int cols = grid[0].length;
        final boolean[][] visited = new boolean[rows][cols];
        final List<Region> regions = new ArrayList<>();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                if (!visited[row][col]) {
                    final Region region = exploreRegion(grid, visited, row, col);
                    regions.add(region);
                }
            }
        }
        return regions;
    }

    /**
     * Explores a region starting from (startRow, startCol) and calculates its area and perimeter.
     */
    private Region exploreRegion(final char[][] grid, final boolean[][] visited, final int startRow, final int startCol) {
        final char plantType = grid[startRow][startCol];
        int area = 0;
        int perimeter = 0;
        final List<Point> coordinateOfPlants = new ArrayList<>();

        // Use a queue for BFS traversal
        final Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = true;
        coordinateOfPlants.add(new Point(startRow, startCol));

        while (!queue.isEmpty()) {
            final int[] current = queue.poll();
            final int row = current[0];
            final int col = current[1];
            area++;

            // Check all neighbors
            for (final int[] direction : DIRECTIONS) {
                final int newRow = row + direction[0];
                final int newCol = col + direction[1];

                // Count perimeter if out of bounds or different plant type
                if (!isInBounds(grid, newRow, newCol) || grid[newRow][newCol] != plantType) {
                    perimeter++;
                } else if (!visited[newRow][newCol]) {
                    // Add to queue if valid and not visited
                    visited[newRow][newCol] = true;
                    queue.add(new int[]{newRow, newCol});
                    coordinateOfPlants.add(new Point(newRow, newCol));
                }
            }
        }

        return new Region(plantType, area, perimeter, coordinateOfPlants);
    }

    protected boolean isInBounds(final char[][] grid, final int row, final int col) {
        return row >= 0 && row < grid.length && col >= 0 && col < grid[0].length;
    }


    /**
     * Helper class to store region information.
     */
    protected static class Region {
        protected final int perimeter;
        private final char plantType;
        private final int area;

        private final List<Point> coordinateOfPlants;

        public Region(final char plantType, final int area, final int perimeter, final List<Point> coordinateOfPlants) {
            this.plantType = plantType;
            this.area = area;
            this.perimeter = perimeter;
            this.coordinateOfPlants = coordinateOfPlants;
        }

        public char getPlantType() {
            return plantType;
        }

        public int getArea() {
            return area;
        }

        public int getPerimeter() {
            return perimeter;
        }

        public List<Point> getCoordinateOfPlants() {
            return coordinateOfPlants;
        }
    }
}


