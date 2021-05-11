package com.hodvidar.adventofcode.y2020;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class _Day06_2 extends AbstractAdventOfCode {
    // not 19 (too low)
    // not 3243 or 3239 (too high)
    public static void main(final String[] args) throws Exception {
        final _Day06_2 me = new _Day06_2();
        final int result = me.getResult(me.getScanner());
        System.err.println("Expected '3158' - result='" + result + "'");
    }

    public static int countUniqueLetterByGroup(final Scanner sc) {
        int counter = 0;
        String line;
        String currentGroupLetters = "";
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            line = line.trim();
            currentGroupLetters += "#" + line;
            if (line.isBlank()) {
                final int uniqueForGroup = countCommonLettersInGroup(currentGroupLetters);
                counter += uniqueForGroup;
                currentGroupLetters = "";
            }
        }

        return counter;
    }

    public static int countCommonLettersInGroup(String groupAnswers) {
        final int alphabetLength = 26;
        if (groupAnswers.charAt(0) == '#') {
            groupAnswers = groupAnswers.substring(1);
        }
        if (groupAnswers.charAt(groupAnswers.length() - 1) == '#') {
            groupAnswers = groupAnswers.substring(0, groupAnswers.length() - 1);
        }
        groupAnswers = groupAnswers.toLowerCase();
        final String[] personsAnswers = groupAnswers.split("#");
        final boolean[][] isItDuplicated = new boolean[personsAnswers.length][alphabetLength];
        int personAnswerCounter = 0;
        for (final String personAnswer : personsAnswers) {
            for (final char letter : personAnswer.toCharArray()) {
                int value = letter;
                value -= 97; // a become 0, z becomes 25
                if (value < 0 || value >= alphabetLength) {
                    continue;
                }
                isItDuplicated[personAnswerCounter][value] = true;
            }
            personAnswerCounter++;
        }


        int duplicatedLetterInAllPersonAnswerCounter = 0;
        for (int i = 0; i < alphabetLength; i++) {
            boolean isAlwaysDuplicated = true;
            for (personAnswerCounter = 0; personAnswerCounter < personsAnswers.length; personAnswerCounter++) {
                if (!isItDuplicated[personAnswerCounter][i]) {
                    isAlwaysDuplicated = false;
                    break;
                }
            }
            if (isAlwaysDuplicated) {
                duplicatedLetterInAllPersonAnswerCounter += 1;
            }

        }

        return duplicatedLetterInAllPersonAnswerCounter;
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