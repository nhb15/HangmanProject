package FinalProject;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;

import java.awt.FlowLayout;
import java.awt.image.BufferedImage;
import java.net.URL;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import javax.swing.Timer;
//UI
public class HangmanFrame extends JFrame implements ActionListener {

	private static boolean isEasy = true;

	private JLabel guessLabel;
	private JLabel wrongCharsLabel;
	private JLabel timeLeftLabel;
	private JLabel timeLeft;
	private JLabel notACharLabel;
	private JLabel alreadyGuessedCharLabel;
	private JLabel inputWordLabel; //in case program is modified later, leaving this in
	private JLabel incorrectWordLabel; //in case program is modified later, leaving this in
	private JLabel hangmanImage;
	private static JLabel underscores;
	private static ArrayList<JLabel> charCorrectLabels = new ArrayList<JLabel> ();

	private JTextField timeLeftField;
	private JTextField charInputField;
	private JTextField wrongCharsField;		
	private char userChar;

	private URL url;
	private Graphics2D g2d;
	private BufferedImage img;

	private JButton showCharInputDialog;

	private static int damageCount = 0;
	private static int time = 300;

	private int timerCount = 300; //300 seconds
	
	//Initialize all the regulars
	public HangmanFrame() {


		setTitle("Nate's version of Hangman");
		//Initialize all the labels	
		guessLabel = new JLabel ("Guess your letter here: ");
		wrongCharsLabel = new JLabel ("You already guessed these incorrect characters: ");
		timeLeftLabel = new JLabel ("You have this much time left: ");
		inputWordLabel = new JLabel("Please input the word to guess here: ");
		incorrectWordLabel = new JLabel ("Please input a real word!");
		timeLeft = new JLabel("" + timerCount);
		
		notACharLabel = new JLabel ("This isn't a character. Please try again. ");
		alreadyGuessedCharLabel = new JLabel ("You have already guessed this character. Please try again. ");

		showCharInputDialog = new JButton ("Guess a new character");
		showCharInputDialog.addActionListener(this);

		//Initialize all the fields

		timeLeftField = new JTextField(20);
		timeLeftField.setEditable(false);

		wrongCharsField = new JTextField(30);
		wrongCharsField.setEditable(false);
		wrongCharsField.setText("");

		charInputField = new JTextField(5);
		charInputField.setEditable(true);
		charInputField.addActionListener(this);

		setLayout(new GridBagLayout());
		GridBagConstraints layoutConst = null;
		layoutConst = new GridBagConstraints();

		//show guessing LABEL
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		layoutConst.insets = new Insets(10,10,10,10);
		add(guessLabel, layoutConst);

		//show guessing FIELD
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 1;
		layoutConst.gridy = 1;
		layoutConst.anchor = GridBagConstraints.WEST;
		layoutConst.insets = new Insets(10,0,10,10);
		add(charInputField, layoutConst);

		//show characters already guessed LABEL
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 3;
		layoutConst.insets = new Insets(10,10,10,10);
		add(wrongCharsLabel, layoutConst);

		//show characters already guessed FIELD
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 1;
		layoutConst.gridy = 3;
		layoutConst.insets = new Insets(10,10,10,10);
		add(wrongCharsField, layoutConst);


		//show warning about bad character (keeping for later)
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 2;
		layoutConst.gridy = 2;
		layoutConst.insets = new Insets(10,10,10,10);
		add(notACharLabel, layoutConst);
		hideLabelGUI(notACharLabel);

		//show warning about character already guessed (keeping for later)
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 2;
		layoutConst.gridy = 2;
		layoutConst.insets = new Insets(10,10,10,10);
		add(alreadyGuessedCharLabel, layoutConst);
		hideLabelGUI(alreadyGuessedCharLabel);

		//Add the time LABEL if on HARD MODE
		if (isEasy == false) {

			layoutConst = new GridBagConstraints();
			layoutConst.gridx = 0;
			layoutConst.gridy = 0;
			layoutConst.insets = new Insets(10,10,10,10);
			add(timeLeftLabel, layoutConst);

			layoutConst = new GridBagConstraints();
			layoutConst.gridx = 1;
			layoutConst.gridy = 0;
			layoutConst.insets = new Insets(10,10,10,10);
			add(timeLeftField, layoutConst);
		}

		ActionListener timeListener = new ActionListener() {

			@Override
			public void actionPerformed (ActionEvent event) {

				setTime(getTime() - 1);
				timeLeftField.setText(convertSecondsToMinutesAndSeconds(getTime()));
				if (getTime() == 0 && isVisible() == true && isEasy == false) {
					showFailure();
					setVisible(false);
				}
			}
		};

		Timer timer = new Timer(1000, timeListener);
		timer.start();
		showUnderscores();

		if (isEasy == false) {
			populateJLabelArrayHard();
		}
		else {
			populateJLabelArrayEasy();
		}
		initializeWordGUI();
		try {
			url = new URL("https://www.justhangman.com/images/hangman_0.gif");

			img = ImageIO.read(url);
			Graphics g = img.getGraphics();
			g2d = (Graphics2D) g;
		}
		catch (Exception IOException) {}

		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 5;
		layoutConst.insets = new Insets(10,10,10,10);
		//show Hangman picture
		showPicture(layoutConst, g2d, img,0);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(1200,500);
		setVisible(true);

	}

