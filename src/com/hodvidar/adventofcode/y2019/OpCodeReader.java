package com.hodvidar.adventofcode.y2019;

public final class OpCodeReader {
    public final int HALT_CODE = 99;
    private final boolean VERBOSE = false;
    /**
     * Maybe I'm not understanding you clearly, but there's no special cases as
     * far as I can see. The parameter mode indicates what the parameter means:
     * <p>
     * Mode 0 means "use the value at/write a value to this (absolute) address".
     * <p>
     * Mode 1 means "use this exact value" ("writing to an exact value" doesn't make
     * sense--in the same way a line of code like 44=200; doesn't make sense--hence the
     * prohibition of parameter mode 1 for write parameters).
     * <p>
     * Mode 2 means "use the value at/write a value to the address given by this offset
     * relative to the relative base".
     * <p>
     * These definitions hold regardless of the instruction.
     */
    private final int POSITION_MODE = 0;
    private final int IMMEDIATE_MODE = 1;
    private final int RELATIVE_MODE = 2;
    private final int MAX_CODE = 22210;
    private final int[] SUPPORTED_OPCODES = new int[]{1, 2, 3, 4, 5, 6, 7, 8,
            9};
    private final int[] SUPPORTED_MODES = new int[]{POSITION_MODE,
            IMMEDIATE_MODE, RELATIVE_MODE};
    /**
     * The computer's available memory should be much larger than the initial program.
     * Memory beyond the initial program starts with the value 0 and
     * can be read or written like any other memory.
     * (It is invalid to try to access memory at a negative address, though.)
     */
    private double[] memory;
    private double output;
    /**
     * Only to use once
     **/
    private Double phaseInput;
    private boolean phaseInputUsed = false;
    private double input;
    /**
     * Inform the owner of the caller
     **/
    private OpCodeReaderInputCallBack caller;
    private double relative_base = 0;
    public OpCodeReader(double[] memory) {
        this.memory = memory;
    }

    public OpCodeReader(double[] memory, OpCodeReaderInputCallBack aCaller) {
        this.memory = memory;
        this.caller = aCaller;
    }

    private void printIfVerbose(String s) {
        if (VERBOSE)
            System.err.println(s);
    }

    public void changeMemoryAdressValue(int addr, double value) {
        this.memory[addr] = value;
    }

    /**
     * Get the current input, except if there is a phase value that was not yet used.
     *
     * @return
     */
    private double getInput() {
        if (phaseInput != null && !phaseInputUsed) {
            phaseInputUsed = true;
            return this.phaseInput.doubleValue();
        }
        printIfVerbose(
                "OpCde3 call input, returning input value=" + this.input);

        if (this.caller != null)
            caller.informValueUsed(input);
        return input;
    }

    /**
     * Set the input that will be used each time an input is asked.
     *
     * @param i - an input, cannot be <code>null</code>.
     */
    public void setInput(double i) {
        this.input = i;
    }

    /**
     * Set the phase (will be used as input only once).
     *
     * @param p - the Phase value, can be <code>null</code>.
     */
    public void setPhaseInput(Double p) {
        this.phaseInput = p;
    }

    /**
     * Start the program at the position 0;
     *
     * @param opCode - the program.
     * @return an array of length 2 with array[0] the endCode, array[1] the current position;
     */
    public double[] run() {
        return run(0);
    }

    /**
     * Start the program at the given position.
     *
     * @param opCode - the program.
     * @param start  - the given position.
     * @return an array of length 2 with array[0] the endCode, array[1] the current position;
     */
    public double[] run(int start) {
        for (int i = start; i < this.memory.length; /* empty increment, do it yourself */) {
            int code = (int) this.memory[i];
            if (code == 99) {
                return new double[]{code, i};
            }
            if ((code % 100) == 4) {
                i = readCode(code, i);
                return new double[]{code, i};
            }
            i = readCode(code, i);
        }

        throw new IllegalStateException(
                "Program should not be outside of loop.");
    }

