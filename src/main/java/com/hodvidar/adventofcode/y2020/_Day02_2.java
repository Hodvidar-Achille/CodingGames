package com.hodvidar.adventofcode.y2020;

import java.util.Objects;

public class _Day02_2 extends _Day02 {

    @Override
    public boolean isValid(final int firstNumber, final int secondNumber, final char letter, final String password) {
        final char firstLetter = password.charAt(firstNumber - 1);
        final char secondLetter = password.charAt(secondNumber - 1);
        if (Objects.equals(letter, firstLetter)) {
            return !Objects.equals(letter, secondLetter);
        } else {
            return Objects.equals(letter, secondLetter);
        }
    }

}
