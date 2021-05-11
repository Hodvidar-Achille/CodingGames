package com.hodvidar.formation.java11;

import java.util.Arrays;

import static java.util.stream.Collectors.joining;


// https://codingdojo.org/kata/RPN/
public class RPNCalculator {

    private static final String PLUS = "+";
    private static final String MINUS = "-";
    private static final String MULTIPLE = "*";
    private static final String DIVIDE = "/";
    private static final String SQRT = "SQRT";
    private static final String MAX = "MAX";

    public static int calculate(final String s) {
        if (!s.contains(" ")) {
            return Integer.parseInt(s);
        }

        final var elements = s.split(" ");

        if (s.contains(MAX) && nextOperatorIsMax(elements)) {
            final var maxIndex = isThereAnotherMaxFollowedByAnOperator(elements, 0);
            if (maxIndex == -1) {
                return handleMax(elements);
            }
            return handleMaxWithOperation(elements, Integer.parseInt(elements[0]), 1, maxIndex);

        }

        if (s.contains(SQRT) && isNumeric(elements[0]) && elements[1].equals(SQRT)) {
            return handleSqrt(elements);
        }

        return handleOperators(elements);
    }

    private static int handleMax(final String[] elements) {
        int max = 0;
        for (int i = 0; i < elements.length; i++) {
            if (MAX.equals(elements[i])) {
                if (i == elements.length - 1) {
                    return max;
                }
                final var nextMaxIndex = isThereAnotherMaxFollowedByAnOperator(elements, i);
                if (nextMaxIndex != -1) {
                    return handleMaxWithOperation(elements, max, i, nextMaxIndex);
                }
                final var remainingExpression = Arrays.stream(elements).skip(i + 1).collect(joining(" "));
                return calculate(max + " " + remainingExpression);
                // return max;
            }
            final var num = Integer.parseInt(elements[i]);
            if (num > max) {
                max = num;
            }
        }
        throw new IllegalStateException("Should not happen");
    }

    private static int handleMaxWithOperation(final String[] elements, final int max, final int i, final int nextMaxIndex) {
        final var previousMax = max;
        final var lengthSubArray = nextMaxIndex - (i);
        final String[] subArrayOfElements = new String[lengthSubArray];
        System.arraycopy(elements, i + 1, subArrayOfElements, 0, subArrayOfElements.length);
        final var secondMax = handleMax(subArrayOfElements);
        final var remainingExpression = Arrays.stream(elements).skip(i + lengthSubArray + 1).collect(joining(" "));
        return calculate(previousMax + " " + secondMax + " " + remainingExpression);
    }

    /**
     * -1
     *
     * @param elements
     * @param currentIndex
     * @return -1 if false, the index of the next MAX otherwise
     */
    private static int isThereAnotherMaxFollowedByAnOperator(final String[] elements, final int currentIndex) {
        for (int i = currentIndex + 1; i < elements.length - 1; i++) {
            if (isOperationOperator(elements[i])) {
                return -1;
            }
            if (MAX.equals(elements[i]) && isOperationOperator(elements[i + 1])) {
                return i;
            }
            if (MAX.equals(elements[i])) {
                return -1;
            }
        }
        return -1;
    }

    private static int handleSqrt(final String[] elements) {
        final int result;
        final var i = Integer.parseInt(elements[0]);
        result = (int) Math.sqrt(i);
        if (elements.length <= 2) {
            return result;
        }
        final var remainingExpression = Arrays.stream(elements).skip(2).collect(joining(" "));
        return calculate(result + " " + remainingExpression);
    }

    private static int handleOperators(final String[] elements) {
        final int result;
        final var i = Integer.parseInt(elements[0]);
        final var ii = Integer.parseInt(elements[1]);
        final var operator = elements[2];

        switch (operator) {
            case PLUS:
                result = i + ii;
                break;
            case MINUS:
                result = i - ii;
                break;
            case MULTIPLE:
                result = i * ii;
                break;
            case DIVIDE:
                result = i / ii;
                break;
            default:
                throw new UnsupportedOperationException(operator);
        }
        if (elements.length > 3) {
            final var remainingExpression = Arrays.stream(elements).skip(3).collect(joining(" "));
            return calculate(result + " " + remainingExpression);
        }
        return result;
    }

    private static boolean isOperationOperator(final String operator) {
        switch (operator) {
            case PLUS:
            case MINUS:
            case DIVIDE:
            case MULTIPLE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isNumeric(final String strNum) {
        if (strNum == null) {
            return false;
        }
        try {
            final int d = Integer.parseInt(strNum);
        } catch (final NumberFormatException nfe) {
            return false;
        }
        return true;
    }

    public static boolean nextOperatorIsMax(final String[] elements) {
        for (final String e : elements) {
            if (isOperationOperator(e)) return false;
            if (SQRT.equals(e)) return false;
            if (MAX.equals(e)) return true;
        }
        return false;
    }
}
