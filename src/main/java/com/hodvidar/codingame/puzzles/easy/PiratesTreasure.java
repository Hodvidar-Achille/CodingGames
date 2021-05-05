package com.hodvidar.codingame.puzzles.easy;

import java.util.Scanner;

/**
 * https://www.codingame.com/ide/puzzle/pirates-treasure by Hodvidar
 **/
class PiratesTreasure {

    public static void main(final String[] args) {
        System.err.println("START");

        final Scanner in = new Scanner(System.in);
        final int W = in.nextInt();
        final int H = in.nextInt();
        final int[][] map = new int[H][W];
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                final int v = in.nextInt();
                map[i][j] = v;
            }
        }
        in.close();

        // Dummy implementation, go through every point and check if
        // 1) It is == 0
        // 2) It is surrounded by 1
        for (int i = 0; i < H; i++) {
            for (int j = 0; j < W; j++) {
                if (map[i][j] == 1)
                    continue;

                if (isSurrounded(j, i, map)) {
                    System.out.println(j + " " + i);
                    return;
                }
            }
        }

        System.err.println("END");
    }

    private static boolean isSurrounded(final int x, final int y, final int[][] map) {
        final boolean isOut_left = (x == 0);
        final boolean isOut_top = (y == 0);
        final boolean isOut_right = (x == map[0].length - 1);
        final boolean isOut_bottom = (y == map.length - 1);

        final boolean topLeft = isOut_top || isOut_left || map[y - 1][x - 1] == 1;
        final boolean top = isOut_top || map[y - 1][x] == 1;
        final boolean topRight = isOut_top || isOut_right || map[y - 1][x + 1] == 1;
        final boolean left = isOut_left || map[y][x - 1] == 1;
        final boolean right = isOut_right || map[y][x + 1] == 1;
        final boolean bottomLeft = isOut_bottom || isOut_left || map[y + 1][x - 1] == 1;
        final boolean bottom = isOut_bottom || map[y + 1][x] == 1;
        final boolean bottomRight = isOut_bottom || isOut_right || map[y + 1][x + 1] == 1;

        return topLeft && top && topRight && left && right && bottomLeft && bottom && bottomRight;
    }
}
