package com.hodvidar.adventofcode.y2019;

public final class MazeSurface extends PaintedSurface {

    public MazeSurface() {
        paintedPoints.add(new MazePoint(0, 0, MazePoint.START, 0));
    }

    @Override
    public PaintedPoint getPaintedPointImpl(double x, double y) {
        return new MazePoint(x, y, 3);
    }

    public int getHigherPathValue() {
        int max = 0;
        for (PaintedPoint p : this.paintedPoints) {
            MazePoint m = (MazePoint) p;
            int v = m.getCountFromStart();
            if (v > max)
                max = v;
        }
        return max;
    }

    public void paintPointWithScore(double x, double y, int value,
                                    int countFromStart) {
        PaintedPoint p = this.getPaintedPointImpl(x, y);
        if (paintedPoints.contains(p)) {
            // retrieve the Panel that already exist
            for (PaintedPoint p2 : this.paintedPoints) {
                if (p2.equals(p)) {
                    p = p2;
                    break;
                }
            }
        } else {
            // use the newly created one
            paintedPoints.add(p);
        }
        p.paint(value);
        MazePoint m = (MazePoint) p;
        m.setCountFromStart(countFromStart);
    }

    public MazePoint getMazePoint(double x, double y) {
        return (MazePoint) this.getPaintedPoint(x, y);
    }

}
