package com.hodvidar.codingame.puzzles.medium;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/don't-panic-episode-1
 * by Hodvidar
 **/
class DontPanicEpisode1 
{

    @SuppressWarnings({ "resource", "unused" })
	public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int nbFloors = in.nextInt(); // number of floors
        int width = in.nextInt(); // width of the area
        int nbRounds = in.nextInt(); // maximum number of rounds
        int exitFloor = in.nextInt(); // floor on which the exit is found
        int exitPos = in.nextInt(); // position of the exit on its floor
        int nbTotalClones = in.nextInt(); // number of generated clones
        int nbAdditionalElevators = in.nextInt(); // ignore (always zero)
        int nbElevators = in.nextInt(); // number of elevators
        Map<Integer, Integer> elevators = new HashMap<>();
        for (int i = 0; i < nbElevators; i++) {
            int elevatorFloor = in.nextInt(); // floor on which this elevator is found
            int elevatorPos = in.nextInt(); // position of the elevator on its floor
            elevators.put(elevatorFloor, elevatorPos);
        }

        // game loop
        while (true) 
        {
            int cloneFloor = in.nextInt(); // floor of the leading clone
            int clonePos = in.nextInt(); // position of the leading clone on its floor
            String direction = in.next(); // direction of the leading clone: LEFT or RIGHT
            boolean isGoingRight = "RIGHT".equals(direction);
            
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");
            System.err.println("f : "+cloneFloor+" p : "+clonePos+" d : "+direction);
            System.err.println("ef : "+exitFloor+" ep : "+exitPos);
            if(cloneFloor == -1)
            {
                System.err.println("wait and go next turn");
                System.out.println("WAIT");
                continue;
            }
            
            // GO !
            // exist floor is same as cloneFloor ?
            if(exitFloor == cloneFloor)
            {
                boolean isExitToTheRight = exitPos >= clonePos;
                System.err.println("same floor isExitToTheRight : "+isExitToTheRight);
                if(isGoingRight == isExitToTheRight)
                    System.out.println("WAIT");
                else
                    System.out.println("BLOCK");
            }
            else
            {
                int elevatorPos = elevators.get(cloneFloor);
                boolean isOnElevator = elevatorPos == clonePos;
                boolean isElevatorToTheRight = elevatorPos > clonePos;
                System.err.println("diff floor isElevatorToTheRight : "+isElevatorToTheRight);
                if(isGoingRight == isElevatorToTheRight || isOnElevator)
                    System.out.println("WAIT");
                else
                    System.out.println("BLOCK");
            }
        }
    }
}
