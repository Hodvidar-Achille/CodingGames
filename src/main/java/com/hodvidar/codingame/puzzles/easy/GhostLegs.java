package com.hodvidar.codingame.puzzles.easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/ghost-legs by Hodvidar
 **/
class GhostLegs {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int W = in.nextInt();
        final int H = in.nextInt();
        // number of vertical lines (A1, B2, ...)
        final int lines = ((W - 1) / 3) + 1;

        char[] letters = new char[lines];
        char[] numbers = new char[lines];
        final Map<Integer, Integer> start_end_Int = new HashMap<>();
        for (int i = 0; i < lines; i++)
            start_end_Int.put(Integer.valueOf(i), Integer.valueOf(i));

        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < H; i++) {
            final String line = in.nextLine();
            System.err.println("Line number " + i);
            System.err.println(line);
            // get "letters" (A, B, C, ...)
            if (i == 0) {
                letters = getChar(line, letters);
            }
            // get "numbers" (1, 2, 3, ...)
            else if (i == H - 1) {
                numbers = getChar(line, numbers);
            } else {
                analyzeLine(line, start_end_Int);
            }
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        for (int i = 0; i < lines; i++) {
            final char letter = letters[i];
            final char number = numbers[start_end_Int.get(Integer.valueOf(i))];
            System.out.println(letter + "" + number);
        }
        // System.out.println("answer");
        in.close();
    }

    private static char[] getChar(final String line, final char[] someChars) {
        System.err.println("#getChar");
        int i = 0;
        for (final char c : line.toCharArray()) {
            if (c != ' ') {
                someChars[i] = c;
                i++;
            }
        }
        return someChars;
    }

    private static void analyzeLine(final String line, final Map<Integer, Integer> map) {
        System.err.println("#analyzeLine");
        int lineNumber = -1;
        boolean previousWasLink = false;
        for (final char c : line.toCharArray()) {
            if (c == '|') {
                previousWasLink = false;
                lineNumber++;
                continue;
            }
            if (c == ' ') {
                previousWasLink = false;
                continue;
            }
            // '-'
            if (previousWasLink)
                continue;
            // 1st '-' of two
            // swap linesNumbers
            previousWasLink = true;
            swapLines(map, lineNumber);
        }
    }

    /**
     * Swaps values of line lineNumber and line lineNumber+1.
     */
    private static void swapLines(final Map<Integer, Integer> map, final int lineNumber) {
        // System.err.println("#swapLines (for n°"+lineNumber+" & n°"+(lineNumber+1)+").");
        final Integer startingNumber1 = getKeyForValue(map, lineNumber);
        final Integer startingNumber2 = getKeyForValue(map, lineNumber + 1);
        map.put(startingNumber1, lineNumber + 1);
        map.put(startingNumber2, lineNumber);
    }

    private static Integer getKeyForValue(final Map<Integer, Integer> map, final Integer value) {
        for (final Map.Entry<Integer, Integer> entry : map.entrySet()) {
            if (Objects.equals(value, entry.getValue()))
                return entry.getKey();
        }
        return null;
    }

}
