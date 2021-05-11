package com.hodvidar.adventofcode.y2020;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _Day08 extends AbstractAdventOfCode {

    protected static final String ACCUMULATOR = "acc";
    protected static final String JUMP = "jmp";
    protected static final String NO_OPERATION = "nop";

    public static void main(final String[] args) throws Exception {
        final _Day08 me = new _Day08();
        final int result = me.getResult(me.getScanner());
        System.err.println("Expected '1528' - result='" + result + "'");
    }

    @Override
    protected int getDay() {
        return 8;
    }

    @Override
    protected int getResult(final Scanner sc) throws FileNotFoundException {
        return getAccumulatorValue(sc);
    }

    public int getAccumulatorValue(final Scanner sc) {
        final List<String> instructions = getInstructions(sc);
        return runInstructions(instructions);
    }

    public int runInstructions(final List<String> instructions) {
        int accumulatorValue = 0;
        final List<Integer> visitedValues = new ArrayList<>();
        for (int i = 0; i < instructions.size(); /* empty */) {
            if (visitedValues.contains(i)) {
                break;
            }
            visitedValues.add(i);
            final String[] instruction = instructions.get(i).split(" ");
            final int value = Integer.parseInt(instruction[1]);

            if (JUMP.equals(instruction[0])) {
                i += value;
                continue;
            }

            if (ACCUMULATOR.equals(instruction[0])) {
                accumulatorValue += value;
            }
            // NO_OPERATION
            i++;
        }
        return accumulatorValue;
    }

    protected List<String> getInstructions(final Scanner sc) {
        String line;
        final List<String> instructions = new ArrayList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            instructions.add(line);
        }
        return instructions;
    }

}
