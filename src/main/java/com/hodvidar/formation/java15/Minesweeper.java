package com.hodvidar.formation.java15;

import static java.lang.Integer.parseInt;


// Minesweeper
public class Minesweeper {

    private static final char LANDMINE = '*';

    public static String computeHintMap(final String grid) {
        // extract grid info
        final var gridIterator = grid.lines().iterator();
        final var firstline = gridIterator.next();
        final var rows = parseInt(firstline.split(" ")[0]);
        final var columns = parseInt(firstline.split(" ")[1]);
        final char[][] gridTab = new char[rows][columns];
        // Build the 2d array of character from the grid (string)
        for (int i = 0; i < rows; i++) {
            gridTab[i] = gridIterator.next().toCharArray();
        }
        // Use the grid to build or result
        final char[][] hintMapTab = new char[rows][columns];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                hintMapTab[i][j] = getHintValue(gridTab, i, j);
            }
        }
        // transform char[] into String
        var hintMap = "";
        for (int i = 0; i < rows; i++) {
            hintMap += new String(hintMapTab[i]) + "\n";
        }

        return hintMap;
    }

    private static char getHintValue(final char[][] gridTab, final int rowIndex, final int columnIndex) {
        if (gridTab[rowIndex][columnIndex] == LANDMINE) {
            return LANDMINE;
        }
        final int numberOfLandMineAround = checkNumberOfCharAround(gridTab, rowIndex, columnIndex, LANDMINE);
        return intToChar(numberOfLandMineAround);
    }

    private static char intToChar(final int numberBelowTen) {
        return (char) (numberBelowTen + '0');
    }

    /**
     * Checks number of occurrence of the given character in the given grid around (8 cells) a given coordinate,
     * with safely avoiding ArrayIndexOutOfBoundsException for
     *
     * @return
     */
    private static int checkNumberOfCharAround(final char[][] gridTab, final int rowIndex, final int columnIndex, final char searchedValue) {
        final var maxRows = gridTab.length;
        final var maxColumns = gridTab[0].length;

        var counter = 0;

        final var checkNorthPossible = rowIndex > 0;
        final var checkSouthPossible = rowIndex < maxRows - 1;
        final var checkWestPossible = columnIndex > 0;
        final var checkEastPossible = columnIndex < maxColumns - 1;
        // N
        if (checkNorthPossible) {
            if (gridTab[rowIndex - 1][columnIndex] == LANDMINE) {
                counter += 1;
            }
            // NE
            if (checkEastPossible) {
                if (gridTab[rowIndex - 1][columnIndex + 1] == LANDMINE) {
                    counter += 1;
                }
            }
            // NW
            if (checkWestPossible) {
                if (gridTab[rowIndex - 1][columnIndex - 1] == LANDMINE) {
                    counter += 1;
                }
            }
        }
        // E
        if (checkEastPossible) {
            if (gridTab[rowIndex][columnIndex + 1] == LANDMINE) {
                counter += 1;
            }
        }
        // S
        if (checkSouthPossible) {
            if (gridTab[rowIndex + 1][columnIndex] == LANDMINE) {
                counter += 1;
            }
            // SE
            if (checkEastPossible) {
                if (gridTab[rowIndex + 1][columnIndex + 1] == LANDMINE) {
                    counter += 1;
                }
            }
            // SW
            if (checkWestPossible) {
                if (gridTab[rowIndex + 1][columnIndex - 1] == LANDMINE) {
                    counter += 1;
                }
            }
        }

        // W
        if (checkWestPossible) {
            if (gridTab[rowIndex][columnIndex - 1] == LANDMINE) {
                counter += 1;
            }
        }

        return counter;
    }
}
