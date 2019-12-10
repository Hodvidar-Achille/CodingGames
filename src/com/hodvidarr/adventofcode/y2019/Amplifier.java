package com.hodvidarr.adventofcode.y2019;

public final class Amplifier
{
	private final OpCodeReader codeReader;
	private int positionInProgram;
	private final Double phase;
	private boolean shutDown;

	public Amplifier(double[] opCode, Double phase)
	{
		this.codeReader = new OpCodeReader(opCode);
		this.phase = phase;
		this.codeReader.setPhaseInput(this.phase);
		this.positionInProgram = 0;
		this.shutDown = false;
	}

	public void setInput(double i)
	{
		this.codeReader.setInput(i);
	}

	public void runProgram()
	{
		double[] results = codeReader.run(positionInProgram);
		int endCode = (int) results[0];
		int endPosition = (int) results[1];
		this.positionInProgram = endPosition;
		if(endCode == this.codeReader.HALT_CODE)
			shutDown = true;
	}

	public boolean isShutDown()
	{
		return this.shutDown;
	}

	public double getOutput()
	{
		return this.codeReader.getOutput();
	}

	/**
	 * Does not check for the ShutDown
	 * @param input
	 * @return
	 */
	@Deprecated
	public double runAndGetOutput(double input)
	{
		this.setInput(input);
		this.runProgram();
		return this.getOutput();
	}
}
