package com.hodvidar.adventofcode.y2020;

import com.hodvidar.adventofcode.AbstractAdventOfCode;

import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class AbstractAdventOfCode2020 extends AbstractAdventOfCode {
    protected AbstractAdventOfCode2020() {
        super(2020);
    }

    @Override
    public double getResultDouble(final Scanner sc) throws FileNotFoundException {
        return getResult(sc);
    }
}
