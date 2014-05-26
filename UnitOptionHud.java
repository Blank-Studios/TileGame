package Core;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;

import Units.Unit;

public class UnitOptionHud implements ActionListener{
	
	TileGrid grid;
	
	/*
	 * User interaction buttons
	 */
	private JButton move;
	private JButton end;
	private JButton attack;
	private JButton spells;
	private JButton inventory;
	private JButton close;
	private JButton info;

	private JFrame optionFrame; //JFrame for the option menu
	private Unit selectedUnit;
	
	public UnitOptionHud(TileGrid grid){
		this.grid = grid;
		move = new JButton("Move");
		attack = new JButton("Attack");
		end = new JButton("End Turn");
		move.addActionListener(this);
		attack.addActionListener(this);
		end.addActionListener(this);
		
		optionFrame = new JFrame("Option Menu");
		optionFrame.setLayout(new FlowLayout());
		optionFrame.add(move);
		optionFrame.add(attack);
		optionFrame.add(end);
		optionFrame.pack();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(!selectedUnit.isMoving()){
			if(e.getSource() == move && selectedUnit.canMove()){
				grid.move();
			}

			if(e.getSource() == attack && selectedUnit.canAttack()){
				grid.attack();
			}

			if(e.getSource() == end){
				grid.endTurn();
			}
		}

		if(selectedUnit != null)
			selectedUnit.checkAndUpdate();
	}
	
	/**
	 * Set the current options for the given unit
	 * @param unit The unit to use
	 */
	public void setOptions(Unit unit){
		
		selectedUnit = unit;
		
		if(selectedUnit.isMoving() || unit.getOwner() != grid.getCurrentPlayer().getID() || !unit.isEnabled()){
			move.setEnabled(false);
			attack.setEnabled(false);
		}
		else{
			if(unit.canMove())
				move.setEnabled(true);
			else
				move.setEnabled(false);

			if(unit.canAttack())
				attack.setEnabled(true);
			else
				attack.setEnabled(false);
		}
	}
	
	/**
	 * Make the option menu visible or not
	 * @param show Should this menu be visible?
	 */
	public void setVisible(boolean show){
		optionFrame.setVisible(show);
	}
}
