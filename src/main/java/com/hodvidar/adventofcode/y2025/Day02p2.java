package com.hodvidar.adventofcode.y2025;

import java.math.BigDecimal;

public class Day02p2 extends Day02 {

    @Override
    protected boolean isInvalid(final double value) {
        String plain = new BigDecimal(Double.toString(value)).toPlainString();
        final int dotIdx = plain.indexOf('.');
        if (dotIdx != -1) {
            plain = plain.substring(0, dotIdx);
        }
        final int n = plain.length();
        if (n == 0) {
            return false;
        }
        for (int blockLen = 1; blockLen <= n / 2; blockLen++) {
            if (n % blockLen != 0) {
                continue;
            }
            final String block = plain.substring(0, blockLen);
            boolean allMatch = true;
            // Compare each subsequent block with the first one
            for (int pos = blockLen; pos < n; pos += blockLen) {
                if (!plain.regionMatches(pos, block, 0, blockLen)) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) {
                return true;
            }
        }
        return false;
    }
}
