package com.hodvidar.adventofcode.y2019;

public final class ScaffoldPanel extends PaintedPoint
{
	public static final int EMPTY = 46;
	public static final int WALL = 35;
	public static final int NEW_LINE = 10;

	public ScaffoldPanel(double x, double y)
	{
		super(x, y, EMPTY);
	}

	public ScaffoldPanel(double x, double y, int value)
	{
		super(x, y, value);
	}

	@Override
	public char printPoint()
	{
		switch (this.value)
		{
		case EMPTY:
			return '.';
		case WALL:
			return '#';
		default:
			return (char) this.value; // We will see
		}
	}

}
