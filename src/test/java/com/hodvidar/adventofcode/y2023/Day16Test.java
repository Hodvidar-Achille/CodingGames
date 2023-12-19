package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class Day16Test extends AbstractTestForAdventOfCode2023 {

    protected Day16Test() {
        super(new Day16());
    }

    @Override
    protected int getNumberOfTheTest() {
        return 1;
    }

    @Override
    protected double getExpectedTestResultDouble() {
        return 46d;
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
        return 6978d;
    }
}