    /**
     * Perform the action defined by the current opCode and return the next position.
     *
     * @param code - current opCode
     * @param i    - the position in the array
     * @return i modified
     */
    private int readCode(int code, int i) {
        return readCode(code, i, POSITION_MODE, POSITION_MODE, POSITION_MODE);
    }

    private int readCode(int code, int i, int param1, int param2, int param3) {
        //		printIfVerbose("code: '" + code + "'  position: '" + i
        //				+ "' relative_base=" + this.relative_base + " p1:" + param1
        //				+ " p2:" + param2 + " p3:" + param3);
        // printIfVerbose("" + arrayToString(this.memory));
        switch (code) {
            case 1:
                return opCode1(i, param1, param2, param3);
            case 2:
                return opCode2(i, param1, param2, param3);
            case 3:
                return opCode3(i, param1);
            case 4:
                return opCode4(i, param1);
            case 5:
                return opCode5(i, param1, param2);
            case 6:
                return opCode6(i, param1, param2);
            case 7:
                return opCode7(i, param1, param2, param3);
            case 8:
                return opCode8(i, param1, param2, param3);
            case 9:
                return opCode9(i, param1);
            default:
                if (code <= HALT_CODE || code > MAX_CODE) {
                    throw new IllegalStateException(
                            "Should not be here (1) encounter opCode:'" + code
                                    + "'");
                }
                return opCodeWithParam(code, i);
        }
    }

    /**
     * Opcode 1 adds together numbers read from two positions
     * and stores the result in a third position. The three integers
     * immediately after the opcode tell you these three positions -
     * the first two indicate the positions from which you should read
     * the input values, and the third indicates the position at which
     * the output should be stored.
     *
     * @param i      - the position in the array.
     * @param param1 - the mode for parameter 1.
     * @param param2 - the mode for parameter 2.
     * @param param3 - the mode for parameter 3, cannot be POSITION_MODE.
     * @return i modified (+4).
     * @since Day2
     */
    private int opCode1(int i, int param1, int param2, int param3) {
        double v1 = getValue(i + 1, param1);
        double v2 = getValue(i + 2, param2);
        int p3 = getPosition(i + 3, param3);
        this.setMemoryValue(v1 + v2, p3);
        i = i + 4;
        return i;
    }

    /**
     * Opcode 2 works exactly like opcode 1, except it multiplies
     * the two inputs instead of adding them. Again, the three integers
     * after the opcode indicate where the inputs and outputs are, not
     * their values.
     *
     * @param i      - the position in the array.
     * @param param1 - the mode for parameter 1.
     * @param param2 - the mode for parameter 2.
     * @param param3 - the mode for parameter 3, cannot be POSITION_MODE.
     * @return i modified (+4).
     * @since Day2
     */
    private int opCode2(int i, int param1, int param2, int param3) {
        double v1 = getValue(i + 1, param1);
        double v2 = getValue(i + 2, param2);
        int p3 = getPosition(i + 3, param3);
        this.setMemoryValue(v1 * v2, p3);
        i = i + 4;
        return i;
    }

    /**
     * Opcode 3 takes a single integer as input and saves it to the position
     * given by its only parameter. For example, the instruction 3,50 would
     * take an input value and store it at address 50.
     *
     * @param i      - the position in the array
     * @param param1
     * @return i modified (+2)
     * @since Day5
     */
    private int opCode3(int i, int param1) {
        // int p1 = getPosition(i + 1, param1);
        int p1 = (int) (getMemoryValue(i + 1) + this.relative_base);
        this.setMemoryValue(getInput(), p1);
        i = i + 2;
        return i;
    }

