package com.hodvidar.adventofcode.y2024;

import com.hodvidar.utils.geometry.Point;

import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class Day12p2 extends Day12 {

    /**
     * each point is a square, they form a single area
     **/
    private static double countCorners(final List<Point> squares) {
        double cornerCounter = 0;
        final Set<Point> setOfSquares = new HashSet<>(squares);
        for (final Point currentSquare : setOfSquares) {
            cornerCounter += countCorner(setOfSquares, currentSquare);
        }
        return cornerCounter;
    }

    private static int countCorner(final Set<Point> squares, final Point currentSquare) {
        final int directNeighbour = numberOfDirectNeighbour(squares, currentSquare);
        return switch (directNeighbour) {
            case 0 -> 4;
            case 1 -> 2;
            case 2 -> countCornerForASquareWith2Neighbours(squares, currentSquare);
            case 3 -> countCornersForSquareWith3Neighbours(squares, currentSquare);
            case 4 -> countCornersForSquareWith4Neighbours(squares, currentSquare);
            default -> throw new IllegalStateException("Canot have this number of direct neighbour");
        };
    }


    private static int numberOfDirectNeighbour(final Set<Point> squares, final Point currentSquare) {
        final double row = currentSquare.getX();
        final double col = currentSquare.getY();
        int neighbour = 0;
        if (squares.contains(new Point(row, col - 1))) neighbour++; // left
        if (squares.contains(new Point(row - 1, col))) neighbour++; // top
        if (squares.contains(new Point(row, col + 1))) neighbour++; // right
        if (squares.contains(new Point(row + 1, col))) neighbour++; // bottom
        return neighbour;
    }

    private static int countCornerForASquareWith2Neighbours(final Set<Point> squares, final Point currentSquare) {
        // T the current square, A squares of same region
        if (isInLine(squares, currentSquare)) {
            //        |  A
            // A T A  |  T
            //        |  A
            return 0; // no corner
        }
        if (IsCornerWith2DirectNeighbourEmpty(squares, currentSquare)) {
            // A T  | T A | A . | . A
            // . A  | A . | T A | A T
            return 2;
        }
        // A T  | T A | A A | A A
        // A A  | A A | T A | A T
        return 1;

    }

    private static boolean isInLine(final Set<Point> squares, final Point currentSquareWith2DirectNeighbours) {
        final double row = currentSquareWith2DirectNeighbours.getX();
        final double col = currentSquareWith2DirectNeighbours.getY();
        // Is in a North-South line
        if (squares.contains(new Point(row - 1, col)) && squares.contains(new Point(row + 1, col))) {
            return true;
        }
        // Is in a East-West line
        return squares.contains(new Point(row, col - 1)) && squares.contains(new Point(row, col + 1));
    }

    /**
     * Check for concave corner if corner 1 empty (can be NE, NW, SW, SE)
     * <p>
     * AAT
     * .1AA
     * ..AA
     * <p>
     * Check that for square T if square 1 is empty or:
     * <p>
     * AAA1
     * ..TAAA
     */
    private static boolean IsCornerWith2DirectNeighbourEmpty(final Set<Point> squares,
                                                             final Point currentSquareWith2DirectNeighbours) {
        final double row = currentSquareWith2DirectNeighbours.getX();
        final double col = currentSquareWith2DirectNeighbours.getY();
        final boolean left = squares.contains(new Point(row, col - 1));
        final boolean top = squares.contains(new Point(row - 1, col));
        final boolean right = squares.contains(new Point(row, col + 1));
        final boolean bottom = squares.contains(new Point(row + 1, col));

        if (left && top) {
            return !squares.contains(new Point(row - 1, col - 1));
        }
        if (top && right) {
            return !squares.contains(new Point(row - 1, col + 1));
        }
        if (right && bottom) {
            return !squares.contains(new Point(row + 1, col + 1));
        }
        if (bottom && left) {
            return !squares.contains(new Point(row + 1, col - 1));
        }
        throw new IllegalStateException("Error in IsCornerWith2DirectNeighbourIsEmpty");
    }

    /**
     * Counts one (concave) corner for 1, 2 if empty
     * .A1 | ... | 1T2 | 1A.
     * .TA | ATA | ATA | AT.
     * .A2 | 1T2 | ... | 2A.
     */
    private static int countCornersForSquareWith3Neighbours(final Set<Point> squares,
                                                            final Point currentSquareWith3DirectNeighbours) {
        final double row = currentSquareWith3DirectNeighbours.getX();
        final double col = currentSquareWith3DirectNeighbours.getY();
        // Looks for side with missing adjacent neighbour, we will check the two opposite corners
        final boolean left = !squares.contains(new Point(row, col - 1));
        final boolean top = !squares.contains(new Point(row - 1, col));
        final boolean right = !squares.contains(new Point(row, col + 1));
        final boolean bottom = !squares.contains(new Point(row + 1, col));
        int concaveCornerCounter = 0;
        // checking top-left
        if (bottom || right) {
            if (!squares.contains(new Point(row - 1, col - 1))) concaveCornerCounter++;
        }
        // checking top-right
        if (left || bottom) {
            if (!squares.contains(new Point(row - 1, col + 1))) concaveCornerCounter++;
        }
        // checking bottom-left
        if (top || right) {
            if (!squares.contains(new Point(row + 1, col - 1))) concaveCornerCounter++;
        }
        // checking bottom-right
        if (top || left) {
            if (!squares.contains(new Point(row + 1, col + 1))) concaveCornerCounter++;
        }
        return concaveCornerCounter;
    }

    /**
     * Counts one (concave) corner for T for each 1, 2, 3, 4 if empty
     * 1A2
     * ATA
     * 3A4
     */
    private static int countCornersForSquareWith4Neighbours(final Set<Point> squares,
                                                            final Point currentSquareWith4DirectNeighbours) {
        final double row = currentSquareWith4DirectNeighbours.getX();
        final double col = currentSquareWith4DirectNeighbours.getY();
        int concaveCornerCounter = 0;
        if (!squares.contains(new Point(row - 1, col - 1))) concaveCornerCounter++;
        if (!squares.contains(new Point(row - 1, col + 1))) concaveCornerCounter++;
        if (!squares.contains(new Point(row + 1, col - 1))) concaveCornerCounter++;
        if (!squares.contains(new Point(row + 1, col + 1))) concaveCornerCounter++;
        return concaveCornerCounter;
    }


    @Override
    public double getResultDouble(final Scanner sc) {
        final char[][] grid = parseInput(sc);
        final List<Region> regions = buildRegions(grid);
        return calculateTotalPrice(regions);
    }

    @Override
    protected double calculateTotalPrice(final List<Region> regions) {
        return regions.stream()
                .mapToDouble(region ->
                        countCorners(region.getCoordinateOfPlants())
                                *
                                region.getArea()).sum();
    }

}
