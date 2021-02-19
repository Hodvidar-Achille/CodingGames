package com.hodvidar.codingame.puzzles.event.y2019amadeus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

class Team {
	int score;
	Collection<Entity> robots;

	void readScore(final Scanner in) {
		score = in.nextInt();
		robots = new ArrayList<>();
	}
}