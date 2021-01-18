package com.hodvidar.utils.regex;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class NumberExtractor {

    /**
     * ex : "x=-1, y=12, z=-100" will become list of doubles : -1.0, 12.0, -100.0
     *
     * @param s a String
     * @return List<Double> from numbers in the string
     */
    public static final List<Double> extractNumber(String s) {
        List<Double> numbers = new ArrayList<>();
        Pattern p = Pattern.compile("-?\\d+");
        Matcher m = p.matcher(s);
        while (m.find()) {
            String a = m.group();
            Double b = Double.parseDouble(a);
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
    public static final int extractFirstInteger(String s) {
        return (int) (double) extractNumber(s).get(0);
    }

    /**
     * 12 white bags --> 12
     * /!\ -106 green bean and 11 carrots --> 10611
     *
     * @param s
     * @return One int using all numbers in the string attached to each others.
     */
    public static int extractInteger(String s) {
        s = s.replaceAll("[^0-9]", "");
        return Integer.parseInt(s);
    }
}
