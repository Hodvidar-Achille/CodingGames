package com.hodvidar.formation.java15;

import static java.lang.Integer.parseInt;


// Minesweeper
public class Minesweeper {

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
		if(gridTab[rowIndex][columnIndex] == '*') {
			return '*';
		}
		int maxRows = gridTab.length;
		int maxColumns = gridTab[0].length;
		return '0';
	}
}
