package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/temperatures by Hodvidar
 **/
class Temperatures {
    private static final int MIN = -5527;

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // the number of temperatures to analyse
        in.nextLine();
        String temps = in.nextLine(); // the n temperatures expressed as integers ranging from -273 to 5526
        String[] listeT = temps.split("\\s+");
        int best = MIN;
        //liste de string à list d'integer
        if(n > 0) {
            for(String s : listeT){
                int i = Integer.parseInt(s);
                best = getBest(best, i);
            }
        }
        if(best == MIN) best = 0;
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        System.out.println(best);
    }

    private static int getBest(int currentBest, int current) {
        if(currentBest == current) return currentBest;
        if(Math.abs(currentBest) == Math.abs(current)) return Math.abs(currentBest);
        return (Math.abs(currentBest) < Math.abs(current)) ? currentBest : current;
    }
}
