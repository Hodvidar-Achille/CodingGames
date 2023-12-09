package com.hodvidar.adventofcode.y2023;

class Day07Test extends AbstractTestForAdventOfCode2023 {
    protected Day07Test() {
        super(new Day07());
    }

    @Override
    protected int getNumberOfTheTest() {
        return 1;
    }

    @Override
    protected double getExpectedResultDouble() {
        return 247119905d;
    } // Too low

    @Override
    protected double getExpectedTestResultDouble() {
        return 6440d;
    } // 6441 ?
}
