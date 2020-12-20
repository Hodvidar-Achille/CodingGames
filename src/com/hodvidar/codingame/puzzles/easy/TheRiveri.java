package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 *    https://www.codingame.com/ide/puzzle/the-river-i-
 * by Hodvidar
 **/
class TheRiveri {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        long r1 = in.nextLong();
        long r2 = in.nextLong();

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        // 1) check if r1 and r2 are equals
        // 2) if not, find next number of the lower 'r'
        // 3) repeat
        /*
        if(r1 <= r2)
            search(r1, r2);
        else
            search(r2, r1);
        */
        long lower = (r1 > r2) ? r2 : r1;
        long higher = (r1 > r2) ? r1 : r2;
        while(true)
        {
            // 1)
            if(lower == higher)
            {
                System.out.println(lower);
                in.close();
                return;   
            }
            
            long temp = nextNumber(lower);
            if(temp <= higher)
            {
                lower = temp;
            }
            else
            {
                lower = higher;
                higher = temp;
            }
        }
    }
    
    /**
     * Find the meeting point using recursion (risk of stack overflow error).
     */
    @SuppressWarnings("unused")
	private static void search(long lower, long higher)
    {
       // System.err.println("search("+lower+","+higher+")");
       
       // 1)
        if(lower == higher)
        {
            System.out.println(lower);
            return;   
        }
        
        // 2) & 3)
        long temp = nextNumber(lower);
        if(temp <= higher)
        {
            search(temp, higher);
        }
        else
        {
            search(higher, temp);
        }
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
