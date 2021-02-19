package com.hodvidar.codingame.puzzles.hard;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/skynet-revolution-episode-2 by Hodvidar
 **/
class SkynetRevolutionEpisode2 {

	public static void main(final String[] args) {
		final SkynetRevolutionEpisode2 s = new SkynetRevolutionEpisode2();
		s.test();
	}

	private void test() {
		@SuppressWarnings("resource") final Scanner in = new Scanner(System.in);
		final int N = in.nextInt(); // the total number of nodes in the level, including the gateways
		final int L = in.nextInt(); // the number of links
		final int E = in.nextInt(); // the number of exit gateways
		final Map<Integer, Node> nodesMap = new HashMap<>();
		System.err.println("    nb nodes = " + N + " nb links = " + L + " nb exits = " + E);
		for (int i = 0; i < L; i++) {
			final int N1 = in.nextInt(); // N1 and N2 defines a link between these nodes
			final int N2 = in.nextInt();
			if (!nodesMap.containsKey(N1))
				nodesMap.put(N1, new Node(N1));
			if (!nodesMap.containsKey(N2))
				nodesMap.put(N2, new Node(N2));
			nodesMap.get(N1).connectDouble(nodesMap.get(N2));
			// System.err.println(N1 + " <-> " + N2);
		}
		final List<Integer> exists = new ArrayList<>();
		for (int i = 0; i < E; i++) {
			final int EI = in.nextInt(); // the index of a gateway node
			exists.add(EI);
			nodesMap.get(EI).becomeExist();
			// System.err.println(EI + " --> ");
		}

		final Map<Integer, Node> dangerousNode = new HashMap<>();
		for (final Node n : nodesMap.values()) {
			if (n.isNearExist())
				dangerousNode.put(n.getValue(), n);
		}
		// game loop
		outer: while (true) {
			for (final Node n : new ArrayList<>(dangerousNode.values())) {
				if (n.getLevel() < 1)
					dangerousNode.remove(n.getValue());
			}
			final int SI = in.nextInt(); // The index of the node on which the Skynet agent is positioned
			                       // this turn
			System.err.println("XoX in " + SI);
			final Node nSI = nodesMap.get(SI);

			if (dangerousNode.containsKey(SI)) {
				System.err.println("### EmergencyCut ###");
				nSI.cutExist();
				continue outer;
			}

			for (final Node n : nSI.getVoisins()) {
				if (n.getLevel() > 1) {
					System.err.println("### Pre-EmergencyCut ###");
					n.cutExist();
					continue outer;
				}
			}

			// TODO a optimize dangerous cut
			// calculate the closest node of SI with level > 1
			// with node having contact with exist weight == 0
			// because of the emergency cut
			nSI.setAllDistance(0);
			int minDist = Integer.MAX_VALUE;
			for (final Node n : dangerousNode.values()) {
				final int d = n.getDist();
				if (d != 0 && d < minDist)
					minDist = d;
			}
			for (final Node n : dangerousNode.values()) {
				if (n.getDist() == minDist && n.getLevel() > 1) {
					System.err.println("### Optimize cut of dangerous link ###");
					n.cutExist();
					continue outer;
				}
			}
			nSI.resetAllDistance();

			System.err.println("### Random cut of dangerous link ###");
			int max = 0;
			for (final Node n : dangerousNode.values()) {
				if (n.getLevel() > max)
					max = n.getLevel();
			}
			for (final Node n : dangerousNode.values()) {
				if (n.getLevel() == max) {
					n.cutExist();
					continue outer;
				}
			}

			// Write an action using System.out.println()
			// To debug: System.err.println("Debug messages...");

			// Example: 0 1 are the indices of the nodes you wish to sever the link between

			// System.out.println(n1 + " " + n2); // in doCut
		}
	}

	// ----------------- INTERNAL CLASSES ---------------------------

	class Node {
		private final int value;
		private final List<Node> voisins;
		private int dist = Integer.MAX_VALUE;
		private int level = 0;
		private boolean isExist = false;

		public Node(final int val) {
			this.value = val;
			this.voisins = new ArrayList<>();
		}

		public void setAllDistance(final int d) {
			this.dist = d;
			for (final Node child : this.voisins) {
				// if(child.getDist() != Integer.MAX_VALUE)
				// continue;
				final int dd;
				if (this.getLevel() > 0)
					dd = d;
				else
					dd = d + 1;

				if (child.getDist() <= dd)
					continue;
				child.setAllDistance(dd);

			}
		}

		public void resetAllDistance() {
			this.dist = Integer.MAX_VALUE;
			for (final Node child : this.voisins) {
				if (child.getDist() == Integer.MAX_VALUE)
					continue;
				child.resetAllDistance();
			}
		}

		public int getDist() {
			return this.dist;
		}

		public void setDist(final int d) {
			this.dist = d;
		}

		public void connectDouble(final Node aNode) {
			this.voisins.add(aNode);
			aNode.connect(this);
		}

		public void connect(final Node aNode) {
			this.voisins.add(aNode);
		}

		public void cutDouble(final Node aNode) {
			aNode.cut(this);
			this.voisins.remove(aNode);
		}

		public void cut(final Node aNode) {
			this.voisins.remove(aNode);
		}

		public void cutExist() {
			for (final Node n : this.voisins) {
				if (n.isExist()) {
					this.cutDouble(n);
					this.removeLevel();
					System.err.println("---Cutting " + this.getValue() + " <-> " + n.getValue() + " ---");
					System.out.println(this.getValue() + " " + n.getValue());
					return;
				}
			}
		}

		public int getLevel() {
			return this.level;
		}

		public void addLevel() {
			this.level += 1;
		}

		public void removeLevel() {
			this.level -= 1;
		}

		public int getValue() {
			return this.value;
		}

		public List<Node> getVoisins() {
			return this.voisins;
		}

		public boolean isExist() {
			return this.isExist;
		}

		public boolean isNearExist() {
			for (final Node n : this.voisins)
				if (n.isExist())
					return true;
			return false;
		}

		public void becomeExist() {
			this.isExist = true;
			for (final Node n : this.voisins)
				n.addLevel();
		}
	}
}
