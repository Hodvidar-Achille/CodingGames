package com.hodvidar.adventofcode.y2023;

import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class _Day04_2 extends _Day04 {


    private CardHolder cardHolder;

    @Override
    public int getResult(final Scanner sc) throws FileNotFoundException {
        cardHolder = new CardHolder();
        while (sc.hasNext()) {
            getDigitFromLine(sc.nextLine());
        }
        return cardHolder.computeCards();
    }

    @Override
    protected int getDigitFromLine(final String line) {
        final Pattern pattern = Pattern.compile("Card (\\d+)");
        final Matcher matcher = pattern.matcher(line);
        int cardId = 0;
        if (matcher.find()) {
            cardId = Integer.parseInt(matcher.group(1));
        }
        final String[] partsAfterCardNumber = line.split(": ")[1].split("\\|");
        final int[] winningNumbers = Arrays.stream(partsAfterCardNumber[0].trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        final int[] numbers = Arrays.stream(partsAfterCardNumber[1].trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        cardHolder.addCard(cardId, winningNumbers, numbers);
        return 0;
    }

    private static class CardHolder {
        private final Map<Integer, Card> cards = new HashMap<>();

        public void addCard(final int id, final int[] winningNumbers, final int[] numbers) {
            cards.put(id, new Card(id, winningNumbers, numbers));
        }

        public int computeCards() {
            // TODO compute cards using numberOfOccurrence and their id
            return 0;
        }
    }

    private static class Card {
        final int id;
        final int[] winningNumbers;
        final int[] numbers;
        final int numberOfOccurrence;

        private Card(final int id, final int[] winningNumbers, final int[] numbers) {
            this.id = id;
            this.winningNumbers = winningNumbers;
            this.numbers = numbers;
            this.numberOfOccurrence = Arrays.stream(numbers).distinct().filter(n -> Arrays.stream(winningNumbers).anyMatch(n2 -> n2 == n)).toArray().length;
        }
    }
}
