package com.hodvidarr.adventofcode.y2019;

public final class Layer
{
	public final int[] pixels;
	public final int length;
	private final int wide;

	private int numberOfZero;
	private int numberOfOne;
	private int numberOfTwo;

	public Layer(int wide, int height)
	{
		this.pixels = new int[wide * height];
		this.length = wide * height;
		this.wide = wide;
	}

	public void addPixel(int pixel, int i)
	{
		i = truncateIndex(i);
		this.pixels[i] = pixel;
		if(pixel == 0)
			this.numberOfZero++;
		else if(pixel == 1)
			this.numberOfOne++;
		else if(pixel == 2)
			this.numberOfTwo++;
	}

	private int truncateIndex(int i)
	{
		if(i >= this.length)
			i = i - length;

		if(i < this.length)
			return i;

		return truncateIndex(i);
	}

	public int getNumberOfZero()
	{
		return this.numberOfZero;
	}

	public int getNumberOfOne()
	{
		return this.numberOfOne;
	}

	public int getNumberOfTwo()
	{
		return this.numberOfTwo;
	}

	public String print()
	{
		String s = "";
		for (int i = 0; i < this.length; i++)
		{
			int p = this.pixels[i];
			s += "" + p;
			if(((i + 1) % wide) == 0)
				s += "\n";
		}
		return s;
	}

	public String print2()
	{
		String s = "";
		for (int i = 0; i < this.length; i++)
		{
			int p = this.pixels[i];
			String p2 = (p == 1) ? "#" : ".";
			s += p2;
			if(((i + 1) % wide) == 0)
				s += "\n";
		}
		return s;
	}

}
