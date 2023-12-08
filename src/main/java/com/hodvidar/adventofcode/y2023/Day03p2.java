package com.hodvidar.adventofcode.y2023;

public class Day03p2 extends Day03 {
    @Override
    protected int computeSchematic() {
        schematic.computeNumbersNextToSymbols();
        return schematic.computeGearRatio();
    }
}
