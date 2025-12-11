package com.hodvidar.adventofcode.y2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day07p2 extends Day07 {

    @Override
    public double getResultDouble(final Scanner sc) {
        linesGrid.clear();
        // First, read all lines
        while (sc.hasNextLine()) {
            this.getDigitFromLine(sc.nextLine());
        }

        // Convert to 2D array
        rows = linesGrid.size();
        cols = linesGrid.getFirst().size();
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
            if (startRow != -1) {
                break;
            }
        }

        if (startRow == -1) {
            return 0; // No starting point found
        }

        List<Beam> currentRowBeams;
        List<Beam> nextRowBeams = new ArrayList<>();
        nextRowBeams.add(new Beam(startRow + 1, startCol));
        final double[] currentTimelines = new double[cols];
        currentTimelines[startCol] = 1;
        final boolean[] columnAlreadyUsed = new boolean[cols];

        for (int row = 1; row < rows; row++) {
            currentRowBeams = List.copyOf(nextRowBeams);
            nextRowBeams = new ArrayList<>();
            for (final Beam currentRowBeam : currentRowBeams) {
                final int r = currentRowBeam.row;
                final int c = currentRowBeam.col;
                if (r < 0 || r >= rows || c < 0 || c >= cols) {
                    continue;
                }
                final char current = grid[r][c];
                if (current == '.') {
                    grid[r][c] = '|';
                    nextRowBeams.add(new Beam(r + 1, c));
                    columnAlreadyUsed[c] = true;
                } else if (current == '^') {
                    // Splitting, potentially bean exist already
                    // Left beam
                    final int leftCol = c - 1;
                    if (!columnAlreadyUsed[leftCol]) {
                        nextRowBeams.add(new Beam(r + 1, leftCol));
                        columnAlreadyUsed[leftCol] = true;
                    }
                    currentTimelines[leftCol] += currentTimelines[c];
                    // Right beam
                    final int rightCol = c + 1;
                    if (!columnAlreadyUsed[rightCol]) {
                        nextRowBeams.add(new Beam(r + 1, rightCol));
                        columnAlreadyUsed[rightCol] = true;
                    }
                    currentTimelines[rightCol] += currentTimelines[c];
                    currentTimelines[c] = 0;
                }
            }
            // reset columnAlreadyUsed
            Arrays.fill(columnAlreadyUsed, false);
        }
        return Arrays.stream(currentTimelines).sum();
    }
}