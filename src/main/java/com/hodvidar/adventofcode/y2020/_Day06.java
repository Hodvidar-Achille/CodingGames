package com.hodvidar.adventofcode.y2020;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class _Day06 extends AbstractAdventOfCode {
    // not 12038 (too high), not 6288 (too low)
    public static void main(final String[] args) throws Exception {
        final _Day06 me = new _Day06();
        final int result = me.getResult(me.getScanner());
        System.err.println("Expected '6297' - result='" + result + "'");
    }

    public static int countUniqueLetterByGroup(final Scanner sc) {
        int counter = 0;
        String line;
        String currentGroupLetters = "";
        do {
            line = sc.nextLine();
            line = line.trim();
            currentGroupLetters += line;
            if (line.isBlank()) {
                final int uniqueForGroup = countUniqueCharacters(currentGroupLetters);
                counter += uniqueForGroup;
                currentGroupLetters = "";
            }
        } while (sc.hasNextLine());

        return counter;
    }

    public static int countUniqueCharacters(String input) {
        input = input.toLowerCase();
        final boolean[] isItThere = new boolean[26];
        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            int value = c;
            value -= 97; // a become 0, z becomes 25
            if (value < 0 || value > 25) {
                continue;
            }
            isItThere[value] = true;
        }

        int count = 0;
        for (int i = 0; i < isItThere.length; i++) {
            if (isItThere[i] == true) {
                count++;
            }
        }

        return count;
    }

    @Override
    protected int getDay() {
        return 6;
    }

    @Override
    protected int getResult(final Scanner sc) throws FileNotFoundException {
        return countUniqueLetterByGroup(sc);
    }

}
