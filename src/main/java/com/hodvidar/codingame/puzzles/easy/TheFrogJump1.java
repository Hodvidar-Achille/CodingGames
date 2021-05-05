package com.hodvidar.codingame.puzzles.easy;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/the-frog-jump-1 by Hodvidar. 100% success.
 **/
class TheFrogJump1 {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);

        // ---- Retrieve information ----
        final int frogNumber = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        final String frogsDistance = in.nextLine();

        final String[] frogJumps2 = frogsDistance.split("\\s+");
        final List<Float> frogJumps3 = new ArrayList<>();
        for (final String s : frogJumps2) {
            frogJumps3.add(Float.valueOf(s));
        }
        Collections.sort(frogJumps3);
        final String initialPosition = in.nextLine();
        final String[] positions = initialPosition.split("\\s+");
        final int x = Integer.valueOf(positions[0]);
        final int y = Integer.valueOf(positions[1]);
        final int mass = in.nextInt();
        final int alpha = in.nextInt();
        final float speed = in.nextFloat();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        final String gravity = in.nextLine();
        final float gravity_y = Float.valueOf(gravity.split("\\s+")[1]);
        final float gravity_x = 0;

        System.err.println("frogNumber:" + frogNumber);
        System.err.println("frogsDistance:" + frogsDistance);
        System.err.println("frogsDistance in order:");
        for (final Float f : frogJumps3)
            System.err.print(" " + f);
        System.err.println("\nx:" + x);
        System.err.println("y:" + y);
        System.err.println("mass:" + mass);
        System.err.println("alpha:" + alpha);
        System.err.println("speed:" + speed);
        System.err.println("gravity_y:" + gravity_y);

        // ---- Physic calculations ----
        final double speed_x = Math.cos(Math.toRadians(alpha)) * speed;
        final double speed_y = Math.sin(Math.toRadians(alpha)) * speed;
        final double delta = Math.pow(speed_y, 2) - 4 * (gravity_y * 1 / 2) * y;
        final double time = (-speed_y - Math.sqrt(delta)) / (2 * gravity_y * 1 / 2);
        double x_final = (gravity_x * 1 / 2 * Math.pow(time, 2)) + (speed_x * time) + x;

        // round to 2 decimals
        x_final = Math.round(x_final * 100.0) / 100.0;

        System.err.println("\nspeed_x:" + speed_x);
        System.err.println("speed_y:" + speed_y);
        System.err.println("delta:" + delta);
        System.err.println("time:" + time);
        System.err.println("x_final:" + x_final);

        // ---- Ajusting possible position ----
        final float rounded_xFinal = Float.valueOf("" + x_final);
        frogJumps3.add(rounded_xFinal);
        Collections.sort(frogJumps3);

        final int[] possiblePositions = new int[frogNumber + 1];
        int position = 1;
        float previousF = 0f;
        System.err.println("possiblePositions:");
        for (int i = frogNumber; i >= 0; i--) {
            final Float f = frogJumps3.get(i);
            if (f.equals(previousF) && i != frogNumber) {
                possiblePositions[i] = possiblePositions[i + 1];
            } else {
                possiblePositions[i] = position;
            }
            System.err.print(" " + possiblePositions[i]);
            position++;
            previousF = f;
        }

        // ---- Position calculations ----
        position = frogNumber + 1;
        System.err.println("\nposition:" + position);
        for (int i = frogNumber; i >= 0; i--) {
            final Float f = frogJumps3.get(i);
            if (rounded_xFinal >= f) {
                System.err.println("rounded_xFinal (" + rounded_xFinal + ") >= f (" + f + ")");
                position = possiblePositions[i];
                break;
            }
            System.err.println("rounded_xFinal (" + rounded_xFinal + ") < f (" + f + ")");
        }

        System.out.println(position);
        in.close();
    }
}
