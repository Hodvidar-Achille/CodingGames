package com.hodvidar.coderbyte.hard;

import java.util.regex.Pattern;
import java.util.stream.IntStream;

// https://coderbyte.com/editor/Bracket%20Combinations:Java
public class BracketCombinations {

    public static int bracketCombinations(final int num) {
        if (num <= 0) {
            return 1; // Base case: an empty string is a valid combination
        }

        return IntStream.range(0, num)
                .mapToObj(i -> bracketCombinations(i) * bracketCombinations(num - 1 - i))
                .reduce(0, Integer::sum);
    }
}
