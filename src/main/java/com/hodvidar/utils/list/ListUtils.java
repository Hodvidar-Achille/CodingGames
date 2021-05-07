package com.hodvidar.utils.list;

import java.util.ArrayList;
import java.util.List;

public class ListUtils {

    public static int[] reverse(final int[] array) {
        if (null == array || array.length < 2) {
            return array;
        }
        int temp;
        for (int i = 0; i < array.length / 2; i++) {
            temp = array[array.length - 1 - i];
            array[array.length - 1 - i] = array[i];
            array[i] = temp;
        }
        return array;
    }

    public static List<Integer> reverse(final List<Integer> list) {
        return arrayToList(reverse(listToArray(list)));
    }

    public static List<Integer> arrayToList(int[] array) {
        if (null == array) {
            return null;
        }
        final List<Integer> intList = new ArrayList<>(array.length);
        for (int i : array) {
            intList.add(i);
        }
        return intList;
    }

    public static List<List<Integer>> array2DToList(int[][] array2D) {
        if (null == array2D) {
            return null;
        }
        final List<List<Integer>> intList = new ArrayList<>(array2D.length);
        for (int[] array : array2D) {
           intList.add(arrayToList(array));
        }
        return intList;
    }

    public static int[] listToArray(List<Integer> list) {
        if (null == list) {
            return null;
        }
        return list.stream().mapToInt(Integer::intValue).toArray();
    }

    public static int[][] listToArray2D(List<List<Integer>> list) {
        if (null == list) {
            return null;
        }
        int[][] array2D = new int[list.size()][];
        for(int i = 0; i < list.size(); i++) {
            List<Integer> sublist = list.get(i);
            array2D[i] = listToArray(sublist);
        }
        return array2D;
    }
}
