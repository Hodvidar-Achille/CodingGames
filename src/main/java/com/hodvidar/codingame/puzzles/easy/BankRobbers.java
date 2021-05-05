package com.hodvidar.codingame.puzzles.easy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/bank-robbers by Hodvidar
 **/
class BankRobbers {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int R = in.nextInt();
        final int V = in.nextInt();
        System.err.println("R=" + R + " V=" + V);
        final List<Integer> times = new ArrayList<>();

        for (int i = 0; i < V; i++) {
            final int C = in.nextInt();
            final int N = in.nextInt();
            final int timeForVault = getTriesNumber(C, N);
            System.err.println("i=" + i + " C=" + C + " N=" + N + " timeForVault=" + timeForVault);
            times.add(timeForVault);
        }

        // init time per robber
        final Integer[] timePerRobber = new Integer[R];
        for (int i = 0; i < R; i++)
            timePerRobber[i] = 0;

        while (true) {
            // 1) remove vault with 0 remaining tries
            final Iterator<Integer> ite = times.iterator();
            while (ite.hasNext()) {
                final Integer v = ite.next();
                if (v == 0)
                    ite.remove();
            }

            // 2) stop if no more vault to crack
            if (times.size() == 0)
                break;

            // 3) tries the vault
            final int max = Math.min(R, times.size());
            for (int i = 0; i < max; i++) {
                times.set(i, times.get(i) - 1);
                timePerRobber[i] = timePerRobber[i] + 1;
            }
        }

        int maxTime = 0;
        for (int i = 0; i < R; i++) {
            final int t = timePerRobber[i];
            if (t > maxTime)
                maxTime = t;
        }
        System.out.println(maxTime);
        in.close();
    }

    // method to get time (in sec) to try all combinaison for one vault
    private static int getTriesNumber(final int c, final int n) {
        final int v = c - n;
        // get the product of n and v...
        final double r = Math.pow(10, n) * Math.pow(5, v);
        final int i = (int) r;
        return i;
    }

}
