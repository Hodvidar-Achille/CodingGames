package com.hodvidar.codingame.puzzles.easy;
import java.util.*;

/**
 *    https://www.codingame.com/ide/puzzle/the-descent
 * by Hodvidar
 * ---
 * The while loop represents the game.
 * Each iteration represents a turn of the game
 * where you are given inputs (the heights of the mountains)
 * and where you have to print an output (the index of the mountain to fire on)
 * The inputs you are given are automatically updated according to your last actions.
 **/
class TheDescent 
{

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);

        // game loop
        while (true) 
        {
            int position = -1;
            int max = -1;
        
            for (int i = 0; i < 8; i++) 
            {
                int mountainH = in.nextInt(); // represents the height of one mountain.
                if(mountainH > max)
                {
                    position = i;
                    max = mountainH;
                }
            }

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            System.out.println(position); // The index of the mountain to fire on.
            in.close();
        }
    }
}
