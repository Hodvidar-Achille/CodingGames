package com.hodvidar.adventofcode.y2025;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day06p2 extends Day06 {

    private final List<String> lines = new ArrayList<>();

    public static double evaluate(final char[][] grid) {
        final int rows = grid.length;
        double sum = 0d;
        // The operator row is the last one (index rows‑1)
        final int operatorRow = rows - 1;
        final int cols = grid[operatorRow].length;
        for (int c = 0; c < cols; c++) {
            final char op = grid[operatorRow][c];
            if (op != PLUS && op != MULTIPLIER) {
                continue;
            }
            // found an operator column
            sum += evaluateColumn(grid, operatorRow, c, op);
        }
        return sum;
    }

    private static double evaluateColumn(final char[][] grid,
                                         final int operatorRow,
                                         final int colIndex,
                                         final char op) {
        // Initial accumulator: 0 for addition, 1 for multiplication
        double accumulator = (op == PLUS) ? 0d : 1d;
        // The grid can be ragged, therefore we cannot rely on grid[0].length.
        // Instead we keep advancing while at least one row still has a cell at column c.
        for (int c = colIndex; ; c++) {
            // Check whether any row (up to the operator row) actually has column c.
            boolean columnExists = false;
            for (int r = 0; r < operatorRow; r++) {
                if (c < grid[r].length) {
                    columnExists = true;
                    break;
                }
            }
            if (!columnExists) {
                // No more columns to read – return the value accumulated so far.
                return accumulator;
            }
            // Build the number formed by the digits that appear in this column.
            StringBuilder sb = new StringBuilder();
            for (int r = 0; r < operatorRow; r++) {
                if (c < grid[r].length) {                 // guard against ragged rows
                    char ch = grid[r][c];
                    if (ch >= '0' && ch <= '9') {
                        sb.append(ch);
                    }
                }
            }
            // If the column contains no digit at all we stop the block.
            if (sb.isEmpty()) {
                return accumulator;
            }
            // Convert the collected digits to a numeric value.
            double columnValue = Double.parseDouble(sb.toString());
            // Apply the operator to the running accumulator.
            if (op == PLUS) {
                accumulator += columnValue;
            } else { // MULTIPLIER
                accumulator *= columnValue;
            }
        }
    }

    @Override
    public double getDigitFromLine(final String line) {
        lines.add(line);
        return 0;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        lines.clear();
        while (sc.hasNextLine()) {
            this.getDigitFromLine(sc.nextLine());
        }
        final char[][] grid = new char[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
        return evaluate(grid);
    }
}