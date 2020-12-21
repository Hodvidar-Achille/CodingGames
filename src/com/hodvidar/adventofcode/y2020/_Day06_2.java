package com.hodvidar.adventofcode.y2020;

import java.util.Scanner;

public class _Day06_2 extends AbstractAdventOfCode {
    @Override
    protected int getDay() {
        return 6;
    }

    // not 19 (too low), not 3243 or 3239 (too high)
    public static void main(String[] args) throws Exception {
        _Day06_2 me = new _Day06_2();
        int result = me.countUniqueLetterByGroup(me.getScanner());
        System.err.println("Expected '??' - result='" + result + "'");
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
        if(groupAnswers.charAt(0) == '#') {
            groupAnswers = groupAnswers.substring(1);
        }
        if(groupAnswers.charAt(groupAnswers.length()-1) == '#') {
            groupAnswers = groupAnswers.substring(0, groupAnswers.length()-1);
        }
        groupAnswers = groupAnswers.toLowerCase();
        String[] personsAnswers = groupAnswers.split("#");
        boolean[] isItDuplicated = new boolean[26];
        boolean[] isItThere = new boolean[26];
        int i = 0;
        for (String  personAnswer : personsAnswers) {
            for(char letter : personAnswer.toCharArray()) {
                int value = letter;
                value -= 97; // a become 0, z becomes 25
                if (value < 0 || value > 25) {
                    continue;
                }
                if(i == 0) {
                    isItThere[value] = true;
                } else if (i == 1) {
                    isItDuplicated[value] = isItThere[value] && true;
                } else {
                    isItDuplicated[value] = isItDuplicated[value] && true;
                }
            }
            i++;
        }
        if(personsAnswers.length == 1){
            isItDuplicated = isItThere;
        }

        int count = 0;
        for (i = 0; i < isItThere.length; i++) {
            if (isItDuplicated[i] == true) {
                count++;
            }
        }

        return count;
    }

}