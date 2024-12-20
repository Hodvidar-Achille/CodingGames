package com.hodvidar.codingame.puzzles.medium;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/winamax-battle
 * by Hodvidar
 **/
class WinamaxBattle {

    private static int nbRounds = 0;

    public static void main(final String[] args) {
        final WinamaxBattle s = new WinamaxBattle();
        s.test();
    }

    private static void computeMatch(final LinkedList<Card> p1cards, final LinkedList<Card> p1cardsSide, final LinkedList<Card> p2cards, final LinkedList<Card> p2cardsSide, final boolean isBattle) {
        if (p1cards.isEmpty()) {
            if (isBattle)
                System.out.println("PAT");
            else
                System.out.println("2 " + nbRounds);
            return;
        }
        if (p2cards.isEmpty()) {
            if (isBattle)
                System.out.println("PAT");
            else
                System.out.println("1 " + nbRounds);
            return;
        }
        nbRounds++;
        //System.err.println("round n° : "+nbRounds);
        //printCards(p1cards);
        //printCards(p2cards);
        final Iterator<Card> itep1 = p1cards.iterator();
        final Iterator<Card> itep2 = p2cards.iterator();
        final Card p1 = itep1.next();
        final Card p2 = itep2.next();
        p1cardsSide.add(p1);
        p2cardsSide.add(p2);
        //System.err.println("p1 ("+p1.getN()+") VS p2 ("+p2.getN()+")");
        if (p1.getV() > p2.getV()) {
            //System.err.println("p1 win this round");
            p1cards.remove(p1);
            p2cards.remove(p2);
            p1cards.addAll(p1cardsSide);
            p1cards.addAll(p2cardsSide);
            p1cardsSide.clear();
            p2cardsSide.clear();
            computeMatch(p1cards, p1cardsSide, p2cards, p2cardsSide, false);
        } else if (p2.getV() > p1.getV()) {
            //System.err.println("p2 win this round");
            p1cards.remove(p1);
            p2cards.remove(p2);
            p2cards.addAll(p1cardsSide);
            p2cards.addAll(p2cardsSide);
            p1cardsSide.clear();
            p2cardsSide.clear();
            computeMatch(p1cards, p1cardsSide, p2cards, p2cardsSide, false);
        } else {
            //System.err.println("battle !");
            // battle - remove 3 cards each
            final List<Card> temp1 = new LinkedList<>();
            final List<Card> temp2 = new LinkedList<>();
            for (int i = 0; i < 3; i++) {
                if (!itep1.hasNext() || !itep2.hasNext()) {
                    System.out.println("PAT");
                    return;
                }
                final Card c1 = itep1.next();
                final Card c2 = itep2.next();
                //System.err.println("put aside (p1) (p2) : "+c1.getN()+" |  "+c2.getN());

                p1cardsSide.add(c1);
                temp1.add(c1);

                p2cardsSide.add(c2);
                temp2.add(c2);
            }
            p1cards.remove(p1);
            p2cards.remove(p2);
            final Iterator<Card> i1 = temp1.iterator();
            final Iterator<Card> i2 = temp2.iterator();
            for (int i = 0; i < 3; i++) {
                final Card c3 = i1.next();
                p1cards.remove(c3);

                final Card c4 = i2.next();
                p2cards.remove(c4);

                //System.err.println("remove (p1) (p2) : "+c3.getN()+" |  "+c4.getN());
            }
            nbRounds--;
            computeMatch(p1cards, p1cardsSide, p2cards, p2cardsSide, true);
        }

    }

    @SuppressWarnings("unused")
    private static void printCards(final List<Card> cards) {
        for (final Card c : cards) {
            System.err.print(c.getN() + ", ");
        }
        System.err.print("\n");
    }

    private static int getCardValue(final String s) {
        if (s.contains("A"))
            return 14;
        if (s.contains("K"))
            return 13;
        if (s.contains("Q"))
            return 12;
        if (s.contains("J"))
            return 11;
        if (s.contains("10"))
            return 10;
        if (s.contains("9"))
            return 9;
        if (s.contains("8"))
            return 8;
        if (s.contains("7"))
            return 7;
        if (s.contains("6"))
            return 6;
        if (s.contains("5"))
            return 5;
        if (s.contains("4"))
            return 4;
        if (s.contains("3"))
            return 3;
        if (s.contains("2"))
            return 2;
        return -1;
    }

    private void test() {
        final Scanner in = new Scanner(System.in);
        final int n = in.nextInt(); // the number of cards for player 1
        final LinkedList<Card> p1cards = new LinkedList<>();
        System.err.println("c1 : ");
        for (int i = 0; i < n; i++) {
            final String cardp1 = in.next(); // the n cards of player 1p1cards
            System.err.print(cardp1 + ", ");
            p1cards.add(new Card(cardp1, getCardValue(cardp1)));
        }
        final int m = in.nextInt(); // the number of cards for player 2
        final LinkedList<Card> p2cards = new LinkedList<>();
        System.err.println("\nc2 : ");
        for (int i = 0; i < m; i++) {
            final String cardp2 = in.next(); // the m cards of player 2
            System.err.print(cardp2 + ", ");
            p2cards.add(new Card(cardp2, getCardValue(cardp2)));
        }
        System.err.println();

        final LinkedList<Card> p1cardsSide = new LinkedList<>();
        final LinkedList<Card> p2cardsSide = new LinkedList<>();


        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        // GO !
        computeMatch(p1cards, p1cardsSide, p2cards, p2cardsSide, false);
        in.close();
    }

    // ------------------ INTERNAL CLASSES -----------------------
    class Card {
        private final String name;
        private final Integer value;

        public Card(final String name, final Integer value) {
            this.name = name;
            this.value = value;
        }

        public String getN() {
            return this.name;
        }

        public int getV() {
            return this.value;
        }
    }
}

