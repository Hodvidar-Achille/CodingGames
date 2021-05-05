package com.hodvidar.utils.number;

import java.util.Scanner;

public class LargeNumber {

    public static void main(final String[] args) {
        System.out.println("Hello World - create scanner : \n");
        final Scanner scanner = new Scanner(System.in);

        System.out.println("Enter \nNumber : ");
        final String numberStr = scanner.nextLine();
        final int number = Integer.parseInt(numberStr);

        System.out.println("Puissance : ");
        final String puissanceStr = scanner.nextLine();
        final int puissance = Integer.parseInt(puissanceStr);

        System.out.println("Diviseur : ");
        final String moduloStr = scanner.nextLine();
        final int modulo = Integer.parseInt(moduloStr);

        final int a = number % modulo;
        final int a1 = a;
        int p = 1;
        for (int i = 1; i <= puissance; i++) {
            p *= a1;
            p = p % modulo;
        }
        System.out.println("modulo = " + p);
        scanner.close();
    }
}

// see http://stackoverflow.com/questions/2177781/how-to-calculate-modulus-of-large-numbers
