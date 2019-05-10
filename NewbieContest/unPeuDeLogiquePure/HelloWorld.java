import java.util.*;

/**
Description :
Il était une fois un petit cercle d'amis composé de 5 passionnés de sécurité informatique.
 Ils avaient pour nom Lebenfrau, CottCott, qq_, S0C13V3R et EiGhT.
Ils avaient chacun leur propre domaine de prédilection,
des langages de programmation préférés différents,
tournaient sous des OS distincts et avaient
tous un gros défaut caractéristique.

Un jour ils décidèrent de se défier tous les cinq sur les épreuves de NC, et ce pendant une semaine. Après 7 jours de combats acharnés par épreuves interposées, le classement rendit son verdict. Malheureusement celui-ci resta secret et les seuls indices dont nous disposons ont été répertoriés ci-dessous. Examinez les soigneusement et peut-être arriverez-vous à répondre aux questions qui suivent.

1) qq_ est amateur de crackmes.
2) Celui qui code en Perl est borné.
3) Le feignant cotoie le fou du Java au classement final.
4) Le développeur PHP tourne sous Mandriva.
5) EiGhT est un ivrogne.
6) Le 3ème du classement final tourne sous Windows 95.
7) Le fondu de programmation est un fanatique d'Ubuntu.
8) Le codeur Delphi a terminé juste devant ou juste derrière le susceptible.
9) Lebenfrau ne jure que par OpenBSD.
10) L'amateur de stéganographie a fini juste devant le fondu de programmation.
11) Il paraîtrait que c'est CottCott qui code en C.
12) S0C13V3R a fini dernier du classement.
13) L'amateur de cryptographie aime le Java.
14) Le boss du Delphi cotoie l'utilisateur de Windows XP au classement final.
15) S0C13V3R termine juste derrière l'amateur de hacking.

Quel fut le classement final, de la 1ère à la 4ème place ?
Quel est le défaut de S0C13V3R ?
Qui est vicieux ?
Et enfin, quel est l'idiot qui tourne encore sous Windows 95 ?
Concaténez avec des tirets les 7 réponses en minuscules, et vous serez récompensés d'un énorme tas de points.

P.S. : toute ressemblance avec des personnes existantes ne serait que purement fortuite.
**/

/**
* Algorithm looking randomly for the solution. Can be very very long.
*/
public final class HelloWorld
{

	public static void main(String[] args)
	{
		System.out.println("Hello World");
		HelloWorld h = new HelloWorld();
		h.start();
	}

	public void start()
	{
		List<Rule> rules = new ArrayList<>();
		List<PositionRule> positionRules = new ArrayList<>();

		// 1) qq_ est amateur de crackmes.
		rules.add(new CharactLink(Name.qq_, Domaine.crackmes));
		// 2)  Celui qui code en Perl est borné.
		rules.add(new CharactLink(Language.Perl, Defaut.borne));
		// 3)  Le feignant cotoie le fou du Java au classement final.
		positionRules.add(new PositionRule(//
			Defaut.feignant, //
			Language.Java,
			(p1, p2) -> {
				return (Math.abs(p1.value - p2.value) == 1);
			}));
		// 4) Le développeur PHP tourne sous Mandriva.
		rules.add(new CharactLink(Language.PHP, Os.Mandriva));
		// 5) EiGhT est un ivrogne.
		rules.add(new CharactLink(Name.EiGhT, Defaut.ivrogne));
		// 6)  Le 3ème du classement final tourne sous Windows 95.
		rules.add(new CharactLink(Position.troisieme, Os.Windows95));
		// 7) Le fondu de programmation est un fanatique d'Ubuntu.
		rules.add(new CharactLink(Domaine.programmation, Os.Ubuntu));
		// 8) Le codeur Delphi a terminé juste devant ou juste derrière le susceptible.
		positionRules.add(new PositionRule(//
			Language.Delphi, //
			Defaut.susceptible, //
			(p1, p2) -> {
				return (Math.abs(p1.value - p2.value) == 1);
			}));
		// 9) Lebenfrau ne jure que par OpenBSD.
		rules.add(new CharactLink(Name.Lebenfrau, Os.OpenBSD));
		// 10)  L'amateur de stéganographie a fini juste devant le fondu de programmation.
		positionRules.add(new PositionRule(//
			Domaine.steganographie, //
			Domaine.programmation, //
			(p1, p2) -> {
				return ((p2.value - p1.value) == 1);
			}));
		// 11) Il paraîtrait que c'est CottCott qui code en C.
		rules.add(new CharactLink(Name.CottCott, Language.C));
		// 12) S0C13V3R a fini dernier du classement.
		rules.add(new CharactLink(Name.S0C13V3R, Position.dernier));
		// 13) L'amateur de cryptographie aime le Java.
		rules.add(new CharactLink(Domaine.cryptographie, Language.Java));
		// 14) Le boss du Delphi cotoie l'utilisateur de Windows XP au classement final.
		positionRules.add(new PositionRule(//
			Language.Delphi, //
			Os.WindowsXP,
			(p1, p2) -> {
				return (Math.abs(p1.value - p2.value) == 1);
			}));
		// 15) S0C13V3R termine juste derrière l'amateur de hacking.
		rules.add(new CharactLink(Domaine.hacking, Position.quatrieme));

		//this.randomTries3(rules, positionRules);
		this.randomTries2(rules, positionRules);
	}

