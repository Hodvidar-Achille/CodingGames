import java.util.*;
import java.io.*;
import java.math.*;

/**
 *      https://www.codingame.com/ide/puzzle/the-frog-jump-1
 * by Hodvidar
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        
        // ---- Retrieve information ----
        int frogNumber = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String frogsDistance = in.nextLine();
    
        String[] frogJumps2 =  frogsDistance.split("\\s+");
        List<Float> frogJumps3 = new ArrayList<>();
        for(String s : frogJumps2)
        {
            frogJumps3.add(Float.valueOf(s));
        }
        Collections.sort(frogJumps3);
        String initialPosition = in.nextLine();
        String[] positions = initialPosition.split("\\s+");
        int x = Integer.valueOf(positions[0]);
        int y = Integer.valueOf(positions[1]);
        int mass = in.nextInt();
        int alpha = in.nextInt();
        float speed = in.nextFloat();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String gravity = in.nextLine();
        float gravity_y = Float.valueOf(gravity.split("\\s+")[1]);
        float gravity_x = 0;
        
        System.err.println("frogNumber:"+frogNumber);
        System.err.println("frogsDistance:"+frogsDistance);
        System.err.println("frogsDistance in order:");
        for(Float f : frogJumps3)
             System.err.print(" "+f);
        System.err.println("\nx:"+x);
        System.err.println("y:"+y);
        System.err.println("mass:"+mass);
        System.err.println("alpha:"+alpha);
        System.err.println("speed:"+speed);
        System.err.println("gravity_y:"+gravity_y);
        
        // ---- Physic calculations ----
        double speed_x = Math.cos(Math.toRadians(alpha)) * speed;
        double speed_y = Math.sin(Math.toRadians(alpha)) * speed;
        double delta = Math.pow(speed_y, 2) - 4 * (gravity_y * 1/2) * y;
        double time = (-speed_y - Math.sqrt(delta)) / (2 * gravity_y * 1/2);
        double x_final = (gravity_x * 1/2 * Math.pow(time, 2)) + (speed_x * time) + x;
        
        // round to 2 decimals
        x_final = Math.round(x_final * 100.0) / 100.0;
        
        System.err.println("\nspeed_x:"+speed_x);
        System.err.println("speed_y:"+speed_y);
        System.err.println("delta:"+delta);
        System.err.println("time:"+time);
        System.err.println("x_final:"+x_final);
        
        // ---- Ajusting possible position ----
        int[] possiblePositions = new int[frogNumber];
        int position = 1;
        float previousF = 0f;
        System.err.println("possiblePositions:");
        for(int i = frogNumber-1; i>=0; i--)
        {
            Float f  = frogJumps3.get(i);
            if(f.equals(previousF) && i != frogNumber-1)
            {
                 possiblePositions[i] = possiblePositions[i+1];
            } 
            else
            {
                possiblePositions[i] = position;
            }
            System.err.print(" "+possiblePositions[i]);
            position++;
            previousF = f;
        }
        
         // ---- Position calculations ----
         position = frogNumber+1;
         System.err.println("position:"+position);
         for(int i = frogNumber-1; i>=0; i--)
         {
            Float f = frogJumps3.get(i);
            if(x_final >= f)
             {
                 System.err.println("x_final ("+x_final+") >= f ("+f+")");
                 position = possiblePositions[i];
                 break;
             }
            System.err.println("x_final ("+x_final+") < f ("+f+")");
         }

        System.out.println(position);
    }
}