	//Perform actions based on character input
	public void actionPerformed(ActionEvent event) {

		if (event.getSource() == charInputField) {

			try {
				//don't set the input into a SearchingChar until validated
				String tempInput = charInputField.getText();

				if (tempInput == null) {

					throw new IllegalArgumentException ("program quit");
				}
				else {

					while ( (SearchingChar.validateUserInput(tempInput) != 0)) {
						tempInput = getInputDialog("Please input a real, unused character: ");
					}

					SearchingChar guessingChar = new SearchingChar(tempInput.charAt(0));

					SearchingChar.addToCharList(guessingChar);

				}
				
				wrongCharsField.setText(getIncorrectChars());
				charInputField.setText("");

				GridBagConstraints layoutConst = null;
				layoutConst = new GridBagConstraints();
				layoutConst.gridx = 0;
				layoutConst.gridy = 5;
				layoutConst.insets = new Insets(10,10,10,10);
				showPicture(layoutConst, g2d, img, getDamageCount());			

				if (isDamageFatal()) {			
					int ans = showFailure();
					setDamageCount(0);
					SearchingChar.clearCharList();
					SearchingChar.setCharCount(0);
					clearCorrectLabels();
					setVisible(false);
					if (ans == 0) {
						HangmanProject.main(null);						
					}
				}

				if (isWordComplete()) {
					int ans = showCongratulations();
					setDamageCount(0);
					SearchingChar.clearCharList();
					SearchingChar.setCharCount(0);
					clearCorrectLabels();
					setVisible(false);
					if (ans == 0) {
						HangmanProject.main(null);						
					}
				}				

			}

			catch (Exception IllegalArgumentException) {}
		}

	}

