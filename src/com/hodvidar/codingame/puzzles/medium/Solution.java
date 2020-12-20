package com.hodvidar.codingame.puzzles.medium;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;


/**
 *    https://www.codingame.com/ide/puzzle/the-gift
 * by Hodvidar
 **/
class TheGift
{

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int C = in.nextInt();
        System.err.println("N :"+N+" C: "+C);
        List<Integer> budgets = new ArrayList<>();
        List<Integer> maxGiven = new ArrayList<>();
        int totalBudget = 0;
        for (int i = 0; i < N; i++) {
            int B = in.nextInt();
            totalBudget += B;
            budgets.add(B);
            System.err.println("B: "+B);
        }
        if(totalBudget < C)
        {
            System.out.println("IMPOSSIBLE");
            in.close();
            return;
        }
        maxGiven = calculMaxGiven(budgets, maxGiven, N, C);
        Collections.sort(maxGiven);
        
        for(int i=0; i<maxGiven.size();i++ )
        {
            System.out.println(maxGiven.get(i));
        }
        
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        //System.out.println("IMPOSSIBLE");
        in.close();
    }
    
    private static List<Integer> calculMaxGiven(List<Integer> budgets, List<Integer> maxGiven, int participants, int price)
    {
        int averageBudget = (int) Math.floor((double) price / (double) participants);
        int giversWithGoodBudget = participants;
        int price2 = price;
        System.err.println("averageBudget: "+averageBudget+" giversWithGoodBudget: "+giversWithGoodBudget+" price2: "+price2);
        List<Integer> budgets2 = new ArrayList<>(budgets);
        for(int i = budgets.size()-1; i >= 0; i--)
        {
            int x = budgets.get(i);
            if(x <= averageBudget)
            {
                giversWithGoodBudget -=1;
                maxGiven.add(x);
                price2 = price2 - x;
                budgets2.remove(i);
            }
        }
        if(giversWithGoodBudget == participants)
        {
            int nbRemainingGivers = participants;
            int newAverageBudget = averageBudget;
            for(int i = 0; i < budgets2.size(); i++)
            {
                newAverageBudget = (int) Math.floor((double) price2 / (double) nbRemainingGivers);
                maxGiven.add(newAverageBudget);
                price2 = price2 - newAverageBudget;
                nbRemainingGivers -=1;
            }
            return maxGiven;
        }
        else
        {
            return calculMaxGiven(budgets2, maxGiven, giversWithGoodBudget, price2);
        }
    }
}
