package com.hodvidar.adventofcode.y2019;

import java.util.ArrayList;
import java.util.List;

/**
 * For Oxygen Maze in Day15
 * @author Hodvidar
 *
 */
public final class RobotRepair
{
	private final boolean VERBOSE;

	@SuppressWarnings("unused")
	private void printPanelsIfVerbose()
	{
		if(VERBOSE)
			this.maze.printInConsole();
	}

	private final MazeSurface maze;

	/**
	 * Use this to 
	 */
	private final Amplifier brain;

	private final List<MazePoint> path;

	/** X coordinate to start **/
	private final int start_x = 0;
	/** Y coordinate to start **/
	private final int start_y = 0;

	private final String UP = "N";
	private final String DOWN = "S";
	private final String RIGHT = "E";
	private final String LEFT = "W";

	private final String[] DIRECTIONS = new String[] { UP, RIGHT, DOWN, LEFT };

	public RobotRepair(double[] memory, boolean verbose)
	{
		this.maze = new MazeSurface();
		this.brain = new Amplifier(memory);
		this.path = new ArrayList<>();
		this.VERBOSE = verbose;
	}

	private double getDirectionNumberFromString(String s)
	{
		switch (s)
		{
		case UP:
			return 1;
		case DOWN:
			return 2;
		case LEFT:
			return 3;
		case RIGHT:
			return 4;
		default:
			throw new IllegalArgumentException(
					"Direction is not reconized:" + s);
		}
	}

	/** Cannot call another method after this one **/
	public int lookForOxygene()
	{
		startExploration(start_x, start_y, true);
		return getLowerPathValue();
	}

	/** Cannot call another method after this one **/
	public int fillWithOxygene()
	{
		// Explore map
		exploreAll();
		PaintedPoint oxygen = null;
		for (PaintedPoint p : this.maze.paintedPoints)
		{
			if(p.value == MazePoint.OXYGEN)
			{
				oxygen = p;
				continue;
			}
			if(p.value == MazePoint.EMPTY)
			{
				p.value = MazePoint.UNKOWN;
				((MazePoint) p).setCountFromStart(-1);
			}
		}
		((MazePoint) oxygen).setCountFromStart(0);
		start2ndExploration((int) oxygen.x, (int) oxygen.y);
		return this.getHigherPathValue();
	}

	private int getLowerPathValue()
	{
		return this.path.size();
	}

	private int getHigherPathValue()
	{
		return this.maze.getHigherPathValue();
	}

	private void exploreAll()
	{
		startExploration(start_x, start_y, false);
	}

	private void startExploration(int x, int y, boolean stopIfGoalFound)
	{
		MazePoint start = this.maze.getMazePoint(x, y);
		for (String newDirection : DIRECTIONS)
		{
			int newX = getXForDirection(x, newDirection);
			int newY = getYForDirection(y, newDirection);
			if(explore(newX, newY, x, y, start, path, stopIfGoalFound))
			{
				if(stopIfGoalFound)
					return; // Stop exploration when Goal is found.
			}
		}
		if(stopIfGoalFound)
			System.out.println("path.size==" + path.size());
	}

	private boolean explore(int x, int y, int previousX, int previousY,
			MazePoint previousPoint, List<MazePoint> path,
			boolean stopIfFoalFound)
	{
		if(isAlreadyKnown(x, y))
		{
			return false;
		}

		if(VERBOSE)
			this.maze.printInConsole();

		// Actually make the robot try to move to (x, y)
		String d = getDirectionToGoTo(previousX, previousY, x, y);
		this.brain.setInput(getDirectionNumberFromString(d));
		this.brain.runProgram();
		double result = this.brain.getOutput();
		if(result == 0)
		{
			this.maze.paintPointWithScore(x, y, 0, -1);
			return false;
		}
		if(result == 2)
		{
			this.maze.paintPointWithScore(x, y, 2,
					previousPoint.getCountFromStart() + 1);
			path.add(this.maze.getMazePoint(x, y));
			if(stopIfFoalFound)
				return true;
		}

		if(result == 1)
		{
			this.maze.paintPointWithScore(x, y, 1,
					previousPoint.getCountFromStart() + 1);
		}

		MazePoint current = this.maze.getMazePoint(x, y);

		for (String newDirection : DIRECTIONS)
		{
			int newX = getXForDirection(x, newDirection);
			int newY = getYForDirection(y, newDirection);
			if(explore(newX, newY, x, y, current, path, stopIfFoalFound))
			{
				path.add(current);
				if(stopIfFoalFound)
					return true;
			}
		}

		// Go back to previous point
		String goBackDirection = getDirectionToGoTo(x, y, previousX, previousY);
		this.brain.setInput(getDirectionNumberFromString(goBackDirection));
		this.brain.runProgram();
		// No need to check answer
		return false;
	}

	private void start2ndExploration(int x, int y)
	{
		MazePoint start = this.maze.getMazePoint(x, y);
		for (String newDirection : DIRECTIONS)
		{
			int newX = getXForDirection(x, newDirection);
			int newY = getYForDirection(y, newDirection);
			explore2nd(newX, newY, x, y, start);
		}
	}

	// Recursively explore all
	private void explore2nd(int x, int y, int previousX, int previousY,
			MazePoint previousPoint)
	{
		if(isAlreadyKnown(x, y))
		{
			return;
		}

		MazePoint current = this.maze.getMazePoint(x, y);
		// p.value is UNKNOWN
		current.value = MazePoint.EMPTY;
		current.setCountFromStart(previousPoint.getCountFromStart() + 1);

		for (String newDirection : DIRECTIONS)
		{
			int newX = getXForDirection(x, newDirection);
			int newY = getYForDirection(y, newDirection);
			explore2nd(newX, newY, x, y, current);
		}

		// end of exploration for this Point.
	}

	private boolean isAlreadyKnown(int x, int y)
	{
		MazePoint m = this.maze.getMazePoint(x, y);
		if(m == null)
			return false;
		return m.value != MazePoint.UNKOWN;
	}

	private String getDirectionToGoTo(int currentX, int currentY, int goalX,
			int goalY)
	{
		if(currentX == goalX && currentY == goalY)
			throw new IllegalStateException(
					"PreviousX&Y == currentX&Y, should not happen.");
		if(currentX != goalX && currentY != goalY)
			throw new IllegalStateException(
					"PreviousX&Y != currentX&Y, should not happen.");

		if(currentX == goalX)
		{
			if(goalY > currentY)
				return UP;
			else
				return DOWN;
		}
		else
		{
			if(goalX > currentX)
				return RIGHT;
			else
				return LEFT;
		}
	}

	private int getXForDirection(int currentX, String direction)
	{
		if(RIGHT.equals(direction))
			return currentX + 1;
		if(LEFT.equals(direction))
			return currentX - 1;
		return currentX;
	}

	private int getYForDirection(int currentY, String direction)
	{
		if(UP.equals(direction))
			return currentY + 1;
		if(DOWN.equals(direction))
			return currentY - 1;
		return currentY;
	}

}
