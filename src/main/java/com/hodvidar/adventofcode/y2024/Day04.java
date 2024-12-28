package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day04 extends AbstractAdventOfCode2024 {
    public static final String XMAS = "XMAS";
    public static final String SAMX = "SAMX";
    protected final List<List<Character>> grid = new ArrayList<>();

    public static void addLineToGrid(final List<List<Character>> grid, final String line) {
        final List<Character> row = new ArrayList<>();
        for (final char c : line.toCharArray()) {
            row.add(c);
        }
        grid.add(row);
    }

    public static List<List<Character>> getHorizontalLines(final List<List<Character>> grid) {
        return new ArrayList<>(grid);
    }

    public static List<List<Character>> getVerticalLines(final List<List<Character>> grid) {
        final List<List<Character>> verticalLines = new ArrayList<>();
        final int columns = grid.getFirst().size();
        for (int col = 0; col < columns; col++) {
            final List<Character> line = new ArrayList<>();
            for (final List<Character> row : grid) {
                line.add(row.get(col));
            }
            verticalLines.add(line);
        }
        return verticalLines;
    }

    public static List<List<Character>> getDiagonalLines(final List<List<Character>> grid) {
        final List<List<Character>> diagonalLines = new ArrayList<>();
        final int rows = grid.size();
        final int cols = grid.get(0).size();

        // Diagonals starting from the top row
        for (int col = 0; col < cols; col++) {
            final List<Character> diagonal = new ArrayList<>();
            for (int i = 0; i < rows && col + i < cols; i++) {
                diagonal.add(grid.get(i).get(col + i));
            }
            diagonalLines.add(diagonal);
        }

        // Diagonals starting from the left column (excluding the first diagonal)
        for (int row = 1; row < rows; row++) {
            final List<Character> diagonal = new ArrayList<>();
            for (int i = 0; i < cols && row + i < rows; i++) {
                diagonal.add(grid.get(row + i).get(i));
            }
            diagonalLines.add(diagonal);
        }

        return diagonalLines;
    }

    public static List<List<Character>> getReverseDiagonalLines(final List<List<Character>> grid) {
        final List<List<Character>> reverseDiagonalLines = new ArrayList<>();
        final int rows = grid.size();
        final int cols = grid.get(0).size();

        // Reverse diagonals starting from the top row
        for (int col = cols - 1; col >= 0; col--) {
            final List<Character> diagonal = new ArrayList<>();
            for (int i = 0; i < rows && col - i >= 0; i++) {
                diagonal.add(grid.get(i).get(col - i));
            }
            reverseDiagonalLines.add(diagonal);
        }

        // Reverse diagonals starting from the rightmost column (excluding the first diagonal)
        for (int row = 1; row < rows; row++) {
            final List<Character> diagonal = new ArrayList<>();
            for (int i = 0; i < cols && row + i < rows; i++) {
                diagonal.add(grid.get(row + i).get(cols - 1 - i));
            }
            reverseDiagonalLines.add(diagonal);
        }

        return reverseDiagonalLines;
    }

    public static int countWordOccurrences(final List<List<Character>> lines, final String word) {
        int count = 0;
        for (final List<Character> line : lines) {
            final StringBuilder sb = new StringBuilder();
            for (final Character c : line) {
                sb.append(c);
            }
            final String lineString = sb.toString();
            int index = lineString.indexOf(word);
            while (index != -1) {
                count++;
                index = lineString.indexOf(word, index + 1);
            }
        }
        return count;
    }


    @Override
    public double getResultDouble(final Scanner sc) {
        grid.clear();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }

        final List<List<Character>> horizontalLines = getHorizontalLines(grid);
        final List<List<Character>> verticalLines = getVerticalLines(grid);
        final List<List<Character>> NWtoSEDiagonalLines = getDiagonalLines(grid);
        final List<List<Character>> NEtoSWDiagonalLines = getReverseDiagonalLines(grid);


        final int countXMAS = countWordOccurrences(horizontalLines, XMAS) +
                countWordOccurrences(verticalLines, XMAS) +
                countWordOccurrences(NWtoSEDiagonalLines, XMAS) +
                countWordOccurrences(NEtoSWDiagonalLines, XMAS);

        final int countSAMX = countWordOccurrences(horizontalLines, SAMX) +
                countWordOccurrences(verticalLines, SAMX) +
                countWordOccurrences(NWtoSEDiagonalLines, SAMX) +
                countWordOccurrences(NEtoSWDiagonalLines, SAMX);


        return countXMAS + countSAMX;
    }

    @Override
    protected void digestLine(final String line) {
        addLineToGrid(grid, line);
    }
}
