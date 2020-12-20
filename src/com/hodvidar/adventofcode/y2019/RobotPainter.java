package com.hodvidar.adventofcode.y2019;

/**
 * To pain ShipPanels in Day11
 *
 * @author Hodvidar
 */
public final class RobotPainter {
    private static final boolean VERBOSE = false;
    private final ShipPanels ship;
    /**
     * Use this to
     */
    private final Amplifier brain;
    private final String UP = "N";
    private final String DOWN = "S";
    private final String RIGHT = "E";
    private final String LEFT = "W";
    private final int TURN_LEFT = 0;
    private final int TURN_RIGHT = 1;
    private final int colorOfFirstPanel;
    /**
     * If true robot will paint,
     * if false robot will move
     **/
    private boolean isPainting = true;
    /**
     * X coordinate to start
     **/
    private int x = 0;
    /**
     * Y coordinate to start
     **/
    private int y = 0;
    private String facing = UP;
    public RobotPainter(double[] memory) {
        this.colorOfFirstPanel = Panel.DARK;
        this.ship = new ShipPanels(this.colorOfFirstPanel);
        this.brain = new Amplifier(memory);
    }

    public RobotPainter(double[] memory, int colorOfFirstPanel) {
        this.colorOfFirstPanel = colorOfFirstPanel;
        this.ship = new ShipPanels(this.colorOfFirstPanel);
        this.brain = new Amplifier(memory);
    }

    private void printPanelsIfVerbose() {
        if (VERBOSE)
            this.ship.printInConsole();
    }

    private void printStateIfVerbose() {
        if (!VERBOSE)
            return;

        String action = (isPainting) ? "Painting" : "Moving";
        String position = " x=" + x + " y=" + y;
        String direction = " facing=" + facing;
        int out = (int) this.brain.getOutput();
        String action2 = "\n--> ";
        if (isPainting) {
            if (out == 1)
                action2 += "Painting in WHITE";
            else
                action2 += "Painting in DARK";
        } else {
            if (out == 1)
                action2 += "Turning RIGHT";
            else
                action2 += "Turning LEFT";
        }

        String s = action + position + direction + action2;
        System.out.println(s);
    }

    public void paint() {
        while (true) {
            // 1) Take current color
            int currentColor = this.ship.getPaintedPointValue(x, y);
            this.brain.setInput(currentColor);
            // 2) Run the program
            this.brain.runProgram();
            this.printStateIfVerbose();
            // 3) Check if end of program
            if (this.brain.isShutDown())
                return;
            // 4) Do action
            int output = (int) this.brain.getOutput();
            if (isPainting) {
                this.ship.paintPoint(x, y, output);
                isPainting = false;
                this.printPanelsIfVerbose();
            } else {
                move(output);
                isPainting = true;
            }
        }
    }

    /**
     * Modifies the 'facing' value and coordinate X and Y.
     *
     * @param order - 0 is turn left, 1 is turn right.
     */
    private void move(int order) {
        if (order != TURN_LEFT && order != TURN_RIGHT)
            throw new IllegalStateException("order not reconize:" + order);
        if (this.facing != this.UP && this.facing != this.DOWN
                && this.facing != this.LEFT && this.facing != this.RIGHT)
            throw new IllegalStateException(
                    "Illegal facing value:" + this.facing);

        if (this.facing == this.UP) {
            if (order == TURN_LEFT) {
                this.facing = LEFT;
                this.x--;
                return;
            }
            // RIGHT
            this.facing = RIGHT;
            this.x++;
            return;
        }

        if (this.facing == this.LEFT) {
            if (order == TURN_LEFT) {
                this.facing = DOWN;
                this.y--;
                return;
            }
            // RIGHT
            this.facing = UP;
            this.y++;
            return;
        }

        if (this.facing == this.DOWN) {
            if (order == TURN_LEFT) {
                this.facing = RIGHT;
                this.x++;
                return;
            }
            // RIGHT
            this.facing = LEFT;
            this.x--;
            return;
        }

        if (this.facing == this.RIGHT) {
            if (order == TURN_LEFT) {
                this.facing = UP;
                this.y++;
                return;
            }
            // RIGHT
            this.facing = DOWN;
            this.y--;
            return;
        }

    }

    /**
     * Number of panels painted at least once or more
     **/
    public int getNumberOfPanelPainted() {
        return this.ship.getNumberOfPaintedPanels();
    }

    public void printPanels() {
        this.ship.printInConsole();
    }
}
