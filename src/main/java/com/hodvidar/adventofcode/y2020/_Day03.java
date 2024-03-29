package com.hodvidar.adventofcode.y2020;

import java.util.Objects;
import java.util.Scanner;

public class _Day03 extends AbstractAdventOfCode2020 {

    private static final char TREE = '#';

    private static boolean isTree(final String pattern, final int abscissaIndex) {
        return Objects.equals(pattern.charAt(abscissaIndex), TREE);
    }

    private static int resetAbscissa(final int patternLength, int abscissaIndex) {
        while (abscissaIndex >= patternLength) {
            abscissaIndex -= patternLength;
        }
        return abscissaIndex;
    }

    public static int countTrees(final Scanner sc, final int ordinateIncrement, final int abscissaIncrement) {
        String line;
        int treeCounter = 0;
        int ordinateCounter = 1;
        int abscissaCounter = 0;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            ordinateCounter -= 1;
            if (ordinateCounter != 0) {
                continue;
            }
            final int patternLength = line.length();
            if (isTree(line, abscissaCounter)) {
                treeCounter += 1;
            }
            abscissaCounter += abscissaIncrement;
            abscissaCounter = resetAbscissa(patternLength, abscissaCounter);
            ordinateCounter += ordinateIncrement;
        }
        return treeCounter;
    }

    @Override
    public int getDay() {
        return 3;
    }

    @Override
    public int getResult(final Scanner sc) {
        return countTrees(sc, 1, 3);
    }
}
