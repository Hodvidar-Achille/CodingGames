package com.hodvidar.leetcode;

import java.util.HashMap;
import java.util.Map;

// https://leetcode.com/problems/two-sum/
public final class TwoSum {
    public int[] twoSum(int[] n, int t) {
        Map<Integer, Integer> m = new HashMap<>();
        for (int i = 0; i < n.length; i++) {
            int c = t - n[i];
            if (m.containsKey(c)) {
                return new int[]{m.get(c), i};
            }
            m.put(n[i], i);
        }
        throw new IllegalArgumentException("No solution");
    }
}