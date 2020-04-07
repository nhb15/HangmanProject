package FinalProject;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JButton;

import javax.swing.Timer;


public class Instructions extends JFrame implements ActionListener {
	
	private JLabel instructionText1;
	private JLabel instructionText2;
	
	private JButton acceptInstructionsEasy;
	private JButton acceptInstructionsHard;
	
	
	public Instructions() {
		setTitle("Nate's version of Hangman, Instructions");
		
		instructionText1 = new JLabel ("First, you will select easy or hard mode. "
				+ "Easy mode is similar to classic Hangman. You have 6 incorrect guesses to get the word right!"
				+ " You may only enter new, legit characters.  "
				+ " Hard mode scrambles the word to guess as sort of an anagram, and is timed. You only have 5 minutes to guess! ");
		
		instructionText2 = new JLabel ("DISCLAIMER: The dictionary used for words was downloaded from github and any inappropriate words are not intended. ");
		acceptInstructionsEasy = new JButton ("Easy");
		acceptInstructionsEasy.addActionListener(this);
		
		acceptInstructionsHard = new JButton("Hard");
		acceptInstructionsHard.addActionListener(this);
		
		
		setLayout(new GridBagLayout());
		GridBagConstraints layoutConst = null;
		layoutConst = new GridBagConstraints();
		
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 0;
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.insets = new Insets(10,10,10,10);
		add(instructionText1, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 1;
		layoutConst.fill = GridBagConstraints.HORIZONTAL;
		layoutConst.insets = new Insets(10,10,10,10);
		add(instructionText2, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 2;
		layoutConst.insets = new Insets(10,10,10,10);
		add(acceptInstructionsEasy, layoutConst);
		
		layoutConst = new GridBagConstraints();
		layoutConst.gridx = 0;
		layoutConst.gridy = 3;
		layoutConst.insets = new Insets(10,10,10,10);
		add(acceptInstructionsHard, layoutConst);
		
		
		setVisible(true);
		pack();
						
		
	}
	public void actionPerformed(ActionEvent event) {
		
		if (event.getSource() == acceptInstructionsEasy) {
			HangmanFrame.setEasy(true);
		}
		else if (event.getSource() == acceptInstructionsHard) {
			HangmanFrame.setEasy(false);
		}
		setVisible(false);
				
		HangmanFrame gameFrame = new HangmanFrame();		
	}	

}
