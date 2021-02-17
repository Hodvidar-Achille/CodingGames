package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/onboarding by Hodvidar
 */
class Onboarding {

	public static void main(final String[] args) {
		@SuppressWarnings("resource") final Scanner in = new Scanner(System.in);

		// game loop
		while (true) {
			final String enemy1 = in.next(); // name of enemy 1
			final int dist1 = in.nextInt(); // distance to enemy 1
			final String enemy2 = in.next(); // name of enemy 2
			final int dist2 = in.nextInt(); // distance to enemy 2

			// Write an action using System.out.println()

			// Enter the code here

			if (dist1 < dist2) {
				System.out.println(enemy1);
			} else {
				System.out.println(enemy2);
			}

		}
	}
}
