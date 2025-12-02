package com.hodvidar.adventofcode.y2025;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day02 extends AbstractAdventOfCode2025 {

    List<Double> digits = new ArrayList<>();

    // Possible optimization, save intervals already checked
    protected boolean isInvalid(final double value) {
        // invalid if number composed of 2 digits repeated perfectly (11, 22, 4545, 687687, etc)
        String plain = new BigDecimal(Double.toString(value)).toPlainString();
        final int dotIdx = plain.indexOf('.');
        if (dotIdx != -1) {
            plain = plain.substring(0, dotIdx);
        }
        if ((plain.length() % 2) != 0) {
            return false;
        }
        // Split the string at the middle and check equality
        final String left = plain.substring(0, plain.length() / 2);
        final String right = plain.substring(plain.length() / 2);
        return left.equals(right);
    }

    @Override
    public double getDigitFromLine(final String line) {
        final String[] parts = line.split("-");
        final String p1 = parts[0];
        final String p2 = parts[1];
        final double p1Val = Double.parseDouble(p1);
        final double p2Val = Double.parseDouble(p2);
        if (p1Val >= p2Val) {
            throw new IllegalArgumentException("p1 should be greater than p2");
        }
        for (double i = p1Val; i <= p2Val; i++) {
            if (isInvalid(i)) {
                digits.add(i);
            }
        }
        return 1;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        digits.clear();
        while (sc.hasNextLine()) {
            final String singleLine = sc.nextLine();
            final String[] parts = singleLine.split(",");
            for (final String part : parts) {
                this.getDigitFromLine(part.trim());
            }
        }
        return digits.stream().mapToDouble(Double::doubleValue).sum();
    }
}
