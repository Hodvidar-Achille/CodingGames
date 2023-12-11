package com.hodvidar.adventofcode.y2023;

import org.junit.jupiter.params.provider.Arguments;

import java.util.stream.Stream;

class Day10p2Test extends AbstractTestForAdventOfCode2023 {
    protected Day10p2Test() {
        super(new Day10p2());
    }

    @Override
    protected double getExpectedResultDouble() {
        return 429d;
    }

    @Override
    protected int getNumberOfTheTest() {
        return 2;
    }

    @Override
    protected double getExpectedTestResultDouble() {
        return 4d;
    }

    @Override
    protected Stream<Arguments> getExpectedTestResults() {
        return Stream.of(
                Arguments.of(getNumberOfTheTest(), getExpectedTestResultDouble()),
                Arguments.of(3, 8d),
                Arguments.of(4, 10d));
    }
}