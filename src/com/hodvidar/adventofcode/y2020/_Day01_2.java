package com.hodvidar.adventofcode.y2020;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class _Day01_2 extends AbstractAdventOfCode
{
    @Override
    protected int getDay() {
        return 1;
    }

    public static void main(String[] args) throws Exception {
        AbstractAdventOfCode me = new _Day01_2();
        int result = getResult(me.getScanner());
        System.err.println("Expected '293450526' - result='" + result + "'");
    }

    public static int getResult(Scanner sc) {
        String line;
        List<Integer> numbers = new ArrayList<>();
        while(sc.hasNextLine()) {
            line = sc.nextLine();
            int number = Integer.parseInt(line);
            if(number >= 2020) {
                continue;
            }
            for (int i = 0; i < numbers.size(); i++) {
                int n1 = numbers.get(i);
                for(int j = 0; j < numbers.size(); j++) {
                    int n2 = numbers.get(j);
                    if(j == i) {
                        continue;
                    }
                    if(n1 + n2 >= 2020) {
                        continue;
                    }
                    if( (n1 + n2 + number) == 2020) {
                        return n1 * n2 * number;
                    }
                }
            }
            numbers.add(number);
        }
        return -1;
    }
}