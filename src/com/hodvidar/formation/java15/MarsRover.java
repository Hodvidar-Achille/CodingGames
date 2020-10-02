package com.hodvidar.formation.java15;

import static java.util.Objects.isNull;

import java.util.Objects;


// https://kata-log.rocks/mars-rover-kata
public final class MarsRover {

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
			return "0 0 N";
		}

		return "0 0 N";
	}


}
