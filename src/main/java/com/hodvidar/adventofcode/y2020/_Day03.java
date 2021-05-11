package com.hodvidar.adventofcode.y2020;

import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;

public class _Day03 extends AbstractAdventOfCode {

    private static final char TREE = '#';

    public static void main(final String[] args) throws Exception {
        final _Day03 me = new _Day03();
        final int result = countTrees(me.getScanner(), 1, 3);
        System.err.println("Expected '257' - result='" + result + "'");
    }

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
    protected int getDay() {
        return 3;
    }

    @Override
    protected int getResult(final Scanner sc) throws FileNotFoundException {
        return countTrees(sc, 1, 3);
    }
}
