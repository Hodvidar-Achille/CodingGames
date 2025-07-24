package com.hodvidar.adventofcode.y2023;

import java.util.List;

public class Day13p2 extends Day13 {

    @Override
    protected ValleyOfMirrors getValleyOfMirrors() {
        return new ValleyOfMirrorsV2();
    }

    private static class ValleyOfMirrorsV2 extends ValleyOfMirrors {
        @Override
        protected ValleyPattern getValleyPattern() {
            return new ValleyPatternP2();
        }
    }

    private static class ValleyPatternP2 extends ValleyPattern {

        private boolean foundSmudge = false;

        @Override
        protected boolean checkIfMirror(final List<String> lines, final int backwardIndex, final int forwardIndex) {
            if (forwardIndex >= lines.size() || backwardIndex < 0) {
                return foundSmudge;
            }
            if (!lines.get(forwardIndex).equals(lines.get(backwardIndex))) {
                if (!foundSmudge) {
                    if (isOnlyOneDifference(lines.get(forwardIndex), lines.get(backwardIndex))) {
                        foundSmudge = true;
                        return checkIfMirror(lines, backwardIndex - 1, forwardIndex + 1);
                    }
                }
                foundSmudge = false;
                return false;
            }
            return checkIfMirror(lines, backwardIndex - 1, forwardIndex + 1);
        }

        private boolean isOnlyOneDifference(final String line1, final String line2) {
            int difference = 0;
            for (int i = 0; i < line1.length(); i++) {
                if (line1.charAt(i) != line2.charAt(i)) {
                    difference++;
                }
                if (difference > 1) {
                    return false;
                }
            }
            return true;
        }
    }
}
