package com.hodvidar.mdf.hackathon.y2019.h1800;

// ---- Do not Copy upper part ----

import java.io.File;
import java.util.Scanner;

/**
 * @author Hodvidar
 */
public final class Problem4 // <-- IsoConstest
{
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
		final Problem4 r = new Problem4(); // <-- IsoContest
		r.doTests();
	}

	public static void printIfVerbose(final String s) {
		if (VERBOSE)
			System.err.println(s);
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
		System.err.println(" --- TEST class" + this.getClass().getSimpleName() + " ---");
		for (; i <= max; i++) {
			System.err.println("\n--- TEST n°" + i + " --");
			final String result = test("resources" + File.separator + INPUT_DIRECTORY + File.separator + "input" + i
			                           + ".txt");
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
		printIfVerbose("Reading inputs...");
		int i = 0;
		while (sc.hasNextLine()) {
			i++;
			line = sc.nextLine();
			printIfVerbose("i=" + i + " line:" + line);
			if (i == 1) {
				// do stuff
				continue;
			}

			// do other stuff

		}
		printIfVerbose("Searching....");
		// Compute result

		final String result = "";
		System.out.println(result);
		sc.close();
		return result;
		// ----------------------------------------------------
		// ------------------ END ---------------------------
		// ----------------------------------------------------
	}
}