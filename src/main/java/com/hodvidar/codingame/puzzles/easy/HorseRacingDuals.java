package com.hodvidar.codingame.puzzles.easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Scanner;

/**
 * https://www.codingame.com/training/easy/horse-racing-duals by Hodvidar
 */
class HorseRacingDuals {
    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int N = in.nextInt();

        // TreeSet is one of Java's sorted data structure
        final ArrayList<Integer> horses = new ArrayList<Integer>();
        for (int i = 0; i < N; i++)
            horses.add(in.nextInt());

        Collections.sort(horses);
        final Iterator<Integer> it = horses.iterator();
        int curr, prev = it.next();
        int min = Integer.MAX_VALUE;
        while (it.hasNext()) {
            curr = it.next();
            if (curr - prev < min)
                min = curr - prev;
            prev = curr;
        }
        System.out.println(min);
        in.close();
    }
}
