package FinalProject;

import java.util.*;

import javax.print.DocFlavor.URL;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException; 

public class InputWord {

	private static String userWord; //the actual word being searched against 

	public InputWord() throws IOException{
		
		setUserWord(findUserWord());
		
		//Make sure the word is of decent length (and eliminate some inconsistencies in the dictionary)
		while (getUserWord().length() < 3) {
			setUserWord(findUserWord());
		}
		System.out.println("The word is: " + getUserWord());

	}

	//Returns a random word from the dictionary
	public String findUserWord() throws IOException {
		String fileWord[] = new String[500000]; //the dictionary array


		FileInputStream fileByteStream = null;
		Scanner inFS = null; 
		int count=0;
		
		System.out.println("Opening file");
		//Relative path:
		java.net.URL url = getClass().getResource("words2.txt");
		fileByteStream = new FileInputStream(url.getPath());
		inFS = new Scanner(fileByteStream);

		try {
			while ( true ) {
				fileWord[count] = inFS.nextLine().toLowerCase();
				count++;
			}
		}
		catch (Exception NoSuchElementException){
			System.out.println("Full file read");
		}
		
		//count is the max size - multiplying it by Math.random will give us a random integer for our index
		int index = (int)(Math.random() * count);
		
		fileByteStream.close();
		inFS.close();
		
		return fileWord[index];		

	}

/** COMMENTING THIS OUT IN FAVOR OF SINGLE PLAYER GAMEPLAY
//check that the first input is a word - move this to main for searching method? 
public boolean validateUserInput(String guessWord) throws IOException {
	
	String fileWord[] = new String[500000]; //the dictionary array


	FileInputStream fileByteStream = null;
	Scanner inFS = null; 
	int count=0;
	int dictionaryCount = 1;

	System.out.println("Opening file");
	fileByteStream = new FileInputStream("C:\\Users\\boldt\\eclipse-workspace\\COMP170\\src\\FinalProject\\words2.txt");
	inFS = new Scanner(fileByteStream);

	try {
		while ( true ) {
			fileWord[count] = inFS.nextLine().toLowerCase();
			count++;

		}
	}
	catch (Exception NoSuchElementException){
		System.out.println("Full file read");
		System.out.println("Count is : " + count);
	}

	fileByteStream.close();
	inFS.close();

	int maxSize = count; //The max size of the dictionary array
	count = count / 2;
	String tempWord = fileWord[count]; //then add or subtract count / 4, count / 8, count by 16...
	while ( !isUserWordExact(tempWord, guessWord)) {
		dictionaryCount++;
		if ( isUserWordLater(tempWord, guessWord)) {
			count = count + dictionaryIncrement(dictionaryCount, maxSize);				
			tempWord = fileWord[count];
		}
		else {
			count = count - dictionaryIncrement(dictionaryCount, maxSize);
			tempWord = fileWord[count];
		}
		//If dictionaryCount ever exceeds the max size of the array, that means we haven't found our word.
		if (dictionaryCount > maxSize) {
			return false;
		}

	}
	System.out.println("Successful find: " + fileWord[count]);



	return true;
}
	
	/**COMMENTING THIS OUT IN FAVOR OF SINGLE PLAYER GAMEPLAY
	public boolean isUserWordLater(String dictionaryWord, String guessWord) {

		if ( guessWord.compareTo(dictionaryWord) > 0) {
			return true;
		}
		return false;
	}

	public boolean isUserWordExact(String dictionaryWord, String guessWord) {

		if ( guessWord.compareTo(dictionaryWord) == 0) {
			return true;
		}
		return false;
	}

	public int dictionaryIncrement(int dictionaryCount, int maxSize) {

		//We need the increment to be an integer since it's array indices -
		//We want the increment to be half of the indices between the current value and the max or min value 
		int result = maxSize / ((int)Math.pow(2, dictionaryCount));
		if (result < 1 ) {
			return 1;
		}
		else {
			return result;
		}

	}
	*/

	
	public static String getUserWord() {
		return userWord;
	}


	public static void setUserWord(String userWord) {
		InputWord.userWord = userWord;
	}




}
