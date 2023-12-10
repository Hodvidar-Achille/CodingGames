package com.hodvidar.adventofcode.y2020;

import com.hodvidar.utils.list.LimitedSizeQueue;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _Day09 extends AbstractAdventOfCode2020 {

    static int readValuesAndLookForInvalid(final Scanner sc, final int numberOfNumberToUseForSums) {
        final LimitedSizeQueue<Double> numbers = new LimitedSizeQueue<>(numberOfNumberToUseForSums);
        List<Double> sums = new ArrayList<>();
        int index = -1;
        while (sc.hasNextLine()) {
            index++;
            final double newValue = Double.parseDouble(sc.nextLine());
            if (index < numberOfNumberToUseForSums) {
                numbers.add(newValue);
                continue;
            }
            if (index == numberOfNumberToUseForSums) {
                numbers.add(newValue);
                sums = createSubSumList(numbers);
                continue;
            }
            if (!sums.contains(newValue)) {
                return (int) newValue;
            }
            numbers.add(newValue);
            sums = createSubSumList(numbers);
        }
        return -1;
    }

    // TODO optimize this
    static List<Double> createSubSumList(final List<Double> numbers) {
        final List<Double> sums = new ArrayList<>();
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = 0; j < numbers.size(); j++) {
                if (i == j) {
                    continue;
                }
                final double sum = numbers.get(i) + numbers.get(j);
                sums.add(sum);
            }
        }
        return sums;
    }

    @Override
    public int getDay() {
        return 9;
    }

    @Override
    public int getResult(final Scanner sc) {
        return readValuesAndLookForInvalid(sc, 25);

    }


}
