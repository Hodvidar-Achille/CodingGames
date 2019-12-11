package com.hodvidarr.adventofcode.y2019;

import com.hodvidarr.utils.geometry.Point;

public final class Panel extends Point
{
	public static final int DARK = 0;
	public static final int WHITE = 1;

	private int color = DARK;
	/** Number of times it has been painted **/
	private int counter = 0;

	public Panel(double x, double y)
	{
		super(x, y);
	}

	public Panel(double x, double y, int color)
	{
		super(x, y);
		this.color = color;
	}

	/**
	 * Returns the color 0 is WHITE, 1 is DARK
	 * @return
	 */
	public int getColor()
	{
		return this.color;
	}

	/**
	 * Give the panel its new color
	 * @param newColor - the color
	 * @return true if the color of the panel actually changed
	 */
	public boolean paint(int newColor)
	{
		if(newColor == this.color)
			return false;

		this.color = newColor;
		this.counter++;
		return true;
	}

	/**
	 * Returns the number of time the panel 
	 * actually changed of color
	 * @return counter
	 */
	public int getCounter()
	{
		return this.counter;
	}

	@Override
	public boolean equals(Object obj)
	{
		return super.equals(obj);
	}

	@Override
	public int hashCode()
	{
		return super.hashCode();
	}

	@Override
	public String toString()
	{
		return "(" + this.x + "; " + this.y + ") " + "color:'" + this.color + "' " + "counter=" + this.counter;
	}

}
