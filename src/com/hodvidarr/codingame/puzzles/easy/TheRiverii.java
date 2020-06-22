package com.hodvidarr.codingame.puzzles.easy;
import java.util.*;

/**
 *    https://www.codingame.com/ide/puzzle/the-river-ii-
 * by Hodvidar
 **/
class TheRiverii {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int r1 = in.nextInt();
    
        System.err.println("r1:"+r1);
        int nbOfRivers = findRivers(r1);
        System.err.println("nbOfRivers:"+nbOfRivers);
        String result = (nbOfRivers >= 2) ? "YES" : "NO";

        System.out.println(result);
        in.close();
    }
    
    // ---- Easy to implement solution, not very optimized ----
    /**
     * Look for how many number that lead to number 'n' using the 'nextNumber(n)' method.
     * Retuns 0 if no solution.
     * Note : Optimization : don't look more than 2 solutions.
     */
    private static int findRivers(long n)
    {
        int count = 0;
        for(int i = 1; i <= n; i++)
        {
            long lower = n - i;
            long next = nextNumber(lower);
            if(next == n)
            {
                // System.err.println("lower:"+lower);
                // System.err.println("next:"+next);
                count++;
                count+=findRivers(lower);
            }
            if(count >= 2)
               return count;
        }
        
        return count;
    }
    
    
    /**
     * Returns n + the sum of its digits.
     */
    public static long nextNumber(long n)
    {
        return n+sumDigits(n);
    }
    
    /**
     * Returns the sum of the digits of n. 
     * (Recursive)
     */
    public static long sumDigits(long n) {
        return (n==0L) ? 0L : (n % 10L) + sumDigits(n/10L);
    }
}