	//Show the Hangman based on how much damage they have
	public void showPicture(GridBagConstraints layoutConst, Graphics2D g2d, BufferedImage img, int damage) {
		try {
			switch (damage) {

			case 0: 

				hangmanImage = new JLabel(new ImageIcon(img));
				add(hangmanImage, layoutConst);


				break;
			case 1: 

				g2d.fill(new Ellipse2D.Float(207,30,100,50));

				hangmanImage = new JLabel(new ImageIcon(img));

				add(hangmanImage, layoutConst);


				break;
			case 2:

				g2d.fill(new Ellipse2D.Float(207,30,100,50));
				g2d.fill(new Rectangle2D.Float(250,75,5,150));

				hangmanImage = new JLabel(new ImageIcon(img));

				add(hangmanImage, layoutConst);


				break;
			case 3:

				g2d.fill(new Ellipse2D.Float(207,30,100,50));
				g2d.fill(new Rectangle2D.Float(250,75,5,150));
				g2d.fill(new Rectangle2D.Float(250,150,50,5));

				hangmanImage = new JLabel(new ImageIcon(img));

				add(hangmanImage, layoutConst);


				break;
			case 4:

				g2d.fill(new Ellipse2D.Float(207,30,100,50));
				g2d.fill(new Rectangle2D.Float(250,75,5,150));
				g2d.fill(new Rectangle2D.Float(250,150,50,5));
				g2d.fill(new Rectangle2D.Float(200,150,50,5));

				hangmanImage = new JLabel(new ImageIcon(img));

				add(hangmanImage, layoutConst);


				break;
			case 5:

				g2d.fill(new Ellipse2D.Float(207,30,100,50));
				g2d.fill(new Rectangle2D.Float(250,75,5,150));
				g2d.fill(new Rectangle2D.Float(250,150,50,5));
				g2d.fill(new Rectangle2D.Float(200,150,50,5));
				g2d.fill(new Rectangle2D.Float(250,220,50,5));

				hangmanImage = new JLabel(new ImageIcon(img));

				add(hangmanImage, layoutConst);

				break;
			case 6:

				g2d.fill(new Ellipse2D.Float(207,30,100,50));
				g2d.fill(new Rectangle2D.Float(250,75,5,150));
				g2d.fill(new Rectangle2D.Float(250,150,50,5));
				g2d.fill(new Rectangle2D.Float(200,150,50,5));
				g2d.fill(new Rectangle2D.Float(250,220,50,5));
				g2d.fill(new Rectangle2D.Float(200,220,50,5));

				hangmanImage = new JLabel(new ImageIcon(img));

				add(hangmanImage, layoutConst);

				break;
			default: 
				break;
			}
		}
		catch (Exception IOException) {}
		
		revalidate();
		repaint();


	}

	public void showUnderscores() {
		String tempStr = "";
		for (int i = 0; i < InputWord.getUserWord().length(); i++) {
			tempStr = "____     ";

			GridBagConstraints layoutConst = null;

			//ADD UNDERSCORES TO GUESS IN GUI IN A SEPERATE METHOD
			layoutConst = new GridBagConstraints();
			layoutConst.gridx = 3 + i;
			layoutConst.gridy = 10;
			layoutConst.anchor = GridBagConstraints.PAGE_END;
			layoutConst.fill = GridBagConstraints.HORIZONTAL;
			underscores = new JLabel (tempStr);
			add(underscores, layoutConst);
		}

	}
	//Declare charCorrectLabels above, this method populates JLabels with characters from the word to guess. 
	public void populateJLabelArrayEasy () {
		//Add labels for all the characters and hide them on start, showing them after they are guessed
		for (int i = 0; i < InputWord.getUserWord().length(); i++) {

			JLabel charCorrectLabelInstance = new JLabel();
			Character c = InputWord.getUserWord().charAt(i);
			charCorrectLabelInstance.setText(c.toString());			
			charCorrectLabels.add(charCorrectLabelInstance);
		}
	}

	//Declare charCorrectLabels above, this method populates JLabels with characters from the word to guess in anagram form. 
	public void populateJLabelArrayHard () {
		//Add labels for all the characters and hide them on start, showing them after they are guessed
		int randStartIndex = 0;
		while (randStartIndex == 0) {
			randStartIndex = (int)(Math.random() * InputWord.getUserWord().length());
		}
		for (int i = randStartIndex; i < InputWord.getUserWord().length(); i++) {

			JLabel charCorrectLabelInstance = new JLabel();
			Character c = InputWord.getUserWord().charAt(i);
			charCorrectLabelInstance.setText(c.toString());			
			charCorrectLabels.add(charCorrectLabelInstance);
		}

		for (int i = 0; i < randStartIndex; i++) {

			JLabel charCorrectLabelInstance = new JLabel();
			Character c = InputWord.getUserWord().charAt(i);
			charCorrectLabelInstance.setText(c.toString());			
			charCorrectLabels.add(charCorrectLabelInstance);
		}
	}

