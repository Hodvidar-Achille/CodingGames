package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * https://adventofcode.com/2019/day/13
 * <p>
 * '398' GOOD
 *
 * @author Hodvidar
 */
public final class _Day13 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = true;

    private static final int NUMBER_OF_TEST = 13;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(final String[] args) throws Exception {
        final String result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("Expected '398' - result='" + result + "'");
    }

    private static String test(final String inputFile) throws Exception {
        final String line;
        final File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        final Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        line = sc.nextLine();
        final String[] opCodeStr = line.split(",");
        sc.close();
        final double[] opCode = new double[opCodeStr.length];
        for (int i = 0; i < opCodeStr.length; i++) {
            final String s = opCodeStr[i];
            final double j = Double.parseDouble(s);
            opCode[i] = j;
        }

        final ArcaneCabinet game = new ArcaneCabinet(opCode);
        game.paint();
        if (VERBOSE)
            game.printScreen();
        final int result = game.countBlock();

        // Stuff
        return "" + result;
    }
}

/*

--- Day 13: Care Package ---

As you ponder the solitude of space and the ever-increasing three-hour 
roundtrip for messages between you and Earth, you notice that the Space 
Mail Indicator Light is blinking. To help keep you sane, the Elves have 
sent you a care package.

It's a new game for the ship's arcade cabinet! Unfortunately, the arcade 
is all the way on the other end of the ship. Surely, it won't be hard to 
build your own - the care package even comes with schematics.

The arcade cabinet runs Intcode software like the game the Elves sent 
(your puzzle input). It has a primitive screen capable of drawing square 
tiles on a grid. The software draws tiles to the screen with output instructions: 
every three output instructions specify the x position (distance from the left), 
y position (distance from the top), and tile id. The tile id is interpreted as follows:

    0 is an empty tile. No game object appears in this tile.
    1 is a wall tile. Walls are indestructible barriers.
    2 is a block tile. Blocks can be broken by the ball.
    3 is a horizontal paddle tile. The paddle is indestructible.
    4 is a ball tile. The ball moves diagonally and bounces off objects.

For example, a sequence of output values like 1,2,3,6,5,4 would draw a 
horizontal paddle tile (1 tile from the left and 2 tiles from the top) 
and a ball tile (6 tiles from the left and 5 tiles from the top).

Start the game. How many block tiles are on the screen when the game exits?


*/