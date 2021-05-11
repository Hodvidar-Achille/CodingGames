package com.hodvidar.adventofcode.y2019;

public final class MazeSurface extends PaintedSurface {

    public MazeSurface() {
        paintedPoints.add(new MazePoint(0, 0, MazePoint.START, 0));
    }

    @Override
    public PaintedPoint getPaintedPointImpl(final double x, final double y) {
        return new MazePoint(x, y, 3);
    }

    public int getHigherPathValue() {
        int max = 0;
        for (final PaintedPoint p : this.paintedPoints) {
            final MazePoint m = (MazePoint) p;
            final int v = m.getCountFromStart();
            if (v > max)
                max = v;
        }
        return max;
    }

    public void paintPointWithScore(final double x, final double y, final int value,
                                    final int countFromStart) {
        PaintedPoint p = this.getPaintedPointImpl(x, y);
        if (paintedPoints.contains(p)) {
            // retrieve the Panel that already exist
            for (final PaintedPoint p2 : this.paintedPoints) {
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
        final MazePoint m = (MazePoint) p;
        m.setCountFromStart(countFromStart);
    }

    public MazePoint getMazePoint(final double x, final double y) {
        return (MazePoint) this.getPaintedPoint(x, y);
    }

}
