package Units;

import java.awt.Graphics;

import Core.GameTile;


public class MagicUnit extends Unit
{
	private String[] spells = new String[10];

	public MagicUnit(GameTile tile,  int playerOwner){
		super(tile, playerOwner);

	}

	protected String[] getSpells()
	{
		return spells;
	}

	@Override
	public void draw(Graphics g) {
		// TODO Auto-generated method stub
		
	}
}
