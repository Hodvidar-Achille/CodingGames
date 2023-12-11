package com.hodvidar.adventofcode.y2023;

import com.hodvidar.utils.geometry.Point;
import com.hodvidar.utils.geometry.Polygon;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Day10p2 extends Day10 {
    @Override
    protected PipeNetwork getNetwork() {
        return new PipeNetworkV2();
    }

    @Override
    public double getResultDouble(final Scanner sc) {
        network = getNetwork();
        while (sc.hasNext()) {
            digestLine(sc.nextLine());
        }
        network.getMaxDistance(); // To build the map
        return ((PipeNetworkV2) network).getNumberOfEnclosedPoints();
    }

    private class PipeNetworkV2 extends PipeNetwork {

        public int getNumberOfEnclosedPoints() {
            List<Point> pathPoints = ((PipeNavigatorV2) navigator1).getPathPoints();
            pathPoints.addAll(((PipeNavigatorV2) navigator2).getPathPoints().reversed());
            pathPoints = pathPoints.stream().distinct().collect(java.util.stream.Collectors.toList());
            final char[][] map2 = this.map;
            pathPoints.forEach(p -> map2[(int) p.y][(int) p.x] = '#');
            final Polygon polygon = new Polygon(pathPoints.toArray(Point[]::new));
            int counterOfInsidePoints = 0;
            for (int y = 0; y < map.length; y++) {
                for (int x = 0; x < map[0].length; x++) {
                    if (map[y][x] != '#') {
                        final Point p = new Point(x, y);
                        if (polygon.isInside(p)) {
                            counterOfInsidePoints++;
                        }
                    }
                }
            }
            return counterOfInsidePoints;
        }

        @Override
        protected PipeNavigator getNavigator(final Point start, final Direction from) {
            return new PipeNavigatorV2(this, start, from);
        }
    }

    private class PipeNavigatorV2 extends PipeNavigator {

        final List<Point> pathPoints = new ArrayList<>();
        public PipeNavigatorV2(final PipeNetwork network, final Point start, final Direction from) {
            super(network, start, from);
            pathPoints.add(start);
            pathPoints.add(new Point(x, y));
        }

        @Override
        public void goToNext() {
            super.goToNext();
            pathPoints.add(new Point(x, y));
        }

        public List<Point> getPathPoints() {
            return pathPoints;
        }
    }
}
