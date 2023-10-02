package com.hodvidar.utils.regex;

public class ArrayExtractor {

    public static String[] getArray(String arrayStr) {
        arrayStr = arrayStr.trim();
        return arrayStr.replace("[", "").replace("]", "").split(",");
    }
}
