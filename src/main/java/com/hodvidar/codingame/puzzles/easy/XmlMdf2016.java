package com.hodvidar.codingame.puzzles.easy;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

/**
 * https://www.codingame.com/ide/puzzle/xml-mdf-2016 by Hodvidar
 **/
class XmlMdf2016 {

	private static final char END_TAG = '-';

	public static void main(final String[] args) {
		final XmlMdf2016 m = new XmlMdf2016();
		m.test();
	}

	private void test() {
		final Scanner in = new Scanner(System.in);
		final String sequence = in.nextLine();
		System.err.println("sequence:" + sequence);

		final Set<Character> letters = new HashSet<>();
		final TagHolder fisrtTag = new TagHolder();
		Tag currentHolder = fisrtTag;
		boolean nextClose = false;
		for (final char c : sequence.toCharArray()) {
			if (c == END_TAG) {
				nextClose = true;
				continue;
			}
			if (nextClose) {
				currentHolder = currentHolder.parent;
				nextClose = false;
				continue;
			}
			letters.add(c);
			final Tag newTag = new Tag(c, currentHolder);
			currentHolder = newTag;
		}

		// Write an action using System.out.println()
		// To debug: System.err.println("Debug messages...");

		final char max = fisrtTag.getHeavierTag(letters);

		System.out.println(max);
		in.close();
	}

	class Tag {
		public final Tag parent;
		private final char name;
		private final double depth;
		private final List<Tag> children = new ArrayList<>();

		public Tag(final char name, final Tag parent) {
			this.name = name;
			this.parent = parent;
			if (this.parent == null) {
				this.depth = 0;
			} else {
				this.depth = 1 + this.parent.depth;
				this.parent.children.add(this);
			}
		}

		// recursive
		protected double getWeight(final char aName) {
			// System.err.println("Tag("+this.name+")#getWeight("+aName+")");
			double totalWeight = 0;
			for (final Tag child : this.children) {
				if (aName == child.name)
					totalWeight += 1 / child.depth;
				totalWeight += child.getWeight(aName);
			}
			// System.err.println("totalWeight="+totalWeight);
			return totalWeight;
		}
	}

	class TagHolder extends Tag {
		public TagHolder() {
			super('_', null);
		}

		public char getHeavierTag(final Set<Character> tags) {
			System.err.println("getHeavierTag...:");
			char heavier = '_';
			double maxWeight = 0;
			for (final Character c : tags) {
				final double weight = this.getWeight(c);
				System.err.println("c: " + c + "  weight=" + weight);

				if (weight < maxWeight)
					continue;

				if (weight > maxWeight) {
					maxWeight = weight;
					heavier = c;
					continue;
				}

				if (c < heavier) {
					maxWeight = weight;
					heavier = c;
				}
			}
			return heavier;
		}
	}
}