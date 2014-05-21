
public class GridManager implements ActionListener
{
    
    private JButton move;
    private JButton attack;
    private JButton spells;
    private JButton inventory;
    private JButton close;
    private JButton info;
    
    private JFrame optionFrame;
    
    public GridManager(){
        move = new JButton("Move");
        attack = new JButton("Attack");
        spells = new JButton("Spells");
        inventory = new JButton("Inventory");
        info = new JButton("Info");
        close = new JButton("Close");
    }
    
}
