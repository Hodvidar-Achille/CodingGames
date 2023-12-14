package com.hodvidar.adventofcode.y2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day13 extends AbstractAdventOfCode2023 {

    private ValleyOfMirrors valleyOfMirrors;

    @Override
    public int getDay() {
        return 13;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        valleyOfMirrors = getValleyOfMirrors();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        return valleyOfMirrors.getSummarizedSum();
    }

    protected ValleyOfMirrors getValleyOfMirrors() {
        return new ValleyOfMirrors();
    }

    @Override
    protected void digestLine(final String line) {
        valleyOfMirrors.addNewLinePattern(line);
    }

    protected static class ValleyOfMirrors {
        private final List<ValleyPattern> patterns = new ArrayList<>();

        private int currentPatternIndex = 0;

        public ValleyOfMirrors() {
            patterns.add(getValleyPattern());
        }

        protected ValleyPattern getValleyPattern() {
            return new ValleyPattern();
        }

        public void addNewLinePattern(final String line) {
            if (line.isBlank()) {
                patterns.add(getValleyPattern());
                currentPatternIndex++;
                return;
            }
            patterns.get(currentPatternIndex).addHorizontalLine(line);
        }

        public int getSummarizedSum() {
            return patterns.stream().mapToInt(ValleyPattern::getSummarizedMirrorPosition).sum();
        }
    }

    protected static class ValleyPattern {
        private final List<String> horizontalLines = new ArrayList<>();
        private final List<String> verticalLines = new ArrayList<>();

        public void addHorizontalLine(final String line) {
            horizontalLines.add(line);
        }

        public int getSummarizedMirrorPosition() {
            int position = getPositionFromLines(horizontalLines);
            if (position != -1) {
                return position * 100;
            }
            if (verticalLines.isEmpty()) {
                buildVerticalLines();
            }
            position = getPositionFromLines(verticalLines);
            return position;
        }

        private void buildVerticalLines() {
            final int numberOfHorizontalLines = horizontalLines.size();
            final int numberOfVerticalLines = horizontalLines.get(0).length();
            for (int i = 0; i < numberOfVerticalLines; i++) {
                final StringBuilder sb = new StringBuilder();
                for (int j = 0; j < numberOfHorizontalLines; j++) {
                    sb.append(horizontalLines.get(j).charAt(i));
                }
                verticalLines.add(sb.toString());
            }
        }

        private int getPositionFromLines(final List<String> lines) {
            for (int i = 1; i < lines.size(); i++) {
                if (checkIfMirror(lines, i - 1, i)) {
                    return i;
                }
            }
            return -1;
        }

        protected boolean checkIfMirror(final List<String> lines,
                                      final int backwardIndex,
                                      final int forwardIndex) {
            if (forwardIndex >= lines.size() || backwardIndex < 0) {
                return true;
            }
            if (!lines.get(forwardIndex).equals(lines.get(backwardIndex))) {
                return false;
            }
            return checkIfMirror(lines, backwardIndex - 1, forwardIndex + 1);
        }
    }
}
