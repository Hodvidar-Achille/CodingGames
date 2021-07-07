package com.hodvidar.miscellaneous.livecoding;

// https://leetcode.com/problems/bulb-switcher-iii/
public class LigthBulb {

    // O(n2)
    public static int numTimesAllBlue(final int[] lights) {
        int counter = 0;
        final boolean[] isOn = new boolean[lights.length];
        for (final int indexOfBulb : lights) {
            final int i = indexOfBulb - 1;
            isOn[i] = true;
            if (areAllOnBlue(isOn)) {
                counter += 1;
            }
        }
        return counter;
    }

    /**
     * Are all bulb ON have only ON bulb on their "left" (index <)
     */
    private static boolean areAllOnBlue(final boolean[] isOn) {
        for (int i = isOn.length - 1; i >= 0; i--) {
            if (isOn[i]) {
                return isBlue(isOn, i);
            }
        }
        throw new IllegalStateException("Should not happen");
    }

    private static boolean isBlue(final boolean[] isOn, final int index) {
        for (int i = index; i >= 0; i--) {
            if (!isOn[i]) {
                return false;
            }
        }
        return true;
    }
}