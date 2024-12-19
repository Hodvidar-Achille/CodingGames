package com.hodvidar.formation.algorithms;

public class TwoPointers {

    public static int[] reverseIntArray(final int[] array) {
        if (array == null || array.length == 0)
            return array;

        int indexStart = 0;
        int indexEnd = array.length - 1;
        int temp;
        while (indexStart < indexEnd) {
            temp = array[indexStart];
            array[indexStart] = array[indexEnd];
            array[indexEnd] = temp;
            indexStart++;
            indexEnd--;
        }
        return array;
    }


    // Really similar tor LongestPalindromicSubstring.isPalindrome
    public static boolean isPalindrome(final String s) {
        if (s == null || s.length() == 0)
            return false;
        if (s.length() == 1)
            return true;
        final char[] c = s.toCharArray();
        int indexStart = 0;
        int indexEnd = c.length - 1;
        while (indexStart < indexEnd) {
            if (c[indexStart] != c[indexEnd]) return false;
            indexStart++;
            indexEnd--;
        }
        return true;
    }


    // TODO find Nth element in a Linked List (of Node objects)
}
