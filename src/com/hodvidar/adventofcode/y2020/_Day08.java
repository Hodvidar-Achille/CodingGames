package com.hodvidar.adventofcode.y2020;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _Day08 extends AbstractAdventOfCode {

    protected static final String ACCUMULATOR = "acc";
    protected static final String JUMP = "jmp";
    protected static final String NO_OPERATION = "nop";

    public static void main(String[] args) throws Exception {
        _Day08 me = new _Day08();
        int result = me.getResult(me.getScanner());
        System.err.println("Expected '1528' - result='" + result + "'");
    }

    @Override
    protected int getDay() {
        return 8;
    }

    @Override
    protected int getResult(Scanner sc) throws FileNotFoundException {
        return getAccumulatorValue(sc);
    }

    public int getAccumulatorValue(Scanner sc) {
        List<String> instructions = getInstructions(sc);
        return runInstructions(instructions);
    }

    public int runInstructions(List<String> instructions) {
        int accumulatorValue = 0;
        List<Integer> visitedValues = new ArrayList<>();
        for (int i = 0; i < instructions.size(); /* empty */) {
            if (visitedValues.contains(i)) {
                break;
            }
            visitedValues.add(i);
            String[] instruction = instructions.get(i).split(" ");
            int value = Integer.parseInt(instruction[1]);

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

    protected List<String> getInstructions(Scanner sc) {
        String line;
        List<String> instructions = new ArrayList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            instructions.add(line);
        }
        return instructions;
    }

}
