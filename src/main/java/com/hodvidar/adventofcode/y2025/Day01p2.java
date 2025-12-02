package com.hodvidar.adventofcode.y2025;

public class Day01p2 extends Day01 {

    @Override
    public double getDigitFromLine(final String line) {
        final char dir = line.charAt(0);
        final int dist = Integer.parseInt(line.substring(1));

        if (dist == 0) {
            return currentState;
        }

        final int oldPos = currentState;
        int newPos;
        int passesThroughZero;
        if (dir == 'R') {
            passesThroughZero = (oldPos + dist) / (MOD);
            newPos = (oldPos + dist) % (MOD);
        } else if (dir == 'L') {
            if (oldPos == 0) {
                passesThroughZero = dist / (MOD);
                newPos = MOD - (dist % (MOD));
            } else {
                passesThroughZero = dist / MOD;
                newPos = oldPos - (dist % (MOD));
                if (newPos == 0) {
                    passesThroughZero++;
                }
                if (newPos < 0) {
                    newPos += MOD;
                    passesThroughZero++;
                }
            }
        } else {
            throw new IllegalArgumentException("Invalid rotation direction: " + dir);
        }
        counter += passesThroughZero;
        currentState = newPos;
        return currentState;
    }
}
