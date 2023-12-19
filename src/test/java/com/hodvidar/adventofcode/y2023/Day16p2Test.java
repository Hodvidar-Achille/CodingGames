package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class Day16p2Test extends AbstractTestForAdventOfCode2023 {

    protected Day16p2Test() {
        super(new Day16p2());
    }

    @Override
    protected int getNumberOfTheTest() {
        return 1;
    }

    @Override
    protected double getExpectedTestResultDouble() {
        return 51d;
    }

    @Override
    protected Stream<Arguments> getExpectedTestResults() {
        return Stream.of(
                Arguments.of(getNumberOfTheTest(), getExpectedTestResultDouble()),
                Arguments.of(2, 8d),
                Arguments.of(3, 14d));
    }

    @Override
    protected double getExpectedResultDouble() {
        return 7315d;
    }
}
