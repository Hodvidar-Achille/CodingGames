import java.util.*;
/**
* For puzzle : https://www.codingame.com/ide/puzzle/robbery-optimisation
*/
class Solution
{

	public static void main(String args[])
	{
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		long[] houses = new long[N];
		for (int i = 0; i < N; i++)
		{
			houses[i] = in.nextLong();
		}

		System.err.print("houses :[");
		for (long i : houses)
			System.err.print(i + ", ");
		System.err.println("]");
		List<Integer> housesToVisit = new ArrayList<>();
		analyzeHouses(
			0,
			N - 1,
			houses,
			housesToVisit,
			false,
			0L,
			0L,
			new ArrayList<Integer>(),
			new ArrayList<Integer>());
		System.err.print("housesToVisit :[");
		for (Integer i : housesToVisit)
			System.err.print(i + ", ");
		System.err.println("]");
		long total = 0;
		for (Integer i : housesToVisit)
			total += houses[i.intValue()];

		// Write an action using System.out.println()
		// To debug: System.err.println("Debug messages...");

		System.out.println(total);
		in.close();
	}

	/**
	 * start : start of array (inclusif).
	 * end : end of array (inclusif).
	 *
	 **/
	private static void analyzeHouses(
		int start,
		int end,
		long[] houses,
		List<Integer> housesToVisit,
		boolean ignoreTrap,
		long e,
		long m,
		List<Integer> listE,
		List<Integer> listM)
	{
		// System.err.println("analyzeHouses(" + start + ", " + end + ", houses, housesToVisit, "
		//  	+ ignoreTrap + ", " + e + ", " + m + ", listE, listM)");

		//  System.err.println("start < 0");
		// Too much on the left.
		if (start < 0)
			return;

		//  System.err.println("end >= houses.length");
		// Too much on the right.
		if (end >= houses.length)
			return;

		//  System.err.println("end < start");
		// Empty segment;
		if (end < start)
			return;

		//  System.err.println("start == end");
		// Only one house.
		if (start == end)
		{
		    // Only add if value not neg.
			if (houses[start] > 0)
				housesToVisit.add(Integer.valueOf(start));
			return;
		}

		//  System.err.println("end - start == 1");
		// Only two houses.
		if (end - start == 1)
		{
			if (houses[start] > houses[end])
			{
				if (houses[start] > 0)
					housesToVisit.add(Integer.valueOf(start));
				return;
			}

			if (houses[end] > 0)
				housesToVisit.add(Integer.valueOf(end));
			return;
		}

		// More than two houses :
		// Cut houses that are trapped and analyze segments.
		//  System.err.println("Loop");
		if (!ignoreTrap)
		{
			for (int i = start; i <= end; i++)
			{
				if (houses[i] < 0L)
				{
					analyzeHouses(
						start,
						i - 1,
						houses,
						housesToVisit,
						false,
						0L,
						0L,
						new ArrayList<Integer>(),
						new ArrayList<Integer>());
					analyzeHouses(
						i + 1,
						end,
						houses,
						housesToVisit,
						false,
						0L,
						0L,
						new ArrayList<Integer>(),
						new ArrayList<Integer>());
					return;
				}
			}
		}

		// System.err.println("analyzeSegment...");
		// No houses trapped in the segment.
		// analyze segment...
		analyzeSegment(start, end, houses, housesToVisit, e, m, listE, listM);
		return;
	}

	/**
	 * valueOnLeft : value that is at the house 'start-1' or 'middle-2', 0 by default.
	 */
	private static void analyzeSegment(
		int start,
		int end,
		long[] houses,
		List<Integer> housesToVisit,
		long e,
		long m,
		List<Integer> listE,
		List<Integer> listM)
	{
		// System.err.println("analyzeSegment(" + start + ", " + end + ", houses, housesToVisit, " + e
		//	+ ", " + m + ", listE, listM");
		int e1 = start;
		int middle = start + 1;
		int e2 = middle + 1;
		long E1 = houses[e1];
		long M = houses[middle];
		long E2 = houses[e2];
		if (M + m > E1 + E2 + e)
		{
			System.err.println("analyzeSegment(...) -> (M + m > E1 + E2 + e) middle="+middle);
			for (Integer ms : listM)
				housesToVisit.add(ms);
			housesToVisit.add(Integer.valueOf(middle));
			analyzeHouses(
				middle + 2,
				end,
				houses,
				housesToVisit,
				true,
				0L,
				0L,
				new ArrayList<Integer>(),
				new ArrayList<Integer>());
			return;
		}

		// 'M + m > E1 + E2 + e' is false
		listE.add(Integer.valueOf(e1));
		// End of (sub)segment ?
		if (e2 == end)
		{
			System.err.println("analyzeSegment(...) -> (e2 == end), middle = "+middle);
			for (Integer ms : listE)
				housesToVisit.add(ms);
			housesToVisit.add(Integer.valueOf(e2));
			return;
		}
		
		System.err.println("analyzeSegment(...) -> Recursion with middle = "+middle);
		// Recursion :
		analyzeHouses(middle, end, houses, housesToVisit, true, m, e + E1, listM, listE);
		return;
	}
}
