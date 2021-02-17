package com.hodvidar.mdf.hackathon.y2019.creditagricole;

import java.io.File;
import java.util.Scanner;

/**
 * https://www.isograd.com/FR/solutionconcours.php Done in [Algorithm too slow for the hard cases]
 * (45min10sec) By Hodvidar
 */
public class PowerPlant {

	// Name of class to put back : IsoContest

	private static final boolean TESTING = true; // ### To change for submit ###

	/**
	 * If 'false' only response and Failure are written
	 **/
	private static final boolean VERBOSE = true;

	private static final boolean ONE_TEST = true;
	private static final int ONE_TEST_NUMBER = 9;
	private static final int NUMBER_OF_TESTS = 10; // TO CHANGE
	private static final String INPUT_DIRECTORY = "power-plant_input"; // TO CHANGE

	public static void main(final String[] args) throws Exception {
		final PowerPlant r = new PowerPlant();
		if (!TESTING) {
			r.test(null);
			return;
		}
		int i;
		final int max;
		if (ONE_TEST) {
			i = ONE_TEST_NUMBER;
			max = ONE_TEST_NUMBER;
		} else {
			i = 1;
			max = NUMBER_OF_TESTS;
		}
		for (; i <= max; i++) {
			System.err.println("\n--- TEST n°" + i + " --");
			final String result = r
			        .test("resources" + File.separator + INPUT_DIRECTORY + File.separator + "input" + i + ".txt");
			// --- CHECKING ---
			final File file2 = new File("resources" + File.separator + INPUT_DIRECTORY + "\\output" + i + ".txt");
			// Scanner sc = new Scanner(System.in);
			final Scanner sc2 = new Scanner(file2);
			final String line2 = sc2.nextLine();
			System.err.println("Solution is: \n" + line2);
			if (result.equals(line2))
				System.err.println("SUCCESS!");
			else
				System.err.println("FAILURE! found: " + result);
			sc2.close();
		}
	}

	public static void printIfVerbose(final String s) {
		if (VERBOSE)
			System.err.println(s);
	}

	private String test(final String inputFile) throws Exception {
		String line = "";
		final Scanner sc;
		if (TESTING) {
			final File file = new File(inputFile);
			sc = new Scanner(file);
		} else {
			sc = new Scanner(System.in);
		}
		// --- INPUT ---
		printIfVerbose("Reading inputs...");
		int i = 0;
		int levels = 0;
		int largeur = 0;
		int[][] piste = null;
		while (sc.hasNextLine()) {
			i++;
			line = sc.nextLine();
			printIfVerbose("i=" + i + " line:" + line);
			if (i == 1) {
				final String[] line2 = line.split("\\s+");
				levels = Integer.parseInt(line2[0]);
				largeur = Integer.parseInt(line2[1]);
				piste = new int[levels][largeur];
				continue;
			}

			// do other stuff
			int i2 = 0;
			for (final char c : line.toCharArray()) {
				i2++;
				if (c == 'X') {
					piste[i - 2][i2 - 1] = 1;
				}
			}
		}

		printIfVerbose("Searching...");

		int min = Integer.MAX_VALUE;
		for (int j = 0; j < largeur; j++) {
			if (piste[0][j] == 0) {
				final int r = lookForPath(piste, 0, j, levels - 1, largeur - 1, -100);
				if (r > 0 && r < min)
					min = r;
			}
		}
		if (min == Integer.MAX_VALUE)
			min = -1;

		final String result = "" + min;
		System.out.println(result);
		sc.close();
		return result;
	}

	/**
	 * Returns sum until number is between 10 and 99
	 **/
	private int lookForPath(final int[][] piste, final int i, final int j, final int max, final int largeur,
	                        final int previousJ) {
		printIfVerbose("i: " + i + "  j:" + j);
		if (i == max)
			return 0;

		int south = -1;
		int east = -1;
		int west = -1;

		// GO S (always if possible)
		if (piste[i + 1][j] == 0) {
			south = lookForPath(piste, i + 1, j, max, largeur, j);
		}

		// GO E (if not W before)
		if (previousJ != j + 1) {
			if (j < largeur && piste[i][j + 1] == 0) {
				east = lookForPath(piste, i, j + 1, max, largeur, j);
			}
		}

		// GO W
		if (previousJ != j - 1) {
			if (j > 0 && piste[i][j - 1] == 0) {
				west = lookForPath(piste, i, j - 1, max, largeur, j);
			}
		}

		// no Path
		if (west == -1 && east == -1 && south == -1)
			return Integer.MIN_VALUE;

		// take the lower between the >0
		int min = Integer.MAX_VALUE;
		if (south >= 0)
			min = south;
		if (east > 0 && min > east)
			min = east;
		if (west > 0 && min > west)
			min = west;
		return 1 + min;
	}

}