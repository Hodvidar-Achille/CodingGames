import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 
 TODO : to continue
 **/
class Solution {

    private static final char END_TAG = '-';
    
    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String sequence = in.nextLine();
        
        Map<Character, Integer> weights = new HashMap<>();
        
        for(char c : sequence.toCharArray())
        {
            Integer w = weigths.get(c);
            if(w == null)
            {
                weigths.put(c, Integer.valueOf(0));
            }
            
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println("answer");
    }
}

class Tag
{
    public final char name;
    public final int depth;
    public final List<Tag> children = new ArrayList<>();
    
}
