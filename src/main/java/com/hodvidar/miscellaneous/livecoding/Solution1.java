package com.hodvidar.miscellaneous.livecoding;

public class Solution1 {

    private Solution1() {
    }

    public static String getResult(final String input) {
        final int[] occurrences = new int[26];
        for (final char ch : input.toCharArray()) {
            occurrences[ch - 'a']++;
        }

        char best_char = 'a';
        int best_res = occurrences[0];

        for (int i = 1; i < 26; i++) {
            if (occurrences[i] > best_res) {
                best_char = (char) ((int) 'a' + i);
                best_res = occurrences[i];
            }
        }

        return Character.toString(best_char);
    }
}
