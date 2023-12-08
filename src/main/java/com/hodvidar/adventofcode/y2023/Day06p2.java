package com.hodvidar.adventofcode.y2023;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Day06p2 extends Day06 {
    @Override
    public double getResultDouble(final Scanner sc) throws FileNotFoundException {
        final RaceCalculator raceCalculator = new RaceCalculator(
                Double.parseDouble(sc.nextLine().split("Time:")[1].trim().replaceAll("\\s+", "")),
                Double.parseDouble(sc.nextLine().split("Distance:")[1].trim().replaceAll("\\s+", "")));
        return raceCalculator.getNumberOfWaysToBreakRecord();
    }
}
