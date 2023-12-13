package com.hodvidar.adventofcode.y2023;

import com.hodvidar.adventofcode.AbstractAdventOfCode;

import java.util.Scanner;

public abstract class AbstractAdventOfCode2023 extends AbstractAdventOfCode {
    protected AbstractAdventOfCode2023() {
        super(2023);
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        return getResult(sc);
    }

    @Override
    public int getResult(final Scanner sc) {
        int counter = 0;
        while (sc.hasNext()) {
            counter += getDigitFromLine(sc.nextLine());
        }
        return counter;
    }
}
