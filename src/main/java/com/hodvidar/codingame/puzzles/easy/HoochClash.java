package com.hodvidar.codingame.puzzles.easy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/hooch-clash by Hodvidar.
 **/
class HoochClash {

	// -- TESTS :
	// 1 1000 9 10 --> 1 12
	// 1000 3000 1356 2644 --> 1200 2680
	// 1000 3000 2511 2962 --> 2719 2790
	// 1 3000 417 2962 --> 1290 2881
	// 1 15 9 15 --> VALID
	// 2 14 9 10 --> VALID
	// --

	/**
	 * Values -- % Result OK (Finalt Tests) 0 -- 100% (but not all tests in try tests) 1 -- 90% 2 --
	 * 90% 3 -- 90% 4 -- 90%
	 */
	private static final int ROUNDING_DECIMAL = 0;

	public static void main(final String[] args) {
		final Scanner in = new Scanner(System.in);
		final int minSize = in.nextInt();
		final int maxSize = in.nextInt();
		final int kingSize1 = in.nextInt();
		final int kingSize2 = in.nextInt();

		System.err.println("minSize=" + minSize);
		System.err.println("maxSize=" + maxSize);
		System.err.println("kingSize1=" + kingSize1);
		System.err.println("kingSize2=" + kingSize2);

		final double totalVolume = getSphereVolume(kingSize1 / 2d) + getSphereVolume(kingSize2 / 2d);
		// Look for "fun" contenders orb sizes.
		final Integer[] funResults = LookForFunSizes(totalVolume, minSize, maxSize, kingSize1, kingSize2);
		if (funResults != null) {
			System.out.println(funResults[0] + " " + funResults[1]);
			in.close();
			return;
		}
		// Look for "valid"
		// Write an action using System.out.println()
		// To debug: System.err.println("Debug messages...");

		System.out.println("VALID");
		in.close();
		// System.out.println("INVALID");
	}

	private static Integer[] LookForFunSizes(final double totalVolume, final int minSize, final int maxSize,
	                                         final int kingSize1,
	                                         final int kingSize2) {
		final List<Integer[]> listOfResult = new ArrayList<>();
		for (int i = minSize; i <= maxSize; i++) {
			if (i == kingSize1)
				continue;
			for (int j = maxSize; j > i; j--) {
				if (j == i)
					continue;
				if (j == kingSize2)
					continue;

				// --- DEBUGGING
				if (i == 1200 && j == 2680) {
					System.err.println("here");
				}
				// ---

				final double comparaison = compareVolume(totalVolume, i, j);
				if (comparaison > 0d)
					break;
				if (comparaison == 0d) {
					listOfResult.add(new Integer[] { i, j });
				}
			}
		}

		if (listOfResult.isEmpty())
			return null;

		// Filter the "funnier" result
		double maxAreaDiff = 0d;
		int index = 0;
		for (int i = 0; i < listOfResult.size(); i++) {
			final Integer[] result = listOfResult.get(i);
			final double radius1 = result[0] / 2d;
			final double radius2 = result[1] / 2d;
			final double diff = getSphereArea(radius2) - getSphereArea(radius1);
			if (diff > maxAreaDiff) {
				maxAreaDiff = diff;
				index = i;
			}
		}
		return listOfResult.get(index);
	}

	private static double compareVolume(final double totalVolume, final int diameter1, final int diameter2) {
		return totalVolume - (getSphereVolume(diameter1 / 2d) + getSphereVolume(diameter2 / 2d));
	}

	private static double getSphereVolume(final double sphereRadius) {
		final double sphereVolume = (4.0 / 3.0) * Math.PI * Math.pow(sphereRadius, 3);
		return rounding(sphereVolume);
	}

	private static double getSphereArea(final double sphereRadius) {
		final double sphereArea = 4 * Math.PI * Math.pow(sphereRadius, 2);
		return rounding(sphereArea);
	}

	private static double rounding(final double n) {
		if (isZero(ROUNDING_DECIMAL))
			return n;
		else
			return Math.round(n * ROUNDING_DECIMAL) / ROUNDING_DECIMAL;
	}

	private static boolean isZero(final double n) {
		return n == 0;
	}
}
