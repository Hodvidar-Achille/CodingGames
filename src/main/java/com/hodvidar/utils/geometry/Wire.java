package com.hodvidar.utils.geometry;

import java.util.ArrayList;
import java.util.List;

/**
 * List of lines that follow each others
 *
 * @author Hodvidar
 */
public final class Wire {
	private final List<Point> points = new ArrayList<>();
	private final List<Segment> segments = new ArrayList<>();
	private Point origin;
	private double length = 0;

	public Wire() {
	}

	/**
	 * Adds the points where the new line will go
	 *
	 * @param p
	 */
	public void addPoint(final Point p) {
		points.add(p);
		if (points.size() > 1) {
			final Segment s = new Segment(points.get(points.size() - 2), p);
			length += s.getLenght();
			segments.add(s);
		} else
			origin = p;
	}

	public List<Segment> getSegments() {
		return this.segments;
	}

	public double getLenth() {
		return this.length;
	}

	public List<Point> getIntersections(final Wire aWire) {
		final List<Point> intersections = new ArrayList<>();
		final List<Segment> otherSegments = aWire.getSegments();

		for (final Segment s1 : otherSegments) {
			for (final Segment s2 : this.segments) {
				final Point i = s1.getIntersection(s2);
				if (i != null) {
					intersections.add(i);
				}

			}
		}
		return intersections;
	}

	/**
	 * Returns the distance to parcour on the wire to encou
	 *
	 * @param p
	 * @return -1 if point is not on the wire. If it is on the wire, the distance from the origin
	 *         point
	 */
	public boolean doesIntersect(final Point p) {
		for (final Segment s : this.segments) {
			if (GeometryServices.isOnSegment(p, s))
				return true;
		}
		return false;
	}

	/**
	 * Returns the distance to travel on the wire to find the given point.
	 *
	 * @param p
	 * @return -1 if point is not on the wire. If it is on the wire, the distance from the origin
	 *         point
	 */
	public double intersectDistance(final Point p) {
		double distance = 0;
		for (final Segment s : this.segments) {
			if (GeometryServices.isOnSegment(p, s)) {
				distance += GeometryServices.getDistance(s.p1, p);
				if (distance == 15474)
					System.out.println("s");
				return distance;
			} else {
				distance += s.getLenght();
			}
		}
		return -1;
	}

	public Point getOrigin() {
		return origin;
	}

}
