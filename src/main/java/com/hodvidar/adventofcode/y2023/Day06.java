package com.hodvidar.adventofcode.y2023;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Day06 extends AbstractAdventOfCode2023 {

    @Override
    public int getDay() {
        return 6;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        final List<Double> raceTimes = new ArrayList<>();
        final List<Double> raceRecordDistances = new ArrayList<>();
        Arrays.stream(sc.nextLine().split("Time:")[1].trim().split("\\s+")).mapToDouble(Double::parseDouble).forEach(raceTimes::add);
        Arrays.stream(sc.nextLine().split("Distance:")[1].trim().split("\\s+")).mapToDouble(Double::parseDouble).forEach(raceRecordDistances::add);
        final List<RaceCalculator> raceCalculators = new ArrayList<>();
        for (int i = 0; i < raceTimes.size(); i++) {
            raceCalculators.add(new RaceCalculator(raceTimes.get(i), raceRecordDistances.get(i)));
        }
        return raceCalculators.stream().mapToDouble(RaceCalculator::getNumberOfWaysToBreakRecord).reduce(1, (a, b) -> a * b);
    }

    protected record RaceCalculator(double time, double distanceRecord) {
        public double getNumberOfWaysToBreakRecord() {
            int counter = 0;
            for (int i = 1; i < time; i++) {
                if (i * (time - i) > distanceRecord) {
                    counter++;
                }
            }
            return counter;
        }
    }
}
