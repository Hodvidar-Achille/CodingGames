package com.hodvidar.adventofcode.y2019;

/**
 * For Day17
 *
 * @author a.genet
 */
@SuppressWarnings("unused")
public final class RobotCamera {
    private final ScaffoldSurface ship;
    private final Amplifier brain;
    private final String UP = "N";
    private final String DOWN = "S";
    private final String RIGHT = "E";
    private final String LEFT = "W";
    private final String facing = UP;
    private final int COMMA = 44;
    private final int A = 65;
    private final int B = 66;
    private final int C = 66;
    private final int NEW_LINE = 10;
    private final int L = 76;
    private final int R = 82;
    private boolean VERBOSE = false;
    /**
     * X coordinate to start
     **/
    private int x = 0;
    /**
     * Y coordinate to start
     **/
    private int y = 0;

    public RobotCamera(final double[] memory, final boolean verbose) {
        this.ship = new ScaffoldSurface();
        this.brain = new Amplifier(memory);
        this.VERBOSE = verbose;
    }

    private void printPanelsIfVerbose() {
        if (VERBOSE)
            this.ship.printInConsole();
    }

    private int getASCIInumber(final int i) {
        if (i < 0 || i > 9)
            throw new IllegalArgumentException("Should be a digit");
        return i + 48;
    }

    private void paint() {
        while (true) {
            // 1) Run the program
            this.brain.runProgram();
            // 2) Check if end of program
            if (this.brain.isShutDown())
                return;
            // 4) Do action
            final int output = (int) this.brain.getOutput();
            if (output == ScaffoldPanel.NEW_LINE) {
                y--;
                x = 0;
                continue;
            }
            this.ship.paintPoint(x, y, output);
            printPanelsIfVerbose();
            x++;
        }
    }

    /**
     * The first step is to calibrate the cameras by
     * getting the alignment parameters of some well-defined
     * points. Locate all scaffold intersections; for each,
     * its alignment parameter is the distance between its left
     * edge and the left edge of the view multiplied by the distance
     * between its top edge and the top edge of the view.
     *
     * @return
     */
    public int calculateCameraCalibration() {
        this.paint();
        int total = 0;
        for (final PaintedPoint p : this.ship.paintedPoints) {
            // Count each point that is a Wall (or the robot on it)
            // and that have its 4 neighbors that are not empty
            // and for each calculate its 'score'.
            if (p.value == ScaffoldPanel.EMPTY)
                continue;
            if (!isIntersection(p))
                continue;

            total += calculateScore(p);
        }
        return total;
    }

    private boolean isIntersection(final PaintedPoint p) {
        if (this.ship.getPaintedPointValue(p.x + 1, p.y) == ScaffoldPanel.EMPTY)
            return false;
        if (this.ship.getPaintedPointValue(p.x - 1, p.y) == ScaffoldPanel.EMPTY)
            return false;
        if (this.ship.getPaintedPointValue(p.x, p.y + 1) == ScaffoldPanel.EMPTY)
            return false;
        return this.ship.getPaintedPointValue(p.x, p.y - 1) != ScaffoldPanel.EMPTY;
    }

    private int calculateScore(final PaintedPoint p) {
        return (int) Math.abs(p.x * p.y);
    }

    public int getNumberOfNonEmpty() {
        int total = 0;
        for (final PaintedPoint p : this.ship.paintedPoints) {
            // Count each point that is a Wall (or the robot on it)
            // and that have its 4 neighbors that are not empty
            // and for each calculate its 'score'.
            if (p.value == ScaffoldPanel.EMPTY)
                continue;
            total++;
        }
        return total;
    }

    public void printPanels() {
        this.ship.printInConsole();
    }
}
