package com.hodvidarr.codingame.puzzles.easy;
import java.util.*;

/**
 * For puzzle : https://www.codingame.com/ide/puzzle/mars-lander-episode-1
 * by Hodvidar
 **/
class MarsLanderEpisode1 {

    @SuppressWarnings("unused")
	public static void main(String[] args) {
        @SuppressWarnings("resource")
		Scanner in = new Scanner(System.in);
        int surfaceN = in.nextInt(); // the number of points used to draw the surface of Mars.
        int xFlatMin = -1;
        int xFlatMax = -1;
        int Yflat = -1;
                
        int previousX = -1;
        int previousY = -1;
        for (int i = 0; i < surfaceN; i++) {
            int landX = in.nextInt(); // X coordinate of a surface point. (0 to 6999)
            int landY = in.nextInt(); // Y coordinate of a surface point. By linking all the points together in a sequential fashion, you form the surface of Mars.
            if(landY == previousY)
            {
                xFlatMax = landX;
                xFlatMin = previousX;
                Yflat = landY;
            }
            Yflat = landY;
            previousX = landX;
            previousY = landY;
        }
        
        Double currentAccelleration = null;
        Double currentVelocity = null;
        Double halfStepVelocity = null;
        Double currentPosition = null;

        double GRAVITY = -3.711d;
        // game loop
        while (true) {
            int X = in.nextInt();
            int Y = in.nextInt();
            int hSpeed = in.nextInt(); // the horizontal speed (in m/s), can be negative.
            int vSpeed = in.nextInt(); // the vertical speed (in m/s), can be negative.
            int fuel = in.nextInt(); // the quantity of remaining fuel in liters.
            int rotate = in.nextInt(); // the rotation angle in degrees (-90 to 90).
            int power = in.nextInt(); // the thrust power (0 to 4).
            if(currentPosition == null)
                currentPosition = (double) Y;
            if(currentVelocity == null)
                currentVelocity = (double) vSpeed;
                
                
            // Write an action using System.out.println()
            // To debug: System.err.println("Debug messages...");

            // GO !  new solution
            currentAccelleration = power + GRAVITY;
            currentVelocity = currentVelocity + currentAccelleration;
            halfStepVelocity = currentVelocity + currentAccelleration * 0.5;
            currentPosition = currentPosition + halfStepVelocity ;
            System.err.println("currentAccelleration: "+currentAccelleration+" currentVelocity: "+currentVelocity+" halfStepVelocity: "+halfStepVelocity+" currentPosition: "+currentPosition);
            System.err.println("Vertical calculation...");
            double distanceFromGround = currentPosition - Yflat;
            double wantedSpeedV = -35.5d; // simple for now
            double actualSpeedV = vSpeed;
            int delayV = 4-power; // simple for now, adapt with angle
            double maxSpeedIncrV = GRAVITY + 4; // simple for now, consider only rotate max at 45°
            int desiredVerticalSpeed = adaptSpeed(distanceFromGround, wantedSpeedV, actualSpeedV, currentAccelleration, delayV, maxSpeedIncrV);
            int newpower = 0;
            if(desiredVerticalSpeed < GRAVITY)
                newpower = 0;
            else if(desiredVerticalSpeed < GRAVITY+1)
                newpower = 1;
            else if(desiredVerticalSpeed < GRAVITY+2)
                newpower = 2;
            else if(desiredVerticalSpeed < GRAVITY+3)
                newpower = 3;
            else
                newpower = 4;
            
            // rotate power. rotate is the desired rotation angle. power is the desired thrust power.
            System.out.println("0 "+newpower);
            

            
            // (old) Easy solution : 
            // 2 integers: rotate power. rotate is the desired rotation angle (should be 0 for level 1), power is the desired thrust power (0 to 4).
            /*  System.out.println(vSpeed <= -40 ? "0 4" : "0 0"); */
        }
    }
    
    /*
    *   return speed (-/+) in order to have maxSpeedAtDistance
    *  (in order to have wantedSpeed when distance arrive at 0)
    *   delay is the number of sec needed to modify speed
    *   distance (no direction) always positif
    *   speeds are positives if forward distance or negative if backward
    *   gravity is for vertical adaptation, it add itselft to actualSpeed each secondes (m/s²)
    */
    private static int adaptSpeed(double distance, double wantedSpeed, double actualSpeed, double actualAcceleration, int delay, double maxSpeedIncr)
    {
        System.err.println("distance: "+distance+" | actualSpeed: "+actualSpeed+" | delay: "+delay+" | maxSpeedIncr: "+maxSpeedIncr);
        if(distance == 0 && actualSpeed == wantedSpeed)
            return 0;

        double newDistanceMinusDelaySpeedAndAcc = newDistanceMinusDelaySpeedAndAcc(distance, actualSpeed, actualAcceleration, delay);
        double maxSpeedAtDistance = maxSpeedAtDistance(newDistanceMinusDelaySpeedAndAcc, wantedSpeed, maxSpeedIncr);
        double desiredThrust = maxSpeedAtDistance - actualSpeed;
        System.err.println("To have speed "+wantedSpeed+" m/s at wanted point, the maximal speed to have at "
            +newDistanceMinusDelaySpeedAndAcc+" m from the point is "+maxSpeedAtDistance+" m/s. Therefore the actualSpeed must be adapt by "+desiredThrust+" m/s" );
        return (int) desiredThrust;

    }

    /*
    * find the maximale speed we need to have at our distance to be sure to be able to have the wantedSpeed à distance == 0
    */
    private static double maxSpeedAtDistance(double distance, double wantedSpeed, double maxSpeedIncr)
    {
        double dist = 0;
        double wSpeed = wantedSpeed;
        while(dist < distance)
        {
            dist -= wSpeed;
            wSpeed -= maxSpeedIncr;
        }
        return wSpeed;
    }

    private static double newDistanceMinusDelaySpeedAndAcc(double distance, double actualSpeed, double actualAcceleration, int delay)
    {
        double distanceDelay = 0;
        for(int i = 0; i < delay; i++)
        {
            actualSpeed += actualAcceleration;
            distanceDelay += actualSpeed;
        }
        return distance - distanceDelay;
    }
}
