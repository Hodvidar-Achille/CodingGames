package com.hodvidarr.adventofcode.y2019;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.hodvidarr.utils.geometry.GeometryServices;
import com.hodvidarr.utils.geometry.Point;
import com.hodvidarr.utils.geometry.Wire;
import com.hodvidarr.utils.geometry.WireBuilder;

/**
 * Passed 1285
 * 
 * @author Hodvidar
 *
 */
public final class Day3
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;

	private static final int NUMBER_OF_TEST = 4;
	private static final String INPUT_DIRECTORY = "aventofcode_2019"; // input1

	public static void printIfVerbose(String s)
	{
		if(VERBOSE)
			System.err.println(s);
	}

	public static void main(String[] args) throws Exception
	{
		int result = test("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST + ".txt");
		System.err.println("result='" + result + "'");
	}

	private static int test(String inputFile) throws Exception
	{
		File file = new File(inputFile);
		// Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");

		// 2 Lines
		String line1 = sc.nextLine();
		String line2 = sc.nextLine();
		sc.close();

		String[] directions1 = line1.split(",");
		String[] directions2 = line2.split(",");

		Point origin = new Point(0, 0);

		WireBuilder wb1 = new WireBuilder(origin);
		WireBuilder wb2 = new WireBuilder(origin);

		for (String d : directions1)
			wb1.addInstruction(d);
		for (String d : directions2)
			wb2.addInstruction(d);

		Wire w1 = wb1.getWire();
		Wire w2 = wb2.getWire();

		List<Point> intersections = w1.getIntersections(w2);

		double minDistance = Double.MAX_VALUE;
		for (Point p : intersections)
		{
			double dist = GeometryServices.getManhattanDistance(origin, p);
			if(dist == 0)
				continue;
			if(dist < minDistance)
				minDistance = dist;
		}

		return (int) minDistance;
	}

}
