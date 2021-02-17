package com.hodvidar.utils.geometry;

/**
 * Like a Line but without the infinite length.
 *
 * @author a.genet
 */
public final class Segment extends Line {

	public Segment(final Point p1, final Point p2) {
		super(p1, p2);
	}

	public double getLenght() {
		return GeometryServices.getDistance(p1, p2);
	}

	/**
	 * Returns the point of intersection, can be <code>null</code> if the segments do not intersect.
	 *
	 * @param other
	 * @return a <code>Point</code> or <code>null</code>.
	 */
	public Point getIntersection(final Segment other) {
		return GeometryServices.getIntersect(p1, p2, other.p1, other.p2);
	}

	@Override
	public String toString() {
		return "segment [" + p1 + ", " + p2 + "]";
	}

}
