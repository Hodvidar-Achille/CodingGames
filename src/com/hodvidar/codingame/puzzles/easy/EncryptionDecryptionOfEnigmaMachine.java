package com.hodvidar.codingame.puzzles.easy;

import java.util.*;

/**
 *      https://www.codingame.com/ide/puzzle/encryptiondecryption-of-enigma-machine
 * by Hodvidar
 **/
class EncryptionDecryptionOfEnigmaMachine {

    private static final String ENCODE = "ENCODE";
    
    private static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
    
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String operation = in.nextLine();
        int pseudoRandomNumber = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        String[] rotors = new String[3];
        for (int i = 0; i < 3; i++) {
            rotors[i] = in.nextLine();
        }
        String message = in.nextLine();
        System.err.println("message n°1: "+message);
        
        // Encore or Decode :
        if(ENCODE.equals(operation))
            message = encodeMessage(message, rotors, pseudoRandomNumber);
        else
            message = decodeMessage(message, rotors, pseudoRandomNumber);
        
        
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");

        System.out.println(message);
        in.close();
    }
    
    private static String encodeMessage(String message, String[] rotors, int number)
    {
        System.err.println("ENCODING...");
        
        // ---- Caesar shift ----
        message = caesarShift(number, message, false);
        System.err.println("message n°2: "+message);
        
        // ---- ROTOR I ----
        message = rotorShift(message, rotors[0], false);
        System.err.println("message n°3: "+message);
        
        // ---- ROTOR II ----
        message = rotorShift(message, rotors[1], false);
        System.err.println("message n°4: "+message);
        
        // ---- ROTOR III ----
        message = rotorShift(message, rotors[2], false);
        System.err.println("message n°5: "+message);
        
        return message;
    }
    
    private static String decodeMessage(String message, String[] rotors, int number)
    {
        System.err.println("DECODING...");
        
        // ---- ROTOR III reverse ---
        message = rotorShift(message, rotors[2], true);
        System.err.println("message n°2: "+message);
        
        // ---- ROTOR II reverse ---
        message = rotorShift(message, rotors[1], true);
        System.err.println("message n°3: "+message);
        
        // ---- ROTOR I reverse ---
        message = rotorShift(message, rotors[0], true);
        System.err.println("message n°4: "+message);
        
        // ---- Caesar shift reverse ---
        message = caesarShift(number, message, true);
        System.err.println("message n°5: "+message);
        
        return message;
    }
    
    private static String caesarShift(int number, String message, boolean decode)
    {
        char[] letters = message.toCharArray();
        String newMessage = "";
        
        if(decode)
        {
            for(char c : letters)
            {
                int n = numberOfLetter(c);
                n = getNumberBetween0And25(n-number);
                newMessage+=ALPHABET[n];
                number++;
            }
            return newMessage;
        }
        
        for(char c : letters)
        {
            int n = numberOfLetter(c);
            n = getNumberBetween0And25(n+number);
            newMessage+=ALPHABET[n];
            number++;
        }
        return newMessage;
    }
    
    private static String rotorShift(String message, String rotor, boolean decode)
    {
        char[] rotor2 = rotor.toCharArray();

        if(decode)
            return rotorShift(message, ALPHABET, rotor2);
        
        return rotorShift(message, rotor2, ALPHABET);
    }
    
    private static String rotorShift(String message, char[] rotor, char[] reference)
    {
        String newMessage = "";
        for(char c : message.toCharArray())
        {
            int n = numberOfLetter(c, reference);
            n = getNumberBetween0And25(n);
            newMessage += rotor[n];
        }
        return newMessage;
    }
    
    /**
     *  Use the ALPHABET to loof for the number.
     */
    private static int numberOfLetter(char aLetter)
    {
        return numberOfLetter(aLetter, ALPHABET);
    }
    /**
     *  Use any char array to look for the number.
     *  @praram reference : must be composed of the 26 letters of the alphabet. 
     */
    private static int numberOfLetter(char aLetter, char[] reference)
    {
        int i = 0;
        for(char c : reference)
        {
            if(c == aLetter)
                return i;
            i++;
        }
        return -1;
    }
    
    private static int getNumberBetween0And25(int n)
    {
        if(n < 26 && n >= 0)
            return n;
            
        if(n > 0)
        {
            n = n - 26;
            return getNumberBetween0And25(n);
        }
        
        n = n + 26;
        return getNumberBetween0And25(n);
    }
}
