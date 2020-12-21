package com.hodvidar.adventofcode.y2020;

import java.util.Scanner;

public class _Day06 extends AbstractAdventOfCode {
    @Override
    protected int getDay() {
        return 6;
    }

    // not 12038 (too high), not 6288 (too low)
    public static void main(String[] args) throws Exception {
        _Day06 me = new _Day06();
        int result = me.countUniqueLetterByGroup(me.getScanner());
        System.err.println("Expected '6297' - result='" + result + "'");
    }

    public int countUniqueLetterByGroup(Scanner sc) {
        int counter = 0;
        String line;
        String currentGroupLetters = "";
        do {
            line = sc.nextLine();
            line = line.trim();
            currentGroupLetters += line;
            if(line.isBlank()) {
                int uniqueForGroup = countUniqueCharacters(currentGroupLetters);
                counter += uniqueForGroup;
                currentGroupLetters = "";
            }
        } while (sc.hasNextLine());

        return counter;
    }

    public int countUniqueCharacters(String input) {
        input = input.toLowerCase();
        boolean[] isItThere = new boolean[26];
        for (int i = 0; i < input.length(); i++) {
            char c = input.charAt(i);
            int value = c;
            value -= 97; // a become 0, z becomes 25
            if(value < 0 || value > 25) {
                continue;
            }
            isItThere[value] = true;
        }

        int count = 0;
        for (int i = 0; i < isItThere.length; i++) {
            if (isItThere[i] == true){
                count++;
            }
        }

        return count;
    }

}
