package com.hodvidar.adventofcode.y2025;

import java.util.*;
import java.util.stream.Collectors;

public class Day07p2 extends Day07 {

    private static void createOrUpdateBeam(final Queue<QuanticBeam> queue,
                                           final Map<String, QuanticBeam> savedBeams,
                                           final int r,
                                           final int c,
                                           final double currentTimeline) {
        final var beam = savedBeams.get(r + "" + c);
        if (beam != null) {
            beam.addTimeline(currentTimeline);
        }
        final var quanticBeam = new QuanticBeam(r, c, currentTimeline);
        savedBeams.put(quanticBeam.position(), quanticBeam);
        queue.offer(quanticBeam);
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        linesGrid.clear();
        // First, read all lines
        while (sc.hasNextLine()) {
            this.getDigitFromLine(sc.nextLine());
        }

        // Convert to 2D array
        rows = linesGrid.size();
        cols = rows > 0 ? linesGrid.get(0).size() : 0;
        grid = buildGrid(linesGrid, rows, cols);

        // Find starting position 'S'
        int startRow = -1, startCol = -1;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (grid[i][j] == 'S') {
                    startRow = i;
                    startCol = j;
                    break;
                }
            }
            if (startRow != -1) break;
        }

        if (startRow == -1) {
            return 0; // No starting point found
        }


        // Queue for beam simulation: each element is [row, col, direction]
        // direction: 'D' = down, 'L' = left, 'R' = right
        final Queue<QuanticBeam> queue = new LinkedList<>();
        final Collection<QuanticBeam> lastBeans = new ArrayList<>();
        final Map<String, QuanticBeam> savedBeams = new HashMap<>();

        // Start beam going down from S
        queue.offer(new QuanticBeam(startRow + 1, startCol, 1));
        // grid[startRow][startCol] = '|'; // Mark starting position as beam

        while (!queue.isEmpty()) {
            final QuanticBeam beam = queue.poll();
            final int r = beam.row;
            final int c = beam.col;
            final double currentTimeline = beam.score;
            // Check bounds
            if (r < 0 || r >= rows || c < 0 || c >= cols) {
                continue;
            }
            if (r == rows - 1) {
                lastBeans.add(beam);
            }
            final char current = grid[r][c];
            if (current == '.') {
                // Empty space - continue beam - always first one
                grid[r][c] = '|';
                final var quanticBeam = new QuanticBeam(r + 1, c, currentTimeline);
                savedBeams.put(quanticBeam.position(), quanticBeam);
                queue.offer(quanticBeam);
            } else if (current == '^') {
                // Splitting, potentially bean exist already
                // Left beam
                if (c - 1 >= 0) {
                    createOrUpdateBeam(queue, savedBeams, r, c - 1, currentTimeline);
                }
                // Right beam
                if (c + 1 < cols) {
                    createOrUpdateBeam(queue, savedBeams, r, c + 1, currentTimeline);
                }
            } else if (current == '|') {
                // Already passed before but we still go down
                // Nothing because collision is handle in case ^
                // final var duplicatedBeam = createOrUpdateBeam(savedBeams, r + 1, c, currentTimeline);
                // queue.offer(duplicatedBeam);
            }
            // If we encounter other characters (like already placed '|'), just continue
        }

        // Count the sum of the score (of timeline) for the last row beans
        /*return lastBeans.stream()
                .collect(Collectors.toMap(
                        QuanticBeam::getCol,  // Use col as key for uniqueness
                        beam -> beam,          // Keep the beam as value
                        (existing, replacement) -> existing  // Keep first occurrence if duplicate col
                ))
                .values()                  // Get the distinct beams by col
                .stream()
                .mapToDouble(QuanticBeam::getScore)
                .sum();*/

        return lastBeans.stream()
                .mapToDouble(QuanticBeam::getScore)
                .sum();
        // TODO FIX ME Found 19 on test for now, need to replace the queue for a Row system (top to bottom)

    }

    // Helper class to represent a beam
    private static class QuanticBeam {
        int row;
        int col;

        double score;

        QuanticBeam(final int row, final int col, final double score) {
            this.row = row;
            this.col = col;
            this.score = score;
        }

        public String position() {
            return this.row + "" + this.col;
        }

        public void addTimeline(final double outsideTimeline) {
            this.score += outsideTimeline;
        }

        public double getScore() {
            return this.score;
        }

        public Object getCol() {
            return this.col;
        }
    }
}