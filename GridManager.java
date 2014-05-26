package Core;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
/**
 * 
 * @author Matt
 *
 *The main GUI of the game
 *
 */
public class GridManager extends MouseAdapter implements Runnable
{

	private JFrame gameFrame; //the frame holding the entire game

	private GamePanel gamePanel; //the panel drawing the game

	private boolean running = false; //should the game run?

	/**
	 * APPROX_MAX_FPS sets the **approximate** maximum for frames in a second
	 * For *most values*, this variable is the FPS for the game; however, some values, such as 50 and 100, do not produce the expected results.
	 * This variable should be no greater than 1000 since the game loop runs in milliseconds - 1000 milliseconds in 1 second.
	 */
	private final static int APPROX_MAX_FPS = 32;
	/**
	 * MAX_FRAME_SKIPS sets the maximum number of frames are allowed to be skipped; the total number of frames that are *updated* but **NOT**
	 * rendered
	 */
	private final static int MAX_FRAME_SKIPS = 5;
	/**
	 * The Frame period is the the duration of a single frame - an update/render cycle - and since the update/render cycle uses milliseconds,
	 * this is equal to the number of milliseconds in a second - 1000 - divided by the APPROX_MAX_FPS
	 */
	private final static int FRAME_PERIOD = 1000/APPROX_MAX_FPS;


	int real_FPS; //the variable tracking the number of frames in a second

	public GridManager(){

		gamePanel = new GamePanel(); //create a new game panel

		gameFrame = new JFrame("Game"); //create a new JFrame
		gameFrame.getContentPane().setPreferredSize(gamePanel.getSize()); //set the size of the frame to the preferre size of the game panel
		gameFrame.pack(); //officially makes the setting of the preferred size complete
		gameFrame.setLocationRelativeTo(null); //set the frame in the middle of the screen
		gameFrame.add(gamePanel); //add the game panel
		gameFrame.setVisible(true); //this frame should be visible
		gameFrame.addMouseListener(this); //this class tracks mouse events
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //the game and program should end if this frame is closed
		gameFrame.setResizable(false); //there is no need to adjust the frame here

		Thread t = new Thread(this); //create a new thread using this class' run method
		t.start(); //start the thread
	}

	@Override
	public void run() {
		running = true; //it is time to start the game

		long timeStart; //The current time
		long timeDiff; //Tracks time difference between start time and next time Time is checked
		int sleepTime = 0; //Amount of time thread should sleep
		int framesSkipped; //number of frames skipped (number of times updating occurred without rendering
		long beginTime = System.currentTimeMillis(); //the starting time - Note: this variable being set before the loop begins *may*
		//be cause of the FPS error not giving the expected results for some values
		real_FPS = 0; //the variable tracking the number of frames in a second
		while(running){

			if(System.currentTimeMillis() - beginTime >= 1000){ //If time passed in milliseconds is greater than or more than a second
				//frames per SECOND
				gameFrame.setTitle("Game - Approx(Coded)FPS: " + APPROX_MAX_FPS + " Real FPS: " + real_FPS); //Just a quick way of showing FPS on screen
				real_FPS = 0; //reset frames back to 0 to check next FPS
				fixedUpdate(); //Runs an update at 1 second intervals (that's what this if is for)
				beginTime = System.currentTimeMillis(); //The beginTime should be reset to now
			}



			timeStart = System.currentTimeMillis();
			framesSkipped = 0;

			update(); //update the game
			render(); //draw all the images/animations on screen

			timeDiff = System.currentTimeMillis() - timeStart; //check the time difference after updating and rendering
			sleepTime = (int)(FRAME_PERIOD - timeDiff); //The frame period is how many frames that (should) occur in a second and timeDiff tracks
			//how long it has been since the first time check(timeStart) and the second time check
			//occurred. sleepTime is the difference between these two, how many frames occurred -
			//the time difference that has occurred. 

			if (sleepTime > 0){ //if sleep time is greater than 0
				try{
					Thread.sleep(sleepTime); //this thread should wait until the required FPS is reached

				}catch(InterruptedException ie){}
			}

			/*
			 * Lag has occurred
			 * If sleepTime is negative (the amount of time that passed between updating and rendering a frame is greater than a frame period)
			 * and the number of frames that have been skipped are less than the maximum allowed to be skipped, stay in this while loop to correct
			 * the error in FPS
			 * 
			 * Note: This will update, but not render the program if lag occurs
			 */
			while (sleepTime < 0 && framesSkipped < MAX_FRAME_SKIPS){
				update();
				sleepTime += FRAME_PERIOD;
				framesSkipped++;
				real_FPS--; //for every frame skipped, subtract a full frame
			}

			real_FPS++; //A complete frame has occurred

		}
	}

	/**
	 * Updates the Game
	 */
	public void update(){
		gamePanel.update();
	}

	/**
	 * Updates the Game at One second intervals
	 */
	public void fixedUpdate(){
		gamePanel.fixedUpdate();
	}

	/**
	 * Only used to start (Testing purposes only)
	 * @param args
	 */
	public static void main(String[] args){
		GridManager m = new GridManager();
	}

	/**
	 * Draw the data in game as it should be
	 */
	public void render(){
		gamePanel.repaint();
		gameFrame.repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		gamePanel.sendMouseEvent(e);
	}
}
