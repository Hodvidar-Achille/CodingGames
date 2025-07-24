package com.hodvidar.adventofcode.y2024;

import com.hodvidar.utils.geometry.GeometryServices;
import com.hodvidar.utils.geometry.Point;
import com.hodvidar.utils.geometry.Segment;

public class Day08p2 extends Day08 {

    protected Point[][] gridOfPoints;

    @Override
    protected void clearLocalVariables() {
        gridOfPoints = null;
    }

    @Override
    protected void addAntinodes(final int i, final int j, final char[][] gridCopy, final int k, final int l) {
        if (gridOfPoints == null) {
            createGridOfPoints(i, j, gridCopy);
        }
        final Point antenna1 = new Point(i, j);
        final Point antenna2 = new Point(k, l);
        final Segment lineBetweenAntenna = new Segment(antenna1, antenna2);

        final int rows = gridCopy.length;
        final int cols = gridCopy[0].length;
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                final Point currentPoint = gridOfPoints[x][y];
                // We ignore point between the two antennas but need to include the antenna exact point
                if (currentPoint.equals(antenna1)) {
                    gridCopy[(int) currentPoint.getX()][(int) currentPoint.getY()] = ANTINODE;
                    continue;
                }
                if (currentPoint.equals(antenna2)) {
                    gridCopy[(int) currentPoint.getX()][(int) currentPoint.getY()] = ANTINODE;
                    continue;
                }
                if (GeometryServices.isOnSegment(currentPoint, lineBetweenAntenna)) {
                    continue;
                }
                if (GeometryServices.areColinear(antenna1, antenna2, currentPoint)) {
                    // on same line
                    gridCopy[(int) currentPoint.getX()][(int) currentPoint.getY()] = ANTINODE;
                }
            }
        }
    }

    private void createGridOfPoints(final int i, final int j, final char[][] gridCopy) {
        final int rows = gridCopy.length;
        final int cols = gridCopy[0].length;
        gridOfPoints = new Point[rows][cols];

        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                gridOfPoints[x][y] = new Point(x, y);
            }
        }
    }
}
