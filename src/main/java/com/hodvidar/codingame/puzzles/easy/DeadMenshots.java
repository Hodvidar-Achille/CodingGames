package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

import com.hodvidar.utils.geometry.GeometryServices;
import com.hodvidar.utils.geometry.Point;

/**
 * https://www.codingame.com/ide/puzzle/dead-mens-shot by Hodvidar
 **/
public class DeadMenshots {

	private static final String HIT = "hit";
	private static final String MISS = "miss";

	public static void main(final String[] args) {
		final DeadMenshots d = new DeadMenshots();
		d.test();
	}

	public void test() {
		final Scanner in = new Scanner(System.in);
		final int N = in.nextInt();
		final Point[] polygon = new Point[N];
		System.err.println("Polygon of N points : " + N);
		int max_X = Integer.MIN_VALUE;
		for (int i = 0; i < N; i++) {
			final int x = in.nextInt();
			final int y = in.nextInt();
			System.err.println((i + 1) + ": x=" + x + "  -  y=" + y);
			polygon[i] = new Point(x, y);
			if (x > max_X)
				max_X = x;
		}
		final int M = in.nextInt();

		System.err.println("max_X=" + max_X);
		final GeometryServices gs = new GeometryServices(max_X);

		System.err.println("Number of shoots : " + M);
		for (int i = 0; i < M; i++) {
			final int x = in.nextInt();
			final int y = in.nextInt();

			System.err.println((i + 1) + ": x=" + x + "  -  y=" + y);
			final Point shoot = new Point(x, y);
			if (gs.isInside(polygon, N, shoot))
				System.out.println(HIT);
			else
				System.out.println(MISS);
		}

		// Write an action using System.out.println()
		// To debug: System.err.println("Debug messages...");

		// System.out.println("hit_or_miss");
		in.close();
	}

}
