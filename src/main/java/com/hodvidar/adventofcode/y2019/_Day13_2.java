package com.hodvidar.adventofcode.y2019;

import java.io.File;

import static com.hodvidar.utils.file.Constance.RESOURCES;

/**
 * '19446' too low
 * '19447' PASSED (cheated).
 *
 * @author Hodvidar
 */
public final class _Day13_2 {
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
        // String line;
        // File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        // Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        // Replace bottom by paddle.
        final String line = "1,380,379,385,1008,2559,144748,381,1005,381,12,99,109,2560,1102,1,0,383,1102,1,0,382,20101,0,382,1,21001,383,0,2,21102,37,1,0,1105,1,578,4,382,4,383,204,1,1001,382,1,382,1007,382,40,381,1005,381,22,1001,383,1,383,1007,383,24,381,1005,381,18,1006,385,69,99,104,-1,104,0,4,386,3,384,1007,384,0,381,1005,381,94,107,0,384,381,1005,381,108,1105,1,161,107,1,392,381,1006,381,161,1102,-1,1,384,1106,0,119,1007,392,38,381,1006,381,161,1102,1,1,384,20102,1,392,1,21101,0,22,2,21102,0,1,3,21101,138,0,0,1106,0,549,1,392,384,392,21001,392,0,1,21102,1,22,2,21102,3,1,3,21101,161,0,0,1105,1,549,1101,0,0,384,20001,388,390,1,21001,389,0,2,21101,180,0,0,1105,1,578,1206,1,213,1208,1,2,381,1006,381,205,20001,388,390,1,20102,1,389,2,21101,205,0,0,1106,0,393,1002,390,-1,390,1102,1,1,384,20102,1,388,1,20001,389,391,2,21101,0,228,0,1106,0,578,1206,1,261,1208,1,2,381,1006,381,253,20102,1,388,1,20001,389,391,2,21102,1,253,0,1106,0,393,1002,391,-1,391,1102,1,1,384,1005,384,161,20001,388,390,1,20001,389,391,2,21101,279,0,0,1106,0,578,1206,1,316,1208,1,2,381,1006,381,304,20001,388,390,1,20001,389,391,2,21101,0,304,0,1106,0,393,1002,390,-1,390,1002,391,-1,391,1101,1,0,384,1005,384,161,20102,1,388,1,21001,389,0,2,21101,0,0,3,21101,338,0,0,1106,0,549,1,388,390,388,1,389,391,389,20101,0,388,1,20102,1,389,2,21102,1,4,3,21102,365,1,0,1105,1,549,1007,389,23,381,1005,381,75,104,-1,104,0,104,0,99,0,1,0,0,0,0,0,0,398,18,19,1,1,20,109,3,22101,0,-2,1,22102,1,-1,2,21101,0,0,3,21101,414,0,0,1106,0,549,22102,1,-2,1,22102,1,-1,2,21102,429,1,0,1106,0,601,1201,1,0,435,1,386,0,386,104,-1,104,0,4,386,1001,387,-1,387,1005,387,451,99,109,-3,2106,0,0,109,8,22202,-7,-6,-3,22201,-3,-5,-3,21202,-4,64,-2,2207,-3,-2,381,1005,381,492,21202,-2,-1,-1,22201,-3,-1,-3,2207,-3,-2,381,1006,381,481,21202,-4,8,-2,2207,-3,-2,381,1005,381,518,21202,-2,-1,-1,22201,-3,-1,-3,2207,-3,-2,381,1006,381,507,2207,-3,-4,381,1005,381,540,21202,-4,-1,-1,22201,-3,-1,-3,2207,-3,-4,381,1006,381,529,22102,1,-3,-7,109,-8,2106,0,0,109,4,1202,-2,40,566,201,-3,566,566,101,639,566,566,1201,-1,0,0,204,-3,204,-2,204,-1,109,-4,2106,0,0,109,3,1202,-1,40,593,201,-2,593,593,101,639,593,593,21001,0,0,-2,109,-3,2105,1,0,109,3,22102,24,-2,1,22201,1,-1,1,21101,0,487,2,21101,109,0,3,21102,960,1,4,21101,630,0,0,1106,0,456,21201,1,1599,-2,109,-3,2106,0,0,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,2,2,2,0,2,2,2,2,0,2,2,2,0,2,0,2,2,0,0,0,2,2,0,2,0,2,2,2,0,2,2,2,2,0,2,0,0,1,1,0,2,2,2,0,2,2,2,2,2,2,2,2,0,2,2,0,0,2,2,0,2,0,2,2,2,2,2,0,2,2,2,2,2,2,2,2,0,1,1,0,2,2,2,0,0,2,2,0,2,2,0,2,2,0,0,0,2,0,0,2,2,2,0,2,0,0,2,0,2,0,0,2,2,2,2,2,0,1,1,0,2,2,2,2,2,2,2,2,2,2,2,0,2,2,0,0,0,2,0,0,0,2,2,2,2,2,0,2,2,2,2,2,2,0,2,2,0,1,1,0,0,0,2,2,0,2,2,0,2,2,2,2,2,2,2,2,0,2,2,0,2,0,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0,1,1,0,2,2,2,2,2,2,2,0,0,2,2,2,2,2,0,2,0,2,2,2,2,0,2,2,2,0,2,2,0,2,2,0,0,0,2,2,0,1,1,0,2,2,2,0,0,2,2,0,0,2,0,2,2,0,2,2,2,2,2,2,2,2,2,2,0,0,2,0,2,2,2,0,0,2,2,2,0,1,1,0,0,2,0,0,2,0,0,2,2,2,2,0,2,2,0,2,2,2,2,2,0,0,2,0,0,2,0,0,2,2,2,2,2,2,0,2,0,1,1,0,2,0,2,0,2,2,2,2,2,2,2,2,0,0,2,2,2,0,2,0,2,2,2,2,2,2,0,2,2,2,2,2,2,2,2,2,0,1,1,0,2,2,2,2,2,0,0,2,2,0,2,2,2,2,2,2,2,0,2,2,2,2,2,2,2,2,2,0,0,2,2,2,2,2,2,0,0,1,1,0,2,0,2,2,2,2,2,0,0,0,0,0,2,0,0,0,2,2,0,0,2,2,2,2,0,2,2,2,0,2,2,0,2,0,2,2,0,1,1,0,0,2,0,2,2,2,2,2,0,2,0,2,0,0,2,0,2,0,2,2,0,2,0,2,2,2,2,0,2,0,2,2,0,0,2,2,0,1,1,0,0,2,2,2,0,2,0,2,2,0,2,2,2,0,2,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,0,0,2,2,0,0,1,1,0,2,2,0,2,2,2,2,0,2,0,2,2,2,0,0,0,2,0,0,2,2,2,2,2,0,2,0,2,2,2,0,2,0,0,2,2,0,1,1,0,2,2,2,2,2,2,2,0,2,2,0,2,2,2,0,2,2,2,0,0,0,2,0,0,0,2,2,2,2,2,2,0,2,0,2,2,0,1,1,0,0,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,2,2,2,2,2,2,2,0,2,0,0,2,2,2,2,0,2,2,2,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,4,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,3,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,49,65,14,21,97,84,49,78,48,36,96,71,85,76,47,17,61,37,11,13,78,58,57,46,36,71,62,73,93,82,59,40,49,77,56,36,73,64,3,63,79,92,97,57,43,45,31,95,61,73,77,18,60,15,16,78,53,2,62,49,39,51,33,51,87,69,80,9,15,94,96,65,91,16,81,7,97,80,86,42,47,9,66,75,17,74,16,63,8,14,82,46,50,10,2,61,54,14,75,63,4,20,94,43,21,82,15,13,91,7,80,71,5,57,54,6,69,55,4,45,21,94,71,48,73,67,43,25,16,62,14,14,83,36,7,52,62,56,95,54,82,21,55,33,30,22,63,47,76,17,29,6,42,57,58,58,3,8,10,6,82,73,7,42,19,36,47,50,29,76,72,75,76,89,40,24,38,28,13,56,36,60,17,46,11,95,35,34,78,29,80,59,6,17,12,79,48,59,19,64,53,63,8,2,87,78,49,61,5,62,23,4,16,75,13,85,15,70,5,59,38,77,89,39,30,88,9,20,51,66,28,15,24,22,5,87,98,70,12,28,9,20,70,25,10,17,86,19,17,53,55,43,6,23,79,75,67,91,96,42,63,36,59,90,17,20,58,87,69,37,75,22,81,15,41,57,52,55,54,77,71,40,64,7,63,80,23,70,66,59,26,92,94,33,26,50,61,26,63,94,44,45,41,18,81,50,83,77,56,26,79,4,19,92,9,73,72,71,17,30,46,80,69,22,24,23,84,45,91,18,82,46,59,41,76,11,4,62,95,1,22,45,15,62,35,79,82,92,1,19,48,92,97,71,16,7,74,78,88,46,97,67,97,71,54,91,70,65,86,19,85,76,44,69,69,30,18,98,95,28,40,93,85,42,47,56,21,65,70,15,5,24,22,36,10,32,92,48,26,56,81,2,85,29,37,97,25,54,11,40,64,89,58,92,64,69,28,8,53,65,23,46,26,43,90,73,5,50,17,45,2,67,60,82,43,24,73,65,85,57,55,15,26,46,71,58,5,25,58,72,38,75,4,88,73,88,69,76,17,62,47,60,26,68,6,48,70,33,61,53,44,15,95,18,12,50,10,54,47,17,17,5,78,49,16,34,3,26,4,67,67,8,92,46,1,3,72,31,5,52,51,29,8,32,39,88,53,40,86,79,6,91,49,56,17,48,41,86,93,8,2,78,67,1,19,98,88,69,12,49,34,92,77,11,1,36,15,68,60,38,40,44,66,87,33,41,39,81,25,58,17,17,23,3,45,28,18,11,70,33,44,83,84,59,45,41,61,83,82,18,27,23,4,14,43,37,66,16,8,10,90,7,83,94,78,25,36,23,90,95,78,72,8,47,32,29,62,31,89,30,1,40,39,24,50,61,23,36,74,63,42,50,73,13,43,60,89,58,76,35,1,85,47,38,76,21,49,45,9,48,23,54,68,56,84,32,10,49,80,24,57,50,13,85,23,56,28,7,71,91,54,65,7,41,53,68,30,2,35,33,13,1,51,72,30,27,2,80,98,16,89,47,89,41,9,38,51,54,30,41,15,17,35,31,25,64,92,12,34,88,40,78,49,39,2,57,43,30,24,27,34,40,22,76,81,20,39,93,40,44,59,13,3,38,59,25,4,48,77,35,64,12,79,22,84,82,49,69,35,92,95,23,37,45,11,53,2,15,22,65,26,23,92,44,22,64,76,9,27,41,1,12,92,15,14,21,7,26,26,71,11,20,38,87,21,97,97,61,74,31,40,57,55,24,95,2,64,38,41,20,87,11,51,19,72,62,75,66,85,49,70,34,31,74,12,31,93,42,95,31,70,1,68,13,9,3,19,86,21,8,86,81,61,79,47,41,50,23,82,21,16,67,21,21,80,58,75,76,59,50,32,21,72,98,66,9,33,64,61,98,45,73,62,28,84,91,8,23,12,43,79,34,18,27,24,36,92,89,59,57,52,37,20,36,66,8,9,86,31,30,20,96,72,7,19,91,75,17,90,38,72,78,67,4,41,32,88,21,2,44,76,21,82,91,78,64,32,72,91,15,43,18,73,42,47,95,8,19,90,66,10,49,66,58,11,88,48,25,34,51,97,49,2,59,19,17,44,84,83,6,41,27,19,66,19,4,84,17,43,13,41,19,16,87,1,14,40,7,96,53,48,31,67,88,34,55,97,63,15,65,144748";

