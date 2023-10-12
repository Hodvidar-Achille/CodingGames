package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class StringAnalyzerTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            " | false",
            "a | false",
            "+ a | true",
            "+ 1 | false",
            "$ 1 | true",
            "$ a | false",
            "* aaa | true",
            "* 111 | true",
            "* a1a | false",
            "* aa | false",
            "* aaaa | false",
            "+ aa | false",
            "*{2} aa | true",
            "*{2} ab | false",
            "*{11} aaaaaaaaaaa | true",
            "++$$**{10} zz99eee8888888888 | true",
            "++$$**{10} zz99eee8888888887 | false",
            "++$$**{10} zz99eee88888888888 | false",
            "++$$**{10} zz99eee888888888 | false",
            "*{1}*{4}**+$+$+++* $%%%%===###a1b2cdeEEE | true",
    })
    void analyze(final String input, final boolean expected) {
        assertThat(StringAnalyzer.analyze(input)).isEqualTo(expected);
    }
}
