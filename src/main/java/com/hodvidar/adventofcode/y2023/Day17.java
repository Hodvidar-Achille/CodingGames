package com.hodvidar.adventofcode.y2023;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class Day17 extends AbstractAdventOfCode2023 {
    protected final int[][] dirs = new int[][]{{0, 1}, {1, 0}, {0, -1}, {-1, 0}}; // down right up left

    protected int mapHeight;
    protected int mapWidth;
    @Override
    public int getDay() {
        return 17;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        final var map = generateMap(sc);
        return solution(map);
    }

    protected int[][] generateMap(final Scanner sc) {
        final List<String> lines = new ArrayList<>();
        while (sc.hasNextLine()) {
            lines.add(sc.nextLine());
        }
        mapHeight = lines.size();
        mapWidth = lines.getFirst().length();
        final int[][] map = new int[lines.size()][lines.getFirst().length()];
        for (int i = 0; i < lines.size(); i++) {
            for (int j = 0; j < mapHeight; j++) {
                map[i][j] = lines.get(i).charAt(j) - '0';
            }
        }
        return map;
    }

    private double solution(final int[][] map) {
        int out = Integer.MAX_VALUE;
        // part 1
        final ArrayList<Node> open = new ArrayList<Node>();
        open.add(new Node(0, 0, 0, dirs[1], 0));
        final HashMap<Key, int[]> closed = new HashMap<Key, int[]>(); // Key -> {count, consec}
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
                int X = x + i[0], Y = y + i[1];
                if (X < 0 || Y < 0 || X >= mapWidth || Y >= mapHeight || dir[0] * i[0] == -1 || dir[1] * i[1] == -1 || consec == 3 && dir == i)
                    continue;
                int newCount = count + map[X][Y];
                if (newCount >= out) continue;
                int newConsec = dir == i ? consec + 1 : 1;
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


    protected class Node {
        int count, x, y, consec;
        int[] dir;

        public Node(int _count, int _x, int _y, int[] _dir, int _consec) {
            count = _count;
            x = _x;
            y = _y;
            dir = _dir;
            consec = _consec;
        }
    }

    protected class Key {
        private final int x;
        private final int y;
        private final int[] dir;

        public Key(final int _x, final int _y, final int[] _dir) {
            x = _x;
            y = _y;
            dir = _dir;
        }

        @Override
        public boolean equals(final Object obj) {
            if (!(obj instanceof final Key k)) return false;
            return x == k.x && y == k.y && dir[0] == k.dir[0] && dir[1] == k.dir[1];
        }

        @Override
        public int hashCode() {
            return (x * mapWidth + y) * 9 + (dir[0] + 1) * 3 + (dir[1] + 1);
        }
    }

}
