package com.hodvidar.adventofcode.y2020;

import java.util.Scanner;

public class _Day03_2 extends _Day03 {

    @Override
    public int getResult(final Scanner sc) {
        final int result1 = countTrees(sc, 1, 1);
        final int result2 = countTrees(sc, 1, 3);
        final int result3 = countTrees(sc, 1, 5);
        final int result4 = countTrees(sc, 1, 7);
        final int result5 = countTrees(sc, 2, 1);
        return result1 * result2 * result3 * result4 * result5;
    }

}
