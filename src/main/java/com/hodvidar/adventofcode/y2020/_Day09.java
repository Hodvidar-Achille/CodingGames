package com.hodvidar.adventofcode.y2020;

import java.io.FileNotFoundException;
import java.util.*;

public class _Day09 extends AbstractAdventOfCode {

    @Override
    protected int getDay() {
        return 9;
    }

    @Override
    protected int getResult(final Scanner sc) throws FileNotFoundException {
        return readValuesAndLookForInvalid(sc , 25);

    }

    public static void main(final String[] args) throws Exception {
        final _Day09 me = new _Day09();
        final int result =  new _Day09().getResult(me.getScanner());
        System.err.println("Expected '??' - result='" + result + "'");
    }

    static int readValuesAndLookForInvalid(final Scanner sc, final int numberOfNumberToUseForSums) {
        final List<Double> numbers = new ArrayList<>();
        final List<List<Integer>> sums = new ArrayList<>();
        int index = 0;
        while (sc.hasNextLine()) {
            final double newValue = Double.parseDouble(sc.nextLine());
            numbers.add(newValue);
            final List<Integer> newSum = new ArrayList<>();
            if(index<numberOfNumberToUseForSums) {
                continue;
            }
            if(index == numberOfNumberToUseForSums) {

            }

            index++;
        }
        return -1;
    }

    static List<Integer> createSubSumList(final List<Integer> numbers, final int numberOfNumberToUseForSums, final int index) {
        final List<Integer> sums = new ArrayList<>();
        for(int i = 0; i < numberOfNumberToUseForSums; i++) {
            for(int j = 0; j < numberOfNumberToUseForSums; j++) {
                if(i == j) {
                    continue;
                }
                final int sum = numbers.get(i) + numbers.get(j);
                sums.add(sum);
            }
        }
        return sums;
    }


}
