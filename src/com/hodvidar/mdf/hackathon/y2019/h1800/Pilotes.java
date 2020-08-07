package com.hodvidar.mdf.hackathon.y2019.h1800;

// ---- Do not Copy upper part ----
import java.io.File;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Scanner;

/**
 *  	2019.10.17 18:00 session n°5
 *   Not finish in time...
 * @author Hodvidar
 */
public final class Pilotes
{
	// Name of class to put back : IsoContest
	public static void main(String[] args) throws Exception
	{
		Pilotes r = new Pilotes(); // <-- IsoContest
		r.doTests();
	}

	private static final boolean TESTING = true; // <-- false

	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false; // <-- false

	private static final boolean ONE_TEST = false;
	private static final int ONE_TEST_NUMBER = 3;
	private static final int NUMBER_OF_TESTS = 4; // TO CHANGE
	private static final String INPUT_DIRECTORY = "test1"; // TO CHANGE

	public void doTests() throws Exception
	{
		if (!TESTING)
		{
			test(null);
			return;
		}
		int i;
		int max;
		if (ONE_TEST)
		{
			i = ONE_TEST_NUMBER;
			max = ONE_TEST_NUMBER;
		} else
		{
			i = 1;
			max = NUMBER_OF_TESTS;
		}
		d(" --- TEST class" + this.getClass().getSimpleName() + " ---");
		for (; i <= max; i++)
		{
			System.err.println("\n--- TEST n°" + i + " --");
			String result = test("resources\\" + INPUT_DIRECTORY + "\\input" + i + ".txt");
			// --- CHECKING ---
			File file2 = new File("resources\\" + INPUT_DIRECTORY + "\\output" + i + ".txt");
			// Scanner sc = new Scanner(System.in);
			Scanner sc2 = new Scanner(file2);
			String line2 = sc2.nextLine();
			d("Solution is: \n" + line2);
			if (result.equals(line2))
				d("SUCCESS!");
			else
				d("FAILURE! found: " + result);
			sc2.close();
		}
	}

	public static void d(String s)
	{
		System.err.println(s);
	}

	public static void debug(String s)
	{
		if (VERBOSE)
			System.err.println(s);
	}

	private String test(String inputFile) throws Exception
	{
		String line = "";
		Scanner sc;
		if (TESTING)
		{
			File file = new File(inputFile);
			sc = new Scanner(file);
		} else
		{
			sc = new Scanner(System.in);
		}
		// ----------------------------------------------------
		// --------------------- START ------------------------
		// ----------------------------------------------------
		debug("Reading inputs...");
		int i = 0;
		int id_our_pilote = 0;
		int[] positions = new int[20];
		for (int c = 0; c < 20; c++)
		{
			positions[c] = c + 1;
		}
		while (sc.hasNextLine())
		{
			i++;
			line = sc.nextLine();
			debug("i=" + i + " line:" + line);
			if (i == 1)
			{
				// do stuff
				id_our_pilote = toInt(line);
				continue;
			}
			if (i == 2)
			{
				// do stuff
				continue;
			}

			String[] line2 = line.split("\\s+");
			int id = toInt(line2[0]);

			int current_position = positions[id - 1];

			// notre pilote
			if (id == id_our_pilote)
			{
				// fin
				if (line2[1].equals("I"))
				{
					System.out.println("KO");
					sc.close();
					return "KO";
				}
			}

			// incidents  all +1
			if (line2[1].equals("I"))
			{
				positions[id - 1] = -1;
				for (int j = 0; j < 20; j++)
				{
					int currentPos = positions[j];
					if (currentPos > current_position)
					{
						positions[j]--; // gain 1 place
					}
				}
				continue;
			}

			// doublage
			positions[id - 1]--; // gain  1 place
			for (int j = 0; j < 20; j++)
			{
				int currentPos = positions[j];
				if (currentPos == positions[id - 1])
				{
					positions[j]++; // loose 1 place
					break;
				}
			}

			// do other stuff

		}
		debug("Searching....");
		// Compute result

		String result = "" + positions[id_our_pilote - 1];
		System.out.println(result);
		sc.close();
		return result;
		// ----------------------------------------------------
		// -------------------- END ---------------------------
		// ----------------------------------------------------
	}

	// ==========================================================
	// ======================== HELPERS =========================
	// ==========================================================
	/* PRINT */

	public static void out(String line)
	{
		System.out.println(line);
	}

	public static void printmap(Integer[][] map)
	{
		debug("printmap()");
		for (int y = 0; y < map.length; y++)
		{
			String s = "";
			for (int x = 0; x < map[0].length; x++)
			{
				if (map[y][x] < 0)
				{
					s += "#";
				} else if (map[y][x] == 0)
				{
					s += ".";
				} else
				{
					s += map[y][x];
				}
			}
			debug(s);
		}
		debug("end printmap()");
	}

	/* PARSERS */

	public static Integer toInt(String line)
	{
		return Integer.parseInt(line);
	}

	public static Float toFloat(String line)
	{
		return Float.parseFloat(line);
	}

	public static String[] split(String line, String regex)
	{
		return line.split(regex);
	}

	public static LocalTime toTime(String time)
	{
		return LocalTime.parse(time, DateTimeFormatter.ISO_TIME); // such as '10:15', '10:15:30' or '10:15:30+01:00'
	}

	public static char[] toAnArrayOfChar(String line)
	{
		return line.toCharArray();
	}

	/* trucs */

	// copie d'un tableau à 2 dimensions
	public static int[][] copyTwoDimensionalArray(int[][] array)
	{
		int[][] copy = Arrays.stream(array).map(int[]::clone).toArray(int[][]::new);
		return copy;
	}

	// différence (en minutes) entre deux LocalTime
	long diffBetweenTwoLocalTimeInMinutes(LocalTime time1, LocalTime time2)
	{
		return time1.until(time2, ChronoUnit.MINUTES);
	}

}