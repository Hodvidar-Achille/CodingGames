package com.hodvidar.mdf.hackathon.y2019.training;

import java.io.File;
import java.text.NumberFormat;
import java.util.Scanner;

/**
 * https://www.isograd.com/FR/solutionconcours.php?contest_id=36 Done in 24min51sec... By Hodvidar
 */
public final class InteretDebiteurs {

	/**
	 * If 'false' only response and Failure are written
	 **/
	private static final boolean VERBOSE = false;
	private static final boolean ONE_TEST = false;

	private static final int ONE_TEST_NUMBER = 1;
	private static final int NUMBER_OF_TESTS = 5;
	private static final String INPUT_DIRECTORY = "interets-debiteurs_input";

	public static void printIfVerbose(final String s) {
		if (VERBOSE)
			System.err.println(s);
	}

	public static void main(final String[] args) throws Exception {
		final InteretDebiteurs r = new InteretDebiteurs();
		int i;
		final int max;
		if (ONE_TEST) {
			i = ONE_TEST_NUMBER;
			max = ONE_TEST_NUMBER;
		} else {
			i = 1;
			max = NUMBER_OF_TESTS;
		}
		for (; i <= max; i++) {
			System.err.println("\n--- TEST n°" + i + " --");
			final String result = r
			        .test("resources" + File.separator + INPUT_DIRECTORY + File.separator + "input" + i + ".txt");
			// --- CHECKING ---
			final File file2 = new File("resources" + File.separator + INPUT_DIRECTORY + "\\output" + i + ".txt");
			// Scanner sc = new Scanner(System.in);
			final Scanner sc2 = new Scanner(file2);
			final String line2 = sc2.nextLine();
			System.err.println("Solution is: \n" + line2);
			if (result.equals(line2))
				System.err.println("SUCCESS!");
			else
				System.err.println("FAILURE! found: " + result);
			sc2.close();
		}
	}

	private String test(final String inputFile) throws Exception {
		final File file = new File(inputFile);
		// --- INPUT ---

		String line = "";
		// Scanner sc = new Scanner(System.in);
		final Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");
		int i = 0;
		double sold = 0;
		double var = 0;
		int daysNegatif = 0;
		double interests_01 = 0;
		double interests_02 = 0;
		while (sc.hasNextLine()) {
			i++;
			line = sc.nextLine();
			printIfVerbose("i=" + i + " line:" + line);
			/* Lisez les données et effectuez votre traitement */
			if (i == 1) {
				continue;
			}
			if (i == 2) {
				sold = Double.parseDouble(line);
				continue;
			}
			var = Double.parseDouble(line);
			sold += var;

			// 1) Count time since debiteur
			if (sold < 0)
				daysNegatif++;
			else
				daysNegatif = 0;

			printIfVerbose("daysNegatif=" + daysNegatif);

			// Compute interest and adjust sold for both method
			if (daysNegatif == 0)
				continue;

			if (daysNegatif > 2) {
				interests_01 += (-1) * sold * (10 / 100d);
			}

			if (daysNegatif > 3) {
				interests_02 += (-1) * sold * (30 / 100d);
			}

			if (daysNegatif <= 3) {
				interests_02 += (-1) * sold * (20 / 100d);
			}
			printIfVerbose("interests_01=" + interests_01);
			printIfVerbose("interests_02=" + interests_02);
		}

		final double diff = Math.ceil(interests_02 - interests_01);
		printIfVerbose("diff=" + diff);
		final NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(0);
		nf.setGroupingUsed(false);
		final String rounded = nf.format(diff);

		System.out.println(rounded);
		sc.close();
		return rounded;
	}
}

/*
 * Énoncé
 * 
 * Vous avez été promu directeur d'une agence bancaire. Votre première idée est de durcir les
 * intérêts débiteurs pour que les clients fassent plus attention à leur compte.
 * 
 * Dans le passé, si le solde d'un client était négatif pendant plus 2 de jours consécutifs, on
 * commençait à calculer un intérêt débiteur. Cet intérêt était égal à 10% du solde du compte à
 * partir du 3ème jour et tant que le compte demeurait négatif.
 * 
 * Désormais, si un client a un solde négatif, dès le premier jour son intérêt débiteur sera égal à
 * 20% du solde, idem pour les jours suivants, et si le solde reste négatif plus de 3 jours
 * consécutifs, l'intérêt débiteur pour chaque jour à partir du 4ème sera de 30% du solde tant que
 * le solde demeure négatif.
 * 
 * Dans l'ancienne comme dans la nouvelle méthode, on ne prélève pas tout de suite les intérêts
 * débiteurs sur le compte, on envoie une facture au client à la fin de l'année avec le total.
 * 
 * Vous souhaitez calculer la différence entre les montants totaux d'intérêts calculés avec
 * l'ancienne méthode et la nouvelle méthode pour voir à quelle point votre méthode est dissuasive.
 * 
 * Format des données
 * 
 * Entrée
 * 
 * Ligne 1 : un entier N compris entre 1 et 365 correspondant au nombre de jours sur lequel vous
 * étudiez le compte. Ligne 2 : un entier compris entre 0 et 500 correspondant au solde du compte
 * bancaire le premier jour de votre étude. Ligne 3 à N+2 : un entier compris entre -400 et 400
 * correspondant à la variation du solde du compte chaque jour.
 * 
 * Sortie
 * 
 * Un entier correspondant à la différence arrondie à l'entier supérieur entre l'ancienne méthode de
 * calcul des intérêts débiteurs et la nouvelle méthode. Pour éviter les soucis liés aux arrondis,
 * une tolérance d'un euro en plus ou en moins sera acceptée.
 * 
 */
