package com.hodvidar.primers.artoptimal;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://primers.xyz/0 -----------------------
 *
 * ENTREE
 *
 * Un fichier texte représentant une image à peindre. La première ligne contient la largeur et la
 * hauteur de l'image. Les lignes suivantes représentent les pixels de l'image. Le symbole '*'
 * indique un pixel vierge et le symbole '#' indique un pixel peint. Exemple d'entrée :
 *
 * 6,5 ###**# ###*** ###*** ***#** ***##*
 *
 * PROBLÈME
 *
 * L'objectif est de peindre l'image en un minimum d'opérations. Deux opérations sont possibles :
 *
 * FILL,x,y,size : peint un carré de taille size dont le côté en haut et à gauche est en x, y.
 * ERASE,x,y : efface la peinture du pixel en x, y.
 *
 * Ce problème provient du Google Hash Code 2014.
 *
 * SORTIE
 *
 * La liste des opérations nécessaires pour peindre l'image, séparées par des sauts de ligne.
 * Exemple de sortie :
 *
 * FILL,0,0,3 FILL,5,0,1 FILL,3,3,2 ERASE,4,3
 */
public class ArtOptimal {

	private static final String INPUT_DIRECTORY = "primers_art-optimal";

	private static final char PAINT = '#';
	private static final String FILL = "FILL";
	private static final String ERASE = "ERASE";
	private static final String COMMA = ",";

	public static String getInputFilePath(final int inputNumber) {
		return "resources" + File.separator + INPUT_DIRECTORY + File.separator + "input" + inputNumber + ".txt";
	}

	public static Scanner getScanner(final int inputNumber) throws FileNotFoundException {
		return new Scanner(new File(getInputFilePath(inputNumber)));
	}

	public static boolean[][] getPixelMap(final Scanner sc) {
		String line;
		line = sc.nextLine();
		final String[] numbers = line.split(",");
		final int wide = Integer.parseInt(numbers[0]);
		final int height = Integer.parseInt(numbers[1]);
		final boolean[][] pixelMap = new boolean[height][wide];
		int i = 0;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			int j = 0;
			for (final char c : line.toCharArray()) {
				if (PAINT == c) {
					pixelMap[i][j] = true;
				}
				j++;
			}
			i++;
		}
		return pixelMap;
	}

	public static List<String> getInstructions(final boolean[][] pixelMap) {
		final List<String> instructions = new ArrayList<>();

		final int wide = pixelMap[0].length;
		final int height = pixelMap.length;

		boolean[][] visitedMap = new boolean[height][wide];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < wide; j++) {
				// 0) ignore already painted pixels
				if (visitedMap[i][j]) {
					continue;
				}
				// 1) Look for painted pixel
				if (pixelMap[i][j]) {
					// 2) evaluate size of square
					final int maxSize = getMaxSize(pixelMap, visitedMap, i, j);
					visitedMap = addFillInstruction(instructions, i, j, maxSize, pixelMap);
					// TODO use maxSize to erase paintedPixel in current square if needed.
				}
			}
		}
		return instructions;
	}

	private static int getMaxSize(final boolean[][] pixelMap,
	                              final boolean[][] visitedMap,
	                              final int i,
	                              final int j) {
		final int wide = pixelMap[0].length;
		final int height = pixelMap.length;
		final int maxPossibleSize =  Math.min(wide-j, height-i);
		if(maxPossibleSize == 1) {
			return 1;
		}
		return getMaxSizeRecursive(pixelMap, visitedMap, i, j, maxPossibleSize);
	}

	private static int getMaxSizeRecursive(final boolean[][] pixelMap,
										   final boolean[][] visitedMap,
										   final int i,
										   final int j,
										   final int maxPossibleSize) {
		final double ratio = getRatio(pixelMap, visitedMap, i, j, maxPossibleSize);
		if(ratio > 0.8d) {
			return maxPossibleSize;
		}
		return getMaxSizeRecursive(pixelMap, visitedMap, i, j, maxPossibleSize-1);
	}

	private static double getRatio(final boolean[][] pixelMap,
								   final boolean[][] visitedMap,
								   final int i,
								   final int j,
								   final int maxPossibleSize) {
		countPixelToPaint(pixelMap, visitedMap, i, j, maxPossibleSize);
		return 0d;
	}

	private static int countPixelToPaint(final boolean[][] pixelMap,
										 final boolean[][] visitedMap,
										 final int i,
										 final int j,
										 final int maxPossibleSize) {
		return 0;
	}

	private static boolean[][] addFillInstruction(final List<String> instructions,
	                                              final int i,
	                                              final int j,
	                                              final int size,
	                                              final boolean[][] visitedMap) {
		instructions.add(FILL + COMMA + i + COMMA + j + COMMA + size);
		visitedMap[i][j] = true;
		if (size == 1) {
			return visitedMap;
		}
		for (int i2 = i; i2 < size; i2++) {
			for (int j2 = j; j2 < size; j2++) {
				visitedMap[i + i2][j + j2] = true;
			}
		}
		return visitedMap;
	}

	public static void printInstruction(final int inputNumber) throws FileNotFoundException {
		for (final String i : getInstructions(getPixelMap(getScanner(inputNumber)))) {
			System.out.println(i);
		}
	}

	/**
	 * Fills with value 'true'
	 * 
	 * @param aMap
	 * @return
	 */
	public static boolean[][] fillMap(final boolean[][] aMap) {
		return fillMap(aMap,
		               0,
		               0,
		               aMap.length,
		               aMap[0].length);
	}

	public static boolean[][] fillMap(final boolean[][] aMap,
	                                  final int i0,
	                                  final int j0,
	                                  final int iMax,
	                                  final int jMax) {
		for (int i = i0; i < iMax; i++) {
			for (int j = j0; j < jMax; j++) {
				aMap[i][j] = true;
			}
		}
		return aMap;
	}

}
