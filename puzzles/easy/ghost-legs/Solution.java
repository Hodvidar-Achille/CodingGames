import java.util.*;
import java.io.*;
import java.math.*;

/**
 *    https://www.codingame.com/ide/puzzle/ghost-legs
 **/
class Solution 
{

    public static void main(String args[]) 
    {
        Scanner in = new Scanner(System.in);
        int W = in.nextInt();
        int H = in.nextInt();
        // number of vertical lines (A1, B2, ...)
        int lines = ((W-1) / 3)+1;
        int columnsNB = lines-1;
        // number of step from top to bottom
        int lengths = H - 2;
        
        char[] letters = new char[lines];
        char[] numbers = new char[lines];
        boolean[][] columns = new boolean[columnsNB][lengths];
        Map<Integer, Integer> start_end_Int = new HashMap<>();
        for(int i = 0; i<lines; i++)
            start_end_Int.put(Integer.valueOf(i), Integer.valueOf(i));
        
        if (in.hasNextLine()) {
            in.nextLine();
        }
        for (int i = 0; i < H; i++) 
        {
            String line = in.nextLine();
            System.err.println("Line number "+i);
            System.err.println(line);
            // get "letters" (A, B, C, ...)
            if(i == 0)
            {
               letters = getChar(line, letters);
            }
            // get "numbers" (1, 2, 3, ...)
            else if(i == H-1)
            {
               numbers = getChar(line, numbers);
            }
            else
            {
                analyzeLine(line, start_end_Int);
            }
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        for(int i = 0; i<lines; i++)
        {
            char letter = letters[i];
            char number = numbers[start_end_Int.get(Integer.valueOf(i))];
            System.out.println(String.valueOf(letter)+""+String.valueOf(number));
        }
        // System.out.println("answer");
    }
    
    private static char[] getChar(String line, char[] someChars)
    {
        System.err.println("#getChar");
        int i = 0;
        for(char c : line.toCharArray())
        {
            if(c != ' ')
            {
                someChars[i] = c;
                i++;
            }
        }
        return someChars;
    }
    
    private static void analyzeLine(String line, Map<Integer, Integer> map)
    {
        System.err.println("#analyzeLine");
        int lineNumber = -1;
        boolean previousWasLink = false;
        for(char c : line.toCharArray())
        {
            if(c == '|')
            {
                previousWasLink = false;
                lineNumber++;
                continue;
            }
            if(c == ' ')
            {
                previousWasLink = false;
                continue;
            }
            // '-'
            if(previousWasLink)
                continue;
            // 1st '-' of two
            // swap linesNumbers
            previousWasLink = true;
            swapLines(map, lineNumber);
        }
    }
    
    /**
     * Swaps values of line lineNumber and line lineNumber+1.
     */
    private static void swapLines(Map<Integer, Integer> map, int lineNumber)
    {
        //System.err.println("#swapLines (for n°"+lineNumber+" & n°"+(lineNumber+1)+").");
        Integer startingNumber1 = getKeyForValue(map, lineNumber);
        Integer startingNumber2 = getKeyForValue(map, lineNumber+1);
        map.put(startingNumber1, lineNumber+1);
        map.put(startingNumber2, lineNumber);
    }
    
    private static Integer getKeyForValue(Map<Integer, Integer> map, Integer value)
    {
        for(Map.Entry<Integer, Integer> entry : map.entrySet())
        {
            if(value.equals(entry.getValue()))
                return entry.getKey();
        }
        return null;
    }
    
}
