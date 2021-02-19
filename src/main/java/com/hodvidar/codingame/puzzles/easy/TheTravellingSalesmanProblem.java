package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

import com.hodvidar.utils.geometry.Point;

/*
 * Note  : "Greedy algorithm"
 * "Starts at the first input given and always chooses the nearest point from the current point"
 * "Not the best algorithm for this problem but gives a reasonable answer".
 */

/**
 * https://www.codingame.com/ide/puzzle/the-travelling-salesman-problem by Hodvidar.
 **/
class TheTravellingSalesmanProblem {

	public static void main(final String[] args) {
		final TheTravellingSalesmanProblem t = new TheTravellingSalesmanProblem();
		t.test();
	}

	private void test() {
		final Scanner in = new Scanner(System.in);
		final int N = in.nextInt();
		System.err.println("N: " + N);
		final Node[] nodes = new Node[N];
		for (int i = 0; i < N; i++) {
			final int X = in.nextInt();
			final int Y = in.nextInt();
			System.err.println("X: " + X + "  Y: " + Y);
			nodes[i] = new Node(new Point(X, Y), i);
		}

		final Graph graph = new Graph(nodes);
		double totalDistance = 0;
		Node n1 = nodes[0];
		do {
			// System.err.println("Searching closest for node("+n1.p.x+", "+n1.p.y+")");
			Node closestNode = null;
			double minDistance = Double.MAX_VALUE;
			for (int j = 0; j < N; j++) {
				final Node n2 = nodes[j];
				// skip itself
				if (n2.equals(n1))
					continue;
				// skip nodes that have already been connected
				if (n2.isAlreadyConnected())
					continue;
				// skip latest node (XXX : bug)
				// if(n2.equals(n1.getPrevious()));
				// continue;
				// skip latest node ancestor except if its finish the loop
				if (!graph.canNodesConnect(n1, n2))
					continue;
				// System.err.println("j: "+j);
				final double distance = n2.getDistance(n1.p, n2.p);
				if (graph.acceptNode(closestNode, minDistance, n2, distance)) {
					minDistance = distance;
					closestNode = n2;
				}
			}
			n1.connect(closestNode);
			totalDistance += minDistance;
			n1 = closestNode;
		} while (!n1.equals(nodes[0]));

		// Write an action using System.out.println()
		// To debug: System.err.println("Debug messages...");
		System.err.println("Exact totalDistance (double): " + totalDistance);
		System.out.println((int) Math.round(totalDistance));
		in.close();
	}

	class Graph {
		public final Node[] nodes;
		public final int size;

		public Graph(final Node[] nodes) {
			this.nodes = nodes;
			this.size = nodes.length;
		}

		public boolean canNodesConnect(final Node n1, final Node n2) {
			final int ancestorLevel = n1.isInPreviousAncestors(n2);
			// System.err.println("canNodesConnect... ancestorLevel="+ancestorLevel);
			// -1 : not in ancestor
			// size -2 : it will end the loop formed by connected nodes.
			return ancestorLevel == -1 || ancestorLevel == this.size - 2;
		}

		public boolean acceptNode(final Node closedtNode, final double minDistance, final Node newNode, final double newDistance) {
			if (closedtNode == null)
				return true;

			if (newDistance > minDistance)
				return false;

			if (newDistance < minDistance)
				return true;

			// equals
			return newNode.position < closedtNode.position;

		}
	}

	class Node {
		public final Point p;
		public final int position;
		private Node previous;
		private Node next;
		private double distanceToNext = 0;

		public Node(final Point p, final int position) {
			this.p = p;
			this.position = position;
		}

		public Node getPrevious() {
			return this.previous;
		}

		public void setPrevious(final Node n) {
			this.previous = n;
		}

		public void connect(final Node n) {
			this.next = n;
			this.distanceToNext = getDistance(this.p, n.p);
			n.setPrevious(this);
			System.err.println("Node(" + this.p.x + ", " + this.p.y + ") is connected with node(" + n.p.x + ", " + n.p.y
			                   + ") with distance=" + this.distanceToNext);
		}

		public boolean isAlreadyConnected() {
			return this.previous != null;
		}

		/**
		 * Returns -1 if Node n is not in previous ancestors, if it is, given at which level it is.
		 * Returns 0 if the first node has node 'n' has ancestor;
		 */
		public int isInPreviousAncestors(final Node n) {
			if (this.previous == null)
				return -1;
			if (n.equals(this.previous))
				return 0;
			final int ancestorCount = this.previous.isInPreviousAncestors(n);
			if (ancestorCount == -1)
				return -1;
			return 1 + ancestorCount;
		}

		public double getDistance(final Point p1, final Point p2) {
			double deltaX = (p1.x - p2.x);
			double deltaY = (p1.y - p2.y);

			deltaX = Math.pow(deltaX, 2);
			deltaY = Math.pow(deltaY, 2);

			return Math.sqrt(deltaX + deltaY);
		}

		@Override
		public boolean equals(final Object obj) {
			// System.err.println("Node.equals(obj)...");
			if (obj == null)
				return false;
			if (!(obj instanceof Node))
				return false;

			final Node n = (Node) obj;
			if (this.position != n.position)
				return false;
			return this.p.equals(n.p);
		}

		@Override
		public int hashCode() {
			return (int) (31 * this.p.x + 89 * this.p.y);
		}

		@Override
		public String toString() {
			final String point = "point:" + ((this.p == null) ? "@null" : this.p);
			final String position = "n°" + this.position;
			final String previous = "previous:" + ((this.previous == null) ? "@null" : this.previous.p);
			final String next = "next:" + ((this.next == null) ? "@null" : this.next.p);
			final String distToNext = "distance to next:" + this.distanceToNext;

			return "{" + point + ", " + position + ", " + previous + ", " + next + ", " + distToNext + "}";
		}
	}

}