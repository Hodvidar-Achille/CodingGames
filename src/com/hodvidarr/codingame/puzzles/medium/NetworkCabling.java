package com.hodvidarr.codingame.puzzles.medium;
import java.util.*;

/**
 *    https://www.codingame.com/ide/puzzle/network-cabling
 * by Hodvidar
 **/
class NetworkCabling {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        List<Double> positions = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            int X = in.nextInt();
            if(X > maxX)
                maxX = X;
            if(X < minX)
                minX = X;
            int Y = in.nextInt();
            positions.add((double) Y);
        }
        in.close();
        
        double mediane = getMediane(positions.toArray(new Double[0]));
        double cableLength = maxX - minX;
        for(double y : positions)
        {
            cableLength += Math.abs(y - mediane);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        String cableLengthStr = String.format("%d", (long) cableLength);
        System.out.println(cableLengthStr);
    }
    
    private static double getMediane(Double[] numArray)
    {
        Arrays.sort(numArray);
        double median;
        if (numArray.length % 2 == 0)
            median = (numArray[numArray.length/2] + numArray[numArray.length/2 - 1])/2;
        else
            median = numArray[numArray.length/2];
            
        return median;
    }
}
