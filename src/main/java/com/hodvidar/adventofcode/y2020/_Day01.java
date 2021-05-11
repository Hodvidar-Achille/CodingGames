package com.hodvidar.adventofcode.y2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Passed
 *
 * @author Hodvidar
 */
public class _Day01 extends AbstractAdventOfCode {

    public static void main(final String[] args) throws Exception {
        final AbstractAdventOfCode me = new _Day01();
        final int result = me.getResult(me.getScanner());
        System.err.println("Expected '388075' - result='" + result + "'");
    }

    @Override
    protected int getDay() {
        return 1;
    }

    @Override
    protected int getResult(final Scanner sc) {
        String line;
        final List<Integer> numbers = new ArrayList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            final int number = Integer.parseInt(line);
            if (number >= 2020) {
                continue;
            }
            for (final int n : numbers) {
                if ((n + number) == 2020) {
                    return n * number;
                }
            }
            numbers.add(number);
        }
        return -1;
    }

}
