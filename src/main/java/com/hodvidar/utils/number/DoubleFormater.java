package com.hodvidar.utils.number;

import java.text.NumberFormat;

public final class DoubleFormater {
    public static final String asInteger(final double d) {
        final NumberFormat nf = NumberFormat.getNumberInstance();
        nf.setMaximumFractionDigits(0);
        nf.setGroupingUsed(false);
        return nf.format(d);
    }
}
