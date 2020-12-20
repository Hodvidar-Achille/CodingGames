package com.hodvidar.codingame.puzzles.medium;

import java.util.Scanner;

/**
 *    https://www.codingame.com/ide/puzzle/the-last-crusade-episode-1
 * by Hodvidar
 **/
class TheLastCrusadeEpisode1 {

    @SuppressWarnings("unused")
	public static void main(String[] args) {
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // number of columns.
        int H = in.nextInt(); // number of rows.
        if (in.hasNextLine()) {
            in.nextLine();
        }
        int[][] grid = new int[W][H];
        for (int i = 0; i < H; i++) {
            int j = 0;
            String LINE = in.nextLine(); // represents a line in the grid and contains W integers. Each integer represents one room of a given type.
            String[] lineChar = LINE.split("\\s+");
            for(String s : lineChar)
            {
                int n =  Integer.parseInt(s);
                grid[j][i] = n;
                j++;
            }
        }
        int EX = in.nextInt(); // the coordinate along the X axis of the exit (not useful for this first mission, but must be read).

        // game loop
        while (true) {
            int XI = in.nextInt();
            int YI = in.nextInt();
            String POS = in.next();

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            
            // GO ! 
            int typeRoom = grid[XI][YI];
            String nextRoom = computeMove(XI, YI, POS, typeRoom);
            
            

            // One line containing the X Y coordinates of the room in which you believe Indy will be on the next turn.
            System.out.println(nextRoom);
        }
    }
    
    private static String computeMove(int x, int y, String entryMove, int t)
    {
        // room where existMove is always down (y+1) 
        // 1; 3; 7; 8; 9; 12; 13
        if(t == 1 || t == 3 || t == 7 || t == 8 || t == 9 || t == 12 || t == 13)
        {
            y = y+1;
        }
        // always right (x+1)
        // 11
        else if(t == 11)
        {
            x = x+1;
        }
        // alwats left (x-1)
        // 10
        else if(t == 10)
        {
            x = x-1;
        }
        // go oposite side 
        // 2: 6   LEFT -> (x+1)     RIGHT -> (x-1)
        else if(t == 2 || t == 6)
        {
            if(entryMove.equals("LEFT"))
                x = x+1;
            else if(entryMove.equals("RIGHT"))
                x = x-1;
        }
        // 4 : TOP -> x-1           RIGHT -> (y+1)
        else if(t == 4)
        {
            if(entryMove.equals("TOP"))
                x = x - 1;
            else if(entryMove.equals("RIGHT"))
                y = y + 1;
        }
        // 5 : TOP -> x+1           LEFT -> (y+1)
        else if(t == 5)
        {
            if(entryMove.equals("TOP"))
                x = x + 1;
            else if(entryMove.equals("LEFT"))
                y = y + 1;
        }
        return x+" "+y;
    }
}
