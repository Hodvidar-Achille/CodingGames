package com.hodvidarr.adventofcode.y2019;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.sun.xml.internal.txw2.IllegalSignatureException;

public final class DayX
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;

	private static final int NUMBER_OF_TEST = 5;
	private static final String INPUT_DIRECTORY = "aventofcode_2019"; // input1

	public static void printIfVerbose(String s)
	{
		if(VERBOSE)
			System.err.println(s);
	}

	public static void main(String[] args) throws Exception
	{
		String result = test("resources\\" + INPUT_DIRECTORY + "\\input" + NUMBER_OF_TEST + ".txt");
		System.err.println("result='" + result + "'");
		Integer[] arr = new Integer[outputs.size()];
		arr = outputs.toArray(arr);
		result = arrayToString(arr);
		System.err.println("result='" + result + "'");
	}

	private static final int POSITION_MODE = 0;
	private static final int IMMEDIATE_MODE = 1;

	private static final List<Integer> outputs = new ArrayList<>();
	private static final int input = 5;
	private static final int[] SUPPORTED_OPCODES = new int[] { 1, 2, 3, 4, 5, 6, 7, 8 };
	private static final int[] SUPPORTED_MODES = new int[] { POSITION_MODE, IMMEDIATE_MODE };

	private static String test(String inputFile) throws Exception
	{
		String line;
		File file = new File(inputFile);
		// Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");

		line = sc.nextLine();
		String[] opCodeStr = line.split(",");
		sc.close();
		int[] opCode = new int[opCodeStr.length];
		for (int i = 0; i < opCodeStr.length; i++)
		{
			String s = opCodeStr[i];
			int j = Integer.parseInt(s);
			opCode[i] = j;
		}

		for (int i = 0; i < opCode.length; /* empty increment, do it yourself */)
		{
			int code = opCode[i];
			if(code == 99)
			{
				return arrayToString(opCode);
			}
			i = readCode(code, opCode, i, POSITION_MODE, POSITION_MODE, POSITION_MODE);
		}

		return arrayToString(opCode);
	}

	private static int readCode(int code, int[] opCode, int i, int param1, int param2, int param3)
	{
		printIfVerbose("code: " + code);
		printIfVerbose("" + arrayToString(opCode));
		switch (code)
		{
		case 1:
			return opCode1(opCode, i, param1, param2);
		case 2:
			return opCode2(opCode, i, param1, param2);
		case 3:
			return opCode3(opCode, i, param1);
		case 4:
			return opCode4(opCode, i, param1);
		case 5:
			return opCode5(opCode, i, param1, param2);
		case 6:
			return opCode6(opCode, i, param1, param2);
		case 7:
			return opCode7(opCode, i, param1, param2);
		case 8:
			return opCode8(opCode, i, param1, param2);
		default:
			if(code <= 99 || code > 11109)
			{
				throw new IllegalSignatureException("Should not be here (1) encounter opCode:'" + code + "'");
			}
			return opCodeWithParam(code, opCode, i);
		}
	}

	/**
	 *  Opcode 1 adds together numbers read from two positions 
	 *  and stores the result in a third position. The three integers 
	 *  immediately after the opcode tell you these three positions - 
	 *  the first two indicate the positions from which you should read 
	 *  the input values, and the third indicates the position at which 
	 *  the output should be stored.
	 * @param opCode - the array
	 * @param i - the position in the array
	 * @param param1
	 * @param param2
	 * @param param3
	 * @return i modified (+4)
	 */
	private static int opCode1(int[] opCode, int i, int param1, int param2)
	{
		int v1 = getValue(opCode, i + 1, param1);
		int v2 = getValue(opCode, i + 2, param2);
		int p3 = opCode[i + 3];
		opCode[p3] = v1 + v2;
		i = i + 4;
		return i;
	}

	/**
	 * Opcode 2 works exactly like opcode 1, except it multiplies 
	 * the two inputs instead of adding them. Again, the three integers 
	 * after the opcode indicate where the inputs and outputs are, not 
	 * their values.
	 * @param opCode - the array
	 * @param i - the position in the array
	 * @param param1
	 * @param param2
	 * @param param3
	 * @return i modified (+4)
	 */
	private static int opCode2(int[] opCode, int i, int param1, int param2)
	{
		int v1 = getValue(opCode, i + 1, param1);
		int v2 = getValue(opCode, i + 2, param2);
		int p3 = opCode[i + 3];
		opCode[p3] = v1 * v2;
		i = i + 4;
		return i;
	}

	/**
	 * Opcode 3 takes a single integer as input and saves it to the position 
	 * given by its only parameter. For example, the instruction 3,50 would 
	 * take an input value and store it at address 50.
	 * @param opCode - the array
	 * @param i - the position in the array
	 * @param param1
	 * @return i modified (+2)
	 */
	private static int opCode3(int[] opCode, int i, int param1)
	{
		//int p1 = getValue(opCode, i + 1, param1);
		int p1 = opCode[i + 1];
		opCode[p1] = input;
		i = i + 2;
		return i;
	}

	/**
	 * Opcode 4 outputs the value of its only parameter. 
	 * For example, the instruction 4,50 would output the value at address 50.
	 * @param opCode
	 * @param i
	 * @param param1
	 * @return i modified (+2)
	 */
	private static int opCode4(int[] opCode, int i, int param1)
	{
		int v1 = getValue(opCode, i + 1, param1);
		outputs.add(v1);
		i = i + 2;
		return i;
	}

	/**
	 * Opcode 5 is jump-if-true: if the first parameter is non-zero, 
	 * it sets the instruction pointer to the value from the second 
	 * parameter. Otherwise, it does nothing.
	 * @param opCode - the array
	 * @param i - the position in the array
	 * @param param1
	 * @param param2
	 * @return i modified (? or +3)
	 */
	private static int opCode5(int[] opCode, int i, int param1, int param2)
	{
		int v1 = getValue(opCode, i + 1, param1);
		if(v1 != 0)
		{
			int v2 = getValue(opCode, i + 2, param2);
			i = v2;
			return i;
		}
		i = i + 3;
		return i;
	}

	/**
	 * Opcode 6 is jump-if-false: if the first parameter is zero, it sets 
	 * the instruction pointer to the value from the second parameter. 
	 * Otherwise, it does nothing.
	 * @param opCode - the array
	 * @param i - the position in the array
	 * @param param1
	 * @param param2
	 * @return i modified (? or +3)
	 */
	private static int opCode6(int[] opCode, int i, int param1, int param2)
	{
		int v1 = getValue(opCode, i + 1, param1);
		if(v1 == 0)
		{
			int v2 = getValue(opCode, i + 2, param2);
			i = v2;
			return i;
		}
		i = i + 3;
		return i;
	}

	/**
	 * Opcode 7 is less than: if the first parameter is less than the second parameter, 
	 * it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
	 * @param opCode - the array
	 * @param i - the position in the array
	 * @param param1
	 * @param param2
	 * @param param3
	 * @return i modified (+4)
	 */
	private static int opCode7(int[] opCode, int i, int param1, int param2)
	{
		int v1 = getValue(opCode, i + 1, param1);
		int v2 = getValue(opCode, i + 2, param2);
		int p3 = opCode[i + 3];
		if(v1 < v2)
		{
			opCode[p3] = 1;
		}
		else
		{
			opCode[p3] = 0;
		}
		i = i + 4;
		return i;
	}

	/**
	 * Opcode 8 is equals: if the first parameter is equal to the second parameter, 
	 * it stores 1 in the position given by the third parameter. Otherwise, it stores 0.
	 * @param opCode - the array
	 * @param i - the position in the array
	 * @param param1
	 * @param param2
	 * @param param3
	 * @return i modified (+4)
	 */
	private static int opCode8(int[] opCode, int i, int param1, int param2)
	{
		int v1 = getValue(opCode, i + 1, param1);
		int v2 = getValue(opCode, i + 2, param2);
		int p3 = opCode[i + 3];
		if(v1 == v2)
		{
			opCode[p3] = 1;
		}
		else
		{
			opCode[p3] = 0;
		}
		i = i + 4;
		return i;
	}

	/**
	 * Handles codes 'aaa0X' with a either '1' or '0' (or empty) and X an opCode. <br/>
	 * Example : <br/>
	 * 1101 --> code 1 with param1 = 1, param2 = 1, param3 = 0 <br/>
	 * 1008 --> code 8 with param1 = 0, param2 = 1, param3 = 0 <br/>
	 * @param code
	 * @param opCode
	 * @param i
	 * @return i modified (?)
	 */
	private static int opCodeWithParam(int code, int[] opCode, int i)
	{
		int subCode = code % 100;
		if(!isSupporterOpCode(subCode))
			throw new IllegalStateException("This case should happen ? code=" + code);

		int modeFor3 = code / 10000;
		if(modeFor3 == IMMEDIATE_MODE)
			throw new IllegalStateException("Should not happen opCode:'" + code + "'");

		int modeFor2 = (code / 1000) % 10;
		int modeFor1 = (code / 100) % 10;

		return readCode(subCode, opCode, i, modeFor1, modeFor2, modeFor3);
	}

	/**
	 * Either get directly the value from opCode[pos] (IMMEDIATE_MODE) 
	 * or the value from from opCode[opCode[pos]] (POSITION_MODE).
	 * @param opCode - the array
	 * @param pos - the initial position to look in the array
	 * @param paramMode - the mode see {@link #SUPPORTED_MODES}
	 * @return
	 */
	private static int getValue(int[] opCode, int pos, int paramMode)
	{
		if(!isSupporterMode(paramMode))
			throw new IllegalStateException("This case should not happen paramMode=" + paramMode);

		if(paramMode == IMMEDIATE_MODE)
		{
			return opCode[pos];
		}
		// paramMode == POSITION_MODE
		return getValue(opCode, opCode[pos], IMMEDIATE_MODE);
	}

	private static boolean isSupporterMode(int mode)
	{
		for (int c : SUPPORTED_MODES)
		{
			if(mode == c)
				return true;
		}
		return false;
	}

	private static boolean isSupporterOpCode(int subCode)
	{
		for (int c : SUPPORTED_OPCODES)
		{
			if(subCode == c)
				return true;
		}
		return false;
	}

	private static String arrayToString(int[] array)
	{
		StringBuilder sb = new StringBuilder();
		int f = array.length - 1;
		for (int i = 0; i <= f; i++)
		{
			if(i != f)
				sb.append(array[i]).append(",");
			else
				sb.append(array[i]);
		}
		return sb.toString();
	}

	private static String arrayToString(Integer[] array)
	{
		StringBuilder sb = new StringBuilder();
		int f = array.length - 1;
		for (int i = 0; i <= f; i++)
		{
			if(i != f)
				sb.append(array[i]).append(",");
			else
				sb.append(array[i]);
		}
		return sb.toString();
	}
}
