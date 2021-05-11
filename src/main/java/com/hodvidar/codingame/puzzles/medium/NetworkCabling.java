package com.hodvidar.codingame.puzzles.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/network-cabling
 * by Hodvidar
 **/
class NetworkCabling {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int N = in.nextInt();
        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        final List<Double> positions = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            final int X = in.nextInt();
            if (X > maxX)
                maxX = X;
            if (X < minX)
                minX = X;
            final int Y = in.nextInt();
            positions.add((double) Y);
        }
        in.close();

        final double mediane = getMediane(positions.toArray(new Double[0]));
        double cableLength = maxX - minX;
        for (final double y : positions) {
            cableLength += Math.abs(y - mediane);
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        final String cableLengthStr = String.format("%d", (long) cableLength);
        System.out.println(cableLengthStr);
    }

    private static double getMediane(final Double[] numArray) {
        Arrays.sort(numArray);
        final double median;
        if (numArray.length % 2 == 0)
            median = (numArray[numArray.length / 2] + numArray[numArray.length / 2 - 1]) / 2;
        else
            median = numArray[numArray.length / 2];

        return median;
    }
}
