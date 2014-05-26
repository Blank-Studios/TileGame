package Core;

import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MediaTracker;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.KeyStroke;

import Interface.Updatable;

public class Dialogue extends JComponent implements Updatable{

	/**
	 * A serial number required by JAVA, but made up by me
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Places the dialog in the center of parent
	 */
	public static int PLACEMENT_CENTER_OF_PARENT = 1;
	/**
	 * Places the dialog at the bottom of parent
	 */
	public static int PLACEMENT_BOTTOM = 2;
	/**
	 * Places the dialog at the top of parent
	 */
	public static int PLACEMENT_TOP = 3;

	/**
	 * Sets the height for the dialog box
	 */
	private int DEFAULT_HEIGHT = 100;

	/**
	 * Should show the dialog box?
	 */
	private boolean showDialogue = false;

	/**
	 * All the dialog that is to be displayed
	 */
	private ArrayList<String> dialogue = new ArrayList<String>();

	/**
	 * Images to be displayed with the dialog
	 */
	private ArrayList<String> imageLocations = new ArrayList<String>();

	/**
	 * Should the text be typed?
	 */
	private boolean typeText = false;

	/**
	 * The current index of the text when typing
	 */
	private int textIndex = 0;

	/**
	 * A file path for the custom image that may be used instead of the default
	 */
	private String dialogueBoxCustomImageFile = null;

	/**
	 * A file path for the default image used when there is no custom image
	 */
	private String dialogueBoxDefaultImageFile = new File("").getAbsolutePath() + "\\src\\Core\\GraphicsFile\\DialogueBoxGraphics\\defaultDialogueImage.png";

	/**
	 * The actual image of the dialog which is either the default or custom image above
	 */
	private Image dialogueBoxImage;

	/**
	 * The unit, or image, that is associated with the text
	 */
	private Image unitImage;

	/**
	 * The x coordinate of the dialog
	 */
	private int x;

	/**
	 * They y coordinate of the dialog
	 */
	private int y;

	/**
	 * The width of the dialog
	 */
	private int width;

	/**
	 * The height of the dialog
	 */
	private int height;

	/**
	 * The current displayed text
	 */
	private String currentText = "";

	/**
	 * The parent of this dialog
	 */
	private JComponent parent;

	/**
	 * The placement of the dialog, which is default to center of parent
	 */
	private int placement = PLACEMENT_CENTER_OF_PARENT;

	/**
	 * Whether the program is waiting for user input
	 */
	private boolean waitForUser = false;

	/**
	 * Tracks loading images to make sure they are fully loaded
	 */
	private MediaTracker tracker;

	/**
	 * The text color of the text to display
	 */
	private Color textColor = Color.green;

	/**
	 * The inset for the text's x coordinate
	 */
	private int insetX;

	/**
	 * The inset for the text's y coordinate
	 */
	private int insetY;

	/**
	 * The current x coordinate when typing
	 */
	private int textX;

	/**
	 * The current y coordinate when typing
	 */
	private int textY;

	/**
	 * How big the images associated with dialog should be
	 */
	private int imageWidth;

	/**
	 * The font of the text being displayed
	 */
	private Font font;

	/**
	 * Used with the font to get the size and other useful goodies
	 */
	private FontMetrics f;

	/**
	 * The line spacing when a new line is created
	 */
	private int lineSpacing;

	/**
	 * The size of the text
	 */
	private int fontSize;

	/**
	 * If a word can be completely typed
	 */
	private boolean canType = false;

	/**
	 * A String array of words
	 */
	private String[] wordsToWrite;

	/**
	 * The index of the wordsToWrite array
	 */
	private int tempIndex;

	/**
	 * Creates a new dialogue box with no text, no images, and not displaying
	 * Note: Placement of dialogue will be default to center of parent
	 * @param parent The container holding this dialogue box, if null dialogue will appear in center of the screen
	 */
	public Dialogue(JComponent parent){
		this.parent = parent;
		createDialogueBoxImage();
	}

