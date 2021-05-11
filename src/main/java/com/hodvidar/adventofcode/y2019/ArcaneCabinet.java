package com.hodvidar.adventofcode.y2019;

// TODO : make the test pass with correct input.
public final class ArcaneCabinet implements OpCodeReaderInputCallBack {
    private static final boolean VERBOSE = true;
    private final GameSurface screen;
    private final Amplifier brain;
    private double currentScore = 0;
    private double ballX = 0;
    private double previousBallX = 0;
    private double paddleX = -1000;
    private double paddleY = 1000;
    private double givenInput = 0;

    public ArcaneCabinet(final double[] memory) {
        this.screen = new GameSurface();
        this.brain = new Amplifier(memory, null, this);
    }

    public static void printIfVerbose(final String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public void changeMemoryAdressValue(final int addr, final double value) {
        this.brain.changeMemoryAdressValue(addr, value);
    }

    /**
     * To call between paint() and game()
     **/
    public void initGame() {
        this.brain.resetPositionInProgram();
    }

    public void paint() {
        while (true) {
            // 1) Run the program x3 for x, y, value
            this.brain.runProgram();
            final int x = (int) this.brain.getOutput();

            this.brain.runProgram();
            int y = (int) this.brain.getOutput();
            // For y we need to reverse them in this case
            y = y * (-1);

            this.brain.runProgram();
            final int value = (int) this.brain.getOutput();

            final boolean isPaddle = (value == GamePixel.PADDLE);
            final boolean isBall = (value == GamePixel.BALL);
            if (isPaddle) {
                paddleX = x;
                paddleY = y;
            }
            if (isBall)
                ballX = x;

            // 2) Check if end of program
            if (this.brain.isShutDown())
                return;

            // 3) Do action
            this.screen.paintPoint(x, y, value);
        }
    }

    /**
     * Play the game alone
     **/
    public void launchGameAuto(final boolean verbose) {
        while (true) {
            // 1) Run the program x3 for x, y, value
            this.brain.runProgram();
            final int x = (int) this.brain.getOutput();

            // reset input ?
            //			this.brain.setInput(0);
            this.brain.runProgram();
            int y = (int) this.brain.getOutput();
            // For y we need to reverse them in this case
            y = y * (-1);

            this.brain.runProgram();
            final int value = (int) this.brain.getOutput();

            //printIfVerbose("x=" + x + " y=" + y + " value=" + value);

            // 2) Check if end of program
            if (this.brain.isShutDown())
                return;

            final boolean isBall = (value == GamePixel.BALL);
            final boolean isPaddle = (value == GamePixel.PADDLE);

            // 3) Do action
            if (x == -1) {
                // Only update score
                this.currentScore = value;
                printIfVerbose("currentScore=" + currentScore);
                continue;
            }

            // modify input to move paddle
            if (isPaddle) {
                //printIfVerbose("new Paddle position: x=" + x + " y=" + y);
                paddleX = x;
                paddleY = y;
            }
            if (isBall) {
                //printIfVerbose("new Ball position x=" + x + " (y=" + y + ")");
                previousBallX = ballX;
                ballX = x;
            }

            //			if(paddleY != 1000)
            //				printIfVerbose("paddleX=" + paddleX + " ballX=" + ballX
            //						+ " previousBallX=" + previousBallX);

            // Case A (input not only set when 'isBall')
            if (paddleY != 1000 /*&& isBall*/) {
                if (paddleX == ballX) {
                    if (previousBallX == ballX) {
                        //printIfVerbose("input = 0");
                        givenInput = 0;
                        this.brain.setInput(givenInput);
                    } else if (previousBallX < ballX) // ball goes -->
                    {
                        //printIfVerbose("input = 1");
                        givenInput = 1;
                        this.brain.setInput(givenInput);
                    } else// ball goes <--
                    {
                        //printIfVerbose("input = -1");

                        givenInput = -1;
                        this.brain.setInput(givenInput);
                    }
                } else if (paddleX > ballX) {
                    //printIfVerbose("input = -1");
                    givenInput = -1;
                    this.brain.setInput(givenInput);
                } else {
                    //printIfVerbose("input = 1");
                    givenInput = 1;
                    this.brain.setInput(givenInput);
                }
            }

            // update screen
            this.screen.paintPoint(x, y, value);

            //			if(verbose && (isBall || isPaddle))
            //				this.printScreen();
        }
    }

    public int countBlock() {
        return this.screen.countPointWithValue(GamePixel.BLOCK);
    }

    public void printScreen() {
        this.screen.printInConsole();
    }

    public double getScore() {
        return this.currentScore;
    }

    @Override
    public void informValueUsed(final double value) {
        if (value == 1) {
            this.screen.paintPoint(paddleX, paddleY, GamePixel.EMPTY);
            paddleX++;
            this.screen.paintPoint(paddleX, paddleY, GamePixel.PADDLE);
        } else if (value == -1) {
            this.screen.paintPoint(paddleX, paddleY, GamePixel.EMPTY);
            paddleX--;
            this.screen.paintPoint(paddleX, paddleY, GamePixel.PADDLE);
        }
    }
}
