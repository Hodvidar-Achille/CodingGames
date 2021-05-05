package com.hodvidar.adventofcode.y2019;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import static com.hodvidar.utils.file.Constance.RESOURCES;

public final class _Day06_2 {
    /**
     * If 'false' only response and Failure are written
     **/
    private static final boolean VERBOSE = false;

    private static final int NUMBER_OF_TEST = 6;
    private static final String INPUT_DIRECTORY = "adventofcode_2019"; // input1
    private static final String COM = "COM";
    private static final String YOU = "YOU";
    private static final String SAN = "SAN";

    public static void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public static void main(String[] args) throws Exception {
        int result = test(RESOURCES + File.separator + INPUT_DIRECTORY + File.separator + "input"
                + NUMBER_OF_TEST + ".txt");
        System.err.println("result='" + result + "'");
    }

    private static int test(String inputFile) throws Exception {
        String line;
        File file = new File(inputFile);
        // Scanner sc = new Scanner(System.in);
        Scanner sc = new Scanner(file);
        printIfVerbose("DEBUGGING");

        Map<String, Node> planetes = new HashMap<>();
        while (sc.hasNextLine()) {
            // line = input[i];
            line = sc.nextLine();
            String[] line2 = line.split("\\)");
            String a = line2[0];
            String b = line2[1];

            Node A = planetes.get(a);
            if (A == null)
                A = new Node(a);
            Node B = planetes.get(b);
            if (B == null)
                B = new Node(b);
            boolean success = B.connectToParent(A);
            if (!success) {
                sc.close();
                throw new IllegalStateException("Failed to connect child:"
                        + B.getName() + " to parent:" + A.getName());
            }
            planetes.put(A.getName(), A);
            planetes.put(B.getName(), B);
        }
        sc.close();
        planetes.get(COM).propagadeLevels(0);
        Node me = planetes.get(YOU);
        Node santa = planetes.get(SAN);

        int myLevel = me.getLevel();
        int santaLevel = santa.getLevel();

        Node commonAncestor = me.findCommonAncestor(santa);
        int commonLevel = commonAncestor.getLevel();

        int total = (myLevel - commonLevel) + (santaLevel - commonLevel);

        // -1 movement to go to same planete as Santa
        return total - 2;
    }

}
