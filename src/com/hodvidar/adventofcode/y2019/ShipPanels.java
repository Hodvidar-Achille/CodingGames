package com.hodvidar.adventofcode.y2019;

public final class ShipPanels extends PaintedSurface
{
	public ShipPanels(int colorOfFirstPanel)
	{
		paintedPoints.add(new Panel(0, 0, colorOfFirstPanel));
	}

	@Override
	public PaintedPoint getPaintedPointImpl(double x, double y)
	{
		return new Panel(x, y);
	}
}
