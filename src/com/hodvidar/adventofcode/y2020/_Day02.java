package com.hodvidar.adventofcode.y2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class _Day02 extends AbstractAdventOfCode {
    @Override
    protected int getDay() {
        return 2;
    }

    public static void main(String[] args) throws Exception {
        _Day02 me = new _Day02();
        int result = me.numberOfCorrect(me.getScanner());
        System.err.println("Expected '469' - result='" + result + "'");
    }

    public int numberOfCorrect(Scanner sc) {
        String line;
        int counter = 0;
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            if(isValid(line)) {
                counter++;
            }
        }
        return counter;
    }

    public boolean isValid(int min, int max, char letter, String password) {
        int letterCounter = 0;
        for(char c : password.toCharArray()) {
            if(Objects.equals(letter, c)) {
                letterCounter++;
                if(letterCounter > max) {
                    return false;
                }
            }
        }
        return letterCounter >= min;
    }

    /**
     * @param policyAndPassword ex : 1-3 a: abcde
     */
    public boolean isValid(String policyAndPassword) {
        String[] policyAndPasswordSplit = policyAndPassword.split(":");
        String policy = policyAndPasswordSplit[0].trim();
        String password = policyAndPasswordSplit[1].trim();

        String[] policySplit = policy.split(" ");
        String policyNumbers = policySplit[0];
        char policyLetter = policySplit[1].charAt(0);
        String[] policyNumbersSplit = policyNumbers.split("-");
        int min = Integer.parseInt(policyNumbersSplit[0]);
        int max = Integer.parseInt(policyNumbersSplit[1]);

        return isValid(min, max, policyLetter, password);
    }
}
