package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

import com.hodvidar.utils.geometry.GeometryServices;
import com.hodvidar.utils.geometry.Point;

/**
 * https://www.codingame.com/ide/puzzle/nature-of-quadrilaterals by Hodvidar.
 **/
class NatureOfQuadrilaterals {

	public static void main(final String[] args) {
		final NatureOfQuadrilaterals d = new NatureOfQuadrilaterals();
		d.test();
	}

	public void test() {
		final Scanner in = new Scanner(System.in);
		System.out.println("Enter the name of the first point: ");
		final String A = in.next();
		System.out.println("Enter its X coordinate (ex: 1 or 157.12 ): ");
		final double xA = in.nextDouble();
		System.out.println("Enter its Y coordinate: ");
		final double yA = in.nextDouble();

		System.out.println("Enter the name of the second point: ");
		final String B = in.next();
		System.out.println("Enter its X coordinate: ");
		final double xB = in.nextDouble();
		System.out.println("Enter its Y coordinate: ");
		final double yB = in.nextDouble();

		System.out.println("Enter the name of the third point: ");
		final String C = in.next();
		System.out.println("Enter its X coordinate: ");
		final double xC = in.nextDouble();
		System.out.println("Enter its Y coordinate: ");
		final double yC = in.nextDouble();

		System.out.println("Enter the name of the fourth point: ");
		final String D = in.next();
		System.out.println("Enter its X coordinate: ");
		final double xD = in.nextDouble();
		System.out.println("Enter its Y coordinate: ");
		final double yD = in.nextDouble();

		final Point a = new Point(xA, yA);
		final Point b = new Point(xB, yB);
		final Point c = new Point(xC, yC);
		final Point d = new Point(xD, yD);
		final String result = A + B + C + D + " is a " + GeometryServices.getQuadrilateralType(a, b, c, d).toLowerCase()
		                + ".";
		System.out.println(result);
		in.close();
	}

}
