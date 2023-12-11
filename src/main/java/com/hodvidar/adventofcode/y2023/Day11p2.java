package com.hodvidar.adventofcode.y2023;

public class Day11p2 extends Day11 {

    public static final double SPACE_EXPANSION_RATE = 1000000d;

    @Override
    protected SpaceImage getSpaceImage() {
        return new SpaceImage(SPACE_EXPANSION_RATE);
    }
}