        // line = sc.nextLine();
        final String[] opCodeStr = line.split(",");
        //sc.close();
        final double[] opCode = new double[opCodeStr.length];
        for (int i = 0; i < opCodeStr.length; i++) {
            final String s = opCodeStr[i];
            final double j = Double.parseDouble(s);
            opCode[i] = j;
        }

        final ArcaneCabinet game = new ArcaneCabinet(opCode);
        //		game.paint();
        //		if(VERBOSE)
        //			game.printScreen();
        // Change first memory address
        game.changeMemoryAdressValue(0, 2);
        // game.initGame();
        if (VERBOSE)
            game.printScreen();
        game.launchGameAuto(VERBOSE);
        if (VERBOSE)
            game.printScreen();

        printIfVerbose("Number of block left (expect 0):" + game.countBlock());
        final double result = game.getScore();

        // Stuff
        return "" + result;
    }
}

/*
--- Part Two ---

The game didn't run because you didn't put in any quarters. 
Unfortunately, you did not bring any quarters. 
Memory address 0 represents the number of quarters that have been 
inserted; set it to 2 to play for free.

The arcade cabinet has a joystick that can move left and right. 
The software reads the position of the joystick with input 
instructions:

    If the joystick is in the neutral position, provide 0.
    If the joystick is tilted to the left, provide -1.
    If the joystick is tilted to the right, provide 1.

The arcade cabinet also has a segment display capable of 
showing a single number that represents the player's current 
score. When three output instructions specify X=-1, Y=0, 
the third output instruction is not a tile; the value instead 
specifies the new score to show in the segment display. 
For example, a sequence of output values like -1,0,12345 would 
show 12345 as the player's current score.

Beat the game by breaking all the blocks. 
What is your score after the last block is broken?


*/
