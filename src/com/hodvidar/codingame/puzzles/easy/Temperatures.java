package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/temperatures
 * by Hodvidar
 **/
class Temperatures 
{

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int n = in.nextInt(); // the number of temperatures to analyse
        in.nextLine();
        String temps = in.nextLine(); // the n temperatures expressed as integers ranging from -273 to 5526
        System.err.println("n == "+n);
        System.err.println("temps == "+temps);
        String[] listeT = temps.split("\\s+");
        int valMin = 0;
        Integer i;
        int valAbsolue = 0;
        int valMinValAbs = 0;
        //liste de string à list d'integer
        if(n > 0) {
            for(String s : listeT){
                i = Integer.parseInt(s);
                System.err.println("i == "+i);
                if(i < 0){
                    valAbsolue = i * -1;
                } else {
                    valAbsolue = i;
                }
                System.err.println("valAbsolue == "+valAbsolue);
                if(valAbsolue < valMinValAbs || valMin == 0){
                    valMin = i;
                } else if(valAbsolue == valMinValAbs && i > 0){
                    valMin = i;
                }
                if(valMin < 0){
                    valMinValAbs = valMin * -1;
                } else {
                    valMinValAbs = valMin;
                }
                System.err.println("valMin == "+valMin);
            }
        }
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        System.out.println(valMin);
        in.close();
    }
}
