package com.hodvidarr.utils.geometry;

/**
 * Build a Wire using an Origin Point and a list of directions and distances instructions.
 * @author a.genet
 *
 */
public final class WireBuilder
{
	private Point previous;
	private final Wire wire;

	public WireBuilder(Point origin)
	{
		this.previous = origin;
		this.wire = new Wire();
		this.wire.addPoint(origin);
	}

	private final Character UP = 'U';
	private final Character RIGHT = 'R';
	private final Character LEFT = 'L';
	private final Character DOWN = 'D';

	/**
	 * Instructions (for now) are 'R', 'U', 'L', 'D' for right, up, left, down follow by an integer.
	 * @param s
	 */
	public void addInstruction(String s)
	{
		Character c = s.charAt(0);
		int value = Integer.parseInt(s.substring(1));
		double x = this.previous.x;
		double y = this.previous.y;
		Point p;
		if(UP.equals(c))
		{
			// +y
			p = new Point(x, y + value);
		}
		else if(DOWN.equals(c))
		{
			// -y
			p = new Point(x, y - value);
		}
		else if(RIGHT.equals(c))
		{
			// +x
			p = new Point(x + value, y);
		}
		else if(LEFT.equals(c))
		{
			// -x
			p = new Point(x - value, y);
		}
		else
		{
			throw new IllegalArgumentException("Does not reconize order: '" + c + "'");
		}
		this.wire.addPoint(p);
		this.previous = p;
	}

	public Wire getWire()
	{
		return this.wire;
	}
}
