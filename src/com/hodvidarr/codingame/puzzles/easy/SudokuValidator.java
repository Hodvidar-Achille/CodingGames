import java.util.*;
import java.io.*;
import java.math.*;

/**
 * Auto-generated code below aims at helping you parse
 * the standard input according to the problem statement.
 **/
class Solution {

    public static void main(String[] args)
    {
        int[][] sudoku = new int[9][9];
        Scanner in = new Scanner(System.in);
        for (int i = 0; i < 9; i++) 
        {
            String row = in.nextLine();
            row = row.replaceAll("\\s+","");
            char[] letters = row.toCharArray();
            for(int j = 0; j < 9; j++)
            {
                sudoku[i][j] = Character.getNumericValue(letters[j]);
            }
        }

        // Write an answer using System.out.println()
        // To debug: System.err.println("Debug messages...");
        if(checkSudokuResult(sudoku))
        {
            System.out.println("true");
        }
        else
        {
            System.out.println("false");
        }
    }


    private static boolean checkSudokuResult(int[][] sudoku)
    {
        // Check that each line and each column has only one time each 9 numbers
        for (int i = 0; i < 9; i++) 
        {
            int[] lineNumbers = new int[9];
            int[] columnNumbers = new int[9];

            for(int j = 0; j < 9; j++)
            {
                int lineCurrentNumber = sudoku[i][j];
                for(int lineP = 0; lineP < j; lineP++)
                {
                    if(lineCurrentNumber == lineNumbers[lineP])
                    {
                        return false;
                    }
                }
                lineNumbers[j] = lineCurrentNumber;

                int columnCurrentNumber = sudoku[j][i];
                for(int columnP = 0; columnP < j; columnP++)
                {
                    if(columnCurrentNumber == columnNumbers[columnP])
                    {
                        return false;
                    }
                }
                columnNumbers[j] = columnCurrentNumber;
            }
        }

        // Check that each subgrid has only one time each 9 numbers
        for (int subL = 0; subL < 3; subL++)
        {
            for (int subC = 0; subC < 3; subC++)
            {
                int[] gridNumbers = new int[9];
                for (int i = 0; i < 3; i++) 
                {
                    for(int j = 0; j < 3; j++)
                    {
                        int subGridCurrentNumber = sudoku[ (subL*3) + i][ (subC*3) +j];
                        int subGridPosition = (i * 3) + j;
                        for(int gridP = 0; gridP < subGridPosition; gridP++)
                        {
                            if(subGridCurrentNumber == gridNumbers[gridP])
                            {
                                return false;
                            }
                        }
                        gridNumbers[subGridPosition] = subGridCurrentNumber;
                    }
                }
            }
            
        }

        return true;
    }
}
