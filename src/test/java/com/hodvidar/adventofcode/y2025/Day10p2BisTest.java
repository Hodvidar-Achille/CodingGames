package com.hodvidar.adventofcode.y2025;


import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

class Day10p2BisTest extends AbstractTestForAdventOfCode2025 {

    protected Day10p2BisTest() {
        super(new Day10p2Bis());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7} | 10",
            "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2} | 12",
            "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5} | 11",
            "[##] (0) (1) {1, 1} | 2",
            "[##] (0) (1) {1, 2} | 3",
            "[##] (0) (1) {2, 2} | 4",
            "[###] (0) (1) (2) {1, 1, 1} | 3",
            "[###] (0) (1) (2) {1, 1, 5} | 7",
            "[####] (0) (1) (2) (3) {1, 2, 3, 4} | 10",
            "[##..] (0,2) (1,3) (2) (3) {3, 3, 4, 4} | 8",
            "[.###...##.] (1,2,3,4,5,6,7,8,9) (1,5) (0,1,3,4,5,6,7,8,9) (4,8,9) (0,6,7,9) (1,6,8) (0,1,2,5,6,7,8) (0,1,2,3,5,7,8,9) (1,2,4,5) {27,79,30,21,45,59,54,36,50,43} | 101"
    })
    void checkOneLine(final String line, final double expectedResult) {
        final double result = testedClass.getDigitFromLine(line);
        assertThat(result).isEqualTo(expectedResult);
    }

    // 18385 too high (in ~20min)
    // 18254 too high (in ~3 sec)
    // 1154~better result
    @Override
    protected double getExpectedResultDouble() {
        return -1;
    }

    @Override
    protected int getExpectedTestResult() {
        return 33;
    }

}
