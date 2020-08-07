package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public final class _Day18
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;

	private static final int NUMBER_OF_TEST = 18;
	private static final String INPUT_DIRECTORY = "aventofcode_2019"; // input1

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
		String result = test("resources\\" + INPUT_DIRECTORY + "\\input"
				+ NUMBER_OF_TEST + ".txt");
		System.err.println("Expected '34841690' - result='" + result + "'");
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
		line = sc.nextLine();
		List<Integer> numbers = new ArrayList<>();
		for (char c : line.toCharArray())
		{
			numbers.add(Integer.parseInt("" + c));
		}
		expectedResult = sc.nextLine();
		sc.close();

		String result = "";
		System.err.println("Found:'" + result + "'");
		System.err.println("Expected:'" + expectedResult + "'");
		return "" + result;
	}

	private static String test(String inputFile) throws Exception
	{
		return subTest(inputFile);
	}

}
