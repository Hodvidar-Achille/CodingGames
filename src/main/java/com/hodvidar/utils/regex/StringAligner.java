package com.hodvidar.utils.regex;

public class StringAligner {

    public static String center(final String string, final int size) {
        return center(string, size, ' ');
    }

    public static String center(final String string, final int size, final char pad) {
        if (string == null || size <= string.length()) {
            return string;
        }
        final StringBuilder sb = new StringBuilder(size);
        for (int i = 0; i < (size - string.length()) / 2; i++) {
            sb.append(pad);
        }
        sb.append(string);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }

    public static String alignLeft(final String string, final int size) {
        return alignLeft(string, size, ' ');
    }

    public static String alignLeft(final String string, final int size, final char pad) {
        if (string == null || size <= string.length()) {
            return string;
        }
        final StringBuilder sb = new StringBuilder(size);
        sb.append(string);
        while (sb.length() < size) {
            sb.append(pad);
        }
        return sb.toString();
    }
}
