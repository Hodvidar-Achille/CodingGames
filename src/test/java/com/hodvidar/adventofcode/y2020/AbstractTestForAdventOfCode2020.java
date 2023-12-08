package com.hodvidar.adventofcode.y2020;

import com.hodvidar.adventofcode.AbstractAdventOfCode;
import com.hodvidar.adventofcode.AbstractTestForAdventOfCode;

import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class AbstractTestForAdventOfCode2020 extends AbstractTestForAdventOfCode {
    protected AbstractTestForAdventOfCode2020(final AbstractAdventOfCode testedClass) {
        super(testedClass, 2020);
    }

    @Override
    protected int getExpectedTestResult() {
        return 0;
    }

    @Override
    protected int getNumberOfTheTest() {
        return 0;
    }
}
