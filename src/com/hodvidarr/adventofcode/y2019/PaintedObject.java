package com.hodvidarr.adventofcode.y2019;

public interface PaintedObject
{
	/**
	 * 
	 * @return the char corresponding to 
	 * the painted object value.
	 */
	public char printPoint();

	/**
	 * Gives the object its new color/symbol value.
	 * @param newValue - the new value of the paint.
	 * @return true if the color of the panel actually changed, 
	 * false if it was already the value.
	 */
	public boolean paint(int newValue);

	/**
	 * Gives the object value.
	 * @return
	 */
	public int getValue();
}
