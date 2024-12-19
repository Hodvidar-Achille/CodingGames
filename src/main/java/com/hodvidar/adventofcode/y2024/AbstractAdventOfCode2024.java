package com.hodvidar.adventofcode.y2024;

import com.hodvidar.adventofcode.AbstractAdventOfCode;

import java.util.Scanner;

public abstract class AbstractAdventOfCode2024 extends AbstractAdventOfCode {
    protected AbstractAdventOfCode2024() {
        super(2024);
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        double counter = 0;
        while (sc.hasNext()) {
            counter += getDigitFromLine(sc.nextLine());
        }
        return counter;
    }

    @Override
    public int getResult(final Scanner sc) {
        return (int) getResultDouble(sc);
    }
}
