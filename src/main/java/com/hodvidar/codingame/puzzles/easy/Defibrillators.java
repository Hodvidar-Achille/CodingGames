package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/defibrillators by Hodvidar
 **/
class Defibrillators {

	public static void main(final String[] args) {
		final Scanner in = new Scanner(System.in);
		final double lonA = Math.toRadians(Double.parseDouble(in.next().replace(",", ".")));
		final double latA = Math.toRadians(Double.parseDouble(in.next().replace(",", ".")));
		final int N = in.nextInt();
		in.nextLine();

		double x, y, d, latB, lonB, dMin = -1.0;
		String defibName = "";
		for (int i = 0; i < N; i++) {
			final String DEFIB = in.nextLine();
			final String[] defib2 = DEFIB.split(";");
			lonB = Math.toRadians(Double.parseDouble(defib2[4].replace(",", ".")));
			latB = Math.toRadians(Double.parseDouble(defib2[5].replace(",", ".")));
			x = (lonB - lonA) * Math.cos((latB + latA) / 2);
			y = latB - latA;
			d = Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2)) * 6371;
			if (dMin < 0 || dMin > d) {
				dMin = d;
				defibName = defib2[1];
			}
		}

		// Write an action using System.out.println()
		// To debug: System.err.println("Debug messages...");

		System.out.println(defibName);
		in.close();
	}
}