	private int accept(List<Rule> rules, List<PositionRule> rules2, Person[] persons)
	{
		int count = 0;
		for (Rule r : rules)
		{
			count++;
			for (Person p : persons)
			{
				if (!r.accept(p))
				{
					return count;
				}
			}
		}

		for (PositionRule r : rules2)
		{
			count++;
			for (Person p1 : persons)
			{
				if (p1.hasCharacteristic(r.c1))
				{
					for (Person p2 : persons)
					{
						if (p2.hasCharacteristic(r.c2))
						{
							if (p1 == p2) // not possible
								return count;
							if (!r.acceptPositions(p1, p2))
								return count;
							break;
						}
					}
					break;
				}
			}
		}

		return -1;
	}

	private interface Rule
	{
		public boolean accept(Person p);
	}

	private class Person
	{
		public Name name;
		public Domaine domaine;
		public Language language;
		public Os os;
		public Defaut defaut;
		public Position position;

		public Person(Name name)
		{
			this.name = name;
		}

		public boolean hasCharacteristic(Characteristic aCharact)
		{
			if (aCharact instanceof Name)
				return aCharact == this.name;
			if (aCharact instanceof Domaine)
				return aCharact == this.domaine;
			if (aCharact instanceof Language)
				return aCharact == this.language;
			if (aCharact instanceof Defaut)
				return aCharact == this.defaut;
			if (aCharact instanceof Os)
				return aCharact == this.os;
			if (aCharact instanceof Position)
				return aCharact == this.position;
			return false;
		}

		public void setCharacteristic(Characteristic aCharact)
		{
			if (aCharact instanceof Name)
				this.name = (Name) aCharact;
			if (aCharact instanceof Domaine)
				this.domaine = (Domaine) aCharact;
			if (aCharact instanceof Language)
				this.language = (Language) aCharact;
			if (aCharact instanceof Defaut)
				this.defaut = (Defaut) aCharact;
			if (aCharact instanceof Os)
				this.os = (Os) aCharact;
			if (aCharact instanceof Position)
				this.position = (Position) aCharact;
		}
	}

	private class CharactLink implements Rule
	{
		public final Characteristic c1;
		public final Characteristic c2;

		public CharactLink(Characteristic c1, Characteristic c2)
		{
			this.c1 = c1;
			this.c2 = c2;
		}

		@Override
		public boolean accept(Person p)
		{
			boolean hasC1 = p.hasCharacteristic(this.c1);
			boolean hasC2 = p.hasCharacteristic(this.c2);
			if (hasC1 && hasC2)
				return true;
			if (hasC1)
				return false;
			if (hasC2)
				return false;
			return true;
		}
	}

	private class PositionRule extends CharactLink
	{
		private final PositionDiff diff;

		public PositionRule(Characteristic c1, Characteristic c2, PositionDiff diff)
		{
			super(c1, c2);
			this.diff = diff;
		}

		public boolean acceptPositions(Person p1, Person p2)
		{
			return this.diff.accept(p1.position, p2.position);
		}
	}

