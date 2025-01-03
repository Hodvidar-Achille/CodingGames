package com.hodvidar.adventofcode.y2023;

import java.util.*;

public class Day07 extends AbstractAdventOfCode2023 {

    private CamelCardGame game;

    @Override
    public int getDay() {
        return 7;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        game = getGame();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        return game.getTotalBid();
    }

    protected CamelCardGame getGame() {
        return new CamelCardGame();
    }

    @Override
    protected void digestLine(final String line) {
        final var cardAndBid = line.split("\\s+");
        game.addHand(cardAndBid[0], Double.parseDouble(cardAndBid[1]));
    }

    protected enum CamelCardHandType {
        HIGH_CARD(1), PAIR(2), TWO_PAIR(3), TRIPLE(4), FULL_HOUSE(5), FOUR_OF_A_KIND(6), FIVE_OF_A_KIND(7);
        public final int power;

        CamelCardHandType(final int power) {
            this.power = power;
        }

        public CamelCardHandType getTypeWithJoker(final int numberOfJokers) {
            if (numberOfJokers == 0) {
                return this;
            }
            if (this == FIVE_OF_A_KIND || this == FULL_HOUSE) {
                return this;
            }
            return getNext().getTypeWithJoker(numberOfJokers - 1);
        }

        private CamelCardHandType getNext() {
            return switch (this) {
                case HIGH_CARD -> PAIR;
                case PAIR -> TRIPLE;
                case TWO_PAIR, FULL_HOUSE -> FULL_HOUSE;
                case TRIPLE -> FOUR_OF_A_KIND;
                case FOUR_OF_A_KIND, FIVE_OF_A_KIND -> FIVE_OF_A_KIND;
            };
        }
    }

    protected class CamelCardGame {
        private final List<CamelCardHand> hands = new ArrayList<>();

        public void addHand(final String card, final double bid) {
            hands.add(getCamelCardHand(card, bid));
        }

        protected CamelCardHand getCamelCardHand(final String card, final double bid) {
            return new CamelCardHand(card, bid);
        }

        public double getTotalBid() {
            final var handsOrdered = hands.stream()
                    .sorted(Comparator.comparing(CamelCardHand::getType)
                            .thenComparing(CamelCardHand::getFirstCardValue)
                            .thenComparing(CamelCardHand::getSecondCardValue)
                            .thenComparing(CamelCardHand::getThirdCardValue)
                            .thenComparing(CamelCardHand::getFourthCardValue)
                            .thenComparing(CamelCardHand::getFifthCardValue))
                    .toList();
            handsOrdered.reversed();
            for (int i = 1; i <= handsOrdered.size(); i++) {
                handsOrdered.get(i - 1).bid = i * handsOrdered.get(i - 1).bid;
            }
            return handsOrdered.stream().mapToDouble(h -> h.bid).sum();
        }
    }

    protected class CamelCardHand {
        public final char[] cards;
        public final CamelCardHandType type;
        public double bid;
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
            return getCamelCardHandType(frequencyMap.values().stream().max(Integer::compareTo).orElse(1), frequencyMap);
        }

        protected CamelCardHandType getCamelCardHandType(final int maxFrequency, final Map<Character, Integer> frequencyMap) {
            return switch (maxFrequency) {
                case 1 -> CamelCardHandType.HIGH_CARD;
                case 2 -> frequencyMap.values().stream().filter(i -> i == 2).count() == 2
                        ? CamelCardHandType.TWO_PAIR
                        : CamelCardHandType.PAIR;
                case 3 -> frequencyMap.values().stream().anyMatch(i -> i == 2)
                        ? CamelCardHandType.FULL_HOUSE
                        : CamelCardHandType.TRIPLE;
                case 4 -> CamelCardHandType.FOUR_OF_A_KIND;
                default -> CamelCardHandType.FIVE_OF_A_KIND;
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

        protected int getCardValue(final char card) {
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
}
