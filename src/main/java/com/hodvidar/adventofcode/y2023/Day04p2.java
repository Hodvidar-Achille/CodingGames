package com.hodvidar.adventofcode.y2023;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Day04p2 extends Day04 {

    private CardHolder cardHolder;

    @Override
    public int getResult(final Scanner sc) {
        cardHolder = new CardHolder();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        return cardHolder.computeCards();
    }

    @Override
    protected void digestLine(final String line) {
        final Pattern pattern = Pattern.compile("Card\\s+(\\d+)");
        final Matcher matcher = pattern.matcher(line);
        int cardId = 0;
        if (matcher.find()) {
            cardId = Integer.parseInt(matcher.group(1));
        }
        final String[] partsAfterCardNumber = line.split(": ")[1].split("\\|");
        final int[] winningNumbers = Arrays.stream(partsAfterCardNumber[0].trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        final int[] numbers = Arrays.stream(partsAfterCardNumber[1].trim().split("\\s+")).mapToInt(Integer::parseInt).toArray();
        cardHolder.addCard(cardId, winningNumbers, numbers);
    }

    private static class CardHolder {
        private final SortedMap<Integer, Card> cards = new TreeMap<>();

        public void addCard(final int id, final int[] winningNumbers, final int[] numbers) {
            cards.put(id, new Card(id, winningNumbers, numbers));
        }

        public int computeCards() {
            useWinningNumbersToAddCopies();
            return cards.values().stream().mapToInt(c -> c.numberOfOccurrence).sum();
        }

        public void useWinningNumbersToAddCopies() {
            for (final Map.Entry<Integer, Card> cardEntry : cards.entrySet()) {
                for (int i = 1; i <= cardEntry.getValue().numberOfWinningNumbers; i++) {
                    final var card = cards.get(cardEntry.getKey() + i);
                    if (card != null) {
                        card.numberOfOccurrence += cardEntry.getValue().numberOfOccurrence;
                    }
                }
            }
        }
    }

    private static class Card {
        final int id;
        final int numberOfWinningNumbers;
        int numberOfOccurrence;

        private Card(final int id, final int[] winningNumbers, final int[] numbers) {
            this.id = id;
            this.numberOfWinningNumbers = Arrays.stream(numbers).distinct().filter(n -> Arrays.stream(winningNumbers).anyMatch(n2 -> n2 == n)).toArray().length;
            numberOfOccurrence = 1;
        }
    }
}
