package com.hodvidar.adventofcode.y2019;

public final class Amplifier {
    private final OpCodeReader codeReader;
    private final Double phase;
    private int positionInProgram;
    private boolean shutDown;

    public Amplifier(final double[] memory) {
        this(memory, null, null);
    }

    public Amplifier(final double[] memory, final Double phase,
                     final OpCodeReaderInputCallBack aCaller) {
        this.codeReader = new OpCodeReader(memory, aCaller);
        this.phase = phase;
        this.codeReader.setPhaseInput(this.phase);
        this.positionInProgram = 0;
        this.shutDown = false;
    }

    public void changeMemoryAdressValue(final int addr, final double value) {
        this.codeReader.changeMemoryAdressValue(addr, value);
    }

    public void resetPositionInProgram() {
        this.positionInProgram = 0;
        this.shutDown = false;
    }

    public void setInput(final double i) {
        this.codeReader.setInput(i);
    }

    public void runProgram() {
        final double[] results = codeReader.run(positionInProgram);
        final int endCode = (int) results[0];
        final int endPosition = (int) results[1];
        this.positionInProgram = endPosition;
        if (endCode == this.codeReader.HALT_CODE)
            shutDown = true;
    }

    public boolean isShutDown() {
        return this.shutDown;
    }

    public double getOutput() {
        return this.codeReader.getOutput();
    }

    /**
     * Does not check for the ShutDown
     *
     * @param input
     * @return
     */
    @Deprecated
    public double runAndGetOutput(final double input) {
        this.setInput(input);
        this.runProgram();
        return this.getOutput();
    }
}
