package com.hodvidarr.adventofcode.y2019;

import java.io.File;
import java.util.List;
import java.util.Scanner;

import com.hodvidarr.utils.number.DoubleFormater;
import com.hodvidarr.utils.regex.NumberExtractor;

public final class _Day12
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;

	private static final int NUMBER_OF_TEST = 12;
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
		String result = test("resources\\" + INPUT_DIRECTORY + "\\input"
				+ NUMBER_OF_TEST + ".txt");
		System.err.println("Expected '10055' - result='" + result + "'");
	}

	private static String subTest(String inputFile) throws Exception
	{
		String line;
		File file = new File(inputFile);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");

		// Create the Moons
		line = sc.nextLine();
		List<Double> values = NumberExtractor.extractNumber(line);
		Moon moonA = new Moon(values.get(0), values.get(1), values.get(2));
		line = sc.nextLine();
		values = NumberExtractor.extractNumber(line);
		Moon moonB = new Moon(values.get(0), values.get(1), values.get(2));
		line = sc.nextLine();
		values = NumberExtractor.extractNumber(line);
		Moon moonC = new Moon(values.get(0), values.get(1), values.get(2));
		line = sc.nextLine();
		values = NumberExtractor.extractNumber(line);
		Moon moonD = new Moon(values.get(0), values.get(1), values.get(2));

		// Create the system and make it simulate the moves.
		SpaceOrbitalSystem system = new SpaceOrbitalSystem(moonA, moonB, moonC,
				moonD);
		line = sc.nextLine();
		Integer nbSteps = Integer.parseInt(line);
		system.doSteps(nbSteps);
		double energy = system.getTotalEnergy();

		// WRITE SOLUTION
		line = sc.nextLine();
		sc.close();
		String energyStr = DoubleFormater.asInteger(energy);
		System.err.println("Expected energy value:'" + line + "'");
		System.err.println("Found:'" + energyStr + "'");
		return "" + energyStr;
	}

	private static String test(String inputFile) throws Exception
	{
		return subTest(inputFile);
	}
}
