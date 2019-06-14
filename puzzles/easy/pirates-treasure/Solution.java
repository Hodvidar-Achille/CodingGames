import java.util.*;

/**
 *      https://www.codingame.com/ide/puzzle/pirates-treasure
 * by Hodvidar
 **/
class Solution 
{

    public static void main(String args[]) 
    {
    	System.err.println("START");
    	
    	Scanner in = new Scanner(System.in);
        int W = in.nextInt();
        int H = in.nextInt();
        int[][] map = new int[H][W];
        for (int i = 0; i < H; i++) 
        {
            for (int j = 0; j < W; j++) 
            {
                int v = in.nextInt();
                map[i][j] = v;
            }
        }
        
        // Dummy implementation, go through every point and check if
        // 1) It is == 0
        // 2) It is surrounded by 1
        for (int i = 0; i < H; i++) 
        {
            for (int j = 0; j < W; j++) 
            {
                if(map[i][j] == 1)
                	continue;
                
                if(isSurrounded(j, i, map))
                {
                	System.out.println(j+" "+i);
                	return;
                }
            }
        }

        System.err.println("END");
    }
    
    private static boolean isSurrounded(int x, int y, int[][] map)
    {
    	boolean isOut_left = (x == 0);
    	boolean isOut_top= (y == 0);
    	boolean isOut_right = (x == map[0].length-1);
    	boolean isOut_bottom = (y == map.length-1);
    	
    	boolean topLeft = isOut_top || isOut_left || map[y-1][x-1] == 1;
    	boolean top = isOut_top || map[y-1][x] == 1;
    	boolean topRight = isOut_top || isOut_right || map[y-1][x+1] == 1;
    	boolean left = isOut_left || map[y][x-1] == 1;
    	boolean right = isOut_right || map[y][x+1] == 1;
    	boolean bottomLeft = isOut_bottom || isOut_left || map[y+1][x-1] == 1;
    	boolean bottom = isOut_bottom || map[y+1][x] == 1;
    	boolean bottomRight = isOut_bottom || isOut_right || map[y+1][x+1] == 1;
    	
    	return topLeft && top && topRight && left && right && bottomLeft && bottom && bottomRight;
    }
}
