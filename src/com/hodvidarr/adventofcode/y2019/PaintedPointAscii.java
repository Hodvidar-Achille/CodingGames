package com.hodvidarr.adventofcode.y2019;

public final class PaintedPointAscii extends PaintedPoint
{

	public static final int EMPTY = '.';

	public PaintedPointAscii(double x, double y)
	{
		this(x, y, EMPTY);
	}

	public PaintedPointAscii(double x, double y, int value)
	{
		super(x, y, value);
	}

	@Override
	public char printPoint()
	{
		return (char) this.value;
	}

}
