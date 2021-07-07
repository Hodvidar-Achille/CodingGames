package com.hodvidar.miscellaneous.livecoding;

import java.util.Stack;

// https://leetcode.com/problems/valid-parentheses/
public class ParenthesisAnalyser2 {

    public static boolean isValid(final String input) {
        if (input.length() % 2 != 0) {
            return false;
        }
        final Stack<Character> pastCharacters = new Stack<>();
        for (final char c : input.toCharArray()) {
            switch (c) {
                case '(':
                case '{':
                case '[':
                    pastCharacters.push(c);
                    break;
                case ')':
                    if (!isValid(pastCharacters, '(')) {
                        return false;
                    }
                    break;
                case ']':
                    if (!isValid(pastCharacters, '[')) {
                        return false;
                    }
                    break;
                case '}':
                    if (!isValid(pastCharacters, '{')) {
                        return false;
                    }
                    break;
            }
        }
        return pastCharacters.empty();
    }

    private static boolean isValid(final Stack<Character> pastCharacters,
                                   final char expectedCharacter) {
        if (pastCharacters.empty()) {
            return false;
        }
        return pastCharacters.pop().charValue() == expectedCharacter;
    }
}