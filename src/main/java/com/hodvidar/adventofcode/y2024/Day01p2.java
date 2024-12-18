package com.hodvidar.adventofcode.y2024;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Day01p2 extends Day01 {


    @Override
    public double getResultDouble(final Scanner sc) {
        listOne.clear();
        listTwo.clear();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        Collections.sort(listOne);
        Collections.sort(listTwo);
        final Map<Integer, Counter> counterMap = new HashMap<>();
        int index2 = 0;
        for (final int number : listOne) {
            final Counter counter = counterMap.get(number);
            if (counter == null) {
                index2 = createNewCounterAndCount(number, counterMap, index2);
                continue;
            }
            // already exist
            counter.incrementNumberOfTimeInListOne();
        }

        return counterMap.values()
                .stream()
                .mapToDouble(Counter::getValue)
                .sum();
    }

    private int createNewCounterAndCount(final int number, final Map<Integer, Counter> counterMap, int index2) {
        final Counter counter = new Counter(number);
        counterMap.put(number, counter);
        for (int j = index2; j < listTwo.size(); j++) {
            final int number2 = listTwo.get(j);
            if (number2 < number) {
                continue;
            }
            if (number2 > number) {
                index2 = Math.max(0, j - 1);
                break;
            }
            // number2 == number
            counter.incrementNumberOfTimeInListTwo();
        }
        return index2;
    }

    private static class Counter {
        final int number;
        int numberOfTimesInListOne;
        int numberOfTimesInListTwo = 0;

        public Counter(final int number) {
            this.number = number;
            this.numberOfTimesInListOne = 1;
        }

        public void incrementNumberOfTimeInListOne() {
            this.numberOfTimesInListOne++;
        }

        public void incrementNumberOfTimeInListTwo() {
            this.numberOfTimesInListTwo++;
        }

        public double getValue() {
            if (this.numberOfTimesInListTwo == 0) {
                return 0;
            }
            return (double) this.number
                    * (double) this.numberOfTimesInListOne
                    * (double) this.numberOfTimesInListTwo;
        }
    }
}
