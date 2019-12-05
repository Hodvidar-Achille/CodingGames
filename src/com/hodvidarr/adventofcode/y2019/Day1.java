package com.hodvidarr.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

/**
 * Passed
 * 
 * 
 * @author Hodvidar
 *
 */
public final class Day1
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;

	private static final int NUMBER_OF_TEST = 1;
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
		String line;
		File file = new File(inputFile);
		// Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");

		// for(int i = 0; i< input.length; i++)
		int totalFuel = 0;
		while (sc.hasNextLine())
		{
			// line = input[i];
			line = sc.nextLine();
			int mass = Integer.parseInt(line);
			int fuel = calculateFuel(mass);
			totalFuel += fuel;
		}

		sc.close();

		return totalFuel;
	}

	/**
	 * Calculates Fuel needed for the given mass. <br/>
	 * divide by 3, round down, substract 2
	 * @param mass
	 * @return
	 */
	private static int calculateFuel(int mass)
	{
		double r = mass / 3d;
		int r2 = (int) Math.floor(r);
		r2 = r2 - 2;
		return r2;
	}
}
