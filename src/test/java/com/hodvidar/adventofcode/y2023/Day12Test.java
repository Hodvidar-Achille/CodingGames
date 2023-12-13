package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Day12Test extends AbstractTestForAdventOfCode2023 {
    protected Day12Test() {
        super(new Day12());
    }

    @Override
    protected int getNumberOfTheTest() {
        return 1;
    }

    @Override
    protected double getExpectedTestResultDouble() {
        return 21d;
    }

    @Override
    protected double getExpectedResultDouble() {
        return 7670d;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "???.### 1,1,3 | 1",
            ".??..??...?##. 1,1,3 | 4",
            "?#?#?#?#?#?#?#? 1,3,1,6 | 1",
            "????.#...#... 4,1,1 | 1 ",
            "????.######..#####. 1,6,5 | 4",
            "?###???????? 3,2,1 | 10",
            "???.###????.###????.###????.###????.### 1,1,3,1,1,3,1,1,3,1,1,3,1,1,3 | 1",
            "?###??????????###??????????###??????????###??????????###???????? 3,2,1,3,2,1,3,2,1,3,2,1,3,2,1 | 506250"
    })
    void numberOfPermutations(final String input, final double expectedResult) {
        assertThat(testedClass.getDigitFromLine(input)).isEqualTo(expectedResult);
    }
}