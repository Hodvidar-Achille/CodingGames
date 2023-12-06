package com.hodvidar.adventofcode.y2023;

import com.hodvidar.adventofcode.AbstractAdventOfCode;

import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class AbstractAdventOfCode2023 extends AbstractAdventOfCode {
    protected AbstractAdventOfCode2023() {
        super(2023);
    }

    @Override
    public int getResult(final Scanner sc) throws FileNotFoundException {
        int counter = 0;
        while (sc.hasNext()) {
            counter += getDigitFromLine(sc.nextLine());
        }
        return counter;
    }

    protected abstract int getDigitFromLine(final String line);
}
