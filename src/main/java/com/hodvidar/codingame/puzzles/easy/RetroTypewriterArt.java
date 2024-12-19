package com.hodvidar.codingame.puzzles.easy;

import java.util.*;

/**
 * https://www.codingame.com/ide/puzzle/retro-typewriter-art
 * by Hodvidar
 **/
class RetroTypewriterArt {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final String T = in.nextLine();
        final StringBuilder sb = new StringBuilder();
        for(final String chunk : T.split("\\s")) {
            sb.append(translateChunk(chunk));
        }
        System.out.println(sb);
    }

    private static String translateChunk(final String aChunk)
    {
        if("nl".equals(aChunk)) {
            return System.lineSeparator();
        }
        final int length = aChunk.length();
        String endOfChunk = aChunk.substring(length-2, length);
        if("sp".equals(endOfChunk)) {
            final int repeatNumber = Integer.valueOf(aChunk.substring(0, length-2));
            return " ".repeat(repeatNumber);
        }
        if("bS".equals(endOfChunk)) {
            final int repeatNumber = Integer.valueOf(aChunk.substring(0, length-2));
            return "\\".repeat(repeatNumber);
        }
        if("sQ".equals(endOfChunk)) {
            final int repeatNumber = Integer.valueOf(aChunk.substring(0, length-2));
            return "'".repeat(repeatNumber);
        }
        endOfChunk = aChunk.substring(length-1, length);
        final int repeatNumber = Integer.valueOf(aChunk.substring(0, length-1));
        return endOfChunk.repeat(repeatNumber);
    }
}
