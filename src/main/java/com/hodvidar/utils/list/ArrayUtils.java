package com.hodvidar.utils.list;

import java.util.Arrays;

public class ArrayUtils {

    private ArrayUtils() {
        // Nothing
    }

    public static void resetToFalse(final boolean[][][] triDimensionalArray) {
        for (final boolean[][] twoDimensionalArray : triDimensionalArray) {
            resetToFalse(twoDimensionalArray);
        }
    }

    public static void resetToFalse(final boolean[][] twoDimensionalArray) {
        for (final boolean[] oneDimensionalArray : twoDimensionalArray) {
            Arrays.fill(oneDimensionalArray, false);
        }
    }
}
