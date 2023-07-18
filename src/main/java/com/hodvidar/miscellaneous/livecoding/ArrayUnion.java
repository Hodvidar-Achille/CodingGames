package com.hodvidar.miscellaneous.livecoding;

import com.hodvidar.utils.list.ListUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ArrayUnion {

    private ArrayUnion() {
        throw new IllegalStateException("Utility class");
    }

    // O(n+m) for n size of input1 and m size of input2
    public static int[] unionOfArraysSimple(final int[] input1, final int[] input2) {
        final List<Integer> result = new ArrayList<>();

        final Map<Integer, Integer> map1 = new HashMap<>();
        for (final int i1 : input1) {
            if (map1.containsKey(i1)) {
                map1.put(i1, map1.get(i1) + 1);
            } else {
                map1.put(i1, 1);
            }
        }
        for (final int i2 : input2) {
            if (map1.containsKey(i2)) {
                final int numberOfDuplicate = map1.get(i2);
                if (numberOfDuplicate <= 0) {
                    continue;
                }
                result.add(i2);
                map1.put(i2, numberOfDuplicate - 1);
            }
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

    /**
     * Another possible way, similar in complexity, but remove the use of the Map
     * int counter1 = 0;
     * int counter2 = 0;
     * List<Integer> result = new ArrayList<>();
     * while(counter1 >= input1.length OR counter2 >= input2.length) {
     * int valeur1 = input1[counter1];
     * int valeur2 = input2[counter2];
     * if(valeur1 == valeur2) {
     * result.add(valeur1);
     * counter1 += 1;
     * counter2 += 1;
     * } else if(valeur1 > valeur2) {
     * counter2 += 1;
     * } else {
     * counter1 +=1;
     * }
     * }
     * return result;
     **/

    // O(n*log2(m)) for n size of the smaller array and m size f the longer one
    public static int[] unionOfArraysFaster(final int[] input1, final int[] input2) {
        if (input1.length < input2.length) {
            return unionOfArrays(input1, input2);
        }
        return unionOfArrays(input2, input1);
    }

    private static int[] unionOfArrays(final int[] smallArray, final int[] longArray) {
        final List<Integer> result = new ArrayList<>();
        // using this as the low value in the binarySearch will slightly improve the perf.
        int previousCorrectIndex = 0;
        for (final int i : smallArray) {
            final int binarySearchResult = ListUtils.binarySearch(longArray, i, previousCorrectIndex);
            if (binarySearchResult != -1) {
                result.add(i);
                previousCorrectIndex = binarySearchResult;
            }
        }
        return result.stream().mapToInt(Integer::intValue).toArray();
    }

}