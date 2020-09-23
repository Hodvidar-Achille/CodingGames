package com.hodvidar.formation.java15;

import static java.lang.Integer.parseInt;


// Minesweeper
public class Minesweeper {

	private static final char LANDMINE = '*';

	public static String computeHintMap(String grid) {
		// extract grid info
		var gridIterator = grid.lines().iterator();
		var firstline = gridIterator.next();
		var rows = parseInt(firstline.split(" ")[0]);
		var columns = parseInt(firstline.split(" ")[1]);
		char[][] gridTab = new char[rows][columns];
		// Build the 2d array of character from the grid (string)
		for(int i = 0; i < rows; i++) {
			gridTab[i] = gridIterator.next().toCharArray();
		}
		// Use the grid to build or result
		char[][] hintMapTab = new char[rows][columns];
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < columns; j++) {
				hintMapTab[i][j] = getHintValue(gridTab, i, j);
			}
		}
		// transform char[] into String
		var hintMap = "";
		for(int i = 0; i < rows; i++) {
			hintMap += new String(hintMapTab[i]) + "\n";
		}

		return hintMap;
	}

	private static char getHintValue(char[][] gridTab, int rowIndex, int columnIndex) {
		if(gridTab[rowIndex][columnIndex] == LANDMINE) {
			return LANDMINE;
		}
		int numberOfLandMineAround = checkNumberOfCharAround(gridTab, rowIndex, columnIndex, LANDMINE);
		return intToChar(numberOfLandMineAround);
	}

	private static char intToChar(int numberBelowTen) {
		return (char)(numberBelowTen+'0');
	}

	/**
	 * Checks number of occurrence of the given character in the given grid around (8 cells) a given coordinate,
	 * with safely avoiding ArrayIndexOutOfBoundsException for
	 * @return
	 */
	private static int checkNumberOfCharAround(char[][] gridTab, int rowIndex, int columnIndex, char searchedValue) {
		var maxRows = gridTab.length;
		var maxColumns = gridTab[0].length;

		var counter = 0;

		var checkNorthPossible = rowIndex > 0;
		var checkSouthPossible = rowIndex < maxRows-1;
		var checkWestPossible = columnIndex > 0;
		var checkEastPossible = columnIndex < maxColumns-1;
		// N
		if(checkNorthPossible) {
			if (gridTab[rowIndex - 1][columnIndex] == LANDMINE) {
				counter += 1;
			}
			// NE
			if (checkEastPossible) {
				if (gridTab[rowIndex - 1][columnIndex+1] == LANDMINE) {
					counter += 1;
				}
			}
			// NW
			if(checkWestPossible) {
				if(gridTab[rowIndex-1][columnIndex-1] == LANDMINE) {
					counter += 1;
				}
			}
		}
		// E
		if(checkEastPossible) {
			if(gridTab[rowIndex][columnIndex+1] == LANDMINE) {
				counter += 1;
			}
		}
		// S
		if(checkSouthPossible) {
			if(gridTab[rowIndex+1][columnIndex] == LANDMINE) {
				counter += 1;
			}
			// SE
			if(checkEastPossible) {
				if(gridTab[rowIndex+1][columnIndex+1] == LANDMINE) {
					counter += 1;
				}
			}
			// SW
			if(checkWestPossible) {
				if(gridTab[rowIndex+1][columnIndex-1] == LANDMINE) {
					counter += 1;
				}
			}
		}

		// W
		if(checkWestPossible) {
			if(gridTab[rowIndex][columnIndex-1] == LANDMINE) {
				counter += 1;
			}
		}

		return counter;
	}
}
