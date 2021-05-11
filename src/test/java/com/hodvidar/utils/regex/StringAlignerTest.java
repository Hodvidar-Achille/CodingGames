package com.hodvidar.utils.regex;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.hodvidar.utils.regex.StringAligner.alignLeft;
import static com.hodvidar.utils.regex.StringAligner.center;
import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

public class StringAlignerTest {

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "| 0 | a |",
            "foo | 3 | a | foo",
            "moon | 10 | * | ***moon***",
            "phone | 14 | # | ####phone#####",
    })
    public void center1(final String stringToCenter, final int size, final char pad, final String expectedResult) {
        assertThat(center(stringToCenter, size, pad)).isEqualTo(expectedResult);
    }

    @Test
    public void center2() {
        assertThat(center(null, 0)).isEqualTo(null);
        assertThat(center("foo", 3)).isEqualTo("foo");
        assertThat(center("foo", -1)).isEqualTo("foo");
        assertThat(center("moon", 10)).isEqualTo("   moon   ");
        assertThat(center("phone", 14, '*')).isEqualTo("****phone*****");
        assertThat(center("India", 6, '-')).isEqualTo("India-");
        assertThat(center("Eclipse IDE", 21, '*')).isEqualTo("*****Eclipse IDE*****");
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "| 0 | a |",
            "foo | 3 | a | foo",
            "moon | 10 | * | moon******",
            "phone | 14 | # | phone#########",
    })
    public void alignLeft1(final String stringToCenter, final int size, final char pad, final String expectedResult) {
        assertThat(alignLeft(stringToCenter, size, pad)).isEqualTo(expectedResult);
    }

    @Test
    public void alignLeft2() {
        assertThat(alignLeft(null, 0)).isEqualTo(null);
        assertThat(alignLeft("foo", 3)).isEqualTo("foo");
        assertThat(alignLeft("foo", -1)).isEqualTo("foo");
        assertThat(alignLeft("moon", 10)).isEqualTo("moon      ");
        assertThat(alignLeft("phone", 14, '*')).isEqualTo("phone*********");
        assertThat(alignLeft("India", 6, '-')).isEqualTo("India-");
        assertThat(alignLeft("Eclipse IDE", 21, '*')).isEqualTo("Eclipse IDE**********");
    }
}
