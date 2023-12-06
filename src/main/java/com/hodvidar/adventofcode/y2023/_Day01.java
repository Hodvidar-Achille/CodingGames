package com.hodvidar.adventofcode.y2023;

import org.assertj.core.util.Streams;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class _Day01 extends AbstractAdventOfCode2023 {
    
    
    @Override
    public int getResult(final Scanner sc) throws FileNotFoundException {
        return Streams.stream(sc).mapToInt(this::getDigitFromLine).sum();
    }

    @Override
    public int getDay() {
        return 1;
    }

    protected int getDigitFromLine(final String line) {
        final String onlyDigit = line.replaceAll("\\D+", "");
        if (onlyDigit.isEmpty()) {
            return 0;
        }
        final char[] eachDigits = onlyDigit.toCharArray();
        return Integer.parseInt(""
                + eachDigits[0] + eachDigits[eachDigits.length - 1]);

    }
}
