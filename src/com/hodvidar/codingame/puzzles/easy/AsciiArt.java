package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/ascii-art
 * by Hodvidar
 **/
public class AsciiArt 
{

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int L = in.nextInt();
        int H = in.nextInt();
        in.nextLine();
        String T = in.nextLine();
        
        //input toujours en uppercase
        T = T.toUpperCase();
        
        int longueurResultat = T.length();
        char[] motEcrit = T.toCharArray();
        // Largeur * (26 lettres + 1 signe) + 26 espace entre les 26 premières lettres
        int LTotale = L * 27 + 26; 
        char[][] tab = new char[H][LTotale];
        char[][] motResultat = new char[H][longueurResultat  * L];
        System.err.println("mot à lire : "+T);
        for (int i = 0; i < H; i++) {
            String ROW = in.nextLine();
            System.err.println("ligne "+i+" : "+ROW);
            
            // recuperation des # dans le tableau
            tab[i] = ROW.toCharArray();
        }

        // System.err.println("ligne test A");
        // alphabet en lettre normale pour comparaison
        char[] alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();
        // System.err.println("ligne test A alpjabet length :" + alphabet.length);
        // pour espace entre lettres dans motResultat (sauf après dernière lettre)
        
        // test contenu
        /*
        for(int i = 0; i < alphabet.length; i++){
             System.err.print("_"+alphabet[i]);
        }
        */
        System.err.println();
        int i2 = 0, j2 = 0;
        boolean boo = false;
        for(int i = 0; i < longueurResultat; i++)
        {
            System.err.println("ligne test B "+ i);
            boo = false;
            for(int j = 0; j < alphabet.length; j++)
            {
               // System.err.println("ligne test C "+ j);
                if(motEcrit[i] == alphabet[j])
                {
                    if(j > 25)
                    {
                        j -= 26;
                        System.err.println("lowerCase to upperCase : "+alphabet[j+26]+" --> "+alphabet[j] );
                    }
                    System.err.println("ligne test D "+ j +" lettre : "+alphabet[j]);
                    // on recupère contenu sur plusieurs cases (de h:0 à h:H et de i+0 à i+L2)
                    for(int a = 0; a < H; a++)
                    {
                        for(int b = 0; b < L; b++)
                        {
                            i2 = L * i;
                            j2 = (L) * j;
                            motResultat[a][i2+b] = tab[a][j2+b];
                            //System.err.println("ligne test E,["+a+"]["+(j+b)+"] signe recupere : "+tab[a][j+b]);
                        }
                    }
                    boo = true;
                	break;
                } 
                else 
                {
                    if(motEcrit[i] == 't')
                    {
                        System.err.println("??????? ????? ????");
                        break;
                    }
                }
            }
            if(!boo)
            {
                System.err.println("special caractère for letter n°"+i+" --> "+motEcrit[i]);
                // ajout du caractère "?"
                for(int a = 0; a < H; a++)
                {
                    for(int b = 0; b < L; b++)
                    {
                        i2 = L * i;
                        j2 = (L) * 26;
                        motResultat[a][i2+b] = tab[a][j2+b];
                        //System.err.println("ligne test E,["+a+"]["+(j+b)+"] signe recupere : "+tab[a][j+b]);
                    }
                }
            }
        }
        
        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        
        //ecriture en H string :
        for(int i = 0; i < H; i++)
        {
            String ligneResult = new String(motResultat[i]);
            System.out.println(ligneResult);
        }
        in.close();
    }
}
