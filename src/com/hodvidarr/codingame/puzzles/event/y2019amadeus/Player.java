package com.hodvidarr.codingame.puzzles.event.y2019amadeus;

import java.util.Scanner;

class Player 
{

	public static void main(String args[]) 
	{
		new Player().run();
	}

	final Scanner in = new Scanner(System.in);

	void run() 
	{
		// Parse initial conditions
		Board board = new Board(in);

		while (true) 
		{
			// Parse current state of the game
			board.update(in);

			// Insert your strategy here
			for (Entity robot : board.myTeam.robots) 
			{
				robot.action = Action.none();
				robot.action.message = "Java Starter";
			}

			// Send your actions for this turn
			for (Entity robot : board.myTeam.robots) 
			{
				System.out.println(robot.action);
			}
		}
	}
}