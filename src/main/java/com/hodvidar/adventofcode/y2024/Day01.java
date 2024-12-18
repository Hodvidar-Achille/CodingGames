package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Day01 extends AbstractAdventOfCode2024 {

    protected final List<Integer> listOne = new ArrayList<>();
    protected final List<Integer> listTwo = new ArrayList<>();

    @Override
    public int getDay() {
        return 1;
    }

    @Override
    protected void digestLine(final String line) {
        final String[] parts = line.trim().split("\\s+");
        // Parse the two numbers and add them to the result list
        if (parts.length == 2) {
            try {
                final int number1 = Integer.parseInt(parts[0]);
                final int number2 = Integer.parseInt(parts[1]);
                listOne.add(number1);
                listTwo.add(number2);
            } catch (NumberFormatException e) {
                System.err.println("Error parsing numbers from line: " + line);
            }
        }
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        listOne.clear();
        listTwo.clear();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        Collections.sort(listOne);
        Collections.sort(listTwo);
        double counter = 0;
        for (int i = 0; i < listOne.size(); i++) {
            counter += Math.abs(listOne.get(i) - listTwo.get(i));
        }
        return counter;
    }
}
