package com.hodvidar.utils.regex;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NumberExtractor {

    private NumberExtractor() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * ex : "x=-1, y=12, z=-100" will become list of doubles : -1.0, 12.0, -100.0
     *
     * @param s a String
     * @return List<Double> from numbers in the string
     */
    public static List<Double> extractNumber(final String s) {
        final List<Double> numbers = new ArrayList<>();
        final Pattern p = Pattern.compile("-?\\d+(\\.\\d+)?");
        final Matcher m = p.matcher(s);
        while (m.find()) {
            final String a = m.group();
            final Double b = Double.parseDouble(a);
            numbers.add(b);
        }
        return numbers;
    }

    /**
     * -105 beans and 13 carrots, 0 --> -105
     *
     * @param s
     * @return first integer in String (with sign)
     */
    public static int extractFirstInteger(final String s) {
        return (int) (double) extractNumber(s).get(0);
    }

    /**
     * 12 white bags --> 12 /!\ -106 green bean and 11 carrots --> 10611
     *
     * @param s
     * @return One int using all numbers in the string attached to each others.
     */
    public static int extractInteger(String s) {
        s = s.replaceAll("[^0-9]", "");
        return Integer.parseInt(s);
    }


    public static int[] getArray(String arrayStr) {
        arrayStr = arrayStr.trim();
        arrayStr = arrayStr.replace("[", "").replace("]", "");
        if (arrayStr.isEmpty())
            return new int[0];
        return Arrays.stream(arrayStr.split(","))
                .mapToInt(element -> Integer.parseInt(element.trim()))
                .toArray();
    }

    public static double[] getArrayOfDouble(String arrayStr) {
        arrayStr = arrayStr.trim();
        arrayStr = arrayStr.replace("[", "").replace("]", "");
        if (arrayStr.isEmpty())
            return new double[0];
        return Arrays.stream(arrayStr.split(","))
                .mapToDouble(element -> Double.parseDouble(element.trim()))
                .toArray();
    }
}
