package com.hodvidar.adventofcode.y2020;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class _Day05_2 extends _Day05 {

    public static int getMissingCode(final Scanner sc) {
        final List<Integer> codes = new ArrayList<>();
        String line;
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            final int code = getFinalPositionCode(line);
            codes.add(code);
        }
        Collections.sort(codes);
        for (int i = 0; i < codes.size() - 1; i++) {
            final int code1 = codes.get(i);
            final int code2 = codes.get(i + 1);
            if (code2 - code1 > 1) {
                return code1 + 1;
            }
        }
        return -1;
    }

    @Override
    public int getResult(final Scanner sc) {
        return getMissingCode(sc);
    }
}
