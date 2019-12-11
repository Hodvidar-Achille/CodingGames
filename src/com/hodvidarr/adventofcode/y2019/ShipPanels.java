package com.hodvidarr.adventofcode.y2019;

import java.util.HashSet;
import java.util.Set;

import com.hodvidarr.utils.geometry.Point;

public final class ShipPanels
{
	private Set<Panel> paintedPanels = new HashSet<>();

	public ShipPanels(int colorOfFirstPanel)
	{
		paintedPanels.add(new Panel(0, 0, colorOfFirstPanel));
	}

	public void paintPanel(int x, int y, int color)
	{
		Panel p = new Panel(x, y);
		if(paintedPanels.contains(p))
		{
			// retrieve the Panel that already exist
			for (Panel p2 : this.paintedPanels)
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
			paintedPanels.add(p);
		}

		p.paint(color);
	}

	public int getNumberOfPaintedPanels()
	{
		return this.paintedPanels.size();
	}

	public int getPanelColor(int x, int y)
	{
		Panel p = new Panel(x, y);
		if(!paintedPanels.contains(p))
			return Panel.DARK;

		for (Panel p2 : this.paintedPanels)
		{
			if(p2.equals(p))
			{
				return p2.getColor();
			}
		}

		throw new IllegalStateException("getPanelColor should not reach this point.");
	}

	public void printPanels()
	{
		// 1) Find the range of the surface to print
		int maxY = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int minX = Integer.MAX_VALUE;

		if(this.paintedPanels.size() == 0)
			return;

		for (Point p : this.paintedPanels)
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
		int[][] surface = new int[height][wide];

		// 2) Apply color on the surface for the painted panel
		for (Panel p : this.paintedPanels)
		{
			int x = (int) p.x;
			int y = (int) p.y;
			int color = p.getColor();

			x = x - minX;
			y = y - minY;
			surface[y][x] = color;
		}

		// 3) Print the surface
		String s = "----------------------------------";
		for (int y = height - 1; y >= 0; y--)
		{
			s += "\n";
			for (int x = 0; x < wide; x++)
			{
				if(Panel.DARK == surface[y][x])
					s += ".";
				else
					s += "#";
			}
		}
		s += "\n----------------------------------";
		System.out.println(s);
	}

}
