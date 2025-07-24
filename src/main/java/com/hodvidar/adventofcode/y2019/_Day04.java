package com.hodvidar.adventofcode.y2019;

/**
 * puzzle input : 272091-815432
 *
 * @author Hodvidar
 */
public final class _Day04 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;
    private static final int MIN = 272091;
    private static final int MAX = 815432;

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        final double result = test();
        System.err.println("result='" + result + "'");
    }

    private static double test() throws Exception {
        double counter = 0;
        for (int i1 = 1; i1 <= 9; i1++) {
            for (int i2 = i1; i2 <= 9; i2++) {
                for (int i3 = i2; i3 <= 9; i3++) {
                    for (int i4 = i3; i4 <= 9; i4++) {
                        for (int i5 = i4; i5 <= 9; i5++) {
                            for (int i6 = i5; i6 <= 9; i6++) {
                                if (isOK(i1, i2, i3, i4, i5, i6))
                                    counter++;
                            }
                        }
                    }
                }
            }
        }

        return counter;
    }

    private static boolean isOK(final int i1, final int i2, final int i3, final int i4, final int i5, final int i6) {
        return isOneDouble(i1, i2, i3, i4, i5, i6) && isInRange(i1, i2, i3, i4, i5, i6);
    }

    private static boolean isOneDouble(final int i1, final int i2, final int i3, final int i4, final int i5, final int i6) {
        return i1 == i2 || i2 == i3 || i3 == i4 || i4 == i5 || i5 == i6;
    }

    private static boolean isInRange(final int i1, final int i2, final int i3, final int i4, final int i5, final int i6) {
        final String s = "" + i1 + i2 + i3 + i4 + i5 + i6;
        final int i = Integer.parseInt(s);
        return i > MIN && i < MAX;
    }

}
