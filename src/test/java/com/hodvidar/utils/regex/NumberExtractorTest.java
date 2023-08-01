package com.hodvidar.utils.regex;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class NumberExtractorTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "<x=-8, y=-10, z=0>| -8 | -10 | 0",
            "<x=5, y=105789, z=10> | 5 | 105789 | 10",
            "<x=2, y=-7, z=3> | 2 | -7 | 3",
            "<x=9, y=-8, z=-3> | 9 | -8 | -3",
            "-105 beans and 13 carrots, 0 | -105 | 13 | 0",
            "[{1.0, 2.0, -3.0}] | 1.0 | 2.0 | -3.0"
    })
    void checkExtractNumber(final String s, final double r1, final double r2, final double r3) {
        final List<Double> result = NumberExtractor.extractNumber(s);
        assertThat(result.get(0)).isEqualTo(r1);
        assertThat(result.get(1)).isEqualTo(r2);
        assertThat(result.get(2)).isEqualTo(r3);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "<x=-8, y=-10, z=0>| -8",
            "<x=5, y=105789, z=10> | 5",
            "<x=2, y=-7, z=3> | 2",
            "<x=9, y=-8, z=-3> | 9",
            "-105 beans and 13 carrots, 0 | -105",
            "115 bags | 115"
    })
    void checkExtractFirstInteger(final String s, final int expectedResult) {
        final int result = NumberExtractor.extractFirstInteger(s);
        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "<x=-8, y=-10, z=0>| 8100",
            "<x=5, y=105789, z=10> | 510578910",
            "<x=2, y=-7, z=3> | 273",
            "<x=9, y=-8, z=-3> | 983",
            "-105 beans and 13 carrots, 0 | 105130",
            "115 bags | 115"
    })
    void checkExtractIntegers(final String s, final int expectedResult) {
        final int result = NumberExtractor.extractInteger(s);
        assertThat(result).isEqualTo(expectedResult);
    }
}
