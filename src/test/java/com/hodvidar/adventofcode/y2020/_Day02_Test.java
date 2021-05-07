package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day02_Test extends AbstractTestForAdventOfCode {

    public _Day02_Test() {
        super(new _Day02());
    }

    @Override
    protected int getExpectedResult() {
        return 469;
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
    void shouldFindResultInSmallNumberPool(final int min, final int max, final char letter, final String password, final boolean isValid) {
        final boolean result = new _Day02().isValid(min, max, letter, password);
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
    void shouldFindResultInSmallNumberPool(final String policy, final boolean isValid) {
        final boolean result = new _Day02().isValid(policy);
        assertThat(result).isEqualTo(isValid);
    }

}
