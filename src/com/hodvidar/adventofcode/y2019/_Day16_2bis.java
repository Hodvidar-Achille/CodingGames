package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

import com.hodvidar.utils.number.MillisecondeFormater;

// FALSE
public final class _Day16_2bis
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = true;

	private static final int NUMBER_OF_TEST = 16;
	private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

	public static void printIfVerbose(String s)
	{
		if(VERBOSE)
			System.err.println(s);
	}

	public static void main(String[] args) throws Exception
	{
		// 61706040 // 61706040
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST
				+ "-test4.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST
				+ "-test5.txt");
		subTest("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST
				+ "-test6.txt");
		//49623910 false
		String result = test("resources\\" + INPUT_DIRECTORY + "\\input"
				+ NUMBER_OF_TEST + ".txt");
		System.err.println("Expected '???' - result='" + result + "'");
	}

	private static final int dixMille = 10000;

	private static String subTest(String inputFile) throws Exception
	{
		System.err.println("Computing on file:" + inputFile);
		String line = "";
		File file = new File(inputFile);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");

		// Create the elements
		long before = System.currentTimeMillis();
		String expectedResult = "";
		line = sc.nextLine();
		expectedResult = sc.nextLine(); // Solution Day16_2
		sc.close();

		// The first seven digits of your initial input signal 
		// also represent the message offset.
		Integer offSet = Integer.parseInt(line.substring(0, 7));
		// x1000
		line = generateLongInput(line);

		printIfVerbose("Work start...");
		String result = faseAlgorithmBad(line);
		printIfVerbose("Work ended.");
		result = result.substring(offSet, offSet + 8);
		long after = System.currentTimeMillis();
		long diff = after - before;
		String diffStr = MillisecondeFormater.asTime(diff);
		System.err.println("Expected value:'" + expectedResult + "'");
		System.err.println("Found:'" + result + "' took:" + diffStr);
		return "" + result;
	}

	private static String test(String inputFile) throws Exception
	{
		return subTest(inputFile);
	}

	private static String generateLongInput(String s)
	{
		String input = "";
		for (int i = 0; i < dixMille; i++)
		{
			input = input + s;
		}
		System.out.println("Input generated " + input.length());
		return input;
	}

	private static String faseAlgorithmBad(String inp)
	{
		StringBuffer b = new StringBuffer();
		char[] chars = inp.toCharArray();

		int acc = 0;
		for (int i = 0; i < chars.length; i++)
		{
			int n = chars[chars.length - i - 1] - 48;
			acc += n;
			b.append(acc % 10);
		}
		b.reverse();
		return b.toString();

	}

	static int[] PATTERN = { 0, 1, 0, -1 };

	public static String faseAlgorithmGood(String x)
	{
		char[] chars = x.toCharArray();
		StringBuffer res = new StringBuffer();
		for (int charIdxBeingGenerated = 0; charIdxBeingGenerated < chars.length; charIdxBeingGenerated++)
		{

			long acc = 0;
			for (int charIdx = charIdxBeingGenerated /* skip initial zeroes */; charIdx < chars.length; charIdx++)
			{
				int n = chars[charIdx] - 48;
				int val = getMultiplier(charIdxBeingGenerated, charIdx);

				if((val == 0)) /* skip internal zero sequences*/
				{
					int skip = (charIdxBeingGenerated);
					// System.out.println("Skipping "+skip);
					charIdx += skip;
					continue;
				}
				acc = acc + n * val;
			}
			res.append(Math.abs(acc) % 10);
		}
		return res.toString();
	}

	private static int getMultiplier(int charIdxBeingGenerated, int charIdx)
	{
		int repetition = charIdxBeingGenerated + 1;
		int idx = (charIdx + 1) / repetition;
		int val = PATTERN[idx % 4];
		return val;
	}
}
