package com.hodvidar.adventofcode.y2020;

import java.util.Scanner;

public class _Day06_2 extends AbstractAdventOfCode {
    @Override
    protected int getDay() {
        return 6;
    }

    // not 19 (too low)
    // not 3243 or 3239 (too high)
    public static void main(String[] args) throws Exception {
        _Day06_2 me = new _Day06_2();
        int result = me.countUniqueLetterByGroup(me.getScanner());
        System.err.println("Expected '3158' - result='" + result + "'");
    }

    public int countUniqueLetterByGroup(Scanner sc) {
        int counter = 0;
        String line;
        String currentGroupLetters = "";
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            line = line.trim();
            currentGroupLetters += "#" +line;
            if (line.isBlank()) {
                int uniqueForGroup = countCommonLettersInGroup(currentGroupLetters);
                counter += uniqueForGroup;
                currentGroupLetters = "";
            }
        }

        return counter;
    }

    public int countCommonLettersInGroup(String groupAnswers) {
        int alphabetLength = 26;
        if(groupAnswers.charAt(0) == '#') {
            groupAnswers = groupAnswers.substring(1);
        }
        if(groupAnswers.charAt(groupAnswers.length()-1) == '#') {
            groupAnswers = groupAnswers.substring(0, groupAnswers.length()-1);
        }
        groupAnswers = groupAnswers.toLowerCase();
        String[] personsAnswers = groupAnswers.split("#");
        boolean[][] isItDuplicated = new boolean[personsAnswers.length][alphabetLength];
        int personAnswerCounter = 0;
        for (String  personAnswer : personsAnswers) {
            for(char letter : personAnswer.toCharArray()) {
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
        for(int i = 0; i < alphabetLength; i++) {
            boolean isAlwaysDuplicated = true;
            for (personAnswerCounter = 0; personAnswerCounter < personsAnswers.length; personAnswerCounter++) {
                if(!isItDuplicated[personAnswerCounter][i]) {
                    isAlwaysDuplicated = false;
                    break;
                }
            }
            if(isAlwaysDuplicated) {
                duplicatedLetterInAllPersonAnswerCounter += 1;
            }

        }

        return duplicatedLetterInAllPersonAnswerCounter;
    }

}