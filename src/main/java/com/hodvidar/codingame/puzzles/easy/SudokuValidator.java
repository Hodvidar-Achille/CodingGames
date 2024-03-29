package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/sudoku-validator by Hodvidar
 **/
class SudokuValidator {

    public static void main(final String[] args) {
        final int[][] sudoku = new int[9][9];
        final Scanner in = new Scanner(System.in);
        for (int i = 0; i < 9; i++) {
            String row = in.nextLine();
            row = row.replaceAll("\\s+", "");
            final char[] letters = row.toCharArray();
            for (int j = 0; j < 9; j++) {
                sudoku[i][j] = Character.getNumericValue(letters[j]);
            }
        }

        // Write an answer using System.out.println()
        // To debug: System.err.println("Debug messages...");
        if (checkSudokuResult(sudoku)) {
            System.out.println("true");
        } else {
            System.out.println("false");
        }
    }

    private static boolean checkSudokuResult(final int[][] sudoku) {
        // Check that each line and each column has only one time each 9 numbers
        for (int i = 0; i < 9; i++) {
            final int[] lineNumbers = new int[9];
            final int[] columnNumbers = new int[9];

            for (int j = 0; j < 9; j++) {
                final int lineCurrentNumber = sudoku[i][j];
                for (int lineP = 0; lineP < j; lineP++) {
                    if (lineCurrentNumber == lineNumbers[lineP]) {
                        return false;
                    }
                }
                lineNumbers[j] = lineCurrentNumber;

                final int columnCurrentNumber = sudoku[j][i];
                for (int columnP = 0; columnP < j; columnP++) {
                    if (columnCurrentNumber == columnNumbers[columnP]) {
                        return false;
                    }
                }
                columnNumbers[j] = columnCurrentNumber;
            }
        }

        // Check that each subgrid has only one time each 9 numbers
        for (int subL = 0; subL < 3; subL++) {
            for (int subC = 0; subC < 3; subC++) {
                final int[] gridNumbers = new int[9];
                for (int i = 0; i < 3; i++) {
                    for (int j = 0; j < 3; j++) {
                        final int subGridCurrentNumber = sudoku[(subL * 3) + i][(subC * 3) + j];
                        final int subGridPosition = (i * 3) + j;
                        for (int gridP = 0; gridP < subGridPosition; gridP++) {
                            if (subGridCurrentNumber == gridNumbers[gridP]) {
                                return false;
                            }
                        }
                        gridNumbers[subGridPosition] = subGridCurrentNumber;
                    }
                }
            }

        }

        return true;
    }
}
