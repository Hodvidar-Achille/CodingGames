package com.hodvidar.adventofcode.y2025;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10Test extends AbstractTestForAdventOfCode2025 {

    protected Day10Test() {
        super(new Day10());
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7} | 2 | 6",
            "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2} | 1 | 5",
            "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5} | 4 | 4",
            "[##] (0) (1) | 2 | 2",
            "[###] (0) (1) (2) | 3 | 3",
            "[####] (0) (1) (2) (3) | 4 | 4",
            "[##..] (0,2) (1,3) (2) (3) | 2 | 4",
    })
    void checkParsing(final String line, final double numberOfButtonActivatedExpected, final double numberOfButtonsExpected) {
        final Day10.ParsedLine parsedLine = Day10.parseLine(line);
        double numberOfButtonActivated = 0;
        for (int i = 0; i < parsedLine.pattern().length; i++) {
            if (parsedLine.pattern()[i]) {
                numberOfButtonActivated++;
            }
        }
        assertThat(numberOfButtonActivated).isEqualTo(numberOfButtonActivatedExpected);
        final double numberOfButtons = parsedLine.groups().size();
        assertThat(numberOfButtons).isEqualTo(numberOfButtonsExpected);
    }

    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {
            "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7} | 2",
            "[...#.] (0,2,3,4) (2,3) (0,4) (0,1,2) (1,2,3,4) {7,5,12,7,2} | 3",
            "[.###.#] (0,1,2,3,4) (0,3,4) (0,1,2,4,5) (1,2) {10,11,11,5,10,5} | 2",
            "[##] (0) (1) | 2",
            "[###] (0) (1) (2) | 3",
            "[####] (0) (1) (2) (3) | 4",
            "[##..] (0,2) (1,3) (2) (3) | 4",
    })
    void checkOneLine(final String line, final double expectedResult) {
        final double result = testedClass.getDigitFromLine(line);
        assertThat(result).isEqualTo(expectedResult);
    }

    @Override
    protected double getExpectedResultDouble() {
        return 517;
    }

    @Override
    protected int getExpectedTestResult() {
        return 7;
    }

}