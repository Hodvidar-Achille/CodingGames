package com.hodvidar.adventofcode.y2020;

import com.hodvidar.adventofcode.AbstractAdventOfCode;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class _Day06 extends AbstractAdventOfCode2020 {

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
    public int getDay() {
        return 6;
    }

    @Override
    public int getResult(final Scanner sc) throws FileNotFoundException {
        return countUniqueLetterByGroup(sc);
    }

}
