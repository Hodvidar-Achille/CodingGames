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
        if (isNull(commands)) {
            return getState();
        }

        for (char c : commands.toCharArray()) {
            move(c);
        }

        return getState();
    }

    private void move(char aCommand) {
        switch (aCommand) {
            case 'L' -> turnLeft();
            case 'R' -> turnRight();
            case 'F' -> goForward();
            case 'B' -> goBackward();
        }
    }

    private void turnLeft() {
        orientation = switch (orientation) {
            case 'N' -> 'W';
            case 'E' -> 'N';
            case 'S' -> 'E';
            case 'W' -> 'S';
            default -> throw new IllegalStateException("Unexpected value: " + orientation);
        };
    }

    private void turnRight() {
        orientation = switch (orientation) {
            case 'N' -> 'E';
            case 'E' -> 'S';
            case 'S' -> 'W';
            case 'W' -> 'N';
            default -> throw new IllegalStateException("Unexpected value: " + orientation);
        };
    }

    private void goForward() {
        switch (orientation) {
            case 'N' -> goNorth();
            case 'E' -> goEast();
            case 'S' -> goSouth();
            case 'W' -> goWest();
        }
    }

    private void goBackward() {
        switch (orientation) {
            case 'N' -> goSouth();
            case 'E' -> goWest();
            case 'S' -> goNorth();
            case 'W' -> goEast();
        }
    }

    private void goNorth() {
        if (y == maxY) {
            y = 0;
        } else {
            y += 1;
        }
    }

    private void goEast() {
        if (x == maxX) {
            x = 0;
        } else {
            x += 1;
        }
    }

    private void goSouth() {
        if (y == 0) {
            y = 9;
        } else {
            y -= 1;
        }
    }

    private void goWest() {
        if (x == 0) {
            x = 9;
        } else {
            x -= 1;
        }
    }

    private String getState() {
        return x + " " + y + " " + orientation;
    }
}
