package com.hodvidar.adventofcode.y2025;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;
import java.util.Set;

/**
 * Your Advent‑of‑Code day implementation.
 */
public class Day10 extends AbstractAdventOfCode2025 {

    /**
     * Parses a single line according to the specification.
     *
     * @param line the raw input line, e.g.
     *             "[.##.] (3) (1,3) (2) (2,3) (0,2) (0,1) {3,5,4,7}"
     * @return a {@link ParsedLine} holding the three extracted structures.
     */
    static ParsedLine parseLine(final String line) {
        // --------------------------------------------------------------
        // 1️⃣  Extract the [...] part → boolean[]
        // --------------------------------------------------------------
        final int startBracket = line.indexOf('[');
        final int endBracket = line.indexOf(']', startBracket);
        final boolean[] boolArr;
        if (startBracket >= 0 && endBracket > startBracket) {
            final String inside = line.substring(startBracket + 1, endBracket);
            boolArr = new boolean[inside.length()];
            for (int i = 0; i < inside.length(); i++) {
                boolArr[i] = (inside.charAt(i) == '#');
            }
        } else {
            // No bracketed pattern – return an empty array (should not happen for valid input)
            boolArr = new boolean[0];
        }

        // --------------------------------------------------------------
        // 2️⃣  Walk the rest of the line and collect every (…) block
        // --------------------------------------------------------------
        final List<List<Integer>> groups = new ArrayList<>();
        int idx = endBracket + 1;                     // start scanning after the ']'

        while (idx < line.length()) {
            // Skip whitespace and any characters that are not '(' or '{'
            final char ch = line.charAt(idx);
            if (ch == '(') {
                // Find the matching ')'
                final int close = line.indexOf(')', idx);
                if (close == -1) break;              // malformed line – stop processing
                final String inner = line.substring(idx + 1, close).trim();
                final List<Integer> ints = new ArrayList<>();
                if (!inner.isEmpty()) {
                    for (final String part : inner.split(",")) {
                        ints.add(Integer.parseInt(part.trim()));
                    }
                }
                groups.add(ints);
                idx = close + 1;                      // continue after ')'
            } else if (ch == '{') {
                // We reached the curly‑brace block – handle it after the loop
                break;
            } else {
                idx++;                                 // ignore any other character (space, etc.)
            }
        }

        // --------------------------------------------------------------
        // 3️⃣  Extract the {...} block → List<Integer>
        // --------------------------------------------------------------
        final List<Integer> numbers = new ArrayList<>();
        final int openBrace = line.indexOf('{', idx);
        final int closeBrace = line.indexOf('}', openBrace);
        if (openBrace >= 0 && closeBrace > openBrace) {
            final String inner = line.substring(openBrace + 1, closeBrace).trim();
            if (!inner.isEmpty()) {
                for (final String part : inner.split(",")) {
                    numbers.add(Integer.parseInt(part.trim()));
                }
            }
        }

        return new ParsedLine(boolArr, groups, numbers);
    }

    /**
     * Finds the smallest set of buttons that toggles the lights from all‑off
     * to the desired pattern.
     *
     * @param pattern target boolean array, e.g. [false,true,true,false]
     * @param groups  list of button definitions – each inner list holds the
     *                indices of lights toggled by that button
     * @return a {@link ButtonSolution} describing the optimal press sequence
     */
    static ButtonSolution solveMinimalButtons(final boolean[] pattern,
                                              final List<List<Integer>> groups) {

        final int nLights = pattern.length;
        final int nButtons = groups.size();

        // ---------- 1️⃣  Encode the target pattern as a bitset (long) ----------
        // We assume nLights ≤ 64 – more than enough for the Advent‑of‑Code tasks.
        long targetMask = 0L;
        for (int i = 0; i < nLights; i++) {
            if (pattern[i]) {
                targetMask |= (1L << i);
            }
        }

        // ---------- 2️⃣  Pre‑compute each button as a bitmask ----------
        final long[] buttonMasks = new long[nButtons];
        for (int b = 0; b < nButtons; b++) {
            long mask = 0L;
            for (final int idx : groups.get(b)) {
                if (idx < 0 || idx >= nLights) {
                    throw new IllegalArgumentException(
                            "Button " + b + " references invalid light index " + idx);
                }
                mask ^= (1L << idx);           // toggle → XOR with a single‑bit mask
            }
            buttonMasks[b] = mask;
        }

        // ---------- 3️⃣  Breadth‑first search over subsets ----------
        // Each queue element stores:
        //   currentMask – lights state after pressing the selected buttons
        //   usedMask    – which buttons have been pressed (bitset of length nButtons)
        //   depth       – number of pressed buttons (also popcount(usedMask))
        record Node(long curMask, long usedMask, int depth) {
        }

        final Queue<Node> q = new ArrayDeque<>();
        final Set<Long> visited = new HashSet<>();      // remember visited (curMask,usedMask) pairs
        // start from all‑off, no button used
        q.add(new Node(0L, 0L, 0));
        visited.add(0L);                          // encode as (curMask << nButtons) | usedMask

        while (!q.isEmpty()) {
            final Node cur = q.poll();

            // Success?  (all‑off → target reached)
            if (cur.curMask == targetMask) {
                // decode which buttons were used
                final List<Integer> pressed = new ArrayList<>();
                for (int b = 0; b < nButtons; b++) {
                    if ((cur.usedMask & (1L << b)) != 0) {
                        pressed.add(b);
                    }
                }
                return new ButtonSolution(cur.depth, pressed);
            }

            // Try adding each button that hasn't been used yet.
            for (int b = 0; b < nButtons; b++) {
                final long buttonBit = 1L << b;
                if ((cur.usedMask & buttonBit) != 0) continue; // already pressed

                final long nextMask = cur.curMask ^ buttonMasks[b]; // toggle lights
                final long nextUsed = cur.usedMask | buttonBit;
                final long encode = (nextMask << nButtons) | nextUsed;
                if (visited.add(encode)) {   // only enqueue unseen states
                    q.add(new Node(nextMask, nextUsed, cur.depth + 1));
                }
            }
        }

        // If we exit the loop there is no solution (should not happen for well‑formed input).
        return new ButtonSolution(-1, Collections.emptyList());
    }

    // ------------------------------------------------------------------------
    // Existing abstract methods – keep the signatures unchanged.
    // ------------------------------------------------------------------------
    @Override
    public double getDigitFromLine(final String line) {
        final ParsedLine parsed = parseLine(line);
        return solveMinimalButtons(parsed.pattern, parsed.groups).presses;
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        double counter = 0;
        while (sc.hasNextLine()) {
            // Process each line – the method currently discards the result.
            counter += this.getDigitFromLine(sc.nextLine());
        }
        return counter;
    }

    record ParsedLine(boolean[] pattern, List<List<Integer>> groups, List<Integer> numbers) {
    }

    record ButtonSolution(int presses, List<Integer> buttonIndices) {
    }
}