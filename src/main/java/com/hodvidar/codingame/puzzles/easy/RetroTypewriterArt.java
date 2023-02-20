import java.util.*;
import java.io.*;
import java.math.*;

/**
 * https://www.codingame.com/ide/puzzle/retro-typewriter-art
 * by Hodvidar
 **/
class Solution {

    public static void main(String args[]) {
        Scanner in = new Scanner(System.in);
        String T = in.nextLine();
        StringBuilder sb = new StringBuilder();
        for(String chunk : T.split("\\s")) {
            sb.append(translateChunk(chunk));
        }
        System.out.println(sb.toString());
    }

    private static String translateChunk(String aChunk)
    {
        if("nl".equals(aChunk)) {
            return System.lineSeparator();
        }
        int length = aChunk.length();
        String endOfChunk = aChunk.substring(length-2, length);
        if("sp".equals(endOfChunk)) {
            int repeatNumber = Integer.valueOf(aChunk.substring(0, length-2));
            return " ".repeat(repeatNumber);
        }
        if("bS".equals(endOfChunk)) {
            int repeatNumber = Integer.valueOf(aChunk.substring(0, length-2));
            return "\\".repeat(repeatNumber);
        }
        if("sQ".equals(endOfChunk)) {
            int repeatNumber = Integer.valueOf(aChunk.substring(0, length-2));
            return "'".repeat(repeatNumber);
        }
        endOfChunk = aChunk.substring(length-1, length);
        int repeatNumber = Integer.valueOf(aChunk.substring(0, length-1));
        return endOfChunk.repeat(repeatNumber);
    }
}
