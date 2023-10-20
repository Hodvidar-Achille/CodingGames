package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Solution6Test {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "00:00:01 | 1",
            "01:01:01 | 3661",
            "00:10:00 | 600"
    })
    void parseTimeInSeconds(final String input, final int expectedResult) {
        assertThat(input).isEqualTo(expectedResult);
    }
}
