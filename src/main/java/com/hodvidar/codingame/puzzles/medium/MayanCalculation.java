package com.hodvidar.codingame.puzzles.medium;

import java.util.*;

/**
 * https://www.codingame.com/ide/puzzle/mayan-calculation
 * by Hodvidar
 **/
class MayanCalculation {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int L = in.nextInt();
        final int H = in.nextInt();
        System.err.println("L: " + L + " H: " + H);

        ArrayList<String> storage = null;
        for (int i = 0; i < H; i++) {
            final String numeral = in.next();
            //System.err.println("i: "+i+" numeral: "+numeral);
            storage = storeNumbers(storage, numeral, L);
        }
        final Map<String, Integer> numbers = storeNumbers2(storage);

        storage = null;
        final int S1 = in.nextInt();
        final int nbOfNumbers1 = S1 / H;
        //System.err.println("S1: "+S1);
        for (int i = 0; i < S1; i++) {
            final String num1Line = in.next();
            //System.err.println("num1Line: "+num1Line);
            storage = storeNewNumber(storage, num1Line, i / H, nbOfNumbers1);
        }

        final int number1 = convertNumber(numbers, storage);

        storage = null;
        final int S2 = in.nextInt();
        final int nbOfNumbers2 = S2 / H;
        //System.err.println("S2: "+S2);
        for (int i = 0; i < S2; i++) {
            final String num2Line = in.next();
            //System.err.println("num2Line: "+num2Line);
            storage = storeNewNumber(storage, num2Line, i / H, nbOfNumbers2);
        }

        final int number2 = convertNumber(numbers, storage);
        final String operation = in.next();
        System.err.println("number1: " + number1);
        System.err.println("number2: " + number2);
        System.err.println("operation: " + operation);

        // Write an action using System.out.println()
        // To debug: System.err.println("Debug messages...");
        final double result = doOperation(number1, number2, operation);
        System.err.println("result: " + result);
        printMayaNumber(result, numbers, L, H);
        //System.out.println(result);
        in.close();
    }

    /**
     * handle if storage is null
     */
    private static ArrayList<String> storeNumbers(ArrayList<String> storage, final String line, final int largeur) {
        if (storage == null || storage.size() == 0) {
            storage = new ArrayList<>();
            for (int i = 0; i < 20; i++)
                storage.add("");
        }

        for (int i = 0; i < 20; i++) {
            final String s = line.substring(i * largeur, (i + 1) * largeur);
            storage.set(i, storage.get(i) + s);
        }
        return storage;
    }

    private static Map<String, Integer> storeNumbers2(final ArrayList<String> storage) {
        final Map<String, Integer> numbers = new HashMap<>();
        for (int i = 0; i < 20; i++) {
            numbers.put(storage.get(i), i);
            //System.err.println("numbers K | V : "+storage.get(i)+" | "+numbers.get(storage.get(i)));
        }
        return numbers;
    }

    private static ArrayList<String> storeNewNumber(ArrayList<String> storage, final String line, final int index, final int nbOfNumbers1) {
        if (storage == null || storage.size() == 0) {
            storage = new ArrayList<>();
            for (int i = 0; i < nbOfNumbers1; i++)
                storage.add("");
        }
        storage.set(index, storage.get(index) + line);
        return storage;
    }

    // [12, 0, 5] --> 4805
    private static int convertNumber(final Map<String, Integer> numbers, final ArrayList<String> storage) {
        final int size = storage.size();
        double power = size - 1;
        int total = 0;
        for (int i = 0; i < size; i++) {
            final int number = numbers.get(storage.get(i));
            total += number * Math.pow(20.0d, power);
            power--;
        }
        return total;
    }

    private static double doOperation(final double number1, final double number2, final String operation) {
        switch (operation) {
            case "+":
                return number1 + number2;
            case "-":
                return number1 - number2;
            case "/":
                return number1 / number2;
            case "*":
                return number1 * number2;
        }
        return -1;
    }

    private static void printMayaNumber(final double number, final Map<String, Integer> numbers, final int largeur, final int hauteur) {
        final ArrayList<Integer> numberList = decomposeNumber(number);
        System.err.println("numberList: " + numberList);
        for (final Integer i : numberList) {
            String line = "";
            for (final Map.Entry<String, Integer> entry : numbers.entrySet()) {
                if (entry.getValue().equals(i)) {
                    line = entry.getKey();
                    for (int j = 0; j < hauteur; j++) {
                        final String s = line.substring(j * largeur, (j + 1) * largeur);
                        System.out.println(s);
                    }
                }
            }
        }
    }

    // 4805 --> [12, 0, 5]
    private static ArrayList<Integer> decomposeNumber(double number) {
        final ArrayList<Integer> storage = new ArrayList<>();
        while (true) {
            final double rest = number % 20d;
            System.err.println("rest: " + rest);
            storage.add((int) rest);
            number = Math.floor(number / 20);
            if (number == 0)
                break;
        }
        Collections.reverse(storage);
        return storage;
    }

}
