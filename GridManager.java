package Core;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class GridManager extends MouseAdapter implements Runnable
{
   
    private JFrame gameFrame;
    
    private GamePanel gamePanel;
    

    
    private boolean running = false;
    
    public GridManager(){
        
        gamePanel = new GamePanel();
        
        gameFrame = new JFrame("Game");
        System.out.println("Game Panel: " + gamePanel.getSize());
        gameFrame.getContentPane().setPreferredSize(gamePanel.getSize());
        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.add(gamePanel);
        gameFrame.setVisible(true);
        gameFrame.addMouseListener(this);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setResizable(false);
        
        Thread t = new Thread(this);
        t.start();
    }

	@Override
	public void run() {
		running = true;
		
		while(running){
			render();
			gameFrame.repaint();
			
			try {
				Thread.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	public static void main(String[] args){
		GridManager m = new GridManager();
	}
	
    public void render(){
    	gamePanel.repaint();
    }
    
    @Override
	public void mouseReleased(MouseEvent e) {
		gamePanel.sendMouseEvent(e);
	}
}
