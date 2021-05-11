package com.hodvidar.codingame.puzzles.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/stock-exchange-losses
 * by Hodvidar
 **/
class StockExchangeLosses {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int n = in.nextInt();
        final List<Integer> values = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int maxLoss = 0;
        for (int i = 0; i < n; i++) {
            final int v = in.nextInt();
            values.add(v);
            if (v > max) {
                if (min - max < maxLoss && min != Integer.MAX_VALUE)
                    maxLoss = min - max;
                max = v;
                min = v;
            } else if (v < min) {
                min = v;
                if (min - max < maxLoss)
                    maxLoss = min - max;
            }
        }


        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(maxLoss);
        in.close();
    }
}
