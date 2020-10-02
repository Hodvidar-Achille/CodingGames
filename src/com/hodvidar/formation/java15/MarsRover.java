package com.hodvidar.formation.java15;

import static java.util.Objects.isNull;

// https://kata-log.rocks/mars-rover-kata
public final class MarsRover {

	private final int maxX = 9;
	private final int maxY = 9;

	private int x;
	private int y;
	private char orientation;

	public MarsRover() {
		x = 0;
		y = 0;
		orientation = 'N';
	}
	public String execute(String commands) {
		if(isNull(commands)) {
			return getState();
		}

		for(char c : commands.toCharArray()) {
			move(c);
		}

		return getState();
	}

	private void move(char aCommand) {
		switch (aCommand) {
			case 'L' : turnLeft();
				break;
			case 'R' : turnRight();
				break;
			case 'F' : goForward();
				break;
			case 'B' : goBackward();
				break;
			default: return;
		}
	}

	private void turnLeft() {
		switch (orientation) {
			case 'N' : orientation = 'W';
				break;
			case 'E' : orientation = 'N';
				break;
			case 'S' : orientation = 'E';
				break;
			case 'W' : orientation = 'S';
				break;
			default: return;
		}
	}

	private void turnRight() {
		switch (orientation) {
			case 'N' : orientation = 'E';
				break;
			case 'E' : orientation = 'S';
				break;
			case 'S' : orientation = 'W';
				break;
			case 'W' : orientation = 'N';
				break;
			default: return;
		}
	}

	private void goForward() {
		switch (orientation) {
			case 'N' : goNorth();
				break;
			case 'E' : goEast();
				break;
			case 'S' : goSouth();
				break;
			case 'W' : goWest();
				break;
			default: return;
		}
	}

	private void goBackward() {
		switch (orientation) {
			case 'N' : goSouth();
				break;
			case 'E' : goWest();
				break;
			case 'S' : goNorth();
				break;
			case 'W' : goEast();
				break;
			default: return;
		}
	}

	private void goNorth() {
		if(y == maxY) {
			y = 0;
		} else {
			y += 1;
		}
	}

	private void goEast() {
		if(x == maxX) {
			x = 0;
		} else {
			x += 1;
		}
	}

	private void goSouth() {
		if(y == 0) {
			y = 9;
		} else {
			y -= 1;
		}
	}

	private void goWest() {
		if(x == 0) {
			x = 9;
		} else {
			x -= 1;
		}
	}

	private String getState() {
		return x + " " + y + " " + orientation;
	}
}
