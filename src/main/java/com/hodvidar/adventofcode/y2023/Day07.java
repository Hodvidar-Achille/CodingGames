package com.hodvidar.adventofcode.y2023;

import java.io.FileNotFoundException;
import java.util.*;

public class Day07 extends AbstractAdventOfCode2023 {

    private CamelCardGame game;

    @Override
    public int getDay() {
        return 7;
    }

    @Override
    public double getResultDouble(final Scanner sc) throws FileNotFoundException {
        game = new CamelCardGame();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        return game.getTotalBid();
    }

    @Override
    protected void digestLine(final String line) {
        final var cardAndBid = line.split("\\s+");
        game.addHand(cardAndBid[0], Double.parseDouble(cardAndBid[1]));
    }

    private static class CamelCardGame {
        private final List<CamelCardHand> hands = new ArrayList<>();

        public void addHand(final String card, final double bid) {
            hands.add(new CamelCardHand(card, bid));
        }

        public double getTotalBid() {
            final var handsOrdered = hands.stream()
                    .sorted(Comparator.comparing(CamelCardHand::getType).reversed()
                            .thenComparing(CamelCardHand::getFirstCardValue).reversed()
                            .thenComparing(CamelCardHand::getSecondCardValue).reversed()
                            .thenComparing(CamelCardHand::getThirdCardValue).reversed()
                            .thenComparing(CamelCardHand::getFourthCardValue).reversed()
                            .thenComparing(CamelCardHand::getFifthCardValue).reversed())
                    .toList();
            for (int i = 1; i <= handsOrdered.size(); i++) {
                handsOrdered.get(i - 1).bid = i * handsOrdered.get(i - 1).bid;
            }
            return handsOrdered.stream().mapToDouble(h -> h.bid).sum();
        }
    }

    private static class CamelCardHand {
        public final char[] cards;
        public double bid;

        public final CamelCardHandType type;

        private Integer firstCardValue = null;
        private Integer secondCardValue = null;
        private Integer thirdCardValue = null;
        private Integer fourthCardValue = null;
        private Integer fifthCardValue = null;

        public CamelCardHand(final String card, final double bid) {
            this.cards = card.toCharArray();
            this.bid = bid;
            this.type = getType();
        }

        public CamelCardHandType getType() {
            if (this.type != null) {
                return this.type;
            }
            final Map<Character, Integer> frequencyMap = new HashMap<>();
            for (final char card : cards) {
                frequencyMap.put(card, frequencyMap.getOrDefault(card, 0) + 1);
            }
            return switch (frequencyMap.values().stream().max(Integer::compareTo).orElse(1)) {
                case 1 -> CamelCardHandType.HIGH_CARD;
                case 2 -> frequencyMap.values().stream().filter(i -> i == 2).count() == 2
                        ? CamelCardHandType.TWO_PAIR
                        : CamelCardHandType.PAIR;
                case 3 -> frequencyMap.values().stream().anyMatch(i -> i == 2)
                        ? CamelCardHandType.FULL_HOUSE
                        : CamelCardHandType.TRIPLE;
                case 4 -> CamelCardHandType.FOUR_OF_A_KIND;
                case 5 -> CamelCardHandType.FIND_OF_A_KIND;
                default -> throw new IllegalStateException("Unexpected value: ");
            };
        }

        public int getFirstCardValue() {
            if (firstCardValue == null) {
                firstCardValue = getCardValue(cards[0]);
            }
            return firstCardValue;
        }

        public int getSecondCardValue() {
            if (secondCardValue == null) {
                secondCardValue = getCardValue(cards[1]);
            }
            return secondCardValue;
        }

        public int getThirdCardValue() {
            if (thirdCardValue == null) {
                thirdCardValue = getCardValue(cards[2]);
            }
            return thirdCardValue;
        }

        public int getFourthCardValue() {
            if (fourthCardValue == null) {
                fourthCardValue = getCardValue(cards[3]);
            }
            return fourthCardValue;
        }

        public int getFifthCardValue() {
            if (fifthCardValue == null) {
                fifthCardValue = getCardValue(cards[4]);
            }
            return fifthCardValue;
        }

        private int getCardValue(final char card) {
            return switch (card) {
                case 'A' -> 14;
                case 'K' -> 13;
                case 'Q' -> 12;
                case 'J' -> 11;
                case 'T' -> 10;
                default -> Integer.parseInt(String.valueOf(card));
            };
        }


    }

    private static enum CamelCardHandType {
        HIGH_CARD(1), PAIR(2), TWO_PAIR(3), TRIPLE(4), FULL_HOUSE(5), FOUR_OF_A_KIND(6), FIND_OF_A_KIND(7);
        public final int power;

        private CamelCardHandType(final int power) {
            this.power = power;
        }
    }
}