	private interface PositionDiff
	{
		public boolean accept(Position p1, Position p2);
	}

	public void print(Person[] persons)
	{
		System.out.println("\nShow the 5 persons :");
		for (Person p : persons)
		{
			System.out.println("--" + p.name + "-- :");
			System.out.println("\t " + p.domaine + "");
			System.out.println("\t " + p.language + "");
			System.out.println("\t " + p.os + "");
			System.out.println("\t " + p.defaut + "");
			System.out.println("\t " + p.position + "");
			System.out.println("--------------------");
		}
	}

	private interface Characteristic
	{
	}

	private enum Name implements Characteristic {
		Lebenfrau, CottCott, qq_, S0C13V3R, EiGhT;
	}

	private enum Language implements Characteristic {
		Perl, Java, Delphi, C, PHP;
	}

	private enum Domaine implements Characteristic {
		crackmes, programmation, steganographie, cryptographie, hacking;
	}

	private enum Os implements Characteristic {
		Mandriva, Windows95, Ubuntu, OpenBSD, WindowsXP;
	}

	private enum Defaut implements Characteristic {
		borne, feignant, ivrogne, susceptible, vicieux;
	}

	private enum Position implements Characteristic {
		premier(1), deuxieme(2), troisieme(3), quatrieme(4), dernier(5);

		public final int value;

		Position(int value)
		{
			this.value = value;
		}
	}

	Characteristic[] names = new Characteristic[] { Name.Lebenfrau, Name.CottCott, Name.qq_,
			Name.S0C13V3R, Name.EiGhT };
	Characteristic[] languages = new Characteristic[] { Language.Perl, Language.Java,
			Language.Delphi, Language.C, Language.PHP };
	Characteristic[] domaines = new Characteristic[] { Domaine.crackmes, Domaine.programmation,
			Domaine.steganographie, Domaine.cryptographie, Domaine.hacking };
	Characteristic[] oss = new Characteristic[] { Os.Mandriva, Os.Windows95, Os.Ubuntu, Os.OpenBSD,
			Os.WindowsXP };
	Characteristic[] defauts = new Characteristic[] { Defaut.borne, Defaut.feignant,
			Defaut.ivrogne, Defaut.susceptible, Defaut.vicieux };
	Characteristic[] positions = new Characteristic[] { Position.premier, Position.deuxieme,
			Position.troisieme, Position.quatrieme, Position.dernier };

	private void randomTries2(List<Rule> rules, List<PositionRule> rules2)
	{
		final Person[] persons = new Person[] { new Person(Name.Lebenfrau),
				new Person(Name.CottCott), new Person(Name.qq_), new Person(Name.S0C13V3R),
				new Person(Name.EiGhT), };

		int count = 0;
		int bestScore = 0;
		List<Integer> used1 = new ArrayList<>();
		List<Integer> used2 = new ArrayList<>();
		List<Integer> used3 = new ArrayList<>();
		List<Integer> used4 = new ArrayList<>();
		List<Integer> used5 = new ArrayList<>();
		while (true)
		{
			count++;
			used1.clear();
			used2.clear();
			used3.clear();
			used4.clear();
			used5.clear();
			for (int i = 0; i < 5; i++)
			{
				int i1 = getRandomWithExclusion(used1);
				used1.add(i1);
				Collections.sort(used1);
				int i2 = getRandomWithExclusion(used2);
				used2.add(i2);
				Collections.sort(used2);
				int i3 = getRandomWithExclusion(used3);
				used3.add(i3);
				Collections.sort(used3);
				int i4 = getRandomWithExclusion(used4);
				used4.add(i4);
				Collections.sort(used4);
				int i5 = getRandomWithExclusion(used5);
				used5.add(i5);
				Collections.sort(used5);
				persons[i].setCharacteristic(this.languages[i1]);
				persons[i].setCharacteristic(this.domaines[i2]);
				persons[i].setCharacteristic(this.oss[i3]);
				persons[i].setCharacteristic(this.defauts[i4]);
				persons[i].setCharacteristic(this.positions[i5]);
			}
			int score = this.accept(rules, rules2, persons);
			if (score == -1)
			{
				System.out.println("\n\nSolution found :");
				this.print(persons);
				return;
			}
			else
			{
				if (score >= 13)
				{
					System.out.println("Score >=13 : ");
					this.print(persons);
				}
				if (score > bestScore)
					bestScore = score;
				if (count % 10000000 == 0)
				{
					System.out.println("Counter = " + count);
					System.out.println("bestScore (of last 10000000 tries)  = " + bestScore);
					bestScore = 0;
				}
			}
		}
	}

