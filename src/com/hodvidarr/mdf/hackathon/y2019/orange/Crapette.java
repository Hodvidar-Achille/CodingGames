package com.hodvidarr.mdf.hackathon.y2019.orange;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * 		https://www.isograd.com/FR/solutionconcours.php?contest_id=50
 * Solution is faster than Solution (PASSED even if theses test failed).
 * by Hodvidar
 *
 */
public final class Crapette 
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;
	
	private static final int NUMBER_OF_TEST = 12;
	private static final String INPUT_DIRECTORY = "crapette_input";
	
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
	
	private static final String F = "fail";
	
	private static String test(String inputFile) throws FileNotFoundException 
	{
		File file = new File(inputFile);
		// --- INPUT ---
		
		String  line = "";
		// Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");
		int i = 0;
		TopOfPile[] piles = {};
		int numberOfCard = 0;
		TopOfPile startPile = new TopOfPile();
		while(sc.hasNextLine()) {
			line = sc.nextLine();
			/* Lisez les données et effectuez votre traitement */
			printIfVerbose("i="+i+" - line="+line);
			if(i == 0)
			{
				int emptyPiles = Integer.parseInt(line);
				piles = new TopOfPile[emptyPiles];
			}
			else if(i == 1)
			{
				numberOfCard = Integer.parseInt(line);
			}
			else
			{
				String[] cardsStr = line.split("\\s+");
				for(String s : cardsStr)
				{
					int card = Integer.parseInt(s);
					startPile.putCard(card);
				}
			}
			
			i++;
		}
		sc.close();
		
		
		for(i = 0; i < piles.length; i++)
		{
			piles[i] = new TopOfPile();
		}
		
		printIfVerbose("Start of search...");
		MouvementWriter mover = new MouvementWriter(piles, startPile);
		printIfVerbose("mover: "+mover.toString());
		
		if(numberOfCard > 4 && piles.length < 4)
		{
			System.out.println(F);
			return F;
		}
		
		if(numberOfCard == 1)
		{
			mover.moveFromStart(0);
			System.out.println(mover.write());
			return mover.write();
		}
		
		if(numberOfCard == 2)
		{
			if(piles.length < 2)
			{
				System.out.println(F);
				return F;
			}
			mover.moveFromStart(0);
			mover.moveFromStart(1);
			mover.move(0, 1);
			printIfVerbose("mover: "+mover.toString());
			System.out.println(mover.write());
			return mover.write();
		}
		
		if(numberOfCard == 3)
		{
			if(piles.length < 3)
			{
				System.out.println(F);
				return F;
			}
			moveNCardsWithNPiles(mover, numberOfCard);
			printIfVerbose("mover: "+mover.toString());
			System.out.println(mover.write());
			return mover.write();
		}
		
		if(piles.length < 4)
		{
			System.out.println(F);
			return F;
		}
		
		if(numberOfCard <= piles.length)
		{
			moveNCardsWithNPiles(mover, numberOfCard);
			printIfVerbose("mover: "+mover.toString());
			System.out.println(mover.write());
			return mover.write();
		}
		
		// Cases already handles : 1 card, 2 cards, 3 cards
		// for 4+ cards, done for same number of piles
		// Need to handle 5 cards with 4 piles (-1 piles)
		// Need to handle 6 cards with 4, 5 piles (-2, -1)
		// Need to handle 7 cards with 4, 5, 6 piles (-3, -2, -1)
		
		if(numberOfCard == piles.length+1)
		{
			moveNCardsWithMinusOnePiles(mover);
			printIfVerbose("mover: "+mover.toString());
			System.out.println(mover.write());
			return mover.write();
		}
		
		if(numberOfCard == piles.length+2)
		{
			moveNCardsWithMinusTwoPiles(mover);
			printIfVerbose("mover: "+mover.toString());
			System.out.println(mover.write());
			return mover.write();
		}
		
		if(numberOfCard == piles.length+3)
		{
			moveNCardsWithMinusThreePiles(mover);
			printIfVerbose("mover: "+mover.toString());
			System.out.println(mover.write());
			return mover.write();
		}
		
		printIfVerbose("SHOULD NOT BE HERE");
		return F;
	}
	
	/**
	 * For N Cards with N Piles
	 * Move c1 to p1, c2 to p2..., cn to pn.
	 * Then move cn-1 from pn-1 to pn, cn-2 to pn..., c1 to pn;
	 * @param mover
	 */
	private static void moveNCardsWithNPiles(MouvementWriter mover, int numberOfCards)
	{
		int max = numberOfCards-1;
		for(int i = 0; i < numberOfCards; i++)
		{
			mover.moveFromStart(i);
		}
		for(int i = max-1; i >=0; i--)
		{
			mover.move(i, max);
		}
	}
	
	private static void moveNCardsWithMinusOnePiles(MouvementWriter mover)
	{
		// take out cards
		for(int i = 0; i < mover.piles.length; i++)
		{
			mover.moveFromStart(i);
		}
		// Pile up smallest with next smallest
		mover.move(0, 1);
		// Take out highest
		mover.moveFromStart(0);
		// Pile up under the highest top to bottom
		for(int i = mover.piles.length-1; i >=2; i--)
		{
			mover.move(i, 0);
		}
		// Free 2nd smallest
		mover.move(1, 2);
		// Finish piling up
		mover.move(1, 0);
		mover.move(2, 0);
	}
	
	private static void moveNCardsWithMinusTwoPiles(MouvementWriter mover)
	{
		// take out cards
		for(int i = 0; i < mover.piles.length; i++)
		{
			mover.moveFromStart(i);
		}
		// 2nd smallest on 3rd smallest
		mover.move(1, 2);
		// 1st smallest on 2nd smallest
		mover.move(0, 2);
		// Take out highest-1
		mover.moveFromStart(0);
		// Take out highest
		mover.moveFromStart(1);
		// Highest-1 on Highest
		mover.move(0, 1);
		// Pile up under the highest top to bottom
		for(int i = mover.piles.length-1; i >=3; i--)
		{
			mover.move(i, 1);
		}
		// Free 2nd smallest
		mover.move(2, 0);
		// Free 3nd smallest
		mover.move(2, 3);
		// Finish piling up
		mover.move(2, 1);
		mover.move(3, 1);
		mover.move(0, 1);
	}
	
	/**
	 * Hardest case here, 7 cards 4 piles (0 to 3)
	 * @param mover
	 */
	private static void moveNCardsWithMinusThreePiles(MouvementWriter mover)
	{
		// take out cards
		for(int i = 0; i < mover.piles.length; i++)
		{
			mover.moveFromStart(i);
		}
		// 3rd smallest on 4th smallest
		mover.move(2, 3);
		// 2nd smallest on 3rd smallest
		mover.move(1, 3);
		// 1st smallest on 2nd smallest
		mover.move(0, 3);
		// Take out last cards
		for(int i = 0; i < mover.piles.length-1; i++)
		{
			mover.moveFromStart(i);
		}
		// Highest-1 on Highest
		mover.move(1, 2);
		// cn-2 on cn-1
		mover.move(0, 2);
		
		// Free c2 moving c1
		mover.move(3, 0);
		// Free c3 moving c2
		mover.move(3, 1);
		// Make space, c1 on c2
		mover.move(0, 1);
		// 3, 2 and 1 are taken, 0 is free
		// Free c4 moving c3
		mover.move(3, 0);
		// Piling up with one last freeing to do
		// c4 on c5
		mover.move(3, 2);
		// c3
		mover.move(0, 2);
		// free c2
		mover.move(1, 0);
		// c2
		mover.move(1, 2);
		// c1
		mover.move(0, 2);
	}
	
	/**
	 * Will keep track of the card movement and give it back.
	 * @author a.genet
	 */
	private static final class MouvementWriter
	{
		public final TopOfPile[] piles;
		public final TopOfPile startPile;
		
		private String movements = "";
		
		public MouvementWriter(TopOfPile[] piles, TopOfPile startPile)
		{
			this.piles = piles;
			this.startPile = startPile;
		}
		
		/**
		 * Give back the list of movements.
		 * @return
		 */
		public String write()
		{
			return movements;
		}
		
		public void move(int indexFrom, int indexTo)
		{
			int cardToMove = piles[indexFrom].takeCard();
			piles[indexTo].putCard(cardToMove);
			if(!"".equals(movements))
				movements+=";";
			movements += cardToMove + " "+ (indexTo+1);
		}
		
		public void moveFromStart(int indexTo)
		{
			int cardToMove = startPile.takeCard();
			piles[indexTo].putCard(cardToMove);
			if(!"".equals(movements))
				movements+=";";
			movements += cardToMove + " "+ (indexTo+1);
		}
		
		@Override
		public String toString()
		{
			String s = "";
			if(this.startPile == null)
				s += "@null";
			else
				s += "(0):"+this.startPile.toString();
			
			if(this.piles == null)
				s += "@null";
			else
			{
				for(int i = 0; i<piles.length; i++)
				{
					TopOfPile p = piles[i];
					s += " - ("+(i+1)+"):";
					if(p == null)
						s += "@null";
					else
						s += p.toString();
				}
			}
			return s;
		}
	}
		
	public static final class TopOfPile
	{
		private int card = 0;
		private TopOfPile next = null;
		
		public TopOfPile()
		{
			
		}
		
		public int seeCard()
		{
			return this.card;
		}
		
		public void putCard(int aCard)
		{
			if(card != 0 && card != aCard+1)
				throw new RuntimeException("Only a card with the value-1 can be put on this pile.");
			
			if(this.card != 0)
			{
				if(this.next == null)
				{
					this.next = new TopOfPile();
				}
				this.next.putCard(card);
			}
			this.card = aCard;
		}
		
		public int takeCard()
		{
			if(card == 0)
				throw new RuntimeException("No card here.");
			
			int cardToReturn = this.card;
			if(this.next == null)
			{
				this.card = 0;
			}
			else
			{
				this.card = this.next.takeCard();
				if(this.next.seeCard() == 0)
					this.next = null;
			}
			
			return cardToReturn;
		}
		
		@Override
		public String toString()
		{
			if(this.next == null)
				return ""+card;
			return card+">"+this.next.toString();
		}
		
	}

}
/*
Objectif

La crapette est un jeu de cartes se jouant à deux dans lequel les joueurs s'affrontent pour 
effectuer une sorte de réussite avant que l'autre n'y parvienne.

Dans ce jeu, 8 cases sont utilisées pour y placer temporairement des piles 
de cartes en séries décroissantes de couleur alternante. Pour réussir, les bons
 joueurs réunissent ces piles dans le but de maximiser le nombre de cases vides.

Votre but est d'écrire un programme permettant de déplacer une pile de cartes consécutives 
ordonnées de façon décroissante depuis une case de départ vers autre une case en respectant 
les règles suivantes :- On ne peut déplacer qu'une carte à la fois.
- Seule la carte du dessus d'une pile peut être déplacée.
- Une carte 2 doit être posée sur un 3, un 3 sur un 4, une dame sur un roi, etc. Il n'y a pas d'as.
- N'importe quelle carte peut-être posée sur une case vide.
Dans cette version simplifiée du jeu, on ne tient pas compte des couleurs des cartes.

Données

Entrée

Ligne 1 : un entier compris entre 1 et 7 (inclus) représentant le nombre de piles vides
Ligne 2 : un entier N compris entre 1 et 7 représentant le nombre de cartes sur la pile de départ
Ligne 3 : N entiers compris entre 2 et 13 séparés par des espaces représentant les cartes 
de la pile de départ (11, 12 et 13 représentent valet, dame et roi), du bas vers le haut. 
Les cartes sont ordonnées de façon décroissante et consécutives. La carte la plus petite est sur le dessus.

Sortie

Une liste de mouvements permettant de déplacer la pile de départ sur une autre pile si c'est 
possible, ou le mot fail sinon. Les mouvements sont séparés par des points-virgules ; chaque 
mouvement consiste en un entier compris entre 2 et 13 inclus décrivant la valeur de la carte à 
déplacer, puis un espace, puis un entier décrivant le numéro de la pile où placer la carte 
(la pile de départ est numérotée 0, les piles vides de 1 jusqu'à N).

À l'issue de ces commandes, la pile de départ doit avoir été reconstituée à l'identique 
sur n'importe laquelle des autres piles. Toutes les piles restantes doivent êtres vides.

Exemple

Entrée

3
3
10 9 8

Sortie

8 1;9 2;8 2;10 1;8 0;9 1;8 1
*/