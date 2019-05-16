import java.util.*;
import java.io.*;
import java.math.*;

/**
 *    https://www.codingame.com/ide/puzzle/power-of-thor-episode-1
 * ---
 * Hint: You can use the debug stream to print initialTX and initialTY, if Thor seems not follow your orders.
 * by Hodvidar
 **/
class Player {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int lightX = in.nextInt(); // the X position of the light of power
        int lightY = in.nextInt(); // the Y position of the light of power
        int initialTX = in.nextInt(); // Thor's starting X position
        int initialTY = in.nextInt(); // Thor's starting Y position
        int newX = initialTX;
        int newY = initialTY;

        // game loop
        while (true) {
            int remainingTurns = in.nextInt(); // The remaining amount of turns Thor can move. Do not remove this line.

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            if(newX < lightX && newY == lightY){
                System.out.println("E");
                newX++;
            }
            if(newX < lightX && newY < lightY){
                System.out.println("SE");
                newX++;
                newY++;
            }
            if(newX == lightX && newY < lightY){
                System.out.println("S");
                newY++;
            }
            if(newX > lightX && newY < lightY){
                System.out.println("SW");
                newY++;
                newX--;
            }
            if(newX > lightX && newY == lightY){
                System.out.println("W");
                newX--;
            }
            if(newX > lightX && newY > lightY){
                System.out.println("NW");
                newY--;
                newX--;
            }
            if(newX == lightX && newY > lightY){
                System.out.println("N");
                newY--;
            }
            if(newX < lightX && newY > lightY){
                System.out.println("NE");
                newY--;
                newX++;
            }
        }
    }
}
