package com.hodvidar.codingame.puzzles.medium;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/there-is-no-spoon-episode-1
 * by Hodvidar
 * <p>
 * Don't let the machines win. You are humanity's last hope...
 **/
class ThereIsNoSpoonEpisode1 {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int width = in.nextInt(); // the number of cells on the X axis
        final int height = in.nextInt(); // the number of cells on the Y axis
        in.nextLine();
        System.err.println("width : " + width + " || height : " + height);
        final char[][] grille = new char[height][width];
        for (int z = 0; z < height; z++) {
            final char[] line = in.nextLine().toCharArray(); // width characters, each either 0 or .
            grille[z] = line;
        }
        /*
        String CoordX ="  |";
        for (int j = 0; j < width; j++){
               CoordX += j + "|";
        }
        CoordX += " j";
        System.err.println(CoordX);
        for (int i = 0; i < height; i++){
            System.err.print(i+" ");
            for (int j = 0; j < width; j++){
                System.err.print("|"+grille[i][j]);
            }
            System.err.print("|\n");
        }
        System.err.println("i");
        */

        final String d = "-1 -1";
        final String e = " ";
        String r;
        String b;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (grille[i][j] != '.') {
                    r = d;
                    b = d;
                    int x = j + 1;
                    while (x < width) {
                        if (grille[i][x] != '.') {
                            r = x + e + i;
                            break;
                        }
                        x++;
                    }
                    int y = i + 1;
                    while (y < height) {
                        if (grille[y][j] != '.') {
                            b = j + e + y;
                            break;
                        }
                        y++;
                    }
                    System.out.println(j + e + i + e + r + e + b);
                }
            }
        }
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");


        // Three coordinates: a node, its right neighbor, its bottom neighbor
        //System.out.println("0 0 1 0 0 1");
        in.close();
    }
}
