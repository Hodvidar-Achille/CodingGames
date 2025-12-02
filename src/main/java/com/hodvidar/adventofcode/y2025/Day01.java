package com.hodvidar.adventofcode.y2025;

import java.util.Scanner;

public class Day01 extends AbstractAdventOfCode2025 {

    protected static final int MOD = 100;
    // starting position and the counter for “pointing at 0”
    protected int currentState = 50;
    protected int counter = 0;

    void resetState() {
        currentState = 50;
        counter = 0;
    }

    /**
     * Parses one line such as “L68” or “R14”, updates the dial position,
     * increments {@code counter} whenever the dial ends up on 0,
     * and returns the new dial value.
     *
     * @param line a rotation instruction (e.g. “L68”)
     * @return the dial value after applying the instruction
     */
    @Override
    public double getDigitFromLine(final String line) {
        final char direction = line.charAt(0);
        final int distance = Integer.parseInt(line.substring(1));

        if (direction == 'L') {
            // moving left means subtracting; we add MOD before % to avoid negatives
            currentState = (currentState - distance + MOD) % MOD;
        } else if (direction == 'R') {
            currentState = (currentState + distance) % MOD;
        } else {
            throw new IllegalArgumentException("Invalid rotation direction: " + direction);
        }
        if (currentState == 0) {
            counter++;
        }
        return currentState;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        this.resetState();
        while (sc.hasNextLine()) {
            this.getDigitFromLine(sc.nextLine());
        }
        return counter;
    }
}
