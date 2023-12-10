package com.hodvidar.adventofcode.y2020;

import java.util.Objects;
import java.util.Scanner;

public class _Day02 extends AbstractAdventOfCode2020 {

    @Override
    public int getDay() {
        return 2;
    }

    @Override
    public int getResult(final Scanner sc) {
        return numberOfCorrect(sc);
    }

    public int numberOfCorrect(final Scanner sc) {
        String line;
        int counter = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            if (isValid(line)) {
                counter++;
            }
        }
        return counter;
    }

    public boolean isValid(final int min, final int max, final char letter, final String password) {
        int letterCounter = 0;
        for (final char c : password.toCharArray()) {
            if (Objects.equals(letter, c)) {
                letterCounter++;
                if (letterCounter > max) {
                    return false;
                }
            }
        }
        return letterCounter >= min;
    }

    /**
     * @param policyAndPassword ex : 1-3 a: abcde
     */
    public boolean isValid(final String policyAndPassword) {
        final String[] policyAndPasswordSplit = policyAndPassword.split(":");
        final String policy = policyAndPasswordSplit[0].trim();
        final String password = policyAndPasswordSplit[1].trim();

        final String[] policySplit = policy.split(" ");
        final String policyNumbers = policySplit[0];
        final char policyLetter = policySplit[1].charAt(0);
        final String[] policyNumbersSplit = policyNumbers.split("-");
        final int min = Integer.parseInt(policyNumbersSplit[0]);
        final int max = Integer.parseInt(policyNumbersSplit[1]);

        return isValid(min, max, policyLetter, password);
    }
}
