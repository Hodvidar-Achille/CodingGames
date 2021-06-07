package com.hodvidar.miscellaneous.livecoding;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class Solution3Test {

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "00:00:01 = 1",
            "01:01:01 = 3661",
            "00:10:00 = 600"
    })
    void parseTimeInSeconds(final String input, final int expectedResult) {
        assertThat(Solution3.parseTimeInSeconds(input)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "1 = 3",
            "67 = 201",
            "300 = 750",
            "301 = 900",
            "360 = 900",
            "365 = 1050",
            "600 = 1500",
            "3600 = 9000",
            "3601 = 9150"
    })
    void computePriceInCents(final int input, final int expectedResult) {
        assertThat(Solution3.computePriceInCents(input)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "00:00:01 = 3",
            "00:01:07 = 201",
            "00:05:00 = 750",
            "00:05:01 = 900",
            "00:06:00 = 900",
            "00:06:05 = 1050",
            "00:10:00 = 1500",
            "01:00:00 = 9000",
            "01:00:01 = 9150",
    })
    void timeToPriceInCents(final String input, final int expectedResult) {
        assertThat(Solution3.computePriceInCents(
                Solution3.parseTimeInSeconds(input)))
                .isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '=', value = {
            "400-234-090 = 400234090",
            "701-080-080 = 701080080",
            "001-001-001 = 1001001"
    })
    void phoneToNumber(final String input, final Double expectedResult) {
        assertThat(Solution3.phoneToNumber(input)).isEqualTo(expectedResult);
    }

    @Test
    void timeToPriceInCentsSum() {
        final String time1 = "00:01:07";
        final String time2 = "00:05:01";
        final String time3 = "00:05:00";
        final String time4 = "00:10:00";
        final int p1 = Solution3.computePriceInCents(
                Solution3.parseTimeInSeconds(time1));
        final int p2 = Solution3.computePriceInCents(
                Solution3.parseTimeInSeconds(time2));
        final int p3 = Solution3.computePriceInCents(
                Solution3.parseTimeInSeconds(time3));
        final int p4 = Solution3.computePriceInCents(
                Solution3.parseTimeInSeconds(time4));
        assertThat(p1 + p2 + p3 + p4).isEqualTo(3351);
    }

    @Test
    void getResult() {
        final String input = """
                00:01:07,400-234-090
                00:05:01,701-080-080
                00:05:00,400-234-090
                """;
        assertThat(Solution3.getResult(input)).isEqualTo(900);
        final String input2 = """
                00:01:07,400-234-090
                00:05:01,701-080-080
                00:05:00,400-234-090
                00:10:00,701-080-080
                01:00:00,111-111-111
                """;
        assertThat(Solution3.getResult(input2)).isEqualTo(3351);
    }
}
