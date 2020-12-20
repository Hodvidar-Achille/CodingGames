package com.hodvidar.adventofcode.y2020;

import java.util.Objects;
import java.util.Scanner;

public class _Day03 extends AbstractAdventOfCode {

    private static final char TREE = '#';

    public static void main(String[] args) throws Exception {
        _Day03 me = new _Day03();
        int result = me.countTrees(me.getScanner(), 1, 3);
        System.err.println("Expected '257' - result='" + result + "'");
    }

    private static boolean isTree(String pattern, int abscissaIndex) {
        return Objects.equals(pattern.charAt(abscissaIndex), TREE);
    }

    private static int resetAbscissa(int patternLength, int abscissaIndex) {
        while (abscissaIndex >= patternLength) {
            abscissaIndex -= patternLength;
        }
        return abscissaIndex;
    }

    @Override
    protected int getDay() {
        return 3;
    }

    public int countTrees(Scanner sc, int ordinateIncrement, int abscissaIncrement) {
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
            int patternLength = line.length();
            if (isTree(line, abscissaCounter)) {
                treeCounter += 1;
            }
            abscissaCounter += abscissaIncrement;
            abscissaCounter = resetAbscissa(patternLength, abscissaCounter);
            ordinateCounter += ordinateIncrement;
        }
        return treeCounter;
    }
}
