package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

/**
 * 504284  good
 * @author a.genet
 *
 */
public final class _Day14
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
				+ "-test1.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST
				+ "-test2.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST
				+ "-test3.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST
				+ "-test4.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST
				+ "-test5.txt");
		String result = test("resources\\" + INPUT_DIRECTORY + "\\input"
				+ NUMBER_OF_TEST + ".txt");
		System.err.println("Expected '504284' - result='" + result + "'");
	}

	private static String subTest(String inputFile) throws Exception
	{
		System.err.println("Computing on file:" + inputFile);
		String line = "";
		File file = new File(inputFile);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");

		// Create the elements
		String expectedResult = "";
		ChemicalSystem system = new ChemicalSystem();
		while (sc.hasNext())
		{
			line = sc.nextLine();
			if(!line.contains("=>"))
			{
				break;
			}
			system.addReaction(line);

		}
		expectedResult = line;
		sc.close();

		system.initReaction(ChemicalSystem.FUEL);
		double result = system.getCostInOreOfReaction();

		System.err.println("Expected value:'" + expectedResult + "'");
		System.err.println("Found:'" + result + "'");
		return "" + result;
	}

	private static String test(String inputFile) throws Exception
	{
		return subTest(inputFile);
	}
}
