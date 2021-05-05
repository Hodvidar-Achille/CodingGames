package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/brackets-extreme-edition By Hodvidar
 **/
class BracketsExtremeEdition {

    private static final char PARE_OPEN = '(';
    private static final char CROC_OPEN = '[';
    private static final char ACCO_OPEN = '{';
    private static final char PARE_CLOSE = ')';
    private static final char CROC_CLOSE = ']';
    private static final char ACCO_CLOSE = '}';

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final String expression = in.next();

        System.err.println("expression: " + expression);

        int numberOfPareOpenned = 0;
        int numberOfCrocOpenned = 0;
        int numberOfAccoOpenned = 0;

        boolean expressionOK = true;

        for (final char c : expression.toCharArray()) {
            switch (c) {
                case PARE_OPEN:
                    numberOfPareOpenned++;
                    break;
                case CROC_OPEN:
                    numberOfCrocOpenned++;
                    break;
                case ACCO_OPEN:
                    numberOfAccoOpenned++;
                    break;
                case PARE_CLOSE:
                    numberOfPareOpenned--;
                    break;
                case CROC_CLOSE:
                    numberOfCrocOpenned--;
                    break;
                case ACCO_CLOSE:
                    numberOfAccoOpenned--;
                    break;
                default:
                    // code block
            }

            if (numberOfPareOpenned < 0 || numberOfCrocOpenned < 0 || numberOfAccoOpenned < 0)
                break;
        }

        if (numberOfPareOpenned != 0 || numberOfCrocOpenned != 0 || numberOfAccoOpenned != 0)
            expressionOK = false;

        System.out.println(expressionOK);
        in.close();
    }
}