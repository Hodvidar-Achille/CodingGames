package com.hodvidar.adventofcode.y2025;

import java.util.*;
import java.util.stream.Collectors;

public class Day07 extends AbstractAdventOfCode2025 {
    protected final List<List<Character>> linesGrid = new ArrayList<>();
    protected char[][] grid;
    protected int rows, cols;

    protected static char[][] buildGrid(final List<List<Character>> linesGrid, final int rows, final int cols) {
        final char[][] grid = new char[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                grid[i][j] = linesGrid.get(i).get(j);
            }
        }

        return grid;
    }

    @Override
    public double getDigitFromLine(final String line) {
        // Save each line as a list of characters
        final List<Character> charList = line.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        linesGrid.add(charList);
        return 0;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        linesGrid.clear();
        // First, read all lines
        while (sc.hasNextLine()) {
            this.getDigitFromLine(sc.nextLine());
        }

        // Convert to 2D array
        rows = linesGrid.size();
        cols = rows > 0 ? linesGrid.get(0).size() : 0;
        grid = buildGrid(linesGrid, rows, cols);

        // Find starting position 'S'
        int startRow = -1, startCol = -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 'S') {
                    startRow = i;
                    startCol = j;
                    break;
                }
            }
            if (startRow != -1) break;
        }

        if (startRow == -1) {
            return 0; // No starting point found
        }

        // Set of positions we've already processed for splitters (to avoid double counting)
        final Set<String> processedSplitters = new HashSet<>();

        // Queue for beam simulation: each element is [row, col, direction]
        // direction: 'D' = down, 'L' = left, 'R' = right
        final Queue<Beam> queue = new LinkedList<>();

        // Start beam going down from S
        queue.offer(new Beam(startRow + 1, startCol));
        // grid[startRow][startCol] = '|'; // Mark starting position as beam

        while (!queue.isEmpty()) {
            final Beam beam = queue.poll();
            final int r = beam.row;
            final int c = beam.col;

            // Check bounds
            if (r < 0 || r >= rows || c < 0 || c >= cols) {
                continue;
            }

            final char current = grid[r][c];

            if (current == '.') {
                // Empty space - continue beam
                grid[r][c] = '|';
                queue.offer(new Beam(r + 1, c));
            } else if (current == '^') {
                // Splitter - count it if not already processed
                final String splitterKey = r + "," + c;
                processedSplitters.add(splitterKey);
                // Left beam
                if (c - 1 >= 0) {
                    final char leftChar = grid[r][c - 1];
                    if (leftChar == '.' || leftChar == '^') {
                        queue.offer(new Beam(r, c - 1));
                    }
                }
                // Right beam
                if (c + 1 < cols) {
                    final char rightChar = grid[r][c + 1];
                    if (rightChar == '.' || rightChar == '^') {
                        queue.offer(new Beam(r, c + 1));
                    }
                }
            } else if (current == '|') {
                // do nothing
            }
            // If we encounter other characters (like already placed '|'), just continue
        }

        // Count the number of splitters that were touched
        return processedSplitters.size();
    }

    // Helper class to represent a beam
    private static class Beam {
        int row;
        int col;

        Beam(final int row, final int col) {
            this.row = row;
            this.col = col;
        }
    }
}

