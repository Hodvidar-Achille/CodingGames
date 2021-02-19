package com.hodvidar.mdf.hackathon.y2019.h1800;

// ---- Do not Copy upper part ----

import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Map;
import java.util.Scanner;

/**
 * @author Hodvidar
 */
@SuppressWarnings("unused")
public final class IsoConstest2 {
	private static final boolean TESTING = true; // <-- false
	/**
	 * If 'false' only response and Failure are written
	 **/
	private static final boolean VERBOSE = true; // <-- False
	private static final boolean ONE_TEST = false;
	private static final int ONE_TEST_NUMBER = 1;
	private static final int NUMBER_OF_TESTS = 6; // TO CHANGE
	private static final String INPUT_DIRECTORY = "xxx_input"; // TO CHANGE

	// Name of class to put back : IsoContest
	public static void main(final String[] args) throws Exception {
		final IsoConstest2 r = new IsoConstest2(); // <-- IsoContest
		r.doTests();
	}

	public static void d(final String s) {
		System.err.println(s);
	}

	public static void debug(final String s) {
		if (VERBOSE)
			System.err.println(s);
	}

	public static void out(final String line) {
		System.out.println(line);
	}

	public static void printmap(final Integer[][] map) {
		debug("printmap()");
		for (int y = 0; y < map.length; y++) {
			String s = "";
			for (int x = 0; x < map[0].length; x++) {
				if (map[y][x] < 0) {
					s += "#";
				} else if (map[y][x] == 0) {
					s += ".";
				} else {
					s += map[y][x];
				}
			}
			debug(s);
		}
		debug("end printmap()");
	}

	// ==========================================================
	// ======================== HELPERS =========================
	// ==========================================================
	/* PRINT */

	public static Integer toInt(final String line) {
		return Integer.parseInt(line);
	}

	public static Float toFloat(final String line) {
		return Float.parseFloat(line);
	}

	/* PARSERS */

	public static String[] split(final String line, final String regex) {
		return line.split(regex);
	}

	public static LocalTime toTime(final String time) {
		return LocalTime.parse(time, DateTimeFormatter.ISO_TIME); // such as '10:15', '10:15:30' or
		                                                          // '10:15:30+01:00'
	}

	public static char[] toAnArrayOfChar(final String line) {
		return line.toCharArray();
	}

	// copie d'un tableau à 2 dimensions
	public static int[][] copyTwoDimensionalArray(final int[][] array) {
		final int[][] copy = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
		return copy;
	}

	public void doTests() throws Exception {
		if (!TESTING) {
			test(null);
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
		d(" --- TEST class" + this.getClass().getSimpleName() + " ---");
		for (; i <= max; i++) {
			System.err.println("\n--- TEST n°" + i + " --");
			final String result = test("resources" + File.separator + INPUT_DIRECTORY + File.separator + "input" + i
			                           + ".txt");
			// --- CHECKING ---
			final File file2 = new File("resources" + File.separator + INPUT_DIRECTORY + "\\output" + i + ".txt");
			// Scanner sc = new Scanner(System.in);
			final Scanner sc2 = new Scanner(file2);
			final String line2 = sc2.nextLine();
			d("Solution is: \n" + line2);
			if (result.equals(line2))
				d("SUCCESS!");
			else
				d("FAILURE! found: " + result);
			sc2.close();
		}
	}

	/* trucs */

	private String test(final String inputFile) throws Exception {
		String line = "";
		final Scanner sc;
		if (TESTING) {
			final File file = new File(inputFile);
			sc = new Scanner(file);
		} else {
			sc = new Scanner(System.in);
		}
		// ----------------------------------------------------
		// --------------------- START ------------------------
		// ----------------------------------------------------
		debug("Reading inputs...");
		int i = 0;
		while (sc.hasNextLine()) {
			i++;
			line = sc.nextLine();
			debug("i=" + i + " line:" + line);
			if (i == 1) {
				// do stuff
				continue;
			}

			// do other stuff

		}
		debug("Searching....");
		// Compute result

		final String result = "";
		System.out.println(result);
		sc.close();
		return result;
		// ----------------------------------------------------
		// ------------------ END ---------------------------
		// ----------------------------------------------------
	}

	// différence (en minutes) entre deux LocalTime
	long diffBetweenTwoLocalTimeInMinutes(final LocalTime time1, final LocalTime time2) {
		return time1.until(time2, ChronoUnit.MINUTES);
	}

	public void forEachOnMap(final Map<String, Integer> map) {
		for (final Map.Entry<String, Integer> entry : map.entrySet()) {
			final String key = entry.getKey();
			final Integer value = entry.getValue();

			// do what you have to do here
		}
	}
}