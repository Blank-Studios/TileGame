

public class TileGrid
{
    private int x;
    private int y;
    private GameTile;
    
    GameTile[][] map;
    
    public TileGrid()
    {
        map = new GameTile[12][12];
        for (int r = 1; r < 10; r++)
        {
            for (int c = 1; c < 10; c++)
            {
                map[r][c] = new GameTile(0);
            }
        }
    }
    
    public int getRow()
    {
        return x;
    }
    
    public int getCol()
    {
        return y;
    }
    
    /**
     * Call this method to conduct a battle, with atk vs def.
     * @param atk - offensive Unit
     * @param def - defensive Unit
     */
    public static void battle(Unit atk, Unit def)
    {
        int[] aStat = atk.getAtts();
        int[] dStat = def.getAtts();
        if (atk.getAtkAtt() == 1)
        {
            if (aStat[1] > dStat[2])
            {
                dStat[0] = 0; //atk.getWep().getDmg() - dStat[0];
            }
        }
        if (atk.getAtkAtt() == 3)
        {
            if (aStat[3] > dStat[4])
            {
                
            }
        }
            
    }
}
