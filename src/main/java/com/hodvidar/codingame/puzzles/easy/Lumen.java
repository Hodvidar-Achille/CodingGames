package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/lumen by Hodvidar
 **/
class Lumen {

    private static final boolean VERBOSE = false;

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int N = in.nextInt();
        final int L = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }

        // 1) dark room
        final Integer[][] room = new Integer[N][N];
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                room[i][j] = 0;

        // 2) adds candles
        for (int i = 0; i < N; i++) {
            final String LINE = in.nextLine();
            // System.err.println(LINE);
            int j = 0;
            for (final char c : LINE.toCharArray()) {
                if (c == ' ')
                    continue;

                if (c == 'C') {
                    printIfVerbose("spreadLight(" + N + ", " + L + ", room, " + i + ", " + j + ")");
                    // 3) spread light
                    spreadLight(N, L, room, i, j);
                }
                j++;
            }
        }

        display(room, N);

        // 4) count dark spots
        int ds = 0;
        for (int i = 0; i < N; i++)
            for (int j = 0; j < N; j++)
                if (room[i][j] == 0)
                    ds++;

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(ds);
        in.close();
    }

    private static void spreadLight(final int N, final int l, final Integer[][] room, final int i, final int j) {
        if (l < 1)
            return;
        if (i < 0 || j < 0 || i >= N || j >= N)
            return;

        // itselft
        if (room[i][j] >= l)
            return;
        room[i][j] = l;

        // DEBUG
        // display(room, N);

        final int newL = l - 1;
        // top-left
        spreadLight(N, newL, room, i - 1, j - 1);
        // top
        spreadLight(N, newL, room, i - 1, j);
        // top-right
        spreadLight(N, newL, room, i - 1, j + 1);
        // right
        spreadLight(N, newL, room, i, j + 1);
        // bottom-right
        spreadLight(N, newL, room, i + 1, j + 1);
        // bottom
        spreadLight(N, newL, room, i + 1, j);
        // bottom-left
        spreadLight(N, newL, room, i + 1, j - 1);
        // left
        spreadLight(N, newL, room, i, j - 1);
    }

    private static void display(final Integer[][] room, final int N) {
        if (!VERBOSE)
            return;
        System.err.println("room :");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                System.err.print(room[i][j]);
            }
            System.err.println();
        }
    }

}
