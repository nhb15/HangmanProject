package FinalProject;

import java.util.*;

public class SearchingChar{

	private char userChar;
	//Maintain this somewhere else - no need for SearchingChar - FIX FOR FUTURE PROJECT
	private static ArrayList<SearchingChar> charList = new ArrayList<SearchingChar>();
	private static int charCount = 0;
	private boolean isGuessedCharCorrect; 

	public SearchingChar (char c) {
				
		this.userChar = c;
		
		//All of this shouldn't be in the constructor - leaving it for now to focus on rest of project. Can fix if visiting again. 
		if (isCharInWord(InputWord.getUserWord())) {
			isGuessedCharCorrect = true;
			HangmanFrame.showCorrectChar(c);
						
		}
		else {
			isGuessedCharCorrect = false;
			HangmanFrame.incrementDamage();
			
		}

		charCount++;
	}

	//Make sure the user entered a letter
	//Return 1 if the letter has been used
	//Return 2 if it's not a valid letter
	//Return 3 if it's more than one letter
	//Return 0 if it's valid
	public static int validateUserInput(String tempC) {
		//Check that it's not a number or special character
		
		if (tempC.length() != 1) {
			return 3;
		}
		char c = tempC.charAt(0);
		
		if (Character.isLetter(c)) {
			//check if letter has been used already - turn into method!
			
			for (int i = 0; i < getCharCount(); i++) {

				if (charList.get(i).getUserChar() == c) {

					//That letter has already been used.
					return 1;
				}
			}

			return 0;
		}
		else {
			//That is not a valid letter.
			return 2;
		}
	}
	
	public static void clearCharList() {
		charList.clear();
	}

	public static void addToCharList (SearchingChar sc) {
		charList.add(sc);
	}

	public boolean isCharInWord(String word) {
		if( word.indexOf(this.userChar) >= 0) {
			return true;
		}
		return false; 

	}


	public char getUserChar() {
		return userChar;
	}

	public void setUserChar(char userChar) {
		this.userChar = userChar;
	}


	public static int getCharCount() {
		return charCount;
	}



	public static void setCharCount(int charCount) {
		SearchingChar.charCount = charCount;
	}

	public boolean isGuessedCharCorrect() {
		return isGuessedCharCorrect;
	}



	public void setGuessedCharCorrect(boolean isGuessedCharCorrect) {
		this.isGuessedCharCorrect = isGuessedCharCorrect;
	}


	public static ArrayList<SearchingChar> getCharList() {
		return charList;
	}
	
	public static void setCharList(ArrayList<SearchingChar> charList) {
		SearchingChar.charList = charList;
	}

}
