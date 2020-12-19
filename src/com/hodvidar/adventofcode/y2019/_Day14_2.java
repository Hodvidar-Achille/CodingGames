package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

import com.hodvidar.utils.number.DoubleFormater;
import com.hodvidar.utils.number.MillisecondeFormater;

/**
 * 2690795 
 * @author Hodvidar
 *  1000000000000
 */
public final class _Day14_2
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;

	private static final int NUMBER_OF_TEST = 14;
	private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

	public static void printIfVerbose(String s)
	{
		if(VERBOSE)
			System.err.println(s);
	}

	public static void main(String[] args) throws Exception
	{
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST
				+ "-test3.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST
				+ "-test4.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST
				+ "-test5.txt");
		String result = test("resources\\" + INPUT_DIRECTORY + "\\input"
				+ NUMBER_OF_TEST + ".txt");
		System.err.println("Expected '2690795' - result='" + result + "'");
	}

	private static final double oneTrillion = 1000000000000d;

	private static String subTest(String inputFile) throws Exception
	{
		System.err.println("Computing on file:" + inputFile);
		String line = "";
		File file = new File(inputFile);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");

		// Create the elements
		String expectedResult = "";
		ChemicalSystem system = new ChemicalSystem(true, oneTrillion);
		while (sc.hasNext())
		{
			line = sc.nextLine();
			if(!line.contains("=>"))
			{
				break;
			}
			system.addReaction(line);

		}
		expectedResult = line; // Solution for Day14
		expectedResult = sc.nextLine(); // Solution for Day14_2
		sc.close();
		long before = System.currentTimeMillis();
		system.initReaction(ChemicalSystem.FUEL);
		double result = system.getQuantityAvailable(ChemicalSystem.FUEL);
		String resultStr = DoubleFormater.asInteger(result);
		long after = System.currentTimeMillis();
		long diff = after - before;
		String diffStr = MillisecondeFormater.asTime(diff);
		System.err.println("Expected value:'" + expectedResult + "'");
		System.err.println("Found:'" + resultStr + "'");
		System.err.println("Took:'" + diffStr + "'");
		return "" + resultStr;
	}

	private static String test(String inputFile) throws Exception
	{
		return subTest(inputFile);
	}
}