package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class Solution4Test {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "1 | 1",
            "2 | 2",
            "3 | 3"
    })
    void parseTimeInSeconds(final int input, final int expectedResult) {
        assertThat(input).isEqualTo(expectedResult);
    }
}
