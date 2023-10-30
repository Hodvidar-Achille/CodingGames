package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/training/easy/may-the-triforce-be-with-you by Hodvidar.
 **/
class MayTheTrifoceBeWithYou {

    private static final char dot = '.';
    private static final char star = '*';
    private static final char space = ' ';

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int N = in.nextInt();
        // int N = 10;
        System.err.println("N=" + N);

        // 1) Find the distance between the first dot '.' and the first start '*'
        int spaceOnLeft = (N == 1) ? 1 : (N * 2) - 1;
        System.err.println("spaceOnTheLeft=" + spaceOnLeft);

        // 2) Build the first triforce
        final StringBuilder sb = new StringBuilder();
        int numberOfStar = 1;
        boolean firstLine = true;
        for (int i = 0; i < N; i++) {
            if (firstLine) {
                sb.append(dot);
                addOneTriforceLayer(sb, spaceOnLeft - 1, numberOfStar);
                firstLine = false;
            } else {
                addOneTriforceLayer(sb, spaceOnLeft, numberOfStar);
            }
            sb.append("\n");
            spaceOnLeft -= 1;
            numberOfStar += 2;
        }

        // 3) build the 2 others triforces
        int spaceInMiddle = numberOfStar - 2;
        numberOfStar = 1;
        for (int i = 0; i < N; i++) {
            // 2nd triforce
            addOneTriforceLayer(sb, spaceOnLeft, numberOfStar);

            // 3rd triforce
            addOneTriforceLayer(sb, spaceInMiddle, numberOfStar);

            // skip last line
            if (i < N - 1)
                sb.append("\n");

            spaceOnLeft -= 1;
            spaceInMiddle -= 2;
            numberOfStar += 2;
        }

        System.out.println(sb.toString());
        in.close();
    }

    private static void addOneTriforceLayer(final StringBuilder sb, final int spaceOnLeft, final int numberOfStar) {
        for (int j = 0; j < spaceOnLeft; j++)
            sb.append(space);
        for (int j = 0; j < numberOfStar; j++)
            sb.append(star);
    }

}

/*
 *
 * . *
 ***
 *****
 *******
 *********
 * * *** ***** ******* *********
 *
 */
