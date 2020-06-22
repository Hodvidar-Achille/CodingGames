package com.hodvidarr.codingame.puzzles.easy;
import java.util.*;

/**
 *    https://www.codingame.com/ide/puzzle/disordered-first-contact
 * by Hodvidar
 **/
class DisorderedFirstContact {

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String MESSAGE = in.nextLine();

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        String result = translate(N, MESSAGE, "");

        System.out.println(result);
        in.close();

    }

// ==================================================================
    
    /**
     * Recursive
     * @param numberOfStep
     * @param message
     * @param result
     * @return result with transformation.
     */
    private static String translate(int numberOfStep, String message, String result)
    {
    	if(numberOfStep == 0)
    		return message;
    	
    	String r = "";
    	
    	// Translate from aliens
    	if(numberOfStep < 0)
    	{
    		int i = 1;
    		int n = 1;
    		while(true)
    		{
    			// One transformation as the aliens do it
    			int lengthStr = message.length();
    			if(lengthStr == 0)
    				return translate(++numberOfStep, result, "");
    			
    			if(lengthStr <= n)
    				n =  lengthStr;
    			
    			r = message.substring(0, n);
    			message = message.substring(n,  lengthStr);
    			// 2nd time, 4th time, 6th time... : add at the beginning
    			if(i % 2 == 0)
    			{
    				result = r+ result ;
    			}
    			// 1st time, 3rd time, 5th time... : add at the end
    			else
    			{
    				result = result + r;
    			}
    			n++;
    			i++;
    		}
    	}
    	
    	// Translate from humans (reverse from previous)
    	int lengthStr = message.length();
    	int n = 1;
    	int firstN = 0;
    	// Loop going up to find the max value of N 
    	// and firstN (the value of N when the string is too small)
    	while(true)
    	{
    		if(lengthStr <= n)
    		{
    			firstN = lengthStr;
    			n--;
    			break;
    		}
    		lengthStr = lengthStr - n;
    		n++;
    	}
		
		// handle case for firstN
		lengthStr = message.length();
		if((n+1) % 2 == 0)
		{
			r = message.substring(0, firstN);
			message = message.substring(firstN,  lengthStr);
		}
		else
		{
			r = message.substring(lengthStr-firstN, lengthStr);
			message = message.substring(0,  lengthStr-firstN);
		}
		result = r + result;
		// n is at its max value
		// Loop going down until n == 0;
		while(true)
		{
			// One transformation as the humans do it
			lengthStr = message.length();
			if(lengthStr == 0)
				return translate(--numberOfStep, result, "");
			
			// 2nd time, 4th time, 6th time... : take from the beginning
			if(n % 2 == 0)
			{
				r = message.substring(0, n);
				message = message.substring(n,  lengthStr);
			}
			// 1st time, 3rd time, 5th time... : take from the end
			else
			{
				r = message.substring(lengthStr-n, lengthStr);
				message = message.substring(0,  lengthStr-n);
			}
			result = r + result;
			n--;
		}
    }
}
