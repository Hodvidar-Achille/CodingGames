package com.hodvidarr.adventofcode.y2019;

public final class Day4_2
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
		return atleastOneDoubleThatIsAlone(i1, i2, i3, i4, i5, i6) && isInRange(i1, i2, i3, i4, i5, i6);
	}

	private static boolean atleastOneDoubleThatIsAlone(int i1, int i2, int i3, int i4, int i5, int i6)
	{
		if(i1 == i2)
		{
			if(i2 != i3)
				return true;
		}
		if(i2 == i3)
		{
			if(i2 != i1 && i3 != i4)
				return true;
		}
		if(i3 == i4)
		{
			if(i3 != i2 && i4 != i5)
				return true;
		}
		if(i4 == i5)
		{
			if(i4 != i3 && i5 != i6)
				return true;
		}
		if(i5 == i6)
		{
			if(i5 != i4)
				return true;
		}
		return false;
	}

	private static boolean isInRange(int i1, int i2, int i3, int i4, int i5, int i6)
	{
		String s = "" + i1 + "" + i2 + "" + i3 + "" + i4 + "" + i5 + "" + i6;
		int i = Integer.parseInt(s);
		return i > MIN && i < MAX;
	}

}
