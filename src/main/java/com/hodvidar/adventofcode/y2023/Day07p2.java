package com.hodvidar.adventofcode.y2023;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class Day07p2 extends Day07 {

    public static final char JOKER = 'J';

    @Override
    protected CamelCardGame getGame() {
        return new CamelCardGameV2();
    }

    private class CamelCardGameV2 extends CamelCardGame {

        @Override
        protected CamelCardHand getCamelCardHand(final String card, final double bid) {
            return new CamelCardHandV2(card, bid);
        }
    }

    private class CamelCardHandV2 extends CamelCardHand {

        public CamelCardHandV2(final String card, final double bid) {
            super(card, bid);
        }

        @Override
        public CamelCardHandType getType() {
            if (this.type != null) {
                return this.type;
            }
            Map<Character, Integer> frequencyMap = new HashMap<>();
            for (final char card : cards) {
                frequencyMap.put(card, frequencyMap.getOrDefault(card, 0) + 1);
            }
            final int numberOfJokers = frequencyMap.getOrDefault(JOKER, 0);
            frequencyMap =  frequencyMap.entrySet().stream()
                    .filter(e -> e.getKey() != JOKER)
                    .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            final int maxFrequency = frequencyMap.values().stream().max(Integer::compareTo).orElse(1);
            return getCamelCardHandType(maxFrequency, frequencyMap).getTypeWithJoker(numberOfJokers);
        }

        @Override
        protected int getCardValue(final char card) {
            return (card == JOKER) ? 0 : super.getCardValue(card);
        }
    }
}
