package com.hodvidar.utils.number;

import java.text.NumberFormat;

public final class DoubleFormater {

    private DoubleFormater() {
        throw new IllegalStateException("Utility class");
    }

    public static String asInteger(final double d) {
        final NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(0);
        nf.setGroupingUsed(false);
        return nf.format(d);
    }

    public static double roundTo3rdDecimal(final double d) {
        return Math.round(d * 1000.0) / 1000.0;
    }
}
