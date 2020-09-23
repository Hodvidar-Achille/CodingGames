package com.hodvidar.formation.java15;

import static java.lang.Integer.parseInt;


// Minesweeper
public class Minesweeper {

	public static String computeHintMap(String grid) {
		var gridIterator = grid.lines().iterator();
		var firstline = gridIterator.next();
		var rows = firstline.split(" ")[0];
		var columns = firstline.split(" ")[1];
		var isMine = grid.contains("*");
		var v = (isMine) ? "*" : "0";
		var hintMap = v.repeat(parseInt(columns));
		hintMap = (hintMap+"\n").repeat(parseInt(rows));

		return hintMap;
	}
}
