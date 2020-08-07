package com.hodvidar.codingame.puzzles.easy;
import java.util.*;

/**
 *    https://www.codingame.com/ide/puzzle/blowing-fuse
 * by Hodvidar
 **/
class BlowingFuse {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int numberOfAppliances = in.nextInt();
        int numberOfClicks = in.nextInt();
        int mainFuseCapacity = in.nextInt();
        
        int[] appliancesConsumption = new int[numberOfAppliances];
        for (int i = 0; i < numberOfAppliances; i++) {
            int electricalConsumption = in.nextInt();
            appliancesConsumption[i] = electricalConsumption;
        }
        
        int maxElectricalConsumption = 0;
        int[] appliancesState = new int[numberOfAppliances];
        
        for (int i = 0; i < numberOfClicks; i++) 
        {
            int applianceUsed = in.nextInt();
            int id = applianceUsed - 1;
            
            // light On or light off the applicance
            if(appliancesState[id] == 0)
            	appliancesState[id] = appliancesConsumption[id];
            else
            	appliancesState[id] = 0;
            	
            
            // calculate the total
            int currentTotalConsumption = Arrays.stream(appliancesState).sum();
            
            if(currentTotalConsumption > mainFuseCapacity)
            {
            	System.out.println("Fuse was blown.");
            	in.close();
            	return;
            }
            if(currentTotalConsumption > maxElectricalConsumption)
            {
            	maxElectricalConsumption = currentTotalConsumption;
            }
        }
        
        

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println("Fuse was not blown.");
        System.out.println("Maximal consumed current was "+maxElectricalConsumption+" A.");
        in.close();
    }
}
