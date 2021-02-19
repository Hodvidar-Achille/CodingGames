package com.hodvidar.utils.geometry;

import java.util.Objects;

public class Line {
	public final Point p1;
	public final Point p2;

	public final double slope;

	public Line(final Point p1, final Point p2) {
		if (Objects.equals(p1, p2))
			throw new IllegalArgumentException("A line must be form by two differents points");

		this.p1 = p1;
		this.p2 = p2;
		this.slope = getSlope(p1, p2);

	}

	public static double getSlope(final Point p1, final Point p2) {
		final double deltaY = p2.y - p1.y;
		final double deltaX = p2.x - p1.x;

		return deltaY / deltaX;
	}

	/**
	 * Does not check if the lines are the same line.
	 *
	 * @param o
	 * @return
	 */
	public boolean isParallel(final Line o) {
		return this.slope == o.slope;
	}

	@Override
	public String toString() {
		return "line (" + p1 + ", " + p2 + ")";
	}
}