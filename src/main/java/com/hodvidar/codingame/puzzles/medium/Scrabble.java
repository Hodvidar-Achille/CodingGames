package com.hodvidar.codingame.puzzles.medium;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/scrabble by Hodvidar
 **/
class Scrabble {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int N = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        final List<String> dictionnary = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            final String W = in.nextLine();
            // System.err.println(W);
            if (W.length() <= 7)
                dictionnary.add(W);
        }
        final String LETTERS = in.nextLine();
        int maxScore = 0;
        String maxWord = "";
        for (final String s : dictionnary) {
            if (canDoWord(LETTERS, s)) {
                final int a = calculScore(s);
                if (a > maxScore) {
                    maxScore = a;
                    maxWord = s;
                }

            }
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(maxWord);
        in.close();
    }

    private static boolean canDoWord(String letters, final String word) {
        for (final char c : word.toCharArray()) {
            final int index = letters.indexOf(c);
            if (index == -1)
                return false;

            letters = letters.substring(0, index) + letters.substring(index + 1);
        }
        return true;
    }

    private static int calculScore(final String aWord) {
        int score = 0;
        for (final char c : aWord.toCharArray()) {
            if (c == 'e' || c == 'a' || c == 'i' || c == 'o' || c == 'n'
                    || c == 'r' || c == 't' || c == 'l' || c == 's' || c == 'u') {
                score += 1;
            }
            if (c == 'd' || c == 'g') {
                score += 2;
            }
            if (c == 'b' || c == 'c' || c == 'm' || c == 'p') {
                score += 3;
            }
            if (c == 'f' || c == 'h' || c == 'v' || c == 'w' || c == 'y') {
                score += 4;
            }
            if (c == 'k') {
                score += 5;
            }
            if (c == 'j' || c == 'x') {
                score += 8;
            }
            if (c == 'q' || c == 'z') {
                score += 10;
            }
        }
        return score;
    }
}
