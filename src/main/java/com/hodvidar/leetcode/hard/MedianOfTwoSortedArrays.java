package com.hodvidar.leetcode.hard;

// https://leetcode.com/problems/median-of-two-sorted-arrays/
/*
Runtime: 2 ms, faster than 99.88% of Java online submissions
for Median of Two Sorted Arrays.
Memory Usage: 39.7 MB, less than 98.69% of Java online submissions
for Median of Two Sorted Arrays.
 */
public class MedianOfTwoSortedArrays {


    public static double findMedianSortedArrays(final int[] nums1, final int[] nums2) {
        final int n = nums1.length;
        final int m = nums2.length;
        if (n == 0) {
            return findMedianSortedArrays(nums2);
        }
        if (m == 0) {
            return findMedianSortedArrays(nums1);
        }
        if (n == 1 && m == 1) {
            return (nums1[0] + nums2[0]) / 2.0;
        }

        // Could be fully removed but is faster than findMedianInSortedArraysComplexCase
        // ----
        final int lastOfn1 = nums1[n - 1];
        final int firstOfn2 = nums2[0];
        if (lastOfn1 < firstOfn2) {
            return findMedianInSortedArraysNonOverlapping(nums1, nums2);
        }
        final int lastOfn2 = nums2[m - 1];
        final int firstOfn1 = nums1[0];
        if (lastOfn2 < firstOfn1) {
            return findMedianInSortedArraysNonOverlapping(nums2, nums1);
        }
        // ----

        return findMedianInSortedArraysComplexCase(nums1, nums2);
    }

    private static Double findMedianInSortedArraysNonOverlapping(final int[] numsMin,
                                                                 final int[] numsMax) {
        final int n = numsMin.length;
        final int m = numsMax.length;
        if (n == m) {
            // n+m is always odd here
            return (numsMin[n - 1] + numsMax[0]) / 2.0;
        } else if (n < m) {
            return findMedianSortedArrays(numsMax, m - n, false);
        } else {
            return findMedianSortedArrays(numsMin, n - m, true);
        }
    }

    private static double findMedianInSortedArraysComplexCase(final int[] nums1,
                                                              final int[] nums2) {
        final int n = nums1.length;
        final int m = nums2.length;
        int i = 0;
        int j = 0;
        int counter = 0;
        final int half = (n + m) / 2;
        int currentNumber;
        if (nums1[0] < nums2[0]) {
            currentNumber = nums1[i];
            i++;
            counter++;
        } else {
            currentNumber = nums2[j];
            j++;
            counter++;
        }
        boolean maxNums1Reached = false;
        boolean maxNums2Reached = false;
        while (true) {
            final int previousNumber = currentNumber;
            if (i == n) {
                i--;
                maxNums1Reached = true;
            }
            if (j == m) {
                j--;
                maxNums2Reached = true;
            }
            final int n1 = nums1[i];
            final int n2 = nums2[j];
            if (n1 == n2) {
                if ((i < n - 1 && !maxNums1Reached) || maxNums2Reached) {
                    currentNumber = n1;
                    i++;
                    counter++;
                } else {
                    currentNumber = n2;
                    j++;
                    counter++;
                }
            } else if ((n1 < n2 && !maxNums1Reached) || maxNums2Reached) {
                currentNumber = n1;
                i++;
                counter++;
            } else {
                currentNumber = n2;
                j++;
                counter++;
            }
            if (counter > half) {
                if ((n + m) % 2 == 0) {
                    return (previousNumber + currentNumber) / 2.0;
                } else {
                    return currentNumber;
                }
            }
        }
    }

    private static double findMedianSortedArrays(final int[] nums) {
        return findMedianSortedArrays(nums, nums.length, false);
    }

    private static double findMedianSortedArrays(final int[] nums, final int length, final boolean isLower) {
        if (nums.length == 1) {
            return nums[0];
        }
        int half = (length / 2);
        if (isLower) {
            half = (nums.length - 1) - half;
        }
        if (length % 2 == 0) {
            if (isLower) {
                return (nums[half + 1] + nums[half]) / 2.0;
            } else {
                return (nums[half - 1] + nums[half]) / 2.0;
            }
        } else {
            return nums[half];
        }
    }
}
