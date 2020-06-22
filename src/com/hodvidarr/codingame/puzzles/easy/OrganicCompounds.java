package com.hodvidarr.codingame.puzzles.easy;
import java.util.*;

/**
 *    https://www.codingame.com/ide/puzzle/organic-compounds
 * by Hodvidar
 **/
class OrganicCompounds {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        System.err.println("N="+N);
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String[] arrayStr = new String[N];
        int max = 0;
        for (int i = 0; i < N; i++) {
            String COMPOUND = in.nextLine();
            System.err.println(COMPOUND);
            arrayStr[i] = COMPOUND;
            int n = COMPOUND.length();
            if(n>max)
                max = n;
        }
        in.close();
        
        // Build compound as table of char
        char[][] compounds = new char[N][max];
        char space = ' ';
        for (int i = 0; i < N; i++)
        {
            String line = arrayStr[i];
            int lineMaxLength = line.length();
            for(int j = 0; j < max; j++)
            {
                if(j >= lineMaxLength)
                {
                    compounds[i][j] = space;
                }
                else
                {
                     compounds[i][j] = line.charAt(j);
                }
            }
        }

        // Go through the table and check rules :
        // For Every 'H' at coordinate (i, j)
        // check that number (i, j+1) is :
        // 0, 1, 2 or 3 
        // and that numbers (1 to 4) at 
        // right : (i, j+3), 
        // left : (i, j-3), 
        // top : (i-1, j) and 
        // bottom : (i+1, j)
        // sum to exactly 4.
        int sum = 0;
        for (int i = 0; i < N; i++)
        {
            for(int j = 0; j < max; j++)
            {
                if(compounds[i][j] != 'H')
                    continue;
                
            	sum = Integer.valueOf(""+compounds[i][j+1]);
            	
            	// check right
            	if(j+3 < max && compounds[i][j+3] != space)
            		sum += Integer.valueOf(""+compounds[i][j+3]);
            	// check left
            	if(j-3 >= 0 && compounds[i][j-3] != space)
            		sum += Integer.valueOf(""+compounds[i][j-3]);
            	// check bottom
            	if(i+1 < N && compounds[i+1][j] != space)
            		sum += Integer.valueOf(""+compounds[i+1][j]);
            	// check top
            	if(i-1 >= 0 && compounds[i-1][j] != space)
            		sum += Integer.valueOf(""+compounds[i-1][j]);
            	
            	if(sum != 4)
            	{
            		System.out.println("INVALID");
           		 	return;
            	}
                
            }
        }
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        System.out.println("VALID");
    }
}
