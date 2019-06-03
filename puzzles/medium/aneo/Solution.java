/*
Vous entrez sur une portion de route et vous comptez vous reposer entièrement sur votre régulateur de vitesse pour traverser la zone sans devoir vous arrêter ni ralentir.

L'objectif est de trouver la vitesse maximale (hors excès de vitesse) qui vous permettra de franchir tous les feux au vert.

Attention : Vous ne pouvez pas franchir un feu à la seconde où il passe au rouge !

Votre véhicule entre directement dans la zone à la vitesse programmée sur le régulateur et ce dernier veille à ce qu'elle ne change plus par la suite.
Input
Ligne 1 : Un entier speed pour la vitesse maximale autorisée sur la portion de route (en km/h).

Ligne 2 : Un entier lightCount pour le nombre de feu de circulation sur la route.

lightCount prochaines lignes :
- Un entier distance représentant la distance du feu par rapport au point de départ (en mètre).
- Un entier duration représentant la durée du feu sur chaque couleur.

Un feu alterne une période de duration secondes au vert puis duration secondes au rouge.
Tous les feux passent au vert en même temps dès votre entrée dans la zone.
Output
Ligne 1 : La vitesse entière (km/h) la plus élevée possible qui permet de franchir tous les feux au vert sans commettre d'excès de vitesse.
*/

import java.util.*;
import java.io.*;
import java.math.*;

/**
 *      https://www.codingame.com/ide/puzzle/aneo
 * by Hodvidar. (TODO : finish)
 **/
class Solution 
{

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        int speed = in.nextInt();
        int lightCount = in.nextInt();
        for (int i = 0; i < lightCount; i++) {
            int distance = in.nextInt();
            int duration = in.nextInt();
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println("answer");
    }

}

class RoadSegment
{
    public final int distance;
    public final int duration;

    public RoadSegment(int distance, int duration)
    {
        this.distance = distance;
        this.duration = duration;
    }
}

class SpeedInterval
{
    /**
     * in meter/s (must be > 0);
     */
    public final double min;
    /**
     * in meter/s (must be less or equals to 200)
     */
    public final double max;

    public SpeedInterval(int min, int max)
    {
        this.min = min;
        this.max = max;
    }
}

class SpeedCalculator()
{
    private List<SpeedInterval> possibleSpeeds = new ArrayList<>();


    public SpeedCalculator()
    {
        double minSpeed = this.getSpeedInMeterPerSecond(1);
        double maxSpeed = this.getSpeedInMeterPerSecond(200);
        SpeedInterval firstInterval = new SpeedInterval(minSpeed, maxSpeed);
        possibleSpeeds.add(firstInterval);
    }

    /**
     * Adapt the possibleSpeed intervals to handle this new segment.
     * 
     * 
     * 
     */
    public void addRoadSegment(RoadSegment segment)
    {

    }



    private double getSpeedInMeterPerSecond(int speedInKmPerHour)
    {
        // Or just / by 3.6d.
        return ((((double) speedInKmPerHour) * 5d) / 18d);
    }

    // risk of losing accuracy with double --> int.
    private int getSpeedInKmPerHour(int speedInMeterPersecond)
    {
        // Or just * by 3.6d.
        return (int) Math.round( ((speedInMeterPersecond) * 18d) / 5d) );
    }

    private double roundTo2Decimals(double number)
    {
        return Math.round(number * 100.0) / 100.0;
    }

}
