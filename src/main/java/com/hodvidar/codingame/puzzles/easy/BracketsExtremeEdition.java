package com.hodvidar.codingame.puzzles.easy;

import java.util.ArrayDeque;
import java.util.Deque;
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

        final boolean expressionOK = isExpressionOK(expression);

        System.out.println(expressionOK);
        in.close();
    }

    public static boolean isExpressionOK(final String expression) {
        if (expression == null) {
            return true;
        }
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
                    throw new IllegalArgumentException("Only brackets are expected");
            }

            if (numberOfPareOpenned < 0 || numberOfCrocOpenned < 0 || numberOfAccoOpenned < 0)
                break;
        }

        if (numberOfPareOpenned != 0 || numberOfCrocOpenned != 0 || numberOfAccoOpenned != 0)
            expressionOK = false;
        return expressionOK;
    }

    public static boolean isExpressionOK2(final String expression) {
        if (expression == null) {
            return true;
        }
        final Deque<Character> stack = new ArrayDeque<Character>();
        for (final char c : expression.toCharArray()) {
            if (c == PARE_OPEN || c == CROC_OPEN || c == ACCO_OPEN) {
                stack.push(c);
                continue;
            }
            if (stack.isEmpty()) {
                return false;
            }
            final char check;
            switch (c) {
                case PARE_CLOSE:
                    check = stack.pop();
                    if (check == '{' || check == '[')
                        return false;
                    break;
                case ACCO_CLOSE:
                    check = stack.pop();
                    if (check == '(' || check == '[')
                        return false;
                    break;
                case CROC_CLOSE:
                    check = stack.pop();
                    if (check == '(' || check == '{')
                        return false;
                    break;
            }
        }
        return stack.isEmpty();
    }
}
