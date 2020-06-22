package com.hodvidarr.codingame.puzzles.easy;
import java.util.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class MayTheTrifoceBeWithYou
{

	private static final char dot = '.';
	private static final char star = '*';
	private static final char space = ' ';

	public static void main(String[] args)
	{
		Scanner in = new Scanner(System.in);
		int N = in.nextInt();
		// int N = 10;
		System.err.println("N=" + N);

		// 1) Find the distance between the first dot '.' and the first start '*'
		int spaceOnLeft = (N == 1) ? 1 : (N * 2) - 1;
		System.err.println("spaceOnTheLeft=" + spaceOnLeft);

		// 2) Build the first triforce
		StringBuilder sb = new StringBuilder();
		int numberOfStar = 1;
		boolean firstLine = true;
		for (int i = 0; i < N; i++)
		{
			if (firstLine)
			{
				sb.append(dot);
				addOneTriforceLayer(sb, spaceOnLeft - 1, numberOfStar);
				firstLine = false;
			}
			else
			{
				addOneTriforceLayer(sb, spaceOnLeft, numberOfStar);
			}
			sb.append("\n");
			spaceOnLeft -= 1;
			numberOfStar += 2;
		}

		// 3) build the 2 others triforces
		int spaceInMiddle = numberOfStar - 2;
		numberOfStar = 1;
		for (int i = 0; i < N; i++)
		{
			// 2nd triforce
			addOneTriforceLayer(sb, spaceOnLeft, numberOfStar);

			// 3rd triforce
			addOneTriforceLayer(sb, spaceInMiddle, numberOfStar);
			
			// skip last line
			if (i < N - 1)
				sb.append("\n");
				
			spaceOnLeft -= 1;
			spaceInMiddle -= 2;
			numberOfStar += 2;
		}

		System.out.println(sb.toString());
		in.close();
	}

	private static void addOneTriforceLayer(StringBuilder sb, int spaceOnLeft, int numberOfStar)
	{
		for (int j = 0; j < spaceOnLeft; j++)
			sb.append(space);
		for (int j = 0; j < numberOfStar; j++)
			sb.append(star);
	}

}

/*

.        *
        ***
       *****
      *******
     *********
    *         *
   ***       ***
  *****     *****
 *******   *******
********* *********

*/
