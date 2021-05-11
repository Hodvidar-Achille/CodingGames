package com.hodvidar.codingame.puzzles.medium;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/mars-lander-episode-2
 * by Hodvidar
 */
class MarsLanderEpisode2 {

    private static final int EPSILON = 5;
    private static final int MAX_VERTICAL_SPEED = 40;
    private static final int MAX_HORIZONTAL_SPEED = 20;

    private static final double GRAVITY = 3.711;
    private static final int SECURITY_DISTANCE_FROM_FLAT_GROUND = 50;

    @SuppressWarnings("unused")
    public static void main(final String[] args) {
        @SuppressWarnings("resource") final Scanner in = new Scanner(System.in);
        final int N = in.nextInt(); // the number of points used to draw the surface of Mars.

        int flatGroundLeftX = -1;
        int flatGroundRightX = -1;
        int flatGroundY = -1;

        int previousPointX = -1;
        int previousPointY = -1;
        for (int i = 0; i < N; i++) {
            final int LAND_X = in.nextInt(); // X coordinate of a surface point. (0 to 6999)
            final int LAND_Y = in.nextInt(); // Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.
            if (previousPointY == LAND_Y) {
                flatGroundLeftX = previousPointX;
                flatGroundRightX = LAND_X;
                flatGroundY = LAND_Y;
            } else {
                previousPointX = LAND_X;
                previousPointY = LAND_Y;
            }
        }

        // game loop
        while (true) {

            final int X = in.nextInt();
            final int Y = in.nextInt();
            final int HS = in.nextInt(); // the horizontal speed (in m/s), can be negative.
            final int VS = in.nextInt(); // the vertical speed (in m/s), can be negative.
            final int F = in.nextInt(); // the quantity of remaining fuel in liters.
            int R = in.nextInt(); // the rotation angle in degrees (-90 to 90).
            int P = in.nextInt(); // the thrust power (0 to 4).

            if (isMarsLanderFlyingOverFlatGround(X, flatGroundLeftX, flatGroundRightX)) {
                System.err.println("MarsLander is Flying Over Flat Ground");
                if (isMarsLanderAboutToLand(Y, flatGroundY)) {
                    System.err.println("MarsLander is about to land");
                    R = 0;
                    P = 3;
                } else if (areMarsLanderSpeedLimitsSatisfied(HS, VS)) {
                    System.err.println("MarsLander speed limitation are satisfied");
                    R = 0;
                    P = 2;
                } else {
                    System.err.println("MarsLander speed limitation are not satisfied !");
                    R = calculateRotationToSlowDownMarsLander(HS, VS);
                    P = 4;
                }

            } else {
                System.err.println("MarsLander is not Flying Over Flat Ground !");
                if (isMarsLanderFlyingInTheWrongDirection(X, HS, flatGroundLeftX, flatGroundRightX)
                        || isMarsLanderFlyingTooFastTowardsFlatGround(HS)) {
                    System.err.println("MarsLander is in wrong direction or flying too fast");
                    R = calculateRotationToSlowDownMarsLander(HS, VS);
                    P = 4;
                } else if (isMarsLanderFlyingTooSlowTowardsFlatGround(HS)) {
                    System.err.println("MarsLander is flying in good direction but too slow");
                    R = calculateRotationToSpeedUpMarsLanderTowardsFlatGround(
                            X,
                            flatGroundLeftX,
                            flatGroundRightX);
                    P = 4;
                } else {
                    System.err.println("MarsLander is flying in good direction at good speed :) ");
                    R = 0;
                    P = calculateThrustPowerToFlyTowardsFlatGround(VS);
                }

            }

            System.out.println(R + " " + P); // R P. R is the desired rotation angle. P is the desired thrust power.
        }

    }

    private static boolean isMarsLanderFlyingOverFlatGround(
            final int marsLanderX,
            final int flatGroundLeftX,
            final int flatGroundRightX) {
        return marsLanderX >= flatGroundLeftX && marsLanderX <= flatGroundRightX;
    }

    private static boolean isMarsLanderAboutToLand(final int marsLanderY, final int flatGroundY) {
        return marsLanderY < flatGroundY + SECURITY_DISTANCE_FROM_FLAT_GROUND;
    }

    private static boolean areMarsLanderSpeedLimitsSatisfied(
            final int marsLanderHorizontalSpeed,
            final int marsLanderVerticalSpeed) {
        return Math.abs(marsLanderHorizontalSpeed) <= (MAX_HORIZONTAL_SPEED - EPSILON)
                && Math.abs(marsLanderVerticalSpeed) <= (MAX_VERTICAL_SPEED - EPSILON);
    }

    private static int calculateRotationToSlowDownMarsLander(final int hs, final int vs) {
        System.err.println("--- calculateRotationToSlowDownMarsLander ---");
        final double s = Math.sqrt(Math.pow(hs, 2) + Math.pow(vs, 2));
        final double r = Math.asin(hs / s);
        final double d = Math.toDegrees(r);
        System.err.println("HS: " + hs + " VS: " + vs + " r: " + r + " d:" + d);
        System.err.println("--- --- ---");
        return (int) d;
    }

    private static boolean isMarsLanderFlyingInTheWrongDirection(
            final int marsLanderX,
            final int marsLanderHorizontalSpeed,
            final int flatGroundLeftX,
            final int flatGroundRightX) {

        if (marsLanderX < flatGroundLeftX && marsLanderHorizontalSpeed < 0) {
            return true;
        }

        return marsLanderX > flatGroundRightX && marsLanderHorizontalSpeed > 0;
    }

    private static boolean isMarsLanderFlyingTooFastTowardsFlatGround(final int marsLanderHorizontalSpeed) {
        return Math.abs(marsLanderHorizontalSpeed) > (MAX_HORIZONTAL_SPEED * 4);
    }

    private static boolean isMarsLanderFlyingTooSlowTowardsFlatGround(final int marsLanderHorizontalSpeed) {
        return Math.abs(marsLanderHorizontalSpeed) < (MAX_HORIZONTAL_SPEED * 2);
    }

    private static int calculateRotationToSpeedUpMarsLanderTowardsFlatGround(
            final int marsLanderX,
            final int flatGroundLeftX,
            final int flatGroundRightX) {

        if (marsLanderX < flatGroundLeftX) {
            return -(int) Math.toDegrees(Math.acos(GRAVITY / 4.0));
        }

        if (marsLanderX > flatGroundRightX) {
            return +(int) Math.toDegrees(Math.acos(GRAVITY / 4.0));
        }

        return 0;
    }

    private static int calculateThrustPowerToFlyTowardsFlatGround(final int marsLanderVerticalSpeed) {
        System.err.println("calculateThrustPowerToFlyTowardsFlatGround...");
        return (marsLanderVerticalSpeed >= 0) ? 3 : 4;
    }

}
