package com.hodvidar.miscellaneous.livecoding;

public class StringAnalyzer {

    /**
     * The given String is 2 part separated by a space. <br/>
     * The first part is a pattern, the second part is a word. <br/>
     * The pattern can contain the following characters: <br/>
     * - '+' which means any alphabetical character <br/>
     * - '$' which means any numerical character <br/>
     * - '*' which means any character 3 times<br/>
     * - '*' can be followed by '{n}' where n is a number, which means the previous character can be repeated n times <br/>
     * n is at least 1.
     * <p>
     * Example: "++$$**{10} zz99eee8888888888" is true
     *
     * @param patternAndWordStr the given string
     * @return <code>true</code> only if the word follow the pattern
     */
    public static boolean analyze(final String patternAndWordStr) {
        if (patternAndWordStr == null || patternAndWordStr.isEmpty()) {
            return false;
        }
        final String[] patternAndWord = patternAndWordStr.split("\\s+");
        if (patternAndWord.length != 2) {
            return false;
        }
        final char[] pattern = patternAndWord[0].toCharArray();
        final char[] word = patternAndWord[1].toCharArray();

        final int maxPatternIndex = pattern.length - 1;
        final int maxWordIndex = word.length - 1;
        int patternIndex = -1;
        int wordIndex = -1;
        while (patternIndex <= maxPatternIndex - 1 && wordIndex <= maxWordIndex - 1) {
            patternIndex++;
            wordIndex++;
            final char patternLetter = pattern[patternIndex];
            final char wordLetter = word[wordIndex];
            switch (patternLetter) {
                case '+' -> {
                    if (!Character.isAlphabetic(wordLetter)) {
                        return false;
                    }
                    if (patternIndex == maxPatternIndex && wordIndex == maxWordIndex) {
                        return true;
                    }
                }
                case '$' -> {
                    if (!Character.isDigit(wordLetter)) {
                        return false;
                    }
                    if (patternIndex == maxPatternIndex && wordIndex == maxWordIndex) {
                        return true;
                    }
                }
                case '*' -> {
                    // look for {?}
                    if (patternIndex + 2 > maxPatternIndex || pattern[patternIndex + 1] != '{') {
                        // only *
                        // are there 3 characters ?
                        if (wordIndex + 2 > maxWordIndex) {
                            return false;
                        }
                        // yes but are they the same
                        if (wordLetter != word[wordIndex + 1] || wordLetter != word[wordIndex + 2]) {
                            return false;
                        }
                        if (patternIndex == maxPatternIndex && wordIndex + 2 == maxWordIndex) {
                            return true;
                        }
                        wordIndex += 2;
                    } else {
                        // there is a *{?}
                        final StringBuilder sb = new StringBuilder();
                        char c = '$';
                        int i = 0;
                        while (c != '}') {
                            c = pattern[patternIndex + 2 + i];
                            if (c == '}') {
                                i = Integer.parseInt(sb.toString());
                                break;
                            }
                            sb.append(c);
                            i++;
                        }
                        for (int j = 1; j <= i - 1; j++) {
                            if (wordIndex + j > maxWordIndex) {
                                return false;
                            }
                            if (wordLetter != word[wordIndex + j]) {
                                return false;
                            }
                        }
                        wordIndex += i - 1;
                        final int numberLength = (int) (Math.log10(i) + 1);
                        patternIndex += numberLength + 2; // { } -> 2    i -> number length
                        if (patternIndex == maxPatternIndex && wordIndex == maxWordIndex) {
                            return true;
                        }
                    }
                }
                default -> {
                    return false;
                }
            }
        }
        return false;
    }


}
