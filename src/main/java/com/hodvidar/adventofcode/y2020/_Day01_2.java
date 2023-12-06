package com.hodvidar.adventofcode.y2020;

import com.hodvidar.adventofcode.AbstractAdventOfCode;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _Day01_2 extends _Day01 {

    @Override
    public int getResult(final Scanner sc) {
        String line;
        final List<Integer> numbers = new ArrayList<>();
        while (sc.hasNextLine()) {
            line = sc.nextLine();
            final int number = Integer.parseInt(line);
            if (number >= 2020) {
                continue;
            }
            for (int i = 0; i < numbers.size(); i++) {
                final int n1 = numbers.get(i);
                for (int j = 0; j < numbers.size(); j++) {
                    final int n2 = numbers.get(j);
                    if (j == i) {
                        continue;
                    }
                    if (n1 + n2 >= 2020) {
                        continue;
                    }
                    if ((n1 + n2 + number) == 2020) {
                        return n1 * n2 * number;
                    }
                }
            }
            numbers.add(number);
        }
        return -1;
    }

}