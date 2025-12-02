package com.hodvidar.adventofcode.y2025;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day02p2Test  extends AbstractTestForAdventOfCode2025 {

    protected Day02p2Test() {
        super(new Day02p2());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "11 | true",
            "111 | true",
            "1111 | true",
            "11111 | true",
            "112112 | true",
            "1234567812345678 | true",
            "1230123 | false",
            "12 | false",
            "121212 | true"
    })
    void checkMiddle(final Double line, final boolean expectedResult) {
        final boolean result = ((Day02p2) testedClass).isInvalid(line);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Override
    protected double getExpectedResultDouble() {
        return 38262920235d;
    }

    @Override
    protected double getExpectedTestResultDouble() {
        return 4174379265d;
    }
}
