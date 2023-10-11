package com.hodvidar.coderbyte.hard;

public class WildcardAnalyzer {

    public static String wildcards(String str) {
        if (str == null || str.isEmpty()) {
            return "false";
        }
        final String[] wildcardAndWord = str.split("\\s+");
        if (wildcardAndWord.length != 2) {
            return "false";
        }
        final char[] wildcard = wildcardAndWord[0].toCharArray();
        final char[] word = wildcardAndWord[1].toCharArray();

        final int maxWildcardIndex = wildcard.length - 1;
        final int maxWordIndex = word.length - 1;
        int wildcardIndex = 0;
        int wordIndex = 0;
        boolean isValid = false;
        // TODO remove true
        while (wildcardIndex <= maxWildcardIndex && wordIndex <= maxWordIndex) {
            final char wildcardLetter = wildcard[wildcardIndex];
            final char wordLetter = word[wordIndex];
            switch (wildcardLetter) {
                case '+' -> {
                    if (!Character.isAlphabetic(wordLetter)) {
                        return "false";
                    }
                    if (wildcardIndex == maxWildcardIndex && wordIndex == maxWordIndex) {
                        return "true";
                    }
                    wildcardIndex++;
                    wordIndex++;
                }
                case '$' -> {
                    if (!Character.isDigit(wordLetter)) {
                        return "false";
                    }
                    if (wildcardIndex == maxWildcardIndex && wordIndex == maxWordIndex) {
                        return "true";
                    }
                    wildcardIndex++;
                    wordIndex++;
                }
                case '*' -> {
                    // look for {?}
                    if (wildcardIndex + 2 > maxWildcardIndex || wildcard[wildcardIndex + 1] != '{') {
                        // only *
                        // are there 3 characters ?
                        if (wordIndex + 2 > maxWordIndex) {
                            return "false";
                        }
                        // yes but are they the same
                        if (wordLetter != word[wordIndex + 1] || wordLetter != word[wordIndex + 2]) {
                            return "false";
                        }
                        if (wildcardIndex == maxWildcardIndex && wordIndex + 2 == maxWordIndex) {
                            return "true";
                        }
                        wordIndex++;
                        wordIndex++;
                        wordIndex++;
                        wildcardIndex++;
                    } else {
                        // there is a *{?}
                        StringBuilder sb = new StringBuilder();
                        char c = '$';
                        int i = 0;
                        while (c != '}') {
                            c = wildcard[wildcardIndex + 2 + i];
                            if (c == '}') {
                                i = Integer.parseInt(sb.toString());
                                break;
                            }
                            sb.append(c);
                            i++;
                        }
                        for (int j = 1; j <= i - 1; j++) {
                            if (wordIndex + j > maxWordIndex) {
                                return "false";
                            }
                            if (wordLetter != word[wordIndex + j]) {
                                return "false";
                            }
                        }
                        wordIndex += i - 1;
                        int numberLength = (int) (Math.log10(i) + 1);
                        wildcardIndex += numberLength + 2; // { } -> 2    i -> number length
                        if (wildcardIndex == maxWildcardIndex && wordIndex == maxWordIndex) {
                            return "true";
                        }
                    }
                }
                default -> {
                    return "false";
                }
            }
        }
        return "false";
    }
}
