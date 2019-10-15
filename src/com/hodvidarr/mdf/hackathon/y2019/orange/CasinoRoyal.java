package com.hodvidarr.mdf.hackathon.y2019.orange;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * 		https://www.isograd.com/FR/solutionconcours.php?contest_id=50
 * by Hodvidar
 */
public final class CasinoRoyal 
{
	/** If 'false' only response and Failure are written **/
	private static final boolean VERBOSE = false;
	
	private static final int NUMBER_OF_TEST = 9;
	private static final String INPUT_DIRECTORY = "casino_royal_input";
	
	public static void printIfVerbose(String s)
	{
		if (VERBOSE)
			System.err.println(s);
	}
	
	public static void main(String[] args) throws Exception 
	{
		for(int i = 1; i <= NUMBER_OF_TEST; i++)
		{
			printIfVerbose("\n\n TEST n°"+i);
			String result = test("resources\\"+INPUT_DIRECTORY+"\\input"+i+".txt");
			// --- CHECKING ---
			File file2 = new File("resources\\"+INPUT_DIRECTORY+"\\output"+i+".txt");
			// Scanner sc = new Scanner(System.in);
			Scanner sc2 = new Scanner(file2);
			String line2 = sc2.nextLine();
			printIfVerbose("Solution is: \n"+line2);
			if(result.equals(line2))
				printIfVerbose("SUCCESS!");
			else
				System.err.println("FAILURE!");
			sc2.close();
		}
	}

	private static String test(String inputFile) throws Exception 
	{
		String  line;
		File file = new File(inputFile);
		// Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(file);
		printIfVerbose("DEBUGGING");
		int maxTirage = 0;
		List<Integer> gains = new ArrayList<>();
		boolean neverNeg = true;
		
		// for(int i = 0; i< input.length; i++)
		while(sc.hasNextLine())
		{
			// line = input[i];
			line = sc.nextLine();
		    if(maxTirage == 0)
		    {
		    	printIfVerbose("nb game="+line);
		    	maxTirage = Integer.parseInt(line);
		    }
		    else
		    {
		    	printIfVerbose("gain game="+line);
		    	int currentGain = Integer.parseInt(line);
		        gains.add(currentGain);
		        if(currentGain < 0)
		            neverNeg = false;
		    }
		}
		
		if(neverNeg)
		{
		    System.out.println("0");
		    sc.close();
		    return "0";
		}
		
		double maxLoss = 0;
		double maxLossOfSegment = 0;
		double currentLossOfSegment = 0;
		boolean activeSegment = false;
		for(int i = 0; i < maxTirage; i++)
		{
		    int currentGain = gains.get(i);
		    
		    if(!activeSegment)
		    {
		        if(currentGain < 0)
		        {
		            activeSegment = true;
		            currentLossOfSegment = currentGain;
		            
		            if(currentLossOfSegment < maxLossOfSegment)
				    {
				        maxLossOfSegment = currentLossOfSegment;
				    }
				    
				    if(maxLossOfSegment < maxLoss)
				    {
				        maxLoss = maxLossOfSegment;
				    }
		        }
		        continue;
		    }
		    
		    if(currentLossOfSegment < maxLossOfSegment)
		    {
		        maxLossOfSegment = currentLossOfSegment;
		    }
		    
		    if(maxLossOfSegment < maxLoss)
		    {
		        maxLoss = maxLossOfSegment;
		    }
		    

		    if(currentLossOfSegment + currentGain > 0)
		    {
		        activeSegment = false;
		        maxLossOfSegment = 0;
		        continue;
		    }
		    
		    // else current gain are neg or don't cancel previous loss
		    currentLossOfSegment += currentGain;
		}
		
		if(currentLossOfSegment < maxLossOfSegment)
	    {
	        maxLossOfSegment = currentLossOfSegment;
	    }
	    
	    if(maxLossOfSegment < maxLoss)
	    {
	        maxLoss = maxLossOfSegment;
	    }
		
		NumberFormat nf = NumberFormat.getNumberInstance();
		nf.setMaximumFractionDigits(0);
		nf.setGroupingUsed(false);
		String rounded = nf.format(maxLoss);
		
		System.out.println(rounded);
		sc.close();
		return rounded;
	/* Vous pouvez aussi effectuer votre traitement une fois que vous avez lu toutes les données.*/
	}

}
