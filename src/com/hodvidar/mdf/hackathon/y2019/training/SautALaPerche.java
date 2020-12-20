package com.hodvidar.mdf.hackathon.y2019.training;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * 		https://www.isograd.com/FR/solutionconcours.php?contest_id=36
 * Done in 36min55sec...
 * By Hodvidar
 */
public final class SautALaPerche {
	
	private static final boolean ONE_TEST = true;
	private static final int ONE_TEST_NUMBER = 2;
	private static final int NUMBER_OF_TESTS = 8;
	private static final String INPUT_DIRECTORY = "saut_a_la_perche_input";

	public static void main(String[] args) throws Exception 
	{
		SautALaPerche r = new SautALaPerche();
		int i;
		int max;
		if(ONE_TEST)
		{
			i = ONE_TEST_NUMBER;
			max = ONE_TEST_NUMBER;
		}
		else
		{
			i = 1;
			max = NUMBER_OF_TESTS;
		}
		for(; i <= max; i++)
		{
			System.err.println("\n--- TEST n°"+i+" --");
			String result = r.test("resources\\"+INPUT_DIRECTORY+"\\input"+i+".txt");
			// --- CHECKING ---
			File file2 = new File("resources\\"+INPUT_DIRECTORY+"\\output"+i+".txt");
			// Scanner sc = new Scanner(System.in);
			Scanner sc2 = new Scanner(file2);
			String line2 = sc2.nextLine();
			System.err.println("Solution is: \n"+line2);
			if(result.equals(line2))
				System.err.println("SUCCESS!");
			else
				System.err.println("FAILURE! found: "+result);
			sc2.close();
		}
	}
	
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;
	
	public static void printIfVerbose(String s)
	{
		if (VERBOSE)
			System.err.println(s);
	}

	private String test(String inputFile) throws Exception 
	{
		File file = new File(inputFile);
		// --- INPUT ---
		
		String  line = "";
		// Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");
		int i = 0;
		Map<String, Perf> athletes = new HashMap<>();
		while(sc.hasNextLine()) {
			i++;
			line = sc.nextLine();
			printIfVerbose("i="+i+" line:"+line);
			if(i == 1)
			{
				continue;
			}
			
			if(line == null || line.isEmpty())
				continue;
			
			String[] line2 = line.split("\\s+");
			String athleteName = line2[0];
			Double jumpH = Double.parseDouble(line2[1]);
			boolean success = line2[2].equals("S");
			
			Perf p = athletes.get(athleteName);
			if(p == null)
				p = new Perf();
			p.addTry(jumpH, success);
			athletes.put(athleteName, p);
		}
		
		double maxPerf = 0;
		int maxTries = 0;
		String maxA = "";
		for(String a : athletes.keySet())
		{
			Perf p = athletes.get(a);
			if(p.maxPerf > maxPerf)
			{
				maxPerf = p.maxPerf;
				maxTries = p.tries.get(maxPerf);
				maxA = a;
				continue;
			}
			
			if(p.maxPerf == maxPerf)
			{
				if(p.tries.get(maxPerf) < maxTries)
				{
					maxTries = p.tries.get(maxPerf);
					maxA = a;
				}
				else if (p.tries.get(maxPerf) == maxTries)
				{
					maxA = "KO";
				}
			}
		}
		
		System.out.println(maxA);
		sc.close();
		return maxA;
	}
	
	class Perf{
		public double maxPerf = 0;
		public Map<Double, Integer> tries = new HashMap<>();
		
		public void addTry(Double jumpH, boolean success)
		{
			Integer nbTry = tries.get(jumpH);
			if(nbTry == null)
				nbTry = 1;
			else
				nbTry++;
			
			if(success)
				maxPerf = jumpH;
			
			tries.put(jumpH, nbTry);
		}
	}
}

/*
Énoncé

Avec les JO de 2022 à Paris, le marché des logiciels de classement s'annonce prometteur pour les éditeurs français. Vous travaillez donc sur un pilote : le saut à la perche. Le classement d'un concours de saut à la perche demande un peu de travail car les règles sont les suivantes :- Chaque sauteur peut choisir la hauteur à laquelle il débute le concours.
- S'il passe une barre de hauteur X, il peut continuer le concours en tentant ensuite une barre à une hauteur strictement supérieure à X.
- S'il échoue à une barre de hauteur X, il peut soit retenter cette hauteur soit continuer le concours à n'importe quelle hauteur strictement supérieure à X.
Les concurrents sont classés selon la hauteur de la dernière barre franchie. En cas d'égalité, ils sont départagés en premier lieu par le nombre d'essais qu'ils ont réalisé à la hauteur de la dernière barre qu'ils ont franchie, en second lieu par le nombre total d'échecs durant le concours. Si sur ces 3 critères, on ne peut pas départager les vainqueurs potentiels, alors il n'y a pas de vainqueur.

Format des données

Entrée

Ligne 1 : un entier N compris entre 1 et 1000 représentant le nombre d'essais réalisés pendant le concours.
Lignes 2 à N+1 : une chaine de 6 caractères en minuscules , un nombre décimal compris entre 4 et 10 avec deux chiffres après la virgule et une lettre (S ou E) séparés par des espaces. La chaîne représente le nom de l'athlète, le nombre représente la hauteur de l'essai et la lettre indique le résultat S pour Succès ou E pour Echec. Ces données sont fournies par ordre chronologiques : la hauteur de la barre est globalement croissante.

Sortie

Le nom du vainqueur du concours ou la chaine KO s'il y a égalité parfaite entre plusieurs vainqueurs potentiels.

Exemples
9
aaaaaa 4.25 S
bbbbbb 4.25 E
bbbbbb 4.25 S
bbbbbb 4.30 E
aaaaaa 4.30 E
aaaaaa 4.30 E
aaaaaa 4.30 E
Dans ce cas aaaaaa gagne car il a passé la 4.25 au 1er essai alors qu'il a fallu 2 essais à bbbbbb. Ensuite, les deux concurrent on échoué à 4.30.

7
aaaaaa 4.25 S
bbbbbb 4.25 E
aaaaaa 4.27 E
bbbbbb 4.30 S
aaaaaa 4.30 S
bbbbbb 4.35 E
aaaaaa 4.38 E
Dans ce cas, la réponse est KO. Les deux concurrents ont une meilleure barre à 4m30 qu'ils ont tous les deux réussie au 1er essai à cette hauteur et ils ont chacun 2 échecs en tout sur le concours.
*/
