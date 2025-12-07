package com.hodvidar.adventofcode.y2025;

import java.util.Scanner;

public class Day08 extends AbstractAdventOfCode2025 {

    @Override
    public double getDigitFromLine(final String line) {
        return 0;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        while (sc.hasNextLine()) {
            this.getDigitFromLine(sc.nextLine());
        }
        return 0;
    }
}