	/**
	 * Creates a new dialogue box with text but does not display. No image is associated with the text.
	 * Note: Placement of dialogue will be default to center of parent
	 * @param parent The container holding this dialogue box, if null dialogue will appear in center of the screen 
	 * @param text The text to display
	 */
	public Dialogue(JComponent parent, String text){
		this.parent = parent;
		addOneString(text, null);
		setUp();
	}

	/**
	 * Creates a new dialogue box with text and shows the box depending on if wanted. No image is associated with the text.
	 * Note: Placement of dialogue will be default to center of parent
	 * @param parent The container holding this dialogue box, if null dialogue will appear in center of the screen
	 * @param text The text to display
	 * @param show Show the dialogue?
	 */
	public Dialogue(JComponent parent, String text, boolean show){
		this.parent = parent;
		addOneString(text, null);
		showDialogue(show);
		setUp();
	}

	/**
	 * Creates a new dialogue box with an image beside the text but does not show the dialogue. Image (given in string file path)
	 * will be associated with text. If imageLocation is null, no image will be associated with the text.
	 * Note: Placement of dialogue will be default to center of parent
	 * @param parent The container holding this dialogue box, if null dialogue will appear in center of the screen
	 * @param text The text to display
	 * @param imageLocation The string path to the image to display
	 */
	public Dialogue(JComponent parent, String text, String imageLocation){
		this.parent = parent;
		addOneString(text, imageLocation);
		setUp();
	}

	/**
	 * Creates a new dialogue box with an image beside the text and shows the box depending on if wanted. Image (given in string file path)
	 * will be associated with text. If imageLocation is null, no image will be associated with the text.
	 * Note: Placement of dialogue will be default to center of parent
	 * @param parent The container holding this dialogue box, if null dialogue will appear in center of the screen
	 * @param text The text to display
	 * @param imageLocation The string path to the image to display
	 * @param show Show the dialogue?
	 */
	public Dialogue(JComponent parent, String text, String imageLocation, boolean show){
		this.parent = parent;
		addOneString(text, imageLocation);
		showDialogue(show);
		setUp();
	}

	/**
	 * Adds an entire dialogue to be used, and no images to be associated with the dialogue. The dialogue is not shown.
	 * Note: Placement of dialogue will be default to center of parent
	 * @param parent The container holding this dialogue box, if null dialogue will appear in center of the screen
	 * @param dialogue The dialogue of strings to be used
	 */
	public Dialogue(JComponent parent, ArrayList<String> dialogue){
		this.parent = parent;
		addDialogue(dialogue, null);
		setUp();
	}

	/**
	 * Adds an entire dialogue and images(given in string file paths) to be associated with the dialogue. The dialogue is not shown. If
	 * imageLocations is null, no images will be associated with the dialogue
	 * Note: Placement of dialogue will be default to center of parent
	 * @param parent The container holding this dialogue box, if null dialogue will appear in center of the screen
	 * @param dialogue The dialogue to be used
	 * @param imageLocations The file paths to the images to be used
	 */
	public Dialogue(JComponent parent, ArrayList<String> dialogue, ArrayList<String> imageLocations){
		this.parent = parent;
		addDialogue(dialogue, imageLocations);
		setUp();
	}

	/**
	 * Adds an entire dialogue to be used, and no images to be associated with the dialogue. The dialogue is shown depending if wanted.
	 * Note: Placement of dialogue will be default to center of parent
	 * @param parent The container holding this dialogue box, if null dialogue will appear in center of the screen
	 * @param dialogue The dialogue of strings to be used
	 * @param show Show the dialogue?
	 * */
	public Dialogue(JComponent parent, ArrayList<String> dialogue, boolean show){
		this.parent = parent;
		addDialogue(dialogue, null);
		showDialogue(show);
		setUp();
	}


