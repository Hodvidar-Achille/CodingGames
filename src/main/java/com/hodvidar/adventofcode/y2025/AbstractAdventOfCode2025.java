package com.hodvidar.adventofcode.y2025;

import java.util.Scanner;

import com.hodvidar.adventofcode.AbstractAdventOfCode;

public class AbstractAdventOfCode2025 extends AbstractAdventOfCode {
    protected AbstractAdventOfCode2025() {
        super(2025);
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

    @Override
    public int getDay() {
        // Get the simple class name (e.g., "Day12p2")
        final String className = this.getClass().getSimpleName();
        // Extract the numeric part at the beginning of the class name
        final String dayNumber = className.replaceAll("[^\\d]", " ").trim().split(" ")[0];
        return Integer.parseInt(dayNumber);
    }
}
