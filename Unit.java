
@SuppressWarnings("unused")
public abstract class Unit
{
    private int[] attributes = new int[7];
    
    private int level;
    
    private int atkAtt;
    
    private boolean fly;
    
    private Weapons[] inventory = new Weapons[5];
    
    private Weapons equip;
    
    public int owner;
    
    public Weapons[] getInv()
    {
        return inventory;
    }
    
    public int getLevel()
    {
        return level;
    }
    
    public void setLevel(int level)
    {
        this.level = level;
    }
    
    public int[] getAtts()
    {
        return attributes;
    }
    
    public void setAtts(int[] atts)
    {
        attributes = atts;
    }
    
    public int getAtkAtt()
    {
        return atkAtt;
    }
    
    public Weapons getWep()
    {
        return equip;
    }
    
}
