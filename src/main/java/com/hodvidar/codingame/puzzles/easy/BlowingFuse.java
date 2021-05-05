package com.hodvidar.codingame.puzzles.easy;

import java.util.Arrays;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/blowing-fuse by Hodvidar
 **/
class BlowingFuse {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int numberOfAppliances = in.nextInt();
        final int numberOfClicks = in.nextInt();
        final int mainFuseCapacity = in.nextInt();

        final int[] appliancesConsumption = new int[numberOfAppliances];
        for (int i = 0; i < numberOfAppliances; i++) {
            final int electricalConsumption = in.nextInt();
            appliancesConsumption[i] = electricalConsumption;
        }

        int maxElectricalConsumption = 0;
        final int[] appliancesState = new int[numberOfAppliances];

        for (int i = 0; i < numberOfClicks; i++) {
            final int applianceUsed = in.nextInt();
            final int id = applianceUsed - 1;

            // light On or light off the applicance
            if (appliancesState[id] == 0)
                appliancesState[id] = appliancesConsumption[id];
            else
                appliancesState[id] = 0;

            // calculate the total
            final int currentTotalConsumption = Arrays.stream(appliancesState).sum();

            if (currentTotalConsumption > mainFuseCapacity) {
                System.out.println("Fuse was blown.");
                in.close();
                return;
            }
            if (currentTotalConsumption > maxElectricalConsumption) {
                maxElectricalConsumption = currentTotalConsumption;
            }
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println("Fuse was not blown.");
        System.out.println("Maximal consumed current was " + maxElectricalConsumption + " A.");
        in.close();
    }
}
