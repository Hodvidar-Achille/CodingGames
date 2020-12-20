package com.hodvidar.newbiecontest.logique;

import java.util.ArrayList;
import java.util.List;

/**
 * https://www.newbiecontest.org/index.php?page=epreuve&no=243
 * by Hodvidar
 * <p>
 * (See details at end of class).
 */
public class NeuroCalc {

    private static List<Integer> modification;

    public static void main(String[] args) {
        System.out.println("Hello World");
        String result = "";
        // nothing before 5 billions
        for (double x = 5000000000d; x < Double.MAX_VALUE; x++) {
            String y = "" + x;
            result = neuroCalc_x2y(x);

            if (result.equals(y + y) && !contains8or9(result)) {
                System.out.println("y -> yy : " + y + " renvoie " + y + y);
                continue;
            }
            if (result.equals(y) && !contains8or9(result)) {
                System.out.println("y -> y : " + y + " renvoie " + y);
                continue;
            }
            String tluser = new StringBuilder(y).reverse().toString();
            if (result.equals(tluser) && !contains8or9(result)) {
                System.out.println("y -> y inversé : " + y + " renvoie " + tluser);
                continue;
            }
            if (x % 1000000000 == 0)
                System.out.println("Still running... " + (x / 1000000000));
        }
    }

    private static boolean contains8or9(String s) {
        return s.contains("8") || s.contains("9");
    }

    private static String neuroCalc_x2y(double x) {
        modification = new ArrayList<>();
        double y = neuroCalc_Analyse(x);
        return applyModification("" + y);
    }

    public static String applyModification(String y) {
        for (Integer i : modification) {
            switch (i) {
                case 3:
                    y = modifcation3(y);
                    break;
                case 4:
                    y = modifcation4(y);
                    break;
                case 5:
                    y = modifcation5(y);
                    break;
                case 6:
                    y = modifcation6(y);
                    break;
                case 7:
                    y = modifcation7(y);
                    break;
                default:
                    break;
            }
        }
        return y;
    }

    public static String modifcation3(String y) {
        return y + y;
    }

    public static String modifcation4(String y) {
        return new StringBuilder(y).reverse().toString();
    }

    public static String modifcation5(String y) {
        if (y.length() > 1)
            return y.substring(1);
        return y;
    }

    public static String modifcation6(String y) {
        return "1" + y;
    }

    public static String modifcation7(String y) {
        return "2" + y;
    }

    public static double neuroCalc_Analyse(double x) {
        String s = "" + x;
        if (x > 100 && s.startsWith("1") && s.endsWith("2")) {
            x = neuroCalc_12(x);
            return neuroCalc_Analyse(x);
        } else if (s.startsWith("3")) {
            x = neuroCalc_3(x);
            return neuroCalc_Analyse(x);
        } else if (s.startsWith("4")) {
            x = neuroCalc_4(x);
            return neuroCalc_Analyse(x);
        } else if (s.startsWith("5")) {
            x = neuroCalc_5(x);
            return neuroCalc_Analyse(x);
        } else if (s.startsWith("6")) {
            x = neuroCalc_6(x);
            return neuroCalc_Analyse(x);
        } else if (s.startsWith("7")) {
            x = neuroCalc_7(x);
            return neuroCalc_Analyse(x);
        }
        return x;
    }

    public static double neuroCalc_12(double x) // 1x2 -> x
    {
        String s = "" + x;
        s = s.substring(1, s.length() - 1); // remove 1st and last
        x = Double.valueOf(s);
        return x;
    }

    public static double neuroCalc_3(double x) // 3x -> x (and y -> yy)
    {
        String s = "" + x;
        s = s.substring(1); // remove first
        x = Double.valueOf(s);
        modification.add(3);
        return x;
    }

    public static double neuroCalc_4(double x) // 4x -> x (and y -> y inverse)
    {
        String s = "" + x;
        s = s.substring(1); // remove first
        x = Double.valueOf(s);
        modification.add(4);
        return x;
    }

