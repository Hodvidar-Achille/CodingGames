package com.hodvidar.codingame.puzzles.event.y2019amadeus;

import java.util.Scanner;

class Player {

    final Scanner in = new Scanner(System.in);

    public static void main(final String[] args) {
        new Player().run();
    }

    void run() {
        // Parse initial conditions
        final Board board = new Board(in);

        while (true) {
            // Parse current state of the game
            board.update(in);

            // Insert your strategy here
            for (final Entity robot : board.myTeam.robots) {
                robot.action = Action.none();
                robot.action.message = "Java Starter";
            }

            // Send your actions for this turn
            for (final Entity robot : board.myTeam.robots) {
                System.out.println(robot.action);
            }
        }
    }
}