    /**
     * Opcode 4 outputs the value of its only parameter.
     * For example, the instruction 4,50 would output the value at address 50.
     *
     * @param opCode
     * @param i
     * @param param1
     * @return i modified (+2)
     * @since Day5
     */
    private int opCode4(int i, int param1) {
        double v1 = getValue(i + 1, param1);
        output = v1;
        i = i + 2;
        return i;
    }

    /**
     * Opcode 5 is jump-if-true: if the first parameter is non-zero,
     * it sets the instruction pointer to the value from the second
     * parameter. Otherwise, it does nothing.
     *
     * @param i      - the position in the array
     * @param param1
     * @param param2
     * @return i modified (? or +3)
     * @since Day5
     */
    private int opCode5(int i, int param1, int param2) {
        double v1 = getValue(i + 1, param1);
        if (v1 != 0) {
            double v2 = getValue(i + 2, param2);
            i = (int) v2;
            return i;
        }
        i = i + 3;
        return i;
    }

    /**
     * Opcode 6 is jump-if-false: if the first parameter is zero, it sets
     * the instruction pointer to the value from the second parameter.
     * Otherwise, it does nothing.
     *
     * @param i      - the position in the array
     * @param param1
     * @param param2
     * @return i modified (? or +3)
     * @since Day5
     */
    private int opCode6(int i, int param1, int param2) {
        double v1 = getValue(i + 1, param1);
        if (v1 == 0) {
            double v2 = getValue(i + 2, param2);
            i = (int) v2;
            return i;
        }
        i = i + 3;
        return i;
    }

    /**
     * Opcode 7 is less than: if the first parameter is less than the second parameter,
     * it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
     *
     * @param i      - the position in the array
     * @param param1
     * @param param2
     * @param param3
     * @return i modified (+4)
     * @since Day5
     */
    private int opCode7(int i, int param1, int param2, int param3) {
        double v1 = getValue(i + 1, param1);
        double v2 = getValue(i + 2, param2);
        int p3 = getPosition(i + 3, param3);
        if (v1 < v2) {
            this.setMemoryValue(1, p3);
        } else {
            this.setMemoryValue(0, p3);
        }
        i = i + 4;
        return i;
    }

    /**
     * Opcode 8 is equals: if the first parameter is equal to the second parameter,
     * it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
     *
     * @param i      - the position in the array
     * @param param1
     * @param param2
     * @param param3
     * @return i modified (+4)
     * @since Day5
     */
    private int opCode8(int i, int param1, int param2, int param3) {
        double v1 = getValue(i + 1, param1);
        double v2 = getValue(i + 2, param2);
        int p3 = getPosition(i + 3, param3);
        if (v1 == v2) {
            this.setMemoryValue(1, p3);
        } else {
            this.setMemoryValue(0, p3);
        }
        i = i + 4;
        return i;
    }

    /**
     * Opcode 9 adjusts the relative base by the value of its only parameter.
     * The relative base increases (or decreases, if the value is negative)
     * by the value of the parameter.
     *
     * @param i      - the position in the array
     * @param param1
     * @return i modified (+2)
     * @since Day9
     */
    private int opCode9(int i, int param1) {
        double v1 = getValue(i + 1, param1);
        this.relative_base += v1;
        i = i + 2;
        return i;
    }

    /**
     * Handles codes 'aaa0X' with 'a' parameter modes and X an opCode. <br/>
     * Example : <br/>
     * 1101 --> code 1 with param1 = 1, param2 = 1, param3 = 0 <br/>
     * 1008 --> code 8 with param1 = 0, param2 = 1, param3 = 0 <br/>
     *
     * @param code
     * @param opCode
     * @param i
     * @return i modified (?)
     */
    private int opCodeWithParam(int code, int i) {
        int subCode = code % 100;
        if (!isSupporterOpCode(subCode))
            throw new IllegalStateException(
                    "This case should happen ? code=" + code);

        int modeFor3 = code / 10000;
        int modeFor2 = (code / 1000) % 10;
        int modeFor1 = (code / 100) % 10;

        return readCode(subCode, i, modeFor1, modeFor2, modeFor3);
    }

