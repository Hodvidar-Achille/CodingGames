package com.hodvidar.adventofcode.y2025;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Day06 extends AbstractAdventOfCode2025 {

    protected static final char MULTIPLIER = '*';
    protected static final char PLUS = '+';

    private final List<List<Integer>> numbers = new ArrayList<>();
    private final List<Character> characters = new ArrayList<>();

    private static List<String> splitIntoWords(final String input) {
        if (input == null || input.isBlank()) {
            return Collections.emptyList();
        }
        final String[] parts = input.trim().split("\\s+");
        return Arrays.asList(parts);
    }

    private static List<Integer> toIntegers(final List<String> source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return source.stream().map(Integer::valueOf).collect(Collectors.toList());
    }

    protected static List<Character> toCharacters(final List<String> source) {
        if (source == null) {
            return Collections.emptyList();
        }
        return source.stream().map(s -> s.charAt(0)).collect(Collectors.toList());
    }

    public static boolean firstElementIsInteger(final List<String> source) {
        if (source == null || source.isEmpty()) {
            return false;
        }
        final String first = source.getFirst().trim();
        return first.matches("-?\\d+");
    }

    public static List<List<Integer>> transpose(final List<List<Integer>> numbers) {
        if (numbers == null || numbers.isEmpty()) {
            return Collections.emptyList();
        }
        final int rows = numbers.size();
        final int cols = numbers.get(0).size();
        final List<List<Integer>> result = new ArrayList<>(cols);
        for (int c = 0; c < cols; c++) {
            final List<Integer> column = new ArrayList<>(rows);
            for (int r = 0; r < rows; r++) {
                column.add(numbers.get(r).get(c));
            }
            result.add(column);
        }
        return result;
    }

    public static long evaluate(final List<List<Integer>> numbers, final List<Character> operators) {
        long total = 0L;
        final int size = Math.min(numbers.size(), operators.size());
        for (int i = 0; i < size; i++) {
            final List<Integer> list = numbers.get(i);
            final char op = operators.get(i);
            long result;
            if (op == MULTIPLIER) {
                result = 1L;
                for (final int v : list)
                    result *= v;
            } else if (op == PLUS) {
                result = 0L;
                for (final int v : list)
                    result += v;
            } else {
                result = 0L;
            }
            total += result;
        }
        return total;
    }

    @Override
    public double getDigitFromLine(final String line) {
        final List<String> words = splitIntoWords(line);
        if (firstElementIsInteger(words)) {
            numbers.add(toIntegers(words));
        } else {
            characters.addAll(toCharacters(words));
        }
        return 0;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        numbers.clear();
        characters.clear();
        while (sc.hasNextLine()) {
            this.getDigitFromLine(sc.nextLine());
        }
        final List<List<Integer>> transposed = transpose(numbers);
        return evaluate(transposed, characters);
    }
}

