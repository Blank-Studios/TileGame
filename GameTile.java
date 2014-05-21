import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;


public class GameTile
{

    private int type = 0;
    private int x = 100;
    private int y = 100;
    private Unit unit = null;
    private JButton move;
    private JButton attack;
    private JButton spells;
    private JButton close;
    private JButton info;
    
    /**
     * Default constructor. Empty 100x100 pixel tile with no bonus values. 
     */
    public GameTile()
    {}
    
    /**
     * Constructor of an empty 100x100 pixel tile with specified bonus value.
     * @param type bonus value code
     */
    public GameTile(int type)
    {
        this.type = type;
    }
    
    /**
     * Constructor of a 100x100 pixel tile with no bonus value and a unit placed in it.
     * @param unit Unit placed in the tile
     */
    public GameTile(Unit unit)
    {
        this.unit = unit;
    }
    
    /**
     * Adds a unit to the tile (used to move Units)
     * @param unit to be added
     */
    public void addUnit(Unit unit)
    {
        this.unit = unit;
    }
    
    /**
     * Opens the menu to do anything for selected tile
     */
    public void getInfo()
    {
        JFrame info = setUpFrame();
        if (!isEmpty())
        {
            info.add(move);
            info.add(attack);
            if (unit instanceof MagicUnit)
            {
                info.add(spells);
            }
        }
        info.add(close);
        info.add(this.info);
    }
    
    private JFrame setUpFrame()
    {
        JFrame infoFrame = new JFrame("Menu");
        infoFrame.setResizable(false);
        infoFrame.setUndecorated(true);
        return infoFrame;
    }
    
    /**
     * Checks to see if the tile is empty.
     * @return whether or not the tile is empty
     */
    private boolean isEmpty()
    {
        return (unit == null);
    }
    
    /**
     * sets the bonus value.
     * @param type the type to set the bonus value to
     */
    public void setType(int type)
    {
        this.type = type;
    }
}
