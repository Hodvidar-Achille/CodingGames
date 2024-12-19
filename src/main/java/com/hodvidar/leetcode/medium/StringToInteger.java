package com.hodvidar.leetcode.medium;

// https://leetcode.com/problems/string-to-integer-atoi/
public class StringToInteger {

    public static int myAtoi(String s) {
        if (s == null)
            return 0;
        s = s.trim();
        if (s.isEmpty())
            return 0;
        final StringBuilder sb = new StringBuilder();
        boolean isNegative = false;
        boolean removedFirstSign = false;
        boolean alreadyMetNumber = false;
        boolean alreadyMetLeadingZero = false;
        boolean isValid = false;
        for (final char c : s.toCharArray()) {
            if (c == '0') {
                alreadyMetLeadingZero = true;
                if (!alreadyMetNumber)
                    continue;
            }
            if (c == '-' || c == '+') {
                if (alreadyMetNumber)
                    break;
                if (alreadyMetLeadingZero)
                    return 0;
                if (removedFirstSign)
                    return 0;
                removedFirstSign = true;
                if (c == '-')
                    isNegative = true;
                continue;
            }
            if (!alreadyMetNumber && (c < '0' || c > '9'))
                return 0;
            else if (c < '0' || c > '9') {
                break;
            }
            alreadyMetNumber = true;
            sb.append(c);
            isValid = true;
        }
        if (!isValid)
            return 0;
        String numberStr = sb.toString();
        final int number;
        try {
            if (isNegative)
                numberStr = "-" + numberStr;
            number = (int) Double.parseDouble(numberStr);
        } catch (final NumberFormatException e) {
            return isNegative ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        }
        return number;
    }
}
