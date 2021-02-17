package com.hodvidar.primers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://primers.xyz/0 ----------------------- NTRÉE
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

	private static final String FILL = "FILL";
	private static final String ERASE = "ERASE";

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
				if (c == '#') {
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
		// TODO
		return instructions;
	}

	public static void printInstruction(final int inputNumber) throws FileNotFoundException {
		for(final String i : getInstructions(getPixelMap(getScanner(inputNumber)))) {
			System.out.println(i);
		}
	}

}
