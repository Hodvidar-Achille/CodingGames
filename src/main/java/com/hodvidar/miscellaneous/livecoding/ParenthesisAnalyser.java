package com.hodvidar.miscellaneous.livecoding;

import java.util.Stack;

public class ParenthesisAnalyser {

    public static boolean isValid(final String input) {
        if (input == null || input.length() == 0) {
            return true;
        }
        int counter = 0;
        for (final char c : input.toCharArray()) {
            if (c == '(') {
                counter += 1;
            } else {
                counter -= 1;
                if (counter < 0) {
                    return false;
                }
            }
        }
        return counter == 0;
    }

    // without a stack
    public static int longestValidSubString(final String input) {
        if (input == null || input.length() < 2) {
            return 0;
        }
        int max = 0;
        int currentSize = 0;
        int counter = 0;
        for (final char c : input.toCharArray()) {
            if (c == ')' && counter == 0) {
                continue;
            } else if (c == ')') {
                counter -= 1;
                currentSize += 1;
                if (counter == 0) {
                    if (max < currentSize) {
                        max = currentSize;
                    }
                    currentSize = 0;
                }
            } else {
                counter += 1;
                currentSize += 1;
            }
        }
        if (max == 0 && counter > 0) {
            return currentSize - counter;
        }
        return max;
    }

    // With a stack
    public static int longestValidSubString2(final String input) {
        if (input == null || input.length() < 2) {
            return 0;
        }
        final Stack<Integer> stack = new Stack<Integer>();
        final int n = input.length();
        int max = 0;
        for (int i = 0; i < n; i++) {
            if (input.charAt(i) == '(') {
                stack.push(i);
            } else {
                if (stack.empty()) {

                } else {
                    final int value = stack.pop();
                    if (stack.isEmpty()) {
                        max = Math.max(max, (i - value) + 1);
                    } else {
                        max = Math.max(max, i - stack.peek());
                    }
                }
            }
        }
        return max;
    }

}
