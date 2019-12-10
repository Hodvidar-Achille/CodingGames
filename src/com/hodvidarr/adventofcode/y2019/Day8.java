package com.hodvidarr.adventofcode.y2019;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public final class Day8
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;

	private static final int NUMBER_OF_TEST = 8;
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

	private static final int WIDE = 25;
	private static final int HIGH = 6;
	private static final int TOTAL = WIDE * HIGH;

	private static int test(String inputFile) throws Exception
	{
		String line;
		File file = new File(inputFile);
		// Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");

		line = sc.nextLine();
		String[] opCodeStr = line.split(",");
		sc.close();
		int[] pixels = new int[line.length()];
		Map<Integer, Layer> layers = new HashMap<>();
		int i = 0;
		for (char c : line.toCharArray())
		{
			int digit = Integer.parseInt("" + c);
			Integer layerNumber = (i / TOTAL) + 1;
			Layer layer = layers.get(layerNumber);
			if(layer == null)
			{
				layer = new Layer(WIDE, HIGH);
				layers.put(layerNumber, layer);
			}
			layer.addPixel(digit, i);
			i++;
		}

		int minZero = Integer.MAX_VALUE;
		Integer numberOfLayer = 0;
		for (int n = 1; n <= layers.size(); n++)
		{
			Layer l = layers.get(n);
			int nbZero = l.getNumberOfZero();
			if(nbZero < minZero)
			{
				minZero = nbZero;
				numberOfLayer = n;
			}
		}

		Layer finalLayer = layers.get(numberOfLayer);

		return finalLayer.getNumberOfOne() * finalLayer.getNumberOfTwo();
	}

}
