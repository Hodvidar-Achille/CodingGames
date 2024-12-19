package com.hodvidar.adventofcode.y2024;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day03p2 extends Day03 {

    private static final String ACTIVATE = "a";
    private static final String DESACTIVATE = "d";

    @Override
    public double getDigitFromLine(final String line) {
        final List<String> validOrders = extractValidOrders(line);
        boolean isActive = true;
        double counter = 0;
        for(final String order : validOrders) {
            if(ACTIVATE.equals(order)) {
                isActive = true;
                continue;
            }
            if(DESACTIVATE.equals(order)) {
                isActive = false;
                continue;
            }
            if(!isActive) {
                continue;
            }
            counter += Multiply(order);
        }
        return counter;
    }

    public static List<String> extractValidOrders(final String input) {
        // Define patterns
        final String mulRegex = "mul\\((\\d+),(\\d+)\\)";
        final String doRegex = "do\\(\\)";
        final String dontRegex = "don't\\(\\)";

        // Compile patterns
        final Pattern mulPattern = Pattern.compile(mulRegex);
        final Pattern doPattern = Pattern.compile(doRegex);
        final Pattern dontPattern = Pattern.compile(dontRegex);

        // Matchers
        final Matcher mulMatcher = mulPattern.matcher(input);
        final Matcher doMatcher = doPattern.matcher(input);
        final Matcher dontMatcher = dontPattern.matcher(input);

        // List to hold results
        final List<String> result = new ArrayList<>();

        // Use a temporary list to store matches with their start indexes
        final List<Match> matches = new ArrayList<>();

        // Extract mul(...) matches
        while (mulMatcher.find()) {
            matches.add(new Match(mulMatcher.start(), mulMatcher.group())); // Activated
        }

        // Extract do() matches
        while (doMatcher.find()) {
            matches.add(new Match(doMatcher.start(), "a")); // Activated
        }

        // Extract don't() matches
        while (dontMatcher.find()) {
            matches.add(new Match(dontMatcher.start(), "d")); // Deactivated
        }

        // Sort matches by index to maintain original order
        matches.sort(Comparator.comparingInt(m -> m.index));

        // Add all sorted values to the result list
        for (final Match match : matches) {
            result.add(match.value);
        }

        return result;
    }

    // Helper class to store match and its index
    private static class Match {
        int index;
        String value;

        Match(final int index, final String value) {
            this.index = index;
            this.value = value;
        }
    }
}
