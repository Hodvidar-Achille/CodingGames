package com.hodvidar.adventofcode.y2024;

import com.hodvidar.adventofcode.AbstractAdventOfCode;
import com.hodvidar.adventofcode.AbstractTestForAdventOfCode;

public abstract class AbstractTestForAdventOfCode2024 extends AbstractTestForAdventOfCode {
    protected AbstractTestForAdventOfCode2024(final AbstractAdventOfCode testedClass) {
        super(testedClass, 2024);
    }

    @Override
    protected int getNumberOfTheTest() {
        return 1;
    }
}