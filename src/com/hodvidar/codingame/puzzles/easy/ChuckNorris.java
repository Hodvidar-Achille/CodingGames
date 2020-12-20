package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/chuck-norris
 * by Hodvidar
 **/
class ChuckNorris {

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        String MESSAGE = in.nextLine();

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        byte[] byteLettresMessage = MESSAGE.getBytes();
        int i = 0;
        StringBuilder builder = new StringBuilder();
        String binaire = "";
        System.err.println("message reçu : "+MESSAGE);
        
        // construire le code binaire charactère par charactère (7 bits)
        String tempBinaireString;
        char[] tempBinaireChar;
        for(byte b : byteLettresMessage){
            i = b;
            tempBinaireString = Integer.toString(i, 2);
            tempBinaireChar = tempBinaireString.toCharArray();
            // verifie que chaque charactère ait 7 digits
            if(tempBinaireChar.length < 7){
                 String bi = "";
                 for(int o = 0; o < 7 - tempBinaireChar.length; o++){
                     bi += "0";
                 }
                 tempBinaireString = bi+tempBinaireString;
                 tempBinaireChar = tempBinaireString.toCharArray();
                 //System.err.println("ajout de '0' --> binaire récupéré : "+binaire);
            }
            binaire += tempBinaireString;
        }
        System.err.println("binaire récupéré : "+binaire);
        char[] binaire2 = binaire.toCharArray();
         
        // construire le code unaire
        builder.setLength(0);
        int j2 = 0;
        for(int j = 0; j < binaire2.length; j++){
            //System.err.println("test case n°"+j +" '"+binaire2[j]+"'");
            if(binaire2[j] == '1'){
                builder.append("0 0");
            } else {
                builder.append("00 0");
            }
            j2 = j+1;
            for(int k = j2; k < binaire2.length; k++){
                if(binaire2[j] == binaire2[k]){
                    builder.append("0");
                    //System.err.println("case n°"+j+" == case n°"+k);
                    j = k;
                } else {
                    builder.append(" ");
                    //System.err.println("case n°"+j+" != case n°"+k+" break !");
                    j = k-1;
                    break;
                }
            }
        }
        binaire = builder.toString();
        System.err.println("unaire récupéré : "+binaire);
        System.out.println(binaire);
        in.close();
    }
}