	public static int getRandomWithExclusion(List<Integer> excludes)
	{
		Random rdm = new Random();
		int random = rdm.nextInt((4) + (1 - excludes.size()));
		for (int ex : excludes)
		{
			if (random < ex)
			{
				break;
			}
			random++;
		}
		return random;
	}

	/**
	 * Has duplicates digits in the 5 first (starting from right) digits.
	 */
	public static boolean hasDuplicates(int n)
	{
		String t = "0000000" + String.valueOf(n);
		if (t.length() > 5)
			t = t.substring(t.length() - 5);
		for (int i = 0; i < t.length() - 1; i++)
		{
			for (int j = i + 1; j < t.length(); j++)
			{
				if (t.charAt(i) == t.charAt(j))
				{
					return true;
				}
			}
		}
		return false;
	}

	private static int[] getValues(int i1)
	{
		int[] values = new int[5];
		int u = i1 % 10;
		if (u > 4)
			return null;
		int d = i1 / 10 % 10;
		if (d > 4)
			return null;
		int c = i1 / 100 % 10;
		if (c > 4)
			return null;
		int m1 = i1 / 1000 % 10;
		if (m1 > 4)
			return null;
		int m2 = i1 / 10000 % 10;
		if (m2 > 4)
			return null;
		values[0] = u;
		values[1] = d;
		values[2] = c;
		values[3] = m1;
		values[4] = m2;
		return values;

	}
	private void randomTries3(List<Rule> rules, List<PositionRule> rules2)
	{
		final Person[] persons = new Person[] { new Person(Name.Lebenfrau),
				new Person(Name.CottCott), new Person(Name.qq_), new Person(Name.S0C13V3R),
				new Person(Name.EiGhT), };

		double count = 0;
		int bestScore = 0;
		for (int i1 = 1234; i1 < 150000; i1++)
		{
			if (hasDuplicates(i1))
				continue;
			int[] v1 = this.getValues(i1);
			if (v1 == null)
				continue;
			for (int i2 = 1234; i2 < 150000; i2++)
			{
				if (hasDuplicates(i2))
					continue;
				int[] v2 = this.getValues(i2);
				if (v2 == null)
					continue;
				for (int i3 = 1234; i3 < 150000; i3++)
				{
					if (hasDuplicates(i3))
						continue;
					int[] v3 = this.getValues(i3);
					if (v3 == null)
						continue;
					for (int i4 = 1234; i4 < 150000; i4++)
					{
						if (hasDuplicates(i4))
							continue;
						int[] v4 = this.getValues(i4);
						if (v4 == null)
							continue;
						for (int i5 = 1234; i5 < 150000; i5++)
						{
							if (hasDuplicates(i5))
								continue;
							int[] v5 = this.getValues(i5);
							if (v5 == null)
								continue;

							// test start :
							count++;
							for (int i = 0; i < 5; i++)
							{
								persons[i].setCharacteristic(this.languages[v1[i]]);
								persons[i].setCharacteristic(this.domaines[v2[i]]);
								persons[i].setCharacteristic(this.oss[v3[i]]);
								persons[i].setCharacteristic(this.defauts[v4[i]]);
								persons[i].setCharacteristic(this.positions[v5[i]]);
							}
							int score = this.accept(rules, rules2, persons);
							if (score == -1)
							{
								System.out.println("\n\nSolution found :");
								this.print(persons);
								return;
							}
							else
							{
								if (score > bestScore)
									bestScore = score;
								if (count % 10000000 == 0)
								{
									System.out.println("Counter = " + count);
									System.out.println("bestScore " + bestScore);
								}
							}
						}
					}
				}
			}
		}
		System.out.println("Counter = " + count);
		System.out.println("Solution not found by randomTries3.");
	}

}
