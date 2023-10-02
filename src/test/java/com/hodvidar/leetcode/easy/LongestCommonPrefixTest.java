package com.hodvidar.leetcode.easy;

import com.hodvidar.utils.regex.ArrayExtractor;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

class LongestCommonPrefixTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[flower,flow,flight] | fl",
            "[dog,racecar,car] | ''",
            "[, lol, anticonstitutionnellement | ''",
            "[, , ] | ''",
            "[anticonstitition,anticonstitition,anticonstitition] | anticonstitition"
    })
    void longestCommonPrefix(final String stringArrays, final String expected) {
        Assertions.assertThat(LongestCommonPrefix.longestCommonPrefix(ArrayExtractor.getArray(stringArrays)))
                .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[flower,flow,flight] | fl",
            "[dog,racecar,car] | ''",
            "[, lol, anticonstitutionnellement | ''",
            "[, , ] | ''",
            "[anticonstitition,anticonstitition,anticonstitition] | anticonstitition"
    })
    void longestCommonPrefixOptimized(final String stringArrays, final String expected) {
        Assertions.assertThat(LongestCommonPrefix.longestCommonPrefixOptimized(ArrayExtractor.getArray(stringArrays)))
                .isEqualTo(expected);
    }
}
