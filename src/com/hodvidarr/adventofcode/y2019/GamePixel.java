package com.hodvidarr.adventofcode.y2019;

public final class GamePixel extends PaintedPoint
{

	public static final int EMPTY = 0;
	public static final int WALL = 1;
	public static final int BLOCK = 2;
	public static final int PADDLE = 3;
	public static final int BALL = 4;

	public GamePixel(double x, double y)
	{
		super(x, y);
	}

	public GamePixel(double x, double y, int value)
	{
		super(x, y, value);
	}

	/**
	0 is an empty tile. No game object appears in this tile.
	1 is a wall tile. Walls are indestructible barriers.
	2 is a block tile. Blocks can be broken by the ball.
	3 is a horizontal paddle tile. The paddle is indestructible.
	4 is a ball tile. The ball moves diagonally and bounces off objects.
	 */
	@Override
	public char printPoint()
	{
		switch (value)
		{
		case 0:
			return ' ';
		case 1:
			return '|';
		case 2:
			return '#';
		case 3:
			return '_';
		case 4:
			return 'O';
		default:
			throw new IllegalStateException("Unknown value:" + value);
		}
	}

}
