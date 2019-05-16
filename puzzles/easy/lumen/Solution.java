import java.util.*;
import java.io.*;
import java.math.*;

/**
 *    https://www.codingame.com/ide/puzzle/lumen
 * by Hodvidar
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt();
        int L = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        
        // 1) dark room
        Integer[][] room = new Integer[N][N];
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++) 
                room[i][j] = 0;
        
        // 2) adds candles
        for (int i = 0; i < N; i++) 
        {
            String LINE = in.nextLine();
            // System.err.println(LINE);
            int j = 0;
            for(char c : LINE.toCharArray())
            {
                if(c == ' ')
                    continue;
                    
                if(c == 'C')
                {   
                    // System.err.println("spreadLight("+N+", "+L+", room, "+i+", "+j+")");
                    // 3) spread light
                    spreadLight(N, L, room, i, j);
                }
                j++;
            }
        }

        // display(room, N);
        
        
        
        // 4) count dark spots
        int ds = 0;
        for (int i = 0; i < N; i++) 
            for (int j = 0; j < N; j++) 
               if(room[i][j] == 0)
                    ds++;
                    
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(ds);
    }
    
    private static void spreadLight(int N, int l, Integer[][] room, int i, int j)
    {
        if(l < 1)
            return;
        if(i < 0 || j < 0 || i >= N || j >= N)
            return;
        
        // itselft
        if(room[i][j] >= l)
            return;
        room[i][j] = l;
        
        // DEBUG
        // display(room, N);
        
        int newL = l-1;
        // top-left
        spreadLight(N, newL, room, i-1, j-1);
        // top
        spreadLight(N, newL, room, i-1, j);
        // top-right
        spreadLight(N, newL, room, i-1, j+1);
        // right
        spreadLight(N, newL, room, i, j+1);
        // bottom-right
        spreadLight(N, newL, room, i+1, j+1);
        // bottom
        spreadLight(N, newL, room, i+1, j);
        // botom-left
        spreadLight(N, newL, room, i+1, j-1);
        // left
        spreadLight(N, newL, room, i, j-1);
    }
    
    private static void display(Integer[][] room, int N)
    {
        System.err.println("room :");
        for (int i = 0; i < N; i++) 
        {
            for (int j = 0; j < N; j++)
            {
                System.err.print(room[i][j]);
            }
            System.err.println("");
        }        
    }
    
    
}
