package com.hodvidar.utils.number;

import java.util.Scanner;

public class LargeNumber {

    public static void main(String[] args) {
        System.out.println("Hello World - create scanner : \n");
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter \nNumber : ");
        String numberStr = scanner.nextLine();
        int number = Integer.parseInt(numberStr);

        System.out.println("Puissance : ");
        String puissanceStr = scanner.nextLine();
        int puissance = Integer.parseInt(puissanceStr);

        System.out.println("Diviseur : ");
        String moduloStr = scanner.nextLine();
        int modulo = Integer.parseInt(moduloStr);

        int a = number % modulo;
        int a1 = a;
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
