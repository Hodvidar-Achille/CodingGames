package com.hodvidar.codingame.puzzles.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/don't-panic-episode-1
 * by Hodvidar
 **/
class DontPanicEpisode1 {

    @SuppressWarnings({"resource", "unused"})
    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int nbFloors = in.nextInt(); // number of floors
        final int width = in.nextInt(); // width of the area
        final int nbRounds = in.nextInt(); // maximum number of rounds
        final int exitFloor = in.nextInt(); // floor on which the exit is found
        final int exitPos = in.nextInt(); // position of the exit on its floor
        final int nbTotalClones = in.nextInt(); // number of generated clones
        final int nbAdditionalElevators = in.nextInt(); // ignore (always zero)
        final int nbElevators = in.nextInt(); // number of elevators
        final Map<Integer, Integer> elevators = new HashMap<>();
        for (int i = 0; i < nbElevators; i++) {
            final int elevatorFloor = in.nextInt(); // floor on which this elevator is found
            final int elevatorPos = in.nextInt(); // position of the elevator on its floor
            elevators.put(elevatorFloor, elevatorPos);
        }

        // game loop
        while (true) {
            final int cloneFloor = in.nextInt(); // floor of the leading clone
            final int clonePos = in.nextInt(); // position of the leading clone on its floor
            final String direction = in.next(); // direction of the leading clone: LEFT or RIGHT
            final boolean isGoingRight = "RIGHT".equals(direction);

            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            System.err.println("f : " + cloneFloor + " p : " + clonePos + " d : " + direction);
            System.err.println("ef : " + exitFloor + " ep : " + exitPos);
            if (cloneFloor == -1) {
                System.err.println("wait and go next turn");
                System.out.println("WAIT");
                continue;
            }

            // GO !
            // exist floor is same as cloneFloor ?
            if (exitFloor == cloneFloor) {
                final boolean isExitToTheRight = exitPos >= clonePos;
                System.err.println("same floor isExitToTheRight : " + isExitToTheRight);
                if (isGoingRight == isExitToTheRight)
                    System.out.println("WAIT");
                else
                    System.out.println("BLOCK");
            } else {
                final int elevatorPos = elevators.get(cloneFloor);
                final boolean isOnElevator = elevatorPos == clonePos;
                final boolean isElevatorToTheRight = elevatorPos > clonePos;
                System.err.println("diff floor isElevatorToTheRight : " + isElevatorToTheRight);
                if (isGoingRight == isElevatorToTheRight || isOnElevator)
                    System.out.println("WAIT");
                else
                    System.out.println("BLOCK");
            }
        }
    }
}
