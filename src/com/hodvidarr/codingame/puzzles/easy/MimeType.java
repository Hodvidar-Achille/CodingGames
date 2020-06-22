package com.hodvidarr.codingame.puzzles.easy;
import java.util.*;

/**
 * https://www.codingame.com/ide/puzzle/mime-type
 * by Hodvidar
 **/
class MimeType 
{

    public static void main(String[] args)
    {
        Scanner in = new Scanner(System.in);
        int N = in.nextInt(); // Number of elements which make up the association table.
        int Q = in.nextInt(); // Number Q of file names to be analyzed.
		Map<String, String> mimes = new HashMap<String, String>(N);
		
        for (int i = 0; i < N; i++) 
        {
            String EXT = in.next(); // file extension
            String MT = in.next(); // MIME type.
			System.err.println("add : "+EXT+" "+MT);
            in.nextLine();
            /**
			extensions[i] = EXT != null ? EXT : "";
			mimes[i] = MT != null ? MT : "";
			**/
			mimes.put(EXT.toLowerCase(), MT);
        }
        
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < Q; i++) 
        {
	        String FNAME = in.nextLine(); // One file name per line.
				
			int l = FNAME.length();
			int p = FNAME.lastIndexOf('.');
				
				
			System.err.println("file to test : "+FNAME);
	
			if(l > 256 || p < 0 || (l - (p+1) > 10)) 
			{
			    builder.append("UNKNOWN\n");
			} 
			else 
			{
				String x = FNAME.substring(p+1, l).toLowerCase();
				System.err.println("    extension detected : "+x);
				if(mimes.containsKey(x)){
					builder.append(mimes.get(x)+"\n");
				} else {
					builder.append("UNKNOWN\n");
				}
			}
        }
        System.out.println(builder.toString());
        in.close();
    }
    
}
