package com.hodvidarr.codingame.puzzles.medium;
import java.util.*;

/**
 *    https://www.codingame.com/ide/puzzle/stock-exchange-losses
 * by Hodvidar
 **/
class StockExchangeLosses {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt();
        List<Integer> values = new ArrayList<>();
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        int maxLoss = 0;
        for (int i = 0; i < n; i++) {
            int v = in.nextInt();
            values.add(v);
            if(v > max)
            {
                if(min-max < maxLoss && min != Integer.MAX_VALUE)
                    maxLoss = min - max;
                max = v;
                min = v;
            }
            else if(v < min)
            {
                min = v;
                if(min-max < maxLoss)
                    maxLoss = min - max;
            }
        }
        

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(maxLoss);
        in.close();
    }
}
