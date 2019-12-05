package com.hodvidarr.adventofcode.y2019;

/**
 * 
 * 
 * 
 * puzzle input : 272091-815432
 * @author Hodvidar
 *
 */
public final class Day4
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;

	public static void printIfVerbose(String s)
	{
		if(VERBOSE)
			System.err.println(s);
	}

	public static void main(String[] args) throws Exception
	{
		double result = test();
		System.err.println("result='" + result + "'");
	}

	private static final int MIN = 272091;
	private static final int MAX = 815432;

	private static double test() throws Exception
	{
		double counter = 0;
		for (int i1 = 1; i1 <= 9; i1++)
		{
			for (int i2 = i1; i2 <= 9; i2++)
			{
				for (int i3 = i2; i3 <= 9; i3++)
				{
					for (int i4 = i3; i4 <= 9; i4++)
					{
						for (int i5 = i4; i5 <= 9; i5++)
						{
							for (int i6 = i5; i6 <= 9; i6++)
							{
								if(isOK(i1, i2, i3, i4, i5, i6))
									counter++;
							}
						}
					}
				}
			}
		}

		return counter;
	}

	private static boolean isOK(int i1, int i2, int i3, int i4, int i5, int i6)
	{
		return isOneDouble(i1, i2, i3, i4, i5, i6) && isInRange(i1, i2, i3, i4, i5, i6);
	}

	private static boolean isOneDouble(int i1, int i2, int i3, int i4, int i5, int i6)
	{
		return i1 == i2 || i2 == i3 || i3 == i4 || i4 == i5 || i5 == i6;
	}

	private static boolean isInRange(int i1, int i2, int i3, int i4, int i5, int i6)
	{
		String s = "" + i1 + "" + i2 + "" + i3 + "" + i4 + "" + i5 + "" + i6;
		int i = Integer.parseInt(s);
		return i > MIN && i < MAX;
	}

}
