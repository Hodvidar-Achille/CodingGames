package com.hodvidar.utils;

import java.util.Base64;

public final class Base64Coder {

    public static void main(final String[] args) {
        // TODO Auto-generated method stub
        String s = "Hello";
        print(s); // Hello
        byte[] b = s.getBytes();
        print(b); // '72101108108111'

        s = new String(b);
        print(s); // Hello

        b = Base64.getEncoder().encode(b);
        print(b); // '83718611598715661'

        // Change b here

        s = new String(b);
        print(s); // 'SGVsbG8='

        b = Base64.getDecoder().decode(b);
        print(b);
        s = new String(b);
        print(s); // 'Hello'

        b = Base64.getEncoder().encode(b);
        print(b); // '83718611598715661'
        s = new String(b);
        print(s); // 'SGVsbG8='

        b = Base64.getDecoder().decode(s);
        print(b); // '72101108108111' == 'Hello'

        s = Base64.getEncoder().encodeToString(b);
        print(s); // 'SGVsbG8='
    }

    private static void print(final String s) {
        System.out.println("s='" + s + "'");
    }

    private static void print(final byte[] b) {
        System.out.print("b='");
        for (final byte a : b) {
            System.out.print(a);
        }
        System.out.println("'");
    }

}
