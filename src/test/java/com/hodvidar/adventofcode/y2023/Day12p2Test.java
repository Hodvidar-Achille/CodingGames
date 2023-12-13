package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Day12p2Test extends AbstractTestForAdventOfCode2023 {
    protected Day12p2Test() {
        super(new Day12p2());
    }

    @Override
    protected int getNumberOfTheTest() {
        return 1;
    }

    @Override
    protected double getExpectedTestResultDouble() {
        return 525152d;
    }

    @Override
    protected double getExpectedResultDouble() {
        return 157383940585037d;
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "???.### 1,1,3 | 1",
            ".??..??...?##. 1,1,3 | 16384",
            "?#?#?#?#?#?#?#? 1,3,1,6 | 1",
            "????.#...#... 4,1,1 | 16 ",
            "????.######..#####. 1,6,5 | 2500",
            "?###???????? 3,2,1 | 506250"
    })
    void numberOfPermutations(final String input, final int expectedResult) {
        assertThat(testedClass.getDigitFromLine(input)).isEqualTo(expectedResult);
    }
}