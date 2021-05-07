package com.hodvidar.hackerrank;

import com.hodvidar.utils.list.ListUtils;

import java.util.List;

/**
 * https://www.hackerrank.com/challenges/arrays-ds/problem
 */
public class ArrayReverse {

    public static int[] reverse(final int[] array) {
        return ListUtils.reverse(array);
    }

    public static List<Integer> reverse(final List<Integer> list) {
        return ListUtils.reverse(list);
    }
}
