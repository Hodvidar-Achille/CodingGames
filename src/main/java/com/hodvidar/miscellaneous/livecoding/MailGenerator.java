package com.hodvidar.miscellaneous.livecoding;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


public class MailGenerator {

    private static String getMailForOnePerson(final String fullName,
                                              final String company,
                                              final Map<String, Integer> nameForMailOccurrences) {
        final String[] splitFullName = fullName.toLowerCase().split(" ");

        String initialAndSurname =
                IntStream.range(0, splitFullName.length)
                        .mapToObj(transformFullName(splitFullName))
                        .collect(Collectors.joining("_"));

        initialAndSurname = handlesDuplication(nameForMailOccurrences, initialAndSurname);
        final String email =
                "<"
                        + initialAndSurname
                        + "@"
                        + company.toLowerCase()
                        + ".com"
                        + ">";
        return fullName + " " + email;
    }

    private static IntFunction<String> transformFullName(final String[] splitFullName) {
        return i
                ->
                isSurname(splitFullName.length, i) ?
                        transformSurname(splitFullName[i]) :
                        transformFirstName(splitFullName[i]);
    }

    private static boolean isSurname(final int arraySize, final int i) {
        return i == (arraySize - 1);
    }

    private static String transformFirstName(final String s) {
        return String.valueOf(s.charAt(0));
    }

    /**
     * Result surname must be max 8 characters long and not contain
     * special characters.
     * (Only special character Dash "-" checked here).
     */
    private static String transformSurname(final String surname) {
        return surname
                .replace("-", "")
                .substring(
                        0,
                        Math.min(surname.length(),
                                8)
                );
    }

    private static String handlesDuplication(final Map<String, Integer> nameForMailOccurrences,
                                             String nameForMail) {
        if (!nameForMailOccurrences.containsKey(nameForMail)) {
            nameForMailOccurrences.put(nameForMail, 1);
            return nameForMail;
        }
        nameForMailOccurrences.put(nameForMail, nameForMailOccurrences.get(nameForMail) + 1);
        nameForMail = nameForMail + nameForMailOccurrences.get(nameForMail);
        return nameForMail;
    }

    public String generateMails(final String names, final String company) {
        final Map<String, Integer> nameForMailOccurrences = new HashMap<>();
        return Arrays.stream(names.split(", "))
                .map(name -> getMailForOnePerson(name, company, nameForMailOccurrences))
                .collect(Collectors.joining(", "));
    }

}