    public static double neuroCalc_5(double x) // 5x -> x (and y -> y sans 1er chiffre)
    {
        String s = "" + x;
        s = s.substring(1); // remove first
        x = Double.valueOf(s);
        modification.add(5);
        return x;
    }

    public static double neuroCalc_6(double x) // 6x -> x (and y -> 1y)
    {
        String s = "" + x;
        s = s.substring(1); // remove first
        x = Double.valueOf(s);
        modification.add(6);
        return x;
    }

    public static double neuroCalc_7(double x) // 7x -> x (and y -> 2y)
    {
        String s = "" + x;
        s = s.substring(1); // remove first
        x = Double.valueOf(s);
        modification.add(7);
        return x;
    }
}

/*
Description :
MiniComm vient de reposer le fer à souder car il a juste terminé son nouvel ordinateur, le NC (NeuroCalc en fait).
MiniComm a pour l'instant simplement créé un petit jeu. NeuroCalc va en fait vous renvoyer un nombre qui sera fonction de celui que vous aurez entré.
Comment NeuroCalc va-t-il procéder ? Rien de plus simple.
D'abord, il faut que vous compreniez la notation que NeuroCalc utilise.
Soient deux nombres x et y, lorsque NeuroCalc parle de xy (oui oui, il parle) cela signifie le nombre composé de la chaîne x suivie de la chaîne y.
Ainsi, par exemple, si x = 235 et y = 98 renvoie xy = 23598.

Elémentaire, non ? Passons aux cinq règles du jeu, où x et y sont des nombres (entiers positifs en système décimal) :

1 - si vous entrez un nombre de la forme 1x2, NeuroCalc retournera x
Exemple : 13542 renvoie 354

2 - Si lorsque vous entrez un nombre x, NeuroCalc renvoie y, alors entrer 3x renverra yy.
Exemple : comme 15432 renvoie 543, 315432 renvoie 543543.

3 - Si x renvoie y, alors 4x renvoie y à l'envers.
Exemple : comme 172962 renvoie 7296, 4172962 renvoie 6927.

4 - Si x renvoie y avec au moins deux chiffres, alors 5x renvoie y sans son premier chiffre.
Exemple : comme 13472 renvoie 347, 513472 renvoie 47.

5 - Si x renvoie y, alors 6x renvoie 1y et 7x renvoie 2y.

----

Épreuve :
Maintenant, les questions :

1 - trouvez un nombre a tel que la valeur retournée par NeuroCalc soit égale à aa.
2 - trouvez un nombre b tel que la valeur retournée par NeuroCalc soit égale à b.
3 - trouvez un nombre c tel que la valeur retournée par NeuroCalc soit c à l'envers.
Pour chacune des questions, plusieurs solutions sont possibles.
Toutes seront acceptées, à condition de ne contenir que les chiffres 1 à 7.

Vous validerez l'épreuve avec les trois nombres a-b-c séparés par un tiret.
Si par exemple a = 890, b = 753, c = 34509, la solution à entrer est 890-753-34509.

Si bien sûr vous souhaitez coder pour réussir cette épreuve, je ne vous en empêcherai pas.
Mais toutefois, sachez que chacune des solutions est quand même supérieure à un milliard.
Cela dit, un peu de code peut permettre de voir ce que ça donne sur différents exemples.
Mais bon... c'est avant tout une épreuve de pure logique.

Ah... Une seconde, NeuroCalc me parle...
Non, NeuroCalc... Oui, je sais bien, mais c'est non. N'insiste pas...
Excusez-moi, mais NeuroCalc voudrait qu'on vous demande aussi un nombre d qui renvoie le carré de d divisé par l'âge de la grand-mère de MiniComm et euh...
Stop NeuroCalc... stop !!!
Pfff.. Jamais je n'aurais dû accepter cette épreuve :(
Roh, ça se débranche où ces bêtes là ? MiniCoooooooooooooom !!!

Entrez une réponse valide pour obtenir le code de validation :


*/
