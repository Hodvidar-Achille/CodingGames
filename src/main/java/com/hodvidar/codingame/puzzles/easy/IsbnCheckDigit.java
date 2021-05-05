package com.hodvidar.codingame.puzzles.easy;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/isbn-check-digit by Hodvidar
 **/
class IsbnCheckDigit {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int N = in.nextInt();
        if (in.hasNextLine()) {
            in.nextLine();
        }
        final List<String> invalids = new ArrayList<>();
        for (int i = 0; i < N; i++) {
            final String ISBN = in.nextLine();
            if (!isCorrect(ISBN)) {
                System.err.println("isCorrect(" + ISBN + ") == FALSE");
                invalids.add(ISBN);
            }
        }

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        System.out.println(invalids.size() + " invalid:");
        for (final String s : invalids)
            System.out.println(s);
        in.close();
    }

    private static boolean isCorrect(final String aISBN) {
        System.err.println("isCorrect(" + aISBN + ")");
        final int length = aISBN.length();
        if (length != 13 && length != 10)
            return false;

        System.err.println("length = " + length);

        if (!aISBN.matches("[0-9]+")) {
            if (length != 10)
                return false;
            if (!aISBN.substring(0, 9).matches("[0-9]+"))
                return false;
            if (!aISBN.startsWith("X", 9))
                return false;
        }

        // 10
        if (length == 10) {
            int m = 10;
            int prod = 0;
            int lastDigit = -1;
            for (final char c : aISBN.toCharArray()) {
                final int a = Character.getNumericValue(c);
                if (m == 1) {
                    lastDigit = (c == 'X') ? 10 : a;
                    break;
                }
                // System.err.println("prod= "+prod+" + a*m:"+a+"*"+m);
                prod += a * m;

                m--;
            }
            System.err.println("prod= " + prod);
            int code = prod % 11;
            System.err.println("prod % 11= " + code);
            code = (code == 0) ? 0 : 11 - code;
            System.err.println("(10)  code = " + code);
            return code == lastDigit;
        }

        // 13
        int m = 0;
        int prod = 0;
        int lastDigit = -1;
        for (final char c : aISBN.toCharArray()) {
            final int a = Character.getNumericValue(c);
            if (m == 12) {
                lastDigit = a;
                break;
            }
            final int i = (m % 2 == 0) ? 1 : 3;
            // System.err.println("prod= "+prod+" + a*i:"+a+"*"+i);
            prod += a * i;
            m++;
        }
        System.err.println("prod= " + prod);
        int code = prod % 10;
        System.err.println("prod % 10= " + code);
        code = (code == 0) ? 0 : 10 - code;
        System.err.println("(13)  code = " + code);
        return code == lastDigit;
    }

}
