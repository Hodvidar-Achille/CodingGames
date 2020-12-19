package com.hodvidar.adventofcode.y2020;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class _Day02_2_Test extends AbstractTestForAdventOfCode {

    @Override
    protected int getDay() {
        return 2;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 3 | a | abcd | true",
            "1 | 3 | b | bacd | true",
            "1 | 3 | c | cabd | true",
            "1 | 3 | c | ccc | false",
            "1 | 3 | c | aac | true",
            "1 | 3 | c | abcccefghc | true",
            "10 | 20 | a | 12345678901234567890 | false",
            "10 | 20 | a | 123456789a123456789a | false",
            "10 | 20 | a | 123456789a1234567890 | true",
            "10 | 20 | a | 1234567890123456789a | true",
    })
    void shouldFindResultInSmallNumberPool(int min, int max, char letter, String password, boolean isValid) {
        boolean result = new _Day02_2().isValid(min, max, letter, password);
        assertThat(result).isEqualTo(isValid);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1-3 a: abcd = true",
            "1-3 b: bacd = true",
            "1-3 c: cabd = true",
            "1-3 c: ccc = false",
            "1-3 c: acc = true",
            "1-3 c: abcccefghc = true",
            "10-20 a: 12345678901234567890 = false",
            "10-20 a: 123456789a123456789a = false",
            "10-20 a: 1234567890123456789a = true",
            "10-20 a: 123456789a1234567890 = true"
    })
    void shouldFindResultInSmallNumberPool(String policy, boolean isValid) {
        boolean result = new _Day02_2().isValid(policy);
        assertThat(result).isEqualTo(isValid);
    }
}