    /**
     * Like 'getValue' but cannot be with POSITION_MODE.
     *
     * @param opCode
     * @param pos
     * @param paramMode
     * @return
     */
    private int getPosition(int pos, int paramMode) {
        if (paramMode == RELATIVE_MODE) {
            // 1)
            // --
            //pos = (int) (pos + this.relative_base);
            // or 2)
            //			pos = (int) getMemoryValue(pos);
            //			pos = (int) (pos + this.relative_base);
            // --
            // or 3)
            pos = (int) getMemoryValue(pos);
            return (int) (pos + this.relative_base);
        }

        return (int) getMemoryValue(pos);
    }

    /**
     * Either get directly the value from opCode[pos] (IMMEDIATE_MODE) <br/>
     * or the value from opCode[pos+relative_base] (RELATIVE_MODE) <br/>
     * or the value from from opCode[opCode[pos]] (POSITION_MODE). <br/>
     * <p>
     * Example:<br/>
     * IMMEDIATE_MODE and pos=5 return memory[5] <br/>
     * POSITION_MODE and pos=5 return memory[memory[5]] <br/>
     * RELATIVE_MODE and pos=5 return memory[memory[5] + relative]
     *
     * @param pos       - the initial position to look in the array
     * @param paramMode - the mode see {@link #SUPPORTED_MODES}
     * @return
     */
    private double getValue(int pos, int paramMode) {
        if (!isSupporterMode(paramMode))
            throw new IllegalStateException(
                    "This case should not happen paramMode=" + paramMode);

        if (paramMode == IMMEDIATE_MODE) // '1'
        {
            double param = getMemoryValue(pos);
            return param;
        }
        if (paramMode == POSITION_MODE) // '0'
        {
            double param = getMemoryValue(pos);
            return getMemoryValue((int) param);
        }
        if (paramMode == RELATIVE_MODE) // '2'
        {
            double param = getMemoryValue(pos);
            return getMemoryValue((int) (param + this.relative_base));
        }

        throw new IllegalStateException(
                "Method 'isSupporterMode' Failed to exclude this paramMode="
                        + paramMode);
    }

    /**
     * Does 'this.memory[position] = value;' with a security.
     * see {@link #checkPositionInMemory}.
     *
     * @param value
     * @param position
     */
    private void setMemoryValue(double value, int position) {
        checkPositionInMemory(position);
        this.memory[position] = value;
    }

    /**
     * Returns 'this.memory[position];' with a security.
     * see {@link #checkPositionInMemory}.
     *
     * @param value
     * @param position
     */
    private double getMemoryValue(int position) {
        checkPositionInMemory(position);
        return this.memory[position];
    }

    /**
     * If position >= this.memory.length, increase the memory length.
     *
     * @param position
     */
    private void checkPositionInMemory(int position) {
        if (position < this.memory.length)
            return;

        double[] longerMemory = new double[position + 100];
        for (int i = 0; i < this.memory.length; i++) {
            longerMemory[i] = this.memory[i];
        }
        this.memory = longerMemory;

    }

    private boolean isSupporterMode(int mode) {
        for (int c : SUPPORTED_MODES) {
            if (mode == c)
                return true;
        }
        return false;
    }

    private boolean isSupporterOpCode(int subCode) {
        for (int c : SUPPORTED_OPCODES) {
            if (subCode == c)
                return true;
        }
        return false;
    }

    @SuppressWarnings("unused")
    private String arrayToString(double[] opCode) {
        StringBuilder sb = new StringBuilder();
        int f = opCode.length - 1;
        for (int i = 0; i <= f; i++) {
            if (i != f)
                sb.append(opCode[i]).append(",");
            else
                sb.append(opCode[i]);
        }
        return sb.toString();
    }

    public double getOutput() {
        return this.output;
    }

}
