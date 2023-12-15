package com.hodvidar.adventofcode.y2023;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class Day15p2 extends Day15 {

    public static final char REMOVE = '-';
    public static final char ADD = '=';
    private final Map<Integer, Map<String, Integer>> boxes = new HashMap<>();

    @Override
    public double getResultDouble(final Scanner sc) {
        boxes.clear();
        super.getResultDouble(sc);
        return computeBoxesFocusingPower();
    }

    private int computeBoxesFocusingPower() {
        int sum = 0;
        for (final Map.Entry<Integer, Map<String, Integer>> box : boxes.entrySet()) {
            if (box.getValue().isEmpty()) {
                continue;
            }
            final int boxNumber = box.getKey();
            int lensPosition = 0;
            int boxFocusPower = 0;
            for (final Map.Entry<String, Integer> entry : box.getValue().entrySet()) {
                lensPosition++;
                boxFocusPower += lensPosition * entry.getValue();
            }
            sum += boxFocusPower * (boxNumber + 1);
        }
        return sum;
    }

    @Override
    public int hashAlgorithm(final String individualStep) {
        final String label = individualStep.substring(0,
                Math.max(individualStep.indexOf(REMOVE),
                        individualStep.indexOf(ADD)));
        final int boxNumber = super.hashAlgorithm(label);
        final char action = individualStep.charAt(label.length());
        boxes.computeIfAbsent(boxNumber, k -> new LinkedHashMap<>());
        final Map<String, Integer> box = boxes.get(boxNumber);
        switch (action) {
            case REMOVE:
                box.remove(label);
                break;
            case ADD:
                final int newValue = Integer.parseInt(individualStep.substring(label.length() + 1));
                box.put(label, newValue);
                break;
            default:
                throw new IllegalArgumentException("Unknown action: " + action);
        }
        return 0;
    }
}
