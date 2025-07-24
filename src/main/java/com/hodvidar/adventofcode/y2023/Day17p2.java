package com.hodvidar.adventofcode.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Day17p2 extends Day17 {

    @Override
    public double getResultDouble(final Scanner sc) {
        return solution2(generateMap(sc));
    }

    private double solution2(final int[][] map) {
        int out = Integer.MAX_VALUE;
        final ArrayList<Node> open = new ArrayList<Node>();
        open.add(new Node(0, 0, 0, dirs[1], 0));
        final HashMap<Key, int[]> closed = new HashMap<Key, int[]>();
        closed.put(new Key(0, 0, dirs[1]), new int[]{0, 0});
        while (open.size() > 0) {
            int minIdx = 0;
            int minScore = Integer.MAX_VALUE;
            for (int i = open.size() - 1; i >= 0; i--) {
                Node n = open.get(i);
                int score = n.count;
                if (score < minScore) {
                    minScore = score;
                    minIdx = i;
                }
            }
            Node n = open.remove(minIdx);
            int count = n.count, x = n.x, y = n.y, consec = n.consec;
            int[] dir = n.dir;
            dirLoop:
            for (int[] i : dirs) {
                boolean turn = i != dir || consec < 4;
                int X, Y, newCount, newConsec;
                if (!turn) {
                    X = x + i[0];
                    Y = y + i[1];
                    if (X < 0 || Y < 0 || X >= mapWidth || Y >= mapHeight || consec == 10) continue;
                    newCount = count + map[X][Y];
                    if (newCount >= out) continue;
                    newConsec = consec + 1;
                } else {
                    if (consec >= 4 && (dir[0] == i[0] || dir[1] == i[1])) continue;
                    X = x;
                    Y = y;
                    newCount = count;
                    newConsec = 0;
                    for (int j = 0; j < 4; j++) {
                        X += i[0];
                        Y += i[1];
                        newConsec++;
                        if (X < 0 || Y < 0 || X >= mapWidth || Y >= mapHeight) continue dirLoop;
                        newCount += map[X][Y];
                        if (newCount >= out) continue dirLoop;
                        if (X == mapWidth - 1 && Y == mapHeight - 1) break;
                    }
                }
                Key k = new Key(X, Y, i);
                if (X == mapWidth - 1 && Y == mapHeight - 1) {
                    if (newCount < out) out = newCount;
                    closed.put(k, new int[]{newCount, newConsec});
                } else {
                    int[] closedNode = closed.getOrDefault(k, null);
                    if (closedNode != null && closedNode[0] <= newCount && closedNode[1] <= newConsec) continue;
                    for (int j = open.size() - 1; j >= 0; j--) {
                        Node openNode = open.get(j);
                        if (openNode.x == X && openNode.y == Y && openNode.count <= newCount && openNode.consec <= newConsec && openNode.dir == i)
                            continue dirLoop;
                    }
                    open.add(new Node(newCount, X, Y, i, newConsec));
                }
            }
            closed.put(new Key(x, y, dir), new int[]{count, consec});
        }
        return out;
    }
}
