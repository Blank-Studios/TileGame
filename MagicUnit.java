package Units;


public abstract class MagicUnit extends Unit
{
    private String[] spells = new String[10];
    
    public MagicUnit(int x, int y){
		super(x, y);
		
	}
    
    protected String[] getSpells()
    {
        return spells;
    }
}
