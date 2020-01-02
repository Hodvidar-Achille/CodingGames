package com.hodvidarr.adventofcode.y2019;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.hodvidarr.utils.geometry.GeometryServices;
import com.hodvidarr.utils.geometry.Point;
import com.hodvidarr.utils.geometry.Segment;

public final class _Day10
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;

	private static final int NUMBER_OF_TEST = 10;
	private static final String INPUT_DIRECTORY = "aventofcode_2019"; // input1

	public static void printIfVerbose(String s)
	{
		if(VERBOSE)
			System.err.println(s);
	}

	public static void main(String[] args) throws Exception
	{
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST + "-test1.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST + "-test2.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST + "-test3.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST + "-test4.txt");
		String result = test("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST + ".txt");
		System.err.println("Expected '260' - result='" + result + "'");
	}

	private static final char ASTEROID = '#';

	private static String subTest(String inputFile) throws Exception
	{
		String line;
		File file = new File(inputFile);
		// Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");

		int y = 0;
		List<Point> asteroids = new ArrayList<>();
		// CREATE ASTEROIDS
		while (true)
		{
			line = sc.nextLine(); // KO : 203 and 0
			if(line.isEmpty())
				break;

			int x = 0;
			for (char c : line.toCharArray())
			{
				if(c == ASTEROID)
				{
					asteroids.add(new Point(x, y));
				}
				x++;
			}
			y++;
		}

		// STUDY ASTEROIDS
		Point bestPlace = null;
		int maxAsteroid = 0;
		for (Point p : asteroids)
		{
			int nbDetected = numberOfDetection(p, asteroids);
			if(nbDetected > maxAsteroid)
			{
				bestPlace = p;
				maxAsteroid = nbDetected;
			}
		}

		// WRITE SOLUTION
		line = sc.nextLine();
		line += " With total of " + sc.nextLine();
		sc.close();
		System.err.println("Expected to find asteroid:" + line + " asteroids detected.");
		System.err.println("Found:" + bestPlace + " with:" + maxAsteroid);
		return "" + maxAsteroid;
	}

	public static int numberOfDetection(Point p, List<Point> others)
	{
		int counter = 0;
		for (Point o : others)
		{
			if(o.equals(p))
				continue;
			if(lineOfSightClear(p, o, others))
				counter++;
		}
		return counter;
	}

	private static boolean lineOfSightClear(Point p1, Point p2, List<Point> others)
	{
		Segment s = new Segment(p1, p2);
		boolean lineOfSightClear = true;
		for (Point o2 : others)
		{
			if(o2.equals(p1) || o2.equals(p2))
				continue;
			if(GeometryServices.isOnSegment(o2, s))
			{
				lineOfSightClear = false;
				break;
			}
		}
		return lineOfSightClear;
	}

	private static String test(String inputFile) throws Exception
	{
		return subTest(inputFile);
	}
}
