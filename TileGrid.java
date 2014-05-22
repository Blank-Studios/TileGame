package Core;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import Units.Unit;



public class TileGrid implements ActionListener
{

	private JButton move;
	private JButton attack;
	private JButton spells;
	private JButton inventory;
	private JButton close;
	private JButton info;

	private JFrame optionFrame;

	private int row;
	private int col;

	GameTile[][] map;

	private GameTile selectedTile;

	public TileGrid(int row, int col)
	{
		setUp();
		
		this.row = row;
		this.col = col;
		int x = 0;
		int y = 0;
		map = new GameTile[row][col];
		for (int r = 0; r < row; r++)
		{
			for (int c = 0; c < col; c++)
			{
				map[r][c] = new GameTile(x, y);
				x += GameTile.getSize();
			}

			x = 0;
			y += GameTile.getSize();
		}

	}
	
	private void setUp(){
		  	move = new JButton("Move");
	        attack = new JButton("Attack");
	        spells = new JButton("Spells");
	        inventory = new JButton("Inventory");
	        info = new JButton("Info");
	        close = new JButton("Close");
	     
	        
	        move.addActionListener(this);
	        attack.addActionListener(this);
	        spells.addActionListener(this);
	        inventory.addActionListener(this);
	        info.addActionListener(this);
	        close.addActionListener(this);
	        
	        optionFrame = new JFrame("Option Menu");
	}

	public int getNumberOfRows(){
		return row;
	}

	public int getNumberOfCols(){
		return col;
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

	public int getGridSize(){
		return GameTile.getSize() * getNumberOfCols();
	}

	public void draw(Graphics g){

		for(int i = 0; i < map.length; i++){
			for(int j = 0; j < map[i].length; j++)
			{
				map[i][j].draw(g);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public GameTile getSelectedTile(int x, int y){
		for(int r = 0; r < getNumberOfRows(); r++){
			for(int c = 0; c < getNumberOfCols(); c++){
				if(map[r][c].withinBounds(x, y))
					return map[r][c];
			}
		}
		
		return null;
	}
	
	public void takeMouseEvent(MouseEvent e){
		int mouseY = e.getY() - 32;
		int mouseX = e.getX();
		
		if(selectedTile != null){
			selectedTile.setSelected(false);
			selectedTile = null;
		}
		
		selectedTile = getSelectedTile(mouseX, mouseY);
		selectedTile.setSelected(true);
	
	}

}
