package com.hodvidar.adventofcode.y2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _Day08_2 extends _Day08 {

    @Override
    public int getResult(final Scanner sc) {
        return getAccumulatorValueAfterFix(sc);
    }

    public int getAccumulatorValueAfterFix(final Scanner sc) {
        List<String> instructions = getInstructions(sc);
        instructions = fixInstructions(instructions);
        return runInstructions(instructions);
    }

    /**
     * Not most efficient way of doing it
     **/
    public List<String> fixInstructions(final List<String> instructions) {
        String[] instruction;
        for (int i = 0; i < instructions.size(); i++) {
            instruction = instructions.get(i).split(" ");
            final int value = Integer.parseInt(instruction[1]);
            if (NO_OPERATION.equals(instruction[0])) {
                instructions.set(i, JUMP + " " + value);
                if (!isLoop(instructions)) {
                    break;
                }
                instructions.set(i, NO_OPERATION + " " + value);
            } else if (JUMP.equals(instruction[0])) {
                instructions.set(i, NO_OPERATION + " " + value);
                if (!isLoop(instructions)) {
                    break;
                }
                instructions.set(i, JUMP + " " + value);
            }
        }
        return instructions;
    }

    public boolean isLoop(final List<String> instructions) {
        final List<Integer> visitedValues = new ArrayList<>();
        String[] instruction;
        for (int i = 0; i < instructions.size(); /* empty */) {
            if (visitedValues.contains(i)) {
                return true;
            }
            visitedValues.add(i);
            instruction = instructions.get(i).split(" ");
            final int value = Integer.parseInt(instruction[1]);

            if (JUMP.equals(instruction[0])) {
                i += value;
                if (i < 0 || i > instructions.size()) {
                    return true;
                }
                continue;
            }

            // NO_OPERATION and ignore ACCUMULATOR
            i++;
        }
        return false;
    }

}
