package com.hodvidar.adventofcode.y2023;

import java.util.Scanner;

import static com.hodvidar.adventofcode.y2023.Day16.Direction.*;

public class Day16p2 extends Day16 {


    @Override
    public double getResultDouble(final Scanner sc) {
        lines.clear();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        grid = new Grid(lines);
        int result = 0;
        int currrentResult;
        // NORTH WALL
        for (int x = 0; x < lines.get(0).length(); x++) {
            currrentResult = grid.getNumberOfLightedSpaces(x, 0, SOUTH);
            if (currrentResult > result) {
                result = currrentResult;
            }
        }
        // SOUTH WALL
        for (int x = 0; x < lines.get(0).length(); x++) {
            currrentResult = grid.getNumberOfLightedSpaces(x, lines.size() - 1, NORTH);
            if (currrentResult > result) {
                result = currrentResult;
            }
        }
        // WEST WALL
        for (int y = 0; y < lines.size(); y++) {
            currrentResult = grid.getNumberOfLightedSpaces(0, y, EAST);
            if (currrentResult > result) {
                result = currrentResult;
            }
        }
        // EAST WALL
        for (int y = 0; y < lines.size(); y++) {
            currrentResult = grid.getNumberOfLightedSpaces(lines.get(0).length() - 1, y, WEST);
            if (currrentResult > result) {
                result = currrentResult;
            }
        }

        return result;
    }
}
