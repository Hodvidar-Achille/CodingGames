package com.hodvidar.adventofcode.y2023;

import com.hodvidar.utils.geometry.Point;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.function.Predicate;

public class _Day03 extends AbstractAdventOfCode2023 {

    protected EngineSchematic schematic;

    @Override
    public int getDay() {
        return 3;
    }


    @Override
    public int getResult(final Scanner sc) throws FileNotFoundException {
        schematic = new EngineSchematic();
        int y = 0;
        while (sc.hasNext()) {
            schematic.incorporateNewLine(y, sc.nextLine());
            y++;
        }
        return getDigitFromLine("irrelevant");
    }

    @Override
    protected int getDigitFromLine(final String line) {
        return schematic.computeNumbersNextToSymbols();
    }

    protected static class EngineSchematic {
        private final List<Symbol> symbols = new ArrayList<>();

        private final List<SegmentNumber> numbers = new ArrayList<>();

        public void incorporateNewLine(final int y, final String line) {
            final char[] chars = line.toCharArray();
            String currentNumber = "";
            SegmentNumber currentSegmentNumber = null;
            for (int x = 0; x < chars.length; x++) {
                final char c = chars[x];
                if (c == '.') {
                    continue;
                }
                if (Character.isDigit(c)) {
                    final Point p = new Point(x, y);
                    if (currentNumber.isEmpty()) {
                        currentSegmentNumber = new SegmentNumber(p);
                    }
                    currentNumber += c;
                    if (x == chars.length - 1 || !Character.isDigit(chars[x + 1])) {
                        currentSegmentNumber.number = Integer.parseInt(currentNumber);
                        currentSegmentNumber.p2 = p;
                        numbers.add(currentSegmentNumber);
                        currentNumber = "";
                    }
                } else {
                    symbols.add(new Symbol(c, x, y));
                }
            }
        }

        public int computeNumbersNextToSymbols() {
            return numbers.stream()
                    .filter(n -> n.isNextToAtLeastOneSymbol(symbols))
                    .mapToInt(n -> n.number).sum();
        }

        /**
         * A gear is any * symbol that is adjacent to exactly two part numbers. Its gear ratio is the result of multiplying those two numbers together.
         *
         * @return
         */
        public int computeGearRatio() {
            return symbols.stream()
                    .filter(s -> s.numberOfAdgacentNumbers == 2)
                    .mapToInt(s -> s.gearRatio)
                    .sum();
        }
    }

    private static class Symbol extends Point {

        public final char sign;

        public int numberOfAdgacentNumbers;
        public int gearRatio = 1;

        public Symbol(final char sign, final int x, final int y) {
            super(x, y);
            this.sign = sign;
        }

        public void addAdjacentNumber(final int number) {
            if (sign == '*' && numberOfAdgacentNumbers <= 2) {
                numberOfAdgacentNumbers++;
                gearRatio *= number;
            }
        }
    }

    private static class SegmentNumber {

        public final Point p1;

        public Point p2;

        public int number;

        public SegmentNumber(final Point p1) {
            this.p1 = p1;
        }

        public boolean isNextToAtLeastOneSymbol(final List<Symbol> symbols) {
            return symbols.stream().filter(isOnSameLineOrAdjacent())
                    .filter(isNotOutSideOfTheHorizontalScope())
                    .peek(s -> s.addAdjacentNumber(number))
                    .findAny()
                    .isPresent();
        }

        private Predicate<Point> isOnSameLineOrAdjacent() {
            return p -> p.y == p1.y || p.y == p1.y + 1 || p.y == p1.y - 1;
        }

        private Predicate<Point> isNotOutSideOfTheHorizontalScope() {
            return p -> !(p.x < p1.x - 1 || p.x > p2.x + 1);
        }
    }
}
