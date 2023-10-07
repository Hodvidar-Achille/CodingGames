package com.hodvidar.leetcode.medium;

public class LongestPalindromicSubstring {


    // Complexity O(n^3), too slow for really long strings
    public static String longestPalindrome(final String s) {
        if (s == null || s.length() == 0)
            return "";
        if (s.length() == 1)
            return s;
        if (s.length() == 2) {
            return (s.charAt(0) == s.charAt(1)) ? s : s.substring(0, 1);
        }
        for (int i = s.length(); i >= 1; i--) {
            for (int j = 0; j <= s.length() - i; j++) {
                final String sub = s.substring(j, j + i);
                if (isPalindrome(sub)) return sub;
            }
        }
        return "";
    }

    public static boolean isPalindrome(final String s) {
        if (s == null || s.length() == 0)
            return false;
        if (s.length() == 1)
            return true;
        final char[] c = s.toCharArray();
        final int length = s.length();
        for (int i = 0; i < length / 2; i++) {
            if (c[i] != c[length - 1 - i]) return false;
        }
        return true;
    }


    // Complexity O(n^2)
    public static String longestPalindromeBetter(String s) {
        return ""; // TODO
    }

    // Complexity O(n), but not mine "Manacher's algorithm"
    public static String longestPalindromeBest(String s) {
        if (s == null || s.length() == 0) {
            return "";
        }

        // Preprocess the string to handle even-length palindromes
        StringBuilder processed = new StringBuilder("#");
        for (char c : s.toCharArray()) {
            processed.append(c).append("#");
        }

        int n = processed.length();
        int[] P = new int[n]; // P[i] stores the radius of the palindrome centered at i
        int center = 0; // Center of the rightmost palindrome
        int right = 0; // Right boundary of the rightmost palindrome

        int maxLen = 0; // Length of the longest palindrome found
        int maxCenter = 0; // Center of the longest palindrome found

        for (int i = 0; i < n; i++) {
            // Calculate the mirror position of i with respect to the center
            int mirror = 2 * center - i;

            // Check if the current index i is within the right boundary of the rightmost palindrome
            if (i < right) {
                P[i] = Math.min(right - i, P[mirror]);
            }

            // Attempt to expand the palindrome centered at i
            int leftBound = i - (P[i] + 1);
            int rightBound = i + (P[i] + 1);

            while (leftBound >= 0 && rightBound < n && processed.charAt(leftBound) == processed.charAt(rightBound)) {
                P[i]++;
                leftBound--;
                rightBound++;
            }

            // If the expanded palindrome is rightmost, update center and right
            if (i + P[i] > right) {
                center = i;
                right = i + P[i];
            }

            // Update the longest palindrome found so far
            if (P[i] > maxLen) {
                maxLen = P[i];
                maxCenter = i;
            }
        }

        // Extract the longest palindrome from the processed string
        int start = (maxCenter - maxLen) / 2;
        int end = start + maxLen;
        return s.substring(start, end);
    }
}
