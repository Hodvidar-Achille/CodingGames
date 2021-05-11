package com.hodvidar.newbiecontest.logique;

/**
 * by Hodvidar
 */
public class LogiqueTresCombinatoire {

    public static void main(final String[] args) {
        System.out.println("Hello World");
        for (int i = 0; i < 32; i++) {
            boolean a = false;
            boolean b = false;
            boolean c = false;
            boolean d = false;
            boolean e = false;
            final String str = Integer.toBinaryString(i);
            int j = 0;
            for (final char ch : str.toCharArray()) {
                switch (j) {
                    case 0:
                        if (ch == '1') {
                            a = true;
                        }
                        break;
                    case 1:
                        if (ch == '1') {
                            b = true;
                        }
                        break;
                    case 2:
                        if (ch == '1') {
                            c = true;
                        }
                        break;
                    case 3:
                        if (ch == '1') {
                            d = true;
                        }
                        break;
                    case 4:
                        if (ch == '1') {
                            e = true;
                        }
                        break;
                    default:
                        break;
                }
                j++;
            }
            final boolean p1 = (a && b); // AND
            final boolean p2 = c == d; //N XOR
            final boolean p3 = e; // OR ?
            final boolean p4 = !(p2 || p3); // N OR
            final boolean p5 = (p1 ^ p4); // XOR
            final boolean p6 = !p5; // NOT
            final boolean p7 = !(e && p5); // N AND
            final boolean p8 = !(!(!p7)); // NOT x3
            final boolean p9 = !p8; // NOT
            final boolean p10 = !(p6 && p8); // N AND
            final boolean p11 = (p10 ^ p9); // XOR
            if (p11) {
                System.out.println("true for : " + str);
            }
        }

    }
}
