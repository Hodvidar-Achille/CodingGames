package com.hodvidar.adventofcode.y2023;

public class _Day03_2 extends _Day03 {
    @Override
    protected int getDigitFromLine(final String line) {
        schematic.computeNumbersNextToSymbols();
        return schematic.computeGearRatio();
    }
}
