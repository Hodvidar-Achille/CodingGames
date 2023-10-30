package com.hodvidar.leetcode.medium;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.com/problems/4sum/
 */
public class FourSum {

    public static List<List<Integer>> fourSum(final int[] nums, final int target) {
        Arrays.sort(nums);
        final List<List<Integer>> result = new ArrayList<>();
        // Handle edge case
        if (nums.length < 4) {
            return result;
        }
        if (Arrays.stream(nums).allMatch(n -> n > 0) && target <= 0) {
            return result;
        }
        searchForElements1and2and3and4(nums, target, result);
        return result;
    }

    private static void searchForElements1and2and3and4(final int[] numsSorted,
                                                       final int target,
                                                       final List<List<Integer>> result) {
        for (int i = 0; i < numsSorted.length - 3; i++) {
            // Skip duplicate elements
            if (i > 0 && numsSorted[i] == numsSorted[i - 1]) {
                continue;
            }
            searchForElements2and3and4(numsSorted, target, result, i);
        }
    }

    private static void searchForElements2and3and4(final int[] numsSorted,
                                                   final int target,
                                                   final List<List<Integer>> result,
                                                   final int i) {
        for (int j = i + 1; j < numsSorted.length - 2; j++) {
            // Skip duplicate elements
            if (j > i + 1 && numsSorted[j] == numsSorted[j - 1]) {
                continue;
            }
            searchForElements3and4(numsSorted, target, result, i, j);
        }
    }

    private static void searchForElements3and4(final int[] numsSorted,
                                               final int target,
                                               final List<List<Integer>> result,
                                               final int i, final int j) {
        int left = j + 1;
        int right = numsSorted.length - 1;
        while (left < right) {
            final int sum = numsSorted[i] + numsSorted[j] + numsSorted[left] + numsSorted[right];
            if (sum == target) {
                result.add(Arrays.asList(numsSorted[i], numsSorted[j], numsSorted[left], numsSorted[right]));

                // Skip duplicate elements
                while (left < right && numsSorted[left] == numsSorted[left + 1]) left++;
                while (left < right && numsSorted[right] == numsSorted[right - 1]) right--;

                left++;
                right--;
            } else if (sum < target) {
                left++;
            } else { // sum > target
                right--;
            }
        }
    }

}
