package com.hodvidar.miscellaneous.livecoding;

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


    // more readable method:
    public static String wildcardsEasierToRead(String str) {
        if (isInvalidInput(str)) {
            return "false";
        }

        final String[] wildcardAndWord = str.split("\\s+");
        final char[] wildcard = wildcardAndWord[0].toCharArray();
        final char[] word = wildcardAndWord[1].toCharArray();

        if (isValidWildcardMatch(wildcard, word)) {
            return "true";
        }
        return "false";
    }

    private static boolean isInvalidInput(String str) {
        return str == null || str.isEmpty() || str.split("\\s+").length != 2;
    }

    private static boolean isValidWildcardMatch(char[] wildcard, char[] word) {
        final int maxWildcardIndex = wildcard.length - 1;
        final int maxWordIndex = word.length - 1;
        int wildcardIndex = 0;
        int wordIndex = 0;

        while (wildcardIndex <= maxWildcardIndex && wordIndex <= maxWordIndex) {
            final char wildcardChar = wildcard[wildcardIndex];
            final char wordChar = word[wordIndex];

            switch (wildcardChar) {
                case '+' -> {
                    if (!processAlphaChar(wordChar, wildcardIndex, wordIndex, maxWildcardIndex, maxWordIndex)) {
                        return false;
                    }
                    wildcardIndex++;
                    wordIndex++;
                }
                case '$' -> {
                    if (!processDigitChar(wordChar, wildcardIndex, wordIndex, maxWildcardIndex, maxWordIndex)) {
                        return false;
                    }
                    wildcardIndex++;
                    wordIndex++;
                }
                case '*' -> {
                    int[] indices = processAsterisk(wildcard, word, wildcardIndex, wordIndex, maxWildcardIndex, maxWordIndex);
                    if (indices == null) {
                        return false;
                    }
                    wildcardIndex = indices[0];
                    wordIndex = indices[1];
                }
                default -> {
                    return false;
                }
            }
        }
        return false;
    }

    private static boolean processAlphaChar(char ch, int wildcardIndex, int wordIndex, int maxWildcardIndex, int maxWordIndex) {
        return Character.isAlphabetic(ch) && !(wildcardIndex == maxWildcardIndex && wordIndex == maxWordIndex);
    }

    private static boolean processDigitChar(char ch, int wildcardIndex, int wordIndex, int maxWildcardIndex, int maxWordIndex) {
        return Character.isDigit(ch) && !(wildcardIndex == maxWildcardIndex && wordIndex == maxWordIndex);
    }

    private static int[] processAsterisk(char[] wildcard, char[] word, int wildcardIndex, int wordIndex, int maxWildcardIndex, int maxWordIndex) {
        char wordChar = word[wordIndex];
        if (wildcardIndex + 2 > maxWildcardIndex || wildcard[wildcardIndex + 1] != '{') {
            // Only '*'
            if (wordIndex + 2 > maxWordIndex ||
                    wordChar != word[wordIndex + 1] ||
                    wordChar != word[wordIndex + 2]) {
                return null;
            }
            if (wildcardIndex == maxWildcardIndex && wordIndex + 2 == maxWordIndex) {
                return new int[] {wildcardIndex, wordIndex};
            }
            return new int[] {wildcardIndex + 1, wordIndex + 3};
        } else {
            // Handling '*{?}'
            StringBuilder sb = new StringBuilder();
            char c = '$';
            int i = 0;
            while (c != '}' && wildcardIndex + 2 + i <= maxWildcardIndex) {
                c = wildcard[wildcardIndex + 2 + i];
                if (c == '}') {
                    i = Integer.parseInt(sb.toString());
                    break;
                }
                sb.append(c);
                i++;
            }
            if (c != '}') {
                // Incomplete pattern like '*{2' without closing '}'
                return null;
            }
            for (int j = 1; j <= i - 1; j++) {
                if (wordIndex + j > maxWordIndex || wordChar != word[wordIndex + j]) {
                    return null;
                }
            }
            int numberLength = (int) (Math.log10(i) + 1);
            return new int[] {wildcardIndex + numberLength + 2, wordIndex + i - 1};
        }
    }


}
