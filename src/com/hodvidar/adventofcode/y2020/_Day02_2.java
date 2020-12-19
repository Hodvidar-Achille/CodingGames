package com.hodvidar.adventofcode.y2020;

import java.util.Objects;
import java.util.Scanner;

public class _Day02_2 extends  _Day02{
    @Override
    protected int getDay() {
        return 2;
    }

    public static void main(String[] args) throws Exception {
        _Day02_2 me = new _Day02_2();
        int result = me.numberOfCorrect(me.getScanner());
        System.err.println("Expected '267' - result='" + result + "'");
    }

    @Override
    public boolean isValid(int firstNumber, int secondNumber, char letter, String password) {
        char firstLetter = password.charAt(firstNumber-1);
        char secondLetter = password.charAt(secondNumber-1);
        if(Objects.equals(letter, firstLetter)) {
            return !Objects.equals(letter, secondLetter);
        } else {
            return Objects.equals(letter, secondLetter);
        }
    }

}
