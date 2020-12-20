package com.hodvidar.mdf.hackathon.y2019.orange;

import java.io.File;
import java.util.Scanner;

/**
 * 		https://www.isograd.com/FR/solutionconcours.php?contest_id=50
 * by Hodvidar 
 */
public final class EmpreinteFichier 
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;
	
	private static final int NUMBER_OF_TEST = 5;
	private static final String INPUT_DIRECTORY = "empreinte_fichier_input";
	
	public static void printIfVerbose(String s)
	{
		if (VERBOSE)
			System.err.println(s);
	}
	
	public static void main(String[] args) throws Exception 
	{
		for(int i = 1; i <= NUMBER_OF_TEST; i++)
		{
			printIfVerbose("\n\n TEST n°"+i);
			String result = test("resources\\"+INPUT_DIRECTORY+"\\input"+i+".txt");
			// --- CHECKING ---
			File file2 = new File("resources\\"+INPUT_DIRECTORY+"\\output"+i+".txt");
			// Scanner sc = new Scanner(System.in);
			Scanner sc2 = new Scanner(file2);
			String line2 = sc2.nextLine();
			printIfVerbose("Solution is: \n"+line2);
			if(result.equals(line2))
				printIfVerbose("SUCCESS!");
			else
				System.err.println("FAILURE!");
			sc2.close();
		}
	}

	private static String test(String inputFile) throws Exception 
	{
		File file = new File(inputFile);
		// --- INPUT ---
		
		String  line = "";
		// Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			/* Lisez les données et effectuez votre traitement */
		}
		printIfVerbose("line="+line);
		
		while(true)
		{
			String lineBefore = line;
			line = line.replaceAll("000", "00");
			printIfVerbose("line'="+line);
			line = line.replaceAll("111", "1");
			printIfVerbose("line''="+line);
			if(line.equals(lineBefore))
				break;
		}

		while(true)
		{
			String lineBefore = line;
			line = line.replaceAll("10", "1");
			printIfVerbose("line'''="+line);
			if(line.equals(lineBefore))
				break;
		}
		
		System.out.println(line);
		sc.close();
		return line;
	}

}

/*
Énoncé

Votre petit frère s'intéresse à l'informatique depuis quelques temps et il vient de découvrir ce qu'est la représentation binaire. Il est ébahi ! Tout ce qu'il voit à travers son écran d'ordinateur est en réalité une succession de 0 et de 1.

Une fois cette notion assimilée, vous décidez de ne pas lui laisser le temps de souffler et vous essayez de lui expliquer comment il peut sécuriser l'envoi de ses fichiers grâce aux empreintes.

Une empreinte de fichier est une représentation binaire réduite du fichier qui, une fois envoyée au destinataire, permet de s'assurer que le fichier n'a pas été altéré durant son transfert.

A partir de la représentation binaire du fichier, il faut effectuer les trois opérations suivantes :
- Opération 1 : Remplacer tous les 000 en 00
- Opération 2 : Remplacer tous les 111 en 1
- Opération 3 : Remplacer tous les 10 en 1
L'opération 3 se fait uniquement lorsque les deux premières opérations ne sont plus possibles. Lorsqu'on a commencé l'opération 3, il n'est plus possible de revenir aux opérations 1 et 2.

L'empreinte est obtenue lorsqu'on ne peut plus appliquer l'opération 3.

L'objectif est de déterminer l'empreinte d'un fichier à partir de sa représentation binaire.

Format des données

Entrée

Ligne 1 : une chaîne de caractères comprenant entre 1 et 100 caractères composée de 0 et de 1 correspondant à la représentation binaire d'un fichier.

Sortie

Une chaîne de caractères correspondant à l'empreinte du fichier.
*/
