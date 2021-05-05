package com.hodvidar.codingame.puzzles.easy;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/mime-type by Hodvidar
 **/
class MimeType {

    public static void main(final String[] args) {
        final Scanner in = new Scanner(System.in);
        final int N = in.nextInt(); // Number of elements which make up the association table.
        final int Q = in.nextInt(); // Number Q of file names to be analyzed.
        final Map<String, String> mimes = new HashMap<String, String>(N);

        for (int i = 0; i < N; i++) {
            final String EXT = in.next(); // file extension
            final String MT = in.next(); // MIME type.
            System.err.println("add : " + EXT + " " + MT);
            in.nextLine();
            /**
             * extensions[i] = EXT != null ? EXT : ""; mimes[i] = MT != null ? MT : "";
             **/
            mimes.put(EXT.toLowerCase(), MT);
        }

        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < Q; i++) {
            final String FNAME = in.nextLine(); // One file name per line.

            final int l = FNAME.length();
            final int p = FNAME.lastIndexOf('.');

            System.err.println("file to test : " + FNAME);

            if (l > 256 || p < 0 || (l - (p + 1) > 10)) {
                builder.append("UNKNOWN\n");
            } else {
                final String x = FNAME.substring(p + 1, l).toLowerCase();
                System.err.println("    extension detected : " + x);
                if (mimes.containsKey(x)) {
                    builder.append(mimes.get(x) + "\n");
                } else {
                    builder.append("UNKNOWN\n");
                }
            }
        }
        System.out.println(builder.toString());
        in.close();
    }

}
