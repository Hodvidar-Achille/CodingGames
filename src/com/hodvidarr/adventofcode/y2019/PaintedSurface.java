package com.hodvidarr.adventofcode.y2019;

import java.util.HashSet;
import java.util.Set;

import com.hodvidarr.utils.geometry.Point;

public abstract class PaintedSurface
{
	protected Set<PaintedPoint> paintedPoints = new HashSet<>();

	public abstract PaintedPoint getPaintedPointImpl(double x, double y);

	public void paintPoint(double x, double y, int value)
	{
		PaintedPoint p = this.getPaintedPointImpl(x, y);
		if(paintedPoints.contains(p))
		{
			// retrieve the Panel that already exist
			for (PaintedPoint p2 : this.paintedPoints)
			{
				if(p2.equals(p))
				{
					p = p2;
					break;
				}
			}
		}
		else
		{
			// use the newly created one
			paintedPoints.add(p);
		}

		p.paint(value);
	}

	public PaintedPoint getPaintedPoint(double x, double y)
	{
		PaintedPoint p = this.getPaintedPointImpl(x, y);
		if(paintedPoints.contains(p))
		{
			for (PaintedPoint p2 : this.paintedPoints)
			{
				if(p2.equals(p))
				{
					return p2;
				}
			}
		}
		else
		{
			return null;
		}
		throw new IllegalStateException("Should never reach this line.");
	}

	public int getNumberOfPaintedPanels()
	{
		return this.paintedPoints.size();
	}

	public int getPaintedPointValue(double x, double y)
	{
		Point p = new Point(x, y);
		if(!paintedPoints.contains(p))
			return PaintedPoint.DEFAULT;

		for (PaintedPoint p2 : this.paintedPoints)
		{
			if(p2.equals(p))
			{
				return p2.getValue();
			}
		}

		throw new IllegalStateException(
				"getPanelColor should not reach this point.");
	}

	public int countPointWithValue(int value)
	{
		int c = 0;
		for (PaintedPoint p : this.paintedPoints)
		{
			if(p.getValue() == value)
				c++;
		}
		return c;
	}

	public void printInConsole()
	{
		// 1) Find the range of the surface to print
		int maxY = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minX = Integer.MAX_VALUE;

		if(this.paintedPoints.size() == 0)
			return;

		for (Point p : this.paintedPoints)
		{
			int x = (int) p.x;
			int y = (int) p.y;
			if(x < minX)
				minX = x;
			if(x > maxX)
				maxX = x;
			if(y < minY)
				minY = y;
			if(y > maxY)
				maxY = y;
		}

		// Range found
		int wide = (maxX - minX) + 1;
		int height = (maxY - minY) + 1;

		// default value is 0 (for dark, perfect)
		PaintedPoint[][] surface = new PaintedPoint[height][wide];
		// Fill with default values
		// ---
		for (int y = height - 1; y >= 0; y--)
		{
			for (int x = 0; x < wide; x++)
			{
				surface[y][x] = getPaintedPointImpl(x, y);
			}
		}
		// ---

		// 2) Apply color on the surface for the painted panel
		for (PaintedPoint p : this.paintedPoints)
		{
			int x = (int) p.x;
			int y = (int) p.y;

			x = x - minX;
			y = y - minY;
			surface[y][x] = p;
		}

		// 3) Print the surface
		String s = "----------------------------------";
		for (int y = height - 1; y >= 0; y--)
		{
			s += "\n";
			for (int x = 0; x < wide; x++)
			{
				s += surface[y][x].printPoint();
			}
		}
		s += "\n----------------------------------";
		System.out.println(s);
	}

}
