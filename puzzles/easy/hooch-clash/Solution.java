import java.util.*;
import java.io.*;
import java.math.*;

/**
 *    https://www.codingame.com/ide/puzzle/hooch-clash
 * By Hodvidar (TODO : finish, might need to use BigDecimal)
 **/
class Solution {

    public static void main(String args[]) {
        // Scanner in = new Scanner(System.in);
        int minSize = 1000; //in.nextInt();
        int maxSize = 3000; //in.nextInt();
        int kingSize1 = 1356; //in.nextInt();
        int kingSize2 = 2644; //in.nextInt();
        // want : 1200 2680
        System.err.println("minSize="+minSize);
        System.err.println("maxSize="+maxSize);
        System.err.println("kingSize1="+kingSize1);
        System.err.println("kingSize2="+kingSize2);
        

        double totalVolume = getSphereVolume(kingSize1/2d) + getSphereVolume(kingSize2/2d);
        // Look for "fun" contenders orb sizes.
        Integer[] funResults = LookForFunSizes(totalVolume, minSize, maxSize, kingSize1, kingSize2);
        if(funResults != null)
        {
        	 System.out.println(funResults[0]+" "+funResults[1]);
        	 return;
        }
        // Look for "valid" 
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println("VALID");
        
        // System.out.println("INVALID");
    }
    
    private static Integer[] LookForFunSizes(double totalVolume, int minSize, int maxSize, int kingSize1, int kingSize2)
    {
    	List<Integer[]> listOfResult = new ArrayList<>(); 
    	for(int i = minSize; i <= maxSize; i++)
        {
         	if(i == kingSize1)
         		continue;
    		for(int j = maxSize; j > i; j--)
            {
             	if(j == i)
             		continue;
             	if(j == kingSize2)
             		continue;
             	
             	// --- DEBUGGING 
             	if(i == 1200 && j == 2680)
             	{
             		System.err.println("here");
             	}
             	// ---
             	
             	
             	double comparaison = compareVolume(totalVolume, i, j);
             	if(comparaison > 0d)
             		break;
             	if(comparaison == 0d)
             	{
             		listOfResult.add(new Integer[]{i, j});
             	}
            }
        }
    	
    	if(listOfResult.isEmpty())
    		return null;
    	
    	// Filter the "funnier" result
    	double maxAreaDiff = 0d;
    	int index = 0;
    	for(int i = 0; i < listOfResult.size(); i++)
    	{
    		Integer[] result = listOfResult.get(i);
    		double radius1 = result[0]/2d;
    		double radius2 = result[1]/2d;
    		double diff = getSphereArea(radius2) - getSphereArea(radius1);
    		if(diff > maxAreaDiff)
    		{
    			maxAreaDiff = diff;
    			index = i;
    		}
    	}
    	return listOfResult.get(index);
    }
    
    private static double compareVolume(double totalVolume, int diameter1, int diameter2)
    {
    	return totalVolume - (getSphereVolume(diameter1/2d) + getSphereVolume(diameter2/2d));
    }
    
    private static double getSphereVolume(double sphereRadius)
	{
		double sphereVolume = ( 4.0 / 3.0 ) * Math.PI * Math.pow(sphereRadius, 3 );
		return rounding(sphereVolume);
	}
	
    private static double getSphereArea(double sphereRadius)
	{
		double sphereArea = 4 * Math.PI * Math.pow(sphereRadius, 2);
		return rounding(sphereArea);
	}
    
    private static double rounding(double n)
    {
    	// return Math.round(n * 15) / 15;
    	return n;
    }
}
