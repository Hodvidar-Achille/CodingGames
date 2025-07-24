package com.hodvidar.leetcode.medium;

import java.util.*;
import java.util.stream.Collectors;

/**
 * https://leetcode.com/problems/3sum/
 * <p>
 * Start: 12:30
 * Finished: 12:57 OK but too slow.
 * Finished: 13:30 done but read solution.
 */
public class ThreeSum {

    /**
     * Given an integer array nums,
     * return all the triplets [nums[i], nums[j], nums[k]]
     * such that i != j, i != k, and j != k,
     * and nums[i] + nums[j]+ nums[k] == 0.
     * <p>
     * Notice that the solution set must not contain duplicate triplets.
     */
    // 0(n^3) ~
    public static List<List<Integer>> threeSum(final int[] nums) {
        final Map<String, List<Integer>> solutions = new LinkedHashMap<>();
        for (int i = 0; i < nums.length; i++) {
            for (int j = i + 1; j < nums.length; j++) {
                for (int k = j + 1; k < nums.length; k++) {
                    checkAndAdd(solutions, nums[i], nums[j], nums[k]);
                }
            }
        }
        return new ArrayList<>(solutions.values());
    }

    public static List<List<Integer>> threeSumFaster(final int[] nums) {
        final Map<String, List<Integer>> solutions = new LinkedHashMap<>();
        Arrays.sort(nums);
        for (int i = 0; i < nums.length; i++) {
            final Map<Integer, Integer> map = new HashMap<>();
            final int targetSum = -nums[i];
            for (int j = 0; j < nums.length && i != j; j++) {

                if (map.containsKey(nums[j])) {
                    final List<Integer> s = new ArrayList<>();
                    s.add(nums[i]);
                    s.add(nums[map.get(nums[j])]);
                    s.add(nums[j]);
                    final String key = getSignature(s);
                    solutions.put(key, s);
                } else {
                    map.put(targetSum - nums[j], j);
                }
            }
        }
        return new ArrayList<>(solutions.values());
    }

    public static List<List<Integer>> threeSumFaster2(final int[] nums) {
        Arrays.sort(nums);
        final List<List<Integer>> li = new LinkedList<>();
        for (int i = 0; i < nums.length - 2; i++) {
            if (i == 0 || (i > 0 && nums[i] != nums[i - 1])) {
                int low = i + 1;
                int high = nums.length - 1;
                final int sum = -nums[i];
                while (low < high) {
                    if (nums[low] + nums[high] == sum) {
                        li.add(Arrays.asList(nums[i], nums[low], nums[high]));
                        while (low < high && nums[low] == nums[low + 1]) low++;
                        while (low < high && nums[high] == nums[high - 1]) high--;
                        low++;
                        high--;
                    } else if (nums[low] + nums[high] > sum) {
                        high--;
                    } else {
                        low++;
                    }
                }
            }
        }
        return li;
    }

    private static void checkAndAdd(final Map<String, List<Integer>> solutions, final int ni, final int nj, final int nk) {
        if (ni + nj + nk == 0) {
            List<Integer> s = new ArrayList<>(3);
            s.add(ni);
            s.add(nj);
            s.add(nk);
            s = s.stream().sorted().collect(Collectors.toList());
            final String key = getSignature(s);
            solutions.put(key, s);
        }
    }

    private static String getSignature(final List<Integer> s) {
        final StringBuilder sb = new StringBuilder();
        for (final Integer i : s) {
            sb.append(i);
        }
        return sb.toString();
    }
}
