package com.hodvidar.adventofcode.y2025;

import com.hodvidar.adventofcode.AbstractAdventOfCode;
import com.hodvidar.adventofcode.AbstractTestForAdventOfCode;

public abstract class AbstractTestForAdventOfCode2025 extends AbstractTestForAdventOfCode {

    protected AbstractTestForAdventOfCode2025(final AbstractAdventOfCode testedClass) {
        super(testedClass, 2025);
    }

    @Override
    protected int getNumberOfTheTest() {
        return 1;
    }
}