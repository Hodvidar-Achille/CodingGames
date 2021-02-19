package com.hodvidar.mdf.hackathon.y2018.h1830;

import java.io.File;
import java.util.Scanner;

public final class FouleALaPlage {
	/**
	 * If 'false' only response and Failure are written
	 **/
	private static final boolean VERBOSE = false;

	private static final int NUMBER_OF_TEST = 6;
	private static final String INPUT_DIRECTORY = "foule-a-la-plage_input";
	private static final String ENTRY = "E";

	public static void printIfVerbose(final String s) {
		if (VERBOSE)
			System.err.println(s);
	}

	public static void main(final String[] args) throws Exception {
		for (int i = 1; i <= NUMBER_OF_TEST; i++) {
			printIfVerbose("\n\nTEST n°" + i);
			final String result = test("resources" + File.separator + INPUT_DIRECTORY + File.separator + "input" + i
			                           + ".txt");
			// --- CHECKING ---
			final File file2 = new File("resources" + File.separator + INPUT_DIRECTORY + "\\output" + i + ".txt");
			// Scanner sc = new Scanner(System.in);
			final Scanner sc2 = new Scanner(file2);
			final String line2 = sc2.nextLine();
			printIfVerbose("Solution is: \n" + line2);
			if (result.equals(line2))
				printIfVerbose("SUCCESS!");
			else
				System.err.println("FAILURE!");
			sc2.close();
		}
	}
	// private static final String EXIT = "S";

	private static String test(final String inputFile) throws Exception {
		String line;
		final File file = new File(inputFile);
		// Scanner sc = new Scanner(System.in);
		final Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");

		int numberOfPassage = 0;
		int numberOfMaster = 0;
		int alarmOnInMinute = 0;
		int previousTimeInMinute = 0;
		int currentTimeInMinute = 0;
		int actualNumberOfPerson = 0;
		int counter = 0;
		while (sc.hasNextLine()) {
			line = sc.nextLine();
			counter++;
			if (counter == 1) {
				numberOfPassage = Integer.parseInt(line);
				printIfVerbose("numberOfPassage=" + numberOfPassage);
				continue;
			}
			if (counter == 2) {
				numberOfMaster = Integer.parseInt(line);
				printIfVerbose("numberOfMaster=" + numberOfMaster);
				continue;
			}
			final String[] line2 = line.split("\\s+");
			final String time = line2[0];
			final String passage = line2[1];

			final String[] time2 = time.split(":");
			final String hourStr = time2[0];
			final String minuteStr = time2[1];
			final int hour = Integer.parseInt(hourStr);
			final int minute = Integer.parseInt(minuteStr);
			// remove leading "0"
			previousTimeInMinute = currentTimeInMinute;
			currentTimeInMinute = (hour * 60) + minute;
			if (counter == 3) {
				// first passage, set actualTimeInMinute
				previousTimeInMinute = currentTimeInMinute;
			}

			final int minuteSinceLastEntry = currentTimeInMinute - previousTimeInMinute;

			if (tooMuchPerson(numberOfMaster, actualNumberOfPerson)) {
				alarmOnInMinute += minuteSinceLastEntry;
			}

			if (ENTRY.equals(passage)) {
				actualNumberOfPerson++;
			} else {
				actualNumberOfPerson--;
			}

			printIfVerbose("Passage n°" + (counter - 2) + ": " + line + " p=" + actualNumberOfPerson + " alarm="
			               + alarmOnInMinute);
		}

		// Add time until 23:00
		previousTimeInMinute = currentTimeInMinute;
		currentTimeInMinute = 23 * 60;
		final int minuteSinceLastEntry = currentTimeInMinute - previousTimeInMinute;
		if (tooMuchPerson(numberOfMaster, actualNumberOfPerson)) {
			alarmOnInMinute += minuteSinceLastEntry;
		}

		printIfVerbose("23:00 : p=" + actualNumberOfPerson + " alarm=" + alarmOnInMinute);

		sc.close();
		System.out.println(alarmOnInMinute);
		return "" + alarmOnInMinute;
	}

	private static boolean tooMuchPerson(final int master, final int person) {
		return (master * 10) < person;
	}
}

/*
 * 
 * Énoncé
 * 
 * Durant l’été, la température grimpe et les vacanciers se ruent naturellement vers les plages.
 * 
 * Afin de diminuer le risque de noyade, les municipalités font appel à des maîtres-nageurs.
 * Théoriquement, chaque maître-nageur peut gérer au maximum 10 personnes à la fois. Cependant, ils
 * se retrouvent parfois débordés et ils ne sont donc plus en mesure de garantir la sécurité des
 * baigneurs. Donc lorsque le nombre de baigneurs est strictement supérieur à dix fois le nombre de
 * maitre-nageurs, une alerte sonore retentit qui incite les baigneurs à redoubler de vigilance.
 * Cette alerte dure aussi longtemps que les maîtres-nageurs sont débordés.
 * 
 * Pour les aider à identifier une « alerte foule », un portique de sécurité placé à l’entrée de la
 * plage scanne toutes les entrées et sorties des baigneurs.
 * 
 * Vous devez déterminer combien de temps par jour l'alerte retentit.
 * 
 * On considère que la journée commence avec zéro baigneur et se termine à 23:00. On vous garantit
 * qu'il y a au maximum une entrée ou une sortie par minute.
 * 
 * Format des données
 * 
 * Entrée
 * 
 * Ligne 1 : un entier N compris entre 1 et 1379 correspondant au nombre d'entrées ou sorties des
 * baigneurs durant une journée. Ligne 2 : un entier M compris entre 1 et 50 correspondant au nombre
 * de maîtres-nageurs. Ligne 3 à N+2 : une chaîne de caractères H et une lettre P séparés par un
 * espace où H correspond à l’heure d’un passage au niveau du portique au format « hh:mm » et P
 * correspond au type de passage, entrée ou sortie (P peut prendre la valeur « E » pour une entrée
 * ou « S » pour une sortie). Ces lignes sont triées par ordre chronologique.
 * 
 * Sortie
 * 
 * Un entier correspondant au temps cumulé durant lequel l'alerte sonore retentit, exprimé en
 * minutes.
 * 
 */