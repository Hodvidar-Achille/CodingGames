import java.util.*;
import java.io.*;
import java.math.*;

/**
 *    https://www.codingame.com/ide/puzzle/winamax-battle
 * by Hodvidar
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // the number of cards for player 1
        LinkedList<Card> p1cards = new LinkedList<>();
        System.err.println("c1 : ");
        for (int i = 0; i < n; i++) {
            String cardp1 = in.next(); // the n cards of player 1p1cards
            System.err.print(cardp1+", ");
            p1cards.add(new Card(cardp1, getCardValue(cardp1)));
        }
        int m = in.nextInt(); // the number of cards for player 2
        LinkedList<Card> p2cards = new LinkedList<>();
        System.err.println("\nc2 : ");
        for (int i = 0; i < m; i++) {
            String cardp2 = in.next(); // the m cards of player 2
            System.err.print(cardp2+", ");
            p2cards.add(new Card(cardp2, getCardValue(cardp2)));
        }
        System.err.println("");

        LinkedList<Card> p1cardsSide = new LinkedList<>();
        LinkedList<Card> p2cardsSide = new LinkedList<>();
        
        
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        // GO !
       computeMatch(p1cards, p1cardsSide, p2cards, p2cardsSide, false);
    }
    
    private static int nbRounds = 0;
    
    private static void computeMatch(LinkedList<Card> p1cards, LinkedList<Card> p1cardsSide, LinkedList<Card> p2cards, LinkedList<Card> p2cardsSide, boolean isBattle)
    {
        if(p1cards.isEmpty())
        {
            if(isBattle)
                System.out.println("PAT");
            else
                System.out.println("2 "+nbRounds);
            return;
        }
        if(p2cards.isEmpty())
        {
            if(isBattle)
                System.out.println("PAT");
            else
                System.out.println("1 "+nbRounds);
            return;
        }
        nbRounds++;
        //System.err.println("round n° : "+nbRounds);
        //printCards(p1cards);
        //printCards(p2cards);
        Iterator<Card> itep1 = p1cards.iterator();
        Iterator<Card> itep2 = p2cards.iterator();
        Card p1 = itep1.next();
        Card p2 = itep2.next();
        p1cardsSide.add(p1);
        p2cardsSide.add(p2);
        //System.err.println("p1 ("+p1.getN()+") VS p2 ("+p2.getN()+")");
        if(p1.getV() > p2.getV())
        {
            //System.err.println("p1 win this round");
            p1cards.remove(p1);
            p2cards.remove(p2);
            p1cards.addAll(p1cardsSide);
            p1cards.addAll(p2cardsSide);
            p1cardsSide.clear();
            p2cardsSide.clear();
            computeMatch(p1cards, p1cardsSide, p2cards, p2cardsSide, false);
        }
        else if(p2.getV() > p1.getV())
        {
            //System.err.println("p2 win this round");
            p1cards.remove(p1);
            p2cards.remove(p2);
            p2cards.addAll(p1cardsSide);
            p2cards.addAll(p2cardsSide);
            p1cardsSide.clear();
            p2cardsSide.clear();
            computeMatch(p1cards, p1cardsSide, p2cards, p2cardsSide, false);
        }
        else
        {
            //System.err.println("battle !");
            // battle - remove 3 cards each
            List<Card> temp1 = new LinkedList<>();
            List<Card> temp2 = new LinkedList<>();
            for(int i = 0; i < 3; i++)
            {
                if(!itep1.hasNext() || !itep2.hasNext())
                {
                    System.out.println("PAT");
                    return;
                }
                Card c1 = itep1.next();
                Card c2 = itep2.next();
                //System.err.println("put aside (p1) (p2) : "+c1.getN()+" |  "+c2.getN());
                
                p1cardsSide.add(c1);
                temp1.add(c1);
                
                p2cardsSide.add(c2);
                temp2.add(c2);
            }
            p1cards.remove(p1);
            p2cards.remove(p2);
            Iterator<Card> i1 = temp1.iterator();
            Iterator<Card> i2 = temp2.iterator();
            for(int i = 0; i < 3; i++)
            {
                Card c3 = i1.next();
                p1cards.remove(c3);
                
                Card c4 = i2.next();
                p2cards.remove(c4);
                
                //System.err.println("remove (p1) (p2) : "+c3.getN()+" |  "+c4.getN());
            }
            nbRounds--;
            computeMatch(p1cards, p1cardsSide, p2cards, p2cardsSide, true);
        }
        
        return;
    }
    
    private static void printCards(List<Card> cards)
    {
        for(Card c : cards)
        {
            System.err.print(c.getN()+", ");
        }
        System.err.print("\n");
    }
    
    private static int getCardValue(String s){
        if(s.contains("A"))
            return 14;
        if(s.contains("K"))
            return 13;
        if(s.contains("Q"))
            return 12;
        if(s.contains("J"))
            return 11;
        if(s.contains("10"))
            return 10;
        if(s.contains("9"))
            return 9;
        if(s.contains("8"))
            return 8;
        if(s.contains("7"))
            return 7;
        if(s.contains("6"))
            return 6;
        if(s.contains("5"))
            return 5;
        if(s.contains("4"))
            return 4;
        if(s.contains("3"))
            return 3;
        if(s.contains("2"))
            return 2;
        return -1;
    }
}

class Card{
    private String name;
    private Integer value;
    
    public Card(String name, Integer value)
    {
        this.name = name;
        this.value = value;
    }
    
    public String getN()
    {
        return this.name;
    }
    
    public int getV()
    {
        return this.value;
    }
}
