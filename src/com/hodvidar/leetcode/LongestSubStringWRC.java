package com.hodvidar.leetcode;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

// https://leetcode.com/problems/longest-substring-without-repeating-characters/
public final class LongestSubStringWRC {
    /**
     * O(2n)
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring(String s) {
        if (s == null || s.isEmpty())
            return 0;
        if (s.length() == 1)
            return 1;

        int n = s.length();
        Set<Character> set = new HashSet<>();
        int max = 0;
        int i = 0;
        int j = 0;
        while (i < n && j < n) {
            // Examinating range [i, j)
            if (!set.contains(s.charAt(j))) {
                set.add(s.charAt(j));
                j++;
                max = Math.max(max, j - i);
            } else {
                set.remove(s.charAt(i));
                i++;
            }
        }
        return max;
    }

    /**
     * O(n)
     *
     * @param s
     * @return
     */
    public int lengthOfLongestSubstring_Faster(String s) {
        if (s == null || s.isEmpty())
            return 0;
        if (s.length() == 1)
            return 1;

        int n = s.length();
        int max = 0;
        Map<Character, Integer> map = new HashMap<>(); // current index of character
        // try to extend the range [i, j]
        for (int j = 0, i = 0; j < n; j++) {
            if (map.containsKey(s.charAt(j))) {
                i = Math.max(map.get(s.charAt(j)), i);
            }
            max = Math.max(max, j - i + 1);
            map.put(s.charAt(j), j + 1);
        }
        return max;
    }
}