	/**
	 * Adds an entire dialogue and images(given in string file paths) to be associated with the dialogue. The dialogue is shown depending if
	 * wanted. If imageLocations is null, no images will be associated with the dialogue
	 * @param parent The container holding this dialogue box, if null dialogue will appear in center of the parent
	 * @param dialogue The dialogue to be used
	 * @param imageLocations The file paths to the images to be used
	 * @param show Show the dialogue? 
	 */
	public Dialogue(JComponent parent, ArrayList<String> dialogue, ArrayList<String> imageLocations, boolean show){
		this.parent = parent;
		addDialogue(dialogue, imageLocations);
		showDialogue(show);	
		setUp();
	}

	/**
	 * Set up the dialogue 
	 */
	private void setUp(){
		tracker = new MediaTracker(this); //Media Tracker helps in making program wait for an image to load
		width = parent.getPreferredSize().width; //Set the width of the dialog - Default is to cover the full width
		height = DEFAULT_HEIGHT; //Set the height for the dialog - Default is a variable that can be changed

		if(imageLocations.get(0) != null){
			imageWidth = 100;
		}

		for(int i = 0; i < imageLocations.size(); i++){
			if(imageLocations.get(i) != null){
				imageLocations.set(i, new File("").getAbsolutePath() + "\\src\\Core\\GraphicsFile\\UnitDialogImages\\" + imageLocations.get(i));
			}
		}

		setDialoguePlacement(placement); //Sets the placement of the dialog
		createDialogueBoxImage(); //creates the image for the dialog box

		insetX = 20; //The text needs to be set an inset of 20 for the rectangle
		insetY = 20; //The text needs to be set an inset of 20 for the rectangle

		font = new Font("Arial", Font.PLAIN, 12); //Default font is arial size 12
		f = Toolkit.getDefaultToolkit().getFontMetrics(font); //Gets the font metrics from the font created above
		lineSpacing = 2; //Line space for the text - Default is 2
		fontSize = font.getSize() * lineSpacing;  //Determine the font size for the text which includes line spacing

		parent.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("released ENTER"), "accept");//create an action map
		//for the dialog
		parent.getActionMap().put("accept", new AbstractAction() {

			/**
			 * Generated serial ID Required by JAVA
			 */
			private static final long serialVersionUID = 2L;

			@Override
			public void actionPerformed(ActionEvent e) {
				if(waitForUser || !isTyping()){ //if the program is waiting for the user to continue or if the text is done typing		
					String word = reconstructWord(); //reconstruct the word that was split
					if(word.length() >= dialogue.get(0).length()){ //if the word reconstructed is the same length as the current word in
						//in the list, then there is no need to show any more of the word - it is
						//done being written and displayed
						dialogue.remove(0); //remove the dialogue
						imageLocations.remove(0); //remove the image that was associated with that dialog
						if(dialogue.size() <= 0) //if the dialogue is empty
							showDialogue(false); //get rid of this dialog
					}
					else{
						dialogue.set(0, dialogue.get(0).substring(word.length())); //shorten the sentence to only what is not being displayed
					}

					canType = false; //used if a word can be typed on screen - That the word typed will not overflow through the edge of 
					//the dialogue
					currentText = "";

					textIndex = 0; //reset variables to begin next dialogue
					tempIndex = 0; //reset variables to begin next dialogue
					waitForUser = false; //reset variables to begin next dialogue
				} else{
					/*
					 *Either the sentence was still being typed or the program somehow errored and is not waiting for
					 *the user to continue - so force the text to skip to being fully displayed and wait for user input
					 */
					waitForUser = true; 
					displayText();
				}

			}});
	}

	/**
	 * Reconstructs the word that was split during typing/outprinting to dialogue
	 * @return String The word that was originally split
	 */
	private String reconstructWord(){
		String word = "";
		for(int i = 0; i < tempIndex; i++){
			word += wordsToWrite[i] + " ";
		}
		return word;
	}

	/**
	 * Set the width for this dialogue box
	 * @param width The new width for the dialogue
	 */
	public void setWidth(int width){
		this.width = width;
	}

	/**
	 * Set the height for this dialogue box
	 * @param height The new height for the dialogue
	 */
	public void setHeight(int height){
		this.height = height;
	}

	/**
	 * Set the location for the dialogue with a specific x and y coordinate
	 * @param x The new x coordinate
	 * @param y The new y coordinate
	 */
	public void setLocation(int x, int y){
		this.x = x;
		this.y = y;
	}

	/**
	 * Set the location for the dialogue at the specified x value
	 * @param x The new x coordinate
	 */
	public void setXLocation(int x){
		this.x = x;
	}

	/**
	 * Set the location for the dialogue at the specified y value
	 * @param y The new y coordinate
	 */
	public void setYLocation(int y){
		this.y = y;
	}

	/**
	 * Sets the dialogue box according to the placement given
	 * @param placement Where to place the box. Value should be a value provided by placement variables of the class
	 */
	public void setDialoguePlacement(int placement){
		this.placement = placement;

		if(placement == PLACEMENT_CENTER_OF_PARENT){
			x = parent.getPreferredSize().width/2 - width/2;
			y = parent.getPreferredSize().height/2 - height/2;
		}

		else if(placement == PLACEMENT_BOTTOM){
			x = 0;
			y = parent.getPreferredSize().height - height;
		}

		else if(placement == PLACEMENT_TOP){
			x = 0;
			y = 0;
		}
	}

	/**
	 * Add an entire dialogue to the current dialogue along with images to be shown with the dialogue. 
	 * If null is inputed for imageLocations, then no images will be associated with the dialogue
	 * @param dialogue The dialogue to use
	 * @param imageLocations The image(given in string file paths) to be associated with dialogue
	 */
	public void addDialogue(ArrayList<String> dialogue, ArrayList<String>imageLocations){
		for(int i = 0; i < dialogue.size(); i++){
			this.dialogue.add(dialogue.get(i));
		}
		addImageLocations(imageLocations);
		createDialogueBoxImage();
	}

	/**
	 * Add one sentence to the dialogue and an image(given in a string file path) to be associated with the sentence. If null is inputed for
	 * imageLocation, then no image will be associated with the sentence
	 * @param string Sentence to add to dialogue
	 * @param imageLocation The image associated with the sentence
	 */
	public void addOneString(String string, String imageLocation){
		dialogue.add(string);
		imageLocations.add(imageLocation);
	}

	/**
	 * Adds image locations to the imageLocations array list. If null is given, the imageLocations will fill the list with null references
	 * until it is the same size as the dialogue - So no images are associated with the dialogue at the same indexes
	 * @param imageLocations The images(given in string file paths) to be added
	 */
	private void addImageLocations(ArrayList<String> imageLocations){
		if(imageLocations == null){
			while(this.imageLocations.size() < dialogue.size())
				this.imageLocations.add(null);
		}
		else{
			for(int i = 0; i < imageLocations.size(); i++){
				this.imageLocations.add(imageLocations.get(i));
			}
		}
	}

	/**
	 * Give the dialogue box a different image. If null is given, the default image will be used. 
	 * Note: This method assumes that the image is in the DialogBoxGraphics folder which is in GraphicsFile, therefore image paths used with
	 * this method should be in that file. The image path given will only need to include the name and type of file that it is
	 * i.e defaultDialogueImage.png
	 * @param imagePath The path to the new image
	 */
	public void setDialogueBoxImage(String imagePath){
		dialogueBoxCustomImageFile = new File("").getAbsolutePath() + "\\src\\Core\\GraphicsFile\\DialogueBoxGraphics\\" + imagePath;
		createDialogueBoxImage();
	}

	/**
	 * Should the text characters be displayed one at a time in a typing-fashion
	 * @param shouldType T Should the text be typed?
	 */
	public void typeText(boolean shouldType){
		typeText = shouldType;
	}

	/**
	 * Hide/show the dialogue to the user
	 * @param show Should the dialogue be visible?
	 */
	public void showDialogue(boolean show){
		showDialogue = show;
		if(show){
			parent.requestFocus();
		}
	}

	@Override
	public void update() {

		if(!waitForUser){
			if(typeText && dialogue.size() > 0 && textIndex < dialogue.get(0).length()){
				type();
			}
			else if (dialogue.size() > 0){
				displayText();
			}
		}
	}

	@Override
	public void fixedUpdate(){}

	@Override
	public void draw(Graphics g) {

		if(showDialogue){
			drawDialogueImage(g);
			drawUnitImage(g);
			drawString(g);
		}
	}

	private void drawUnitImage(Graphics g){
		if(unitImage != null){
			g.drawImage(unitImage, x, y, null);	
		}

	}

	/**
	 * Draws the text on the screen
	 * @param g The graphics to draw on
	 */
	private void drawString(Graphics g){
		if(currentText != null){
			g.setColor(textColor);

			textX = x + insetX; //set the next position for the text
			textY = y + insetY; //set the next position for the text

			if(unitImage != null){
				textX += imageWidth;
			}

			wordsToWrite = currentText.split(" "); //split the word with spaces

			for(tempIndex = 0; tempIndex < wordsToWrite.length; tempIndex++){ //while there is still words left
				if((!isTyping() && canAddWord(wordsToWrite[tempIndex])) || (isTyping() && canTypeWord())){ //if the dialog will not overflow

					g.drawString(wordsToWrite[tempIndex], textX, textY); //draw the word
					textX += f.stringWidth(wordsToWrite[tempIndex]) + f.charWidth(' '); //add the text width and the width for a space to x

					if(tempIndex + 1 < wordsToWrite.length && !canAddWord(wordsToWrite[tempIndex + 1])){ //if the x position of the next word will overflow, go to the next line
						textX = insetX;

						if(canAddLine()){//is there room for a new line?
							textY += fontSize;
							if(unitImage != null){
								textX += imageWidth;//reset variables for new line
							}	
						}
						else{//No, so stop adding words
							break;
						}		

					}
				}
				else{
					break; //word can not be added at this time, stop displaying new words
				}
			}

			String word = reconstructWord(); //reconstruct the word split from spaces

			if( (!canTypeWord() && !canAddLine()) || (tempIndex < wordsToWrite.length && !canAddLine() && !canAddWord(wordsToWrite[tempIndex]))
					|| word.length() >= dialogue.get(0).length()){ //If a word can not be added without overflowing the dialog
				g.drawString(">>", x + width - fontSize, y + height - insetY); //draw the marker to continue(can be image)
				textX = insetX; //reset variables to continue
				textY = insetY; //reset variables to continue
				waitForUser = true; //wait for user
			}


		}
	}

	/**
	 * Determines if the word can be typed during the type effect - Similar to canAddWord
	 * This will take the word that is at the current index and see if it can be typed, if it can, this method will run but will 
	 * wait until a space is reached so that typing can occur without interruption or error. Once a space is reached, then reset and verify
	 * that a new word, if present, can be typed without overflowing dialog box
	 * @return boolean Whether the entire word can be typed on the dialog
	 */
	private boolean canTypeWord(){
		String word = dialogue.get(0); //get the current dialogue to be displayed

		if(word.charAt(textIndex) == ' '){ //if there is a space
			canType = false; //reset variable to continue adding words when typing
		}

		if(!canType){//if we are currently unable to type a word, check to see if we can type a new word
			int i = textIndex; //the current index of the word we are typing

			while(word.indexOf(" ") >= 0 && word.indexOf(" ") < i){ //if there is a space before the current index
				i -= word.indexOf(" ") + 1; //subtract the index to be proportioned to the removal of the characters
				word = word.substring(word.indexOf(" ") + 1); //remove the all words and space before the index
			}
			if(word.indexOf(" ") >= 0 && word.indexOf(" ") != textIndex){ //if there are spaces after this word or space
				word = word.substring(0, word.indexOf(" ")); //get rid of them
			}
			else if(word.indexOf(" ") == textIndex){ //if this character is a space
				if(word.length() > 1) //make sure not to out of bounds
					word = word.substring(word.indexOf(" "), word.indexOf(" ") + 1); //substring the space
				else
					word = word.substring(word.indexOf(" ")); //substring the space
			}

			if(canAddWord(word)){//if the word that now has no spaces before or after it can fit in the dialog box
				canType = true; //the word can be typed
			}
		}
		return canType;
	}

	/**
	 * Creates the image for the dialogue box using a custom image if provided; otherwise, a default image is used
	 */
	private void createDialogueBoxImage(){
		Toolkit toolkit = Toolkit.getDefaultToolkit();

		if(dialogueBoxCustomImageFile != null)
			dialogueBoxImage = toolkit.getImage(dialogueBoxCustomImageFile);
		else
			dialogueBoxImage = toolkit.getImage(dialogueBoxDefaultImageFile);

		if(imageLocations.get(0) != null){
			unitImage = toolkit.getImage(imageLocations.get(0));
			tracker.addImage(unitImage, 1);
		}

		tracker.addImage(dialogueBoxImage, 0);

		try {
			tracker.waitForAll();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		dialogueBoxImage = scaleImage(dialogueBoxImage, width, height);
		if (unitImage != null){
			unitImage = scaleImage(unitImage, 50, height);
		}
	}

	/**
	 * Scales the dialog box image
	 * @param image The image to scale
	 * @param newWidth The new width of the image
	 * @param newHeight The new height of the image
	 * @return image The scaled image
	 */
	private Image scaleImage(Image image, int newWidth, int newHeight){
		return image.getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
	}

	/**
	 * Draws the image for the dialogue box
	 * @param g The graphics to be used
	 */
	private void drawDialogueImage(Graphics g){
		if(dialogueBoxImage != null)
			g.drawImage(dialogueBoxImage, x, y, null);
		else{
			g.setColor(Color.red);
			g.fillRect(x, y, width, height);
		}
	}

	/**
	 * Types the characters on screen
	 */
	private void type(){
		if(dialogue.size() > 0 && textIndex < dialogue.get(0).length()){
			if(textIndex < dialogue.get(0).length() - 1)
				currentText += dialogue.get(0).substring(textIndex, textIndex + 1);
			else
				currentText += dialogue.get(0).substring(textIndex);

			textIndex++;
		}
	}

	/**
	 * Determines if another word can be added to the current display of the dialog
	 * @param word The word in question
	 * @return boolean Can the word be added?
	 */
	private boolean canAddWord(String word){
		return textX + f.stringWidth(word) <= width - f.stringWidth(">>") * 2;
	}

	/**
	 * 
	 */
	private boolean canAddLine(){
		return textY + fontSize < y + height;
	}

	/**
	 * Shows the text immediately with no effects
	 */
	private void displayText(){
		currentText = dialogue.get(0);
	}

	/**
	 * Returns whether this dialogue is currently typing text on screen
	 * @return boolean Whether this dialogue is supposed to be typing and is or not
	 */
	public boolean isTyping(){
		return typeText && dialogue.size() > 0 &&  textIndex < dialogue.get(0).length();
	}

	/**
	 * Set the x inset for the text when displaying
	 * @param x The amount of inset for the x coordinate
	 */
	public void setInsetX(int x){
		insetX = x;
	}

	/**
	 * Set the y inset for the text when displaying
	 * @param y The amount of inset for the y coordinate
	 */
	public void setInsetY(int y){
		insetY = y;
	}

	/**
	 * Sets both the x and y insets at the same time
	 * @param x The amount of inset for the x coordinate
	 * @param y The amount of inset for the y coordinate
	 */
	public void setInsets(int x, int y){
		setInsetX(x);
		setInsetY(y);
	}

	/**
	 * Set the Font for the text being displayed
	 * @param f The font for the text
	 */
	public void setFont(Font f){
		font = f;
	}

	/**
	 * Set the color of the text being displayed
	 * @param color The color of the text
	 */
	public void setTextColor(Color color){
		textColor = color;
	}
}
