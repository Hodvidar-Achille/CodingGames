package com.hodvidar.adventofcode.y2023;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day14 extends AbstractAdventOfCode2023 {

    public static final char ROUND_ROCK = 'O';
    public static final char SQUARE_ROCK = '#';
    public static final char EMPTY_SPACE = '.';
    protected char[][] grid;

    @Override
    public int getDay() {
        return 14;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        initGrid(sc);
        tiltGridToNorth();
        return getSumOfRoundRockLoad();
    }

    protected void initGrid(final Scanner sc) {
        final List<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        grid = new char[lines.size()][lines.get(0).length()];
        for (int i = 0; i < lines.size(); i++) {
            grid[i] = lines.get(i).toCharArray();
        }
    }

    protected double getSumOfRoundRockLoad() {
        double sum = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                if (grid[i][j] == ROUND_ROCK) {
                    sum += grid.length - i;
                }
            }
        }
        return sum;
    }

    protected void tiltGridToNorth() {
        for (int i = 1; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++) {
                moveRoundRockToNorth(i, j);
            }
        }
    }

    private void moveRoundRockToNorth(final int i, final int j) {
        if (grid[i][j] != ROUND_ROCK) {
            return;
        }
        if (i == 0) {
            return;
        }
        if (grid[i - 1][j] == ROUND_ROCK) {
            return;
        }
        if (grid[i - 1][j] == SQUARE_ROCK) {
            return;
        }
        grid[i - 1][j] = ROUND_ROCK;
        grid[i][j] = EMPTY_SPACE;
        moveRoundRockToNorth(i - 1, j);
    }
}
