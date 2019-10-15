package com.hodvidarr.codingame.puzzles.medium;
import java.util.*;

/**
 *   https://www.codingame.com/ide/puzzle/shadows-of-the-knight-episode-1
 * by Hodvidar
 **/
class ShadowsOfTheKnightEpisode1 {

    @SuppressWarnings("unused")
	public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt(); // width of the building.
        int H = in.nextInt(); // height of the building.
        int N = in.nextInt(); // maximum number of turns before game over.
        int X0 = in.nextInt();
        int Y0 = in.nextInt();
    
        System.err.println("W = "+W);
        System.err.println("H = "+H);
        int x = X0;
        int y = Y0;
        int xMax = W;
        int xMin = 0;
        int yMax = H;
        int yMin = 0;
        // game loop
        while (true) {
            String bombDir = in.next(); // the direction of the bombs from batman's current location (U, UR, R, DR, D, DL, L or UL)
            int hauteur = bombDir.contains("D") ? 1 : bombDir.contains("U") ? -1 : 0;
            int largeur = bombDir.contains("R") ? 1 : bombDir.contains("L") ? -1 : 0;
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            
            // hauteur mouvement :
            if(hauteur == 1)
            {
                yMin = y;
                y = (yMax+y) /2;

            }
            else if(hauteur == -1)
            {
                yMax = y;
                y = (yMin+y) /2;
            }
            
            // largeur mouvement :
            if(largeur == 1)
           {
                xMin = x;
                x = (xMax+x) /2;

            }
            else if(largeur == -1)
            {
                xMax = x;
                x = (xMin+x) /2;
            }
            // the location of the next window Batman should jump to.
            System.out.println(x+" "+y);
            in.close();
        }
    }
}
