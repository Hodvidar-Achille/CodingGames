package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day02_Test extends AbstractTestForAdventOfCode {
    @Override
    protected int getDay() {
        return 2;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 3 | a | a | true",
            "1 | 3 | b | b | true",
            "1 | 3 | c | c | true",
            "1 | 3 | c | a | false",
            "1 | 3 | c | abcccefghc | false",
            "10 | 20 | a | abcccefghc | false",
            "10 | 20 | a | aaaaaaaaabbbbbbbbbbb | false",
            "10 | 20 | a | aaaaaaaaaaaaaaaaaaaaaaaaaabbbbbb | false",
            "10 | 20 | a | ababababababababababababab | true",
    })
    void shouldFindResultInSmallNumberPool(int min, int max, char letter, String password, boolean isValid) {
        boolean result = new _Day02().isValid(min, max, letter, password);
        assertThat(result).isEqualTo(isValid);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1-3 a: a = true",
            "1-3 b: b = true",
            "1-3 c: c = true",
            "1-3 c: a = false",
            "1-3 c: abcdecccabde = false",
            "10-20 a: abcccefghc = false",
            "10-20 a: aaaaaaaaabbbbbbbbbbb = false",
            "10-20 a: aaaaaaaaaaaaaaaaaaaaaaaaaabbbbbb = false",
            "10-20 a: ababababababababababababab = true"
    })
    void shouldFindResultInSmallNumberPool(String policy, boolean isValid) {
        boolean result = new _Day02().isValid(policy);
        assertThat(result).isEqualTo(isValid);
    }

}