	//Add word to GUI
	public void initializeWordGUI () {

		GridBagConstraints layoutConst = null;

		for (int i = 0; i < InputWord.getUserWord().length(); i++) {
			layoutConst = new GridBagConstraints();
			layoutConst.gridx = 3 + i;
			layoutConst.gridy = 9;
			layoutConst.anchor = GridBagConstraints.PAGE_END;
			layoutConst.fill = GridBagConstraints.HORIZONTAL;
			add(charCorrectLabels.get(i), layoutConst);
			hideLabelGUI(charCorrectLabels.get(i));
		}
	}
	
	//Show the letter when guessed correctly
	public static void showCorrectChar(char c) {
		for (int i = 0; i < charCorrectLabels.size(); i++) {
			if (charCorrectLabels.get(i).getText().charAt(0) == c) {
				showLabelGUI(charCorrectLabels.get(i));
			}
		}
	}

	//Check end game condition
	public static boolean isWordComplete() {
		int count = 0;
		for (int i = 0; i < charCorrectLabels.size(); i++) {
			if (charCorrectLabels.get(i).isVisible()) {
				count++;
			}
		}
		return (count >= InputWord.getUserWord().length());
	}

	//Convert counter into readable format for timer
	public static String convertSecondsToMinutesAndSeconds(int seconds) {
		int min = seconds / 60; 
		int sec = seconds % 60; 
		String str = "Mins: " + min + ", seconds: " + sec;
		return str;
	}

	public static int showFailure() {
		return JOptionPane.showConfirmDialog(null, "Sorry, you lost the game! Would you like to try again? ", "Nate's Hangman Game",0);
		//JOptionPane.showMessageDialog(null, "Sorry, you lost the game!", "Nate's Hangman Game", 1 );
	}

	public static int showCongratulations() {
		return JOptionPane.showConfirmDialog(null, "Congrats, you win the game! Would you like to play again? ", "Nate's Hangman Game",0);

		//JOptionPane.showMessageDialog(null, "Congratulations, you win the game!", "Nate's Hangman Game", 1 );
	}

	public static String getInputDialog(String str) {
		return JOptionPane.showInputDialog(str);
	}

	public static void clearCorrectLabels() {
		charCorrectLabels.clear();
	}


	public static void showLabelGUI(JLabel label) {
		label.setVisible(true);

	}

	public static void hideLabelGUI(JLabel label) {
		label.setVisible(false);
	}

	public static void hideFieldGUI(JTextField field) {
		field.setVisible(false);
	}

	public void setChar(char c) {
		this.userChar = c;		
	}

	public char getChar() {
		return userChar;
	}


	//Retrieve all the incorrect characters guessed so far
	public static String getIncorrectChars() {
		String tempStr = " [ ";
		for (int i = 0; i < SearchingChar.getCharCount(); i++) {
			if (! SearchingChar.getCharList().get(i).isGuessedCharCorrect()) {
				tempStr = tempStr + SearchingChar.getCharList().get(i).getUserChar() + ", ";
			}
		}
		tempStr = tempStr + " ] ";
		return tempStr;
	}

	public static boolean isEasy() {
		return isEasy;
	}

	public static void setEasy(boolean isEasy) {
		HangmanFrame.isEasy = isEasy;
	}

	public static boolean isDamageFatal () {
		return damageCount > 5;
	}

	public static int getDamageCount() {
		return damageCount;
	}

	public static void setDamageCount(int damageCount) {
		HangmanFrame.damageCount = damageCount;
	}

	public static void incrementDamage() {
		damageCount++;
	}

	public static int getTime() {
		return time;
	}

	public static void setTime(int time) {
		HangmanFrame.time = time;
	}

}
