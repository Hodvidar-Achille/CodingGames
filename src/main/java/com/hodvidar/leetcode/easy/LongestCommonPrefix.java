package com.hodvidar.leetcode.easy;

import java.util.Arrays;
import java.util.stream.Collector;

/**
 *  https://leetcode.com/problems/longest-common-prefix/
 */
public class LongestCommonPrefix {

    // Runtime 6ms "Beats 19.66% of users with Java"
    public static String longestCommonPrefix(String[] strs) {
        char[][] arraysOfLetters = Arrays.stream(strs)
                .map(String::toCharArray)
                .toArray(char[][]::new);

        int smallestSize = Arrays.stream(strs)
                .mapToInt(String::length)
                .min()
                .orElse(0);

        if(smallestSize == 0) return "";
        int numberOfArrays = strs.length;

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < smallestSize; i++) {
            char currentChar = arraysOfLetters[0][i];
            for(int j = 1; j < numberOfArrays; j++) {
                if(currentChar != arraysOfLetters[j][i]) return sb.toString();
            }
            sb.append(currentChar);
        }
        return sb.toString();
    }

    // Runtime 7ms Beats "19.22% of users with Java"
    public static String longestCommonPrefixOptimized(String[] strs) {

        Collector<String, ?, char[][]> collector = Collector.of(
                () -> new int[]{Integer.MAX_VALUE}, // Initialize with a large value
                (acc, str) -> {
                    char[] charArray = str.toCharArray(); // Convert the string to a char array
                    acc[0] = Math.min(acc[0], charArray.length); // Calculate the minimum size
                },
                (acc1, acc2) -> {
                    acc1[0] = Math.min(acc1[0], acc2[0]); // Combine the minimum sizes
                    return acc1;
                },
                acc -> {
                    int smallestSize = acc[0]; // Retrieve the smallest size
                    return Arrays.stream(strs)
                            .map(s -> s.substring(0, smallestSize).toCharArray())
                            .toArray(char[][]::new); // Convert strings to char arrays of the smallest size
                }
        );


        char[][] arraysOfLetters = Arrays.stream(strs)
                .collect(collector);

        int smallestSize = arraysOfLetters[0].length;

        if(smallestSize == 0) return "";
        int numberOfArrays = strs.length;

        StringBuilder sb = new StringBuilder();
        for(int i = 0; i < smallestSize; i++) {
            char currentChar = arraysOfLetters[0][i];
            for(int j = 1; j < numberOfArrays; j++) {
                if(currentChar != arraysOfLetters[j][i]) return sb.toString();
            }
            sb.append(currentChar);
        }
        return sb.toString();
    }
}
