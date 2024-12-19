package com.hodvidar.leetcode.easy;

/**
 * https://leetcode.com/problems/palindrome-number/
 */
public class IsIntegerPalindrome {

    public static boolean isPalindrome(final int x) {
        if(x < 0) return false;
        if(x < 10) return true;
        final String s = "" + x;
        final int length = s.length();
        final int maxIndex = length - 1;
        final char[] c = s.toCharArray();
        for(int i = 0; i < length / 2; i++) {
            if(c[i] != c[maxIndex - i]) return false;
        }
        return true;
    }
}
