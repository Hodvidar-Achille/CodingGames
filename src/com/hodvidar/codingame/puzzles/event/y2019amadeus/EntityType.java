package com.hodvidar.codingame.puzzles.event.y2019amadeus;

enum EntityType 
{
	NOTHING, ALLY_ROBOT, ENEMY_ROBOT, RADAR, TRAP, AMADEUSIUM;

	static EntityType valueOf(int id) {
		return values()[id + 1];
	}
}