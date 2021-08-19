package com.hodvidar.leetcode.hard;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MedianOfTwoSortedArraysTest {

    @Test
    public void test1() {
        final int[] nums1 = new int[]{1, 3};
        final int[] nums2 = new int[]{2};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(2.0);
    }

    @Test
    public void test2() {
        final int[] nums1 = new int[]{1, 2};
        final int[] nums2 = new int[]{3, 4};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(2.5);
    }

    @Test
    public void test3() {
        final int[] nums1 = new int[]{0};
        final int[] nums2 = new int[]{0};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(0.0);
    }

    @Test
    public void test4() {
        final int[] nums1 = new int[]{};
        final int[] nums2 = new int[]{2};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(2.0);
    }

    @Test
    public void test5() {
        final int[] nums1 = new int[]{3};
        final int[] nums2 = new int[]{};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(3.0);
    }

    @Test
    public void test6() {
        final int[] nums1 = new int[]{3, 5, 6, 7};
        final int[] nums2 = new int[]{};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(5.5);
    }

    @Test
    public void test7() {
        final int[] nums1 = new int[]{3, 5, 6};
        final int[] nums2 = new int[]{};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(5);
    }

    @Test
    public void test8() {
        final int[] nums1 = new int[]{1, 2, 3, 4, 5};
        final int[] nums2 = new int[]{11, 12, 13, 14, 15};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(8.0);
    }

    @Test
    public void test9() {
        final int[] nums1 = new int[]{11, 12, 13, 14, 15};
        final int[] nums2 = new int[]{1, 2, 3, 4, 5};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(8.0);
    }

    @Test
    public void test10() {
        final int[] nums1 = new int[]{1, 5, 9, 13, 17};
        final int[] nums2 = new int[]{2, 6, 10, 14, 18};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(9.5);
    }

    @Test
    public void test11() {
        final int[] nums1 = new int[]{1, 5, 9, 13, 17};
        final int[] nums2 = new int[]{2, 6, 10, 14};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(9.0);
    }

    @Test
    public void test12() {
        final int[] nums1 = new int[]{0, 0, 0, 0, 0};
        final int[] nums2 = new int[]{-1, 0, 0, 0, 0, 0, 1};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(0.0);
    }

    @Test
    public void test13() {
        final int[] nums1 = new int[]{1, 3};
        final int[] nums2 = new int[]{2, 7};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(2.5);
    }

    @Test
    public void test14() {
        final int[] nums1 = new int[]{0, 0};
        final int[] nums2 = new int[]{0, 0};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(0.0);
    }

    @Test
    public void test15() {
        final int[] nums1 = new int[]{3};
        final int[] nums2 = new int[]{-2, -1};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(-1.0);
    }

    @Test
    public void test16() {
        final int[] nums1 = new int[]{2, 3};
        final int[] nums2 = new int[]{1};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(2.0);
    }

    @Test
    public void test17() {
        final int[] nums1 = new int[]{3};
        final int[] nums2 = new int[]{1, 2};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(2.0);
    }

    @Test
    public void test18() {
        final int[] nums1 = new int[]{4};
        final int[] nums2 = new int[]{1, 2, 3};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(2.5);
    }

    @Test
    public void test19() {
        final int[] nums1 = new int[]{4, 5, 6};
        final int[] nums2 = new int[]{1, 2, 3};
        assertThat(MedianOfTwoSortedArrays.findMedianSortedArrays(nums1, nums2)).isEqualTo(3.5);
    }
}
