package com.hodvidar.leetcode.medium;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * https://leetcode.com/problems/letter-combinations-of-a-phone-number/
 */
public class LetterCombinationsOfPhoneNumber {

    private static final Map<Character, String> phone = new HashMap<>() {{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};

    public static List<String> letterCombinations(final String digits) {
        if (digits.length() == 0) {
            return new ArrayList<>();
        }

        final List<String> result = new ArrayList<>();
        backtrack("", digits, result);
        return result;
    }

    private static void backtrack(final String combination,
                                  final String nextDigits,
                                  final List<String> result) {
        if (nextDigits.length() == 0) {
            result.add(combination);
            return;
        }

        final char digit = nextDigits.charAt(0);
        final String letters = phone.get(digit);
        for (int i = 0; i < letters.length(); i++) {
            final String letter = phone.get(digit).substring(i, i + 1);
            backtrack(combination + letter, nextDigits.substring(1), result);
        }
    }
}
