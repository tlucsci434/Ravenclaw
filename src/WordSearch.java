package src;
import java.io.File;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class WordSearch 
{
	private static final int SMALL_NUM_WORDS = 5;
	private static final int MED_NUM_WORDS = 7;
	private static final int LARGE_NUM_WORDS = 10;
	private static final int WORDS_PER_FILE = 20;
	
	private String[] wordList;
	private String[] foundList;
	private WordGrid myGrid;
	private int numWordsFound;
	private String filename;
	private int numWords;
	
	public WordSearch(GridSize size, String filename)
	{
		myGrid = new WordGrid(size);
		
		if (size == GridSize.SMALL)
			numWords = SMALL_NUM_WORDS;
		else if (size == GridSize.MEDIUM)
			numWords = MED_NUM_WORDS;
		else
			numWords = LARGE_NUM_WORDS;
		
		wordList = new String[numWords];
		foundList = new String[numWords];
		
		for (int i = 0; i < numWords; i++)
		{
			wordList[i] = "";
			foundList[i] = "";
		}
		
		this.filename = filename;
		numWordsFound = 0;
	}
	
	public GridSize getSize()
	{
		return myGrid.getSize();
	}
	
	public void readWords()
	{
		Random generator = new Random();
		String[] tempWords = new String[WORDS_PER_FILE];
		int wordCount = 0;
		boolean wordInList;
		int index;
		
		try (Scanner filescan = new Scanner(new File(filename)))
		{
			for (int i = 0; i < WORDS_PER_FILE && filescan.hasNext(); i++)
			{
				tempWords[i] = filescan.next().toUpperCase();
			}
			
			// pick random words from ones read from the file
			while (wordCount < numWords)
			{
				index = generator.nextInt(WORDS_PER_FILE);
				
				// check to see if random index has already been used
				wordInList = false;
				
				for (int j = 0; j < wordList.length && !wordInList; j++)
				{
					// if random word has not already been added
					if (wordList[j].equals(tempWords[index]))
					{
						wordInList = true;
					}
				}
				
				if (!wordInList)
				{
					wordList[wordCount] = tempWords[index];
					wordCount++;
				}
			}
		}
		catch (FileNotFoundException e)
		{
			System.err.println("Could not open the file " + filename + ".");
		}
	}
	
	public void placeWords()
	{
		for (int i = 0; i < wordList.length; i++)
		{
			myGrid.placeWord(wordList[i]);
		}
		
		myGrid.fillGrid();
	}
	
	public String checkWord(int row1, int col1, int row2, int col2)
	{
		String forwardWord = myGrid.getWord(row1, col1, row2, col2);
		String backwardWord = myGrid.getWord(row2, col2, row1, col1);
		String word;
		
		if (wordListContains(forwardWord) && !foundListContains(forwardWord))
		{
			word = forwardWord;
			addToFoundList(word);
		}
		else if (wordListContains(backwardWord) && !foundListContains(backwardWord))
		{	
			word = backwardWord;
			addToFoundList(word);
		}
		else
			word = null;
		
		return word;
	}
	
	public String[] getWordList()
	{
		String[] copyWordList = Arrays.copyOf(wordList, numWords);
		return copyWordList;
	}
	
	public String[] getFoundList()
	{
		String[] copyFoundList = Arrays.copyOf(foundList, numWords);
		return copyFoundList;
	} 
	
	public int getNumWordsLeft()
	{
		return wordList.length - numWordsFound;
	}
	
	public char[][] getGridLetters()
	{
		return myGrid.getGridLetters();
	}
	
	private void addToFoundList(String word)
	{
		foundList[numWordsFound] = word;
		numWordsFound++;
	}
	
	private boolean foundListContains(String word)
	{
		boolean found = false;
		
		for (int i = 0; i < numWordsFound && !found; i++)
			if (foundList[i].equals(word))
				found = true;
		
		return found;
	}
	
	private boolean wordListContains(String word)
	{
		boolean found = false;
		
		for (int i = 0; i < wordList.length && !found; i++)
			if (wordList[i].equals(word))
				found = true;
		
		return found;
	}
	
	public String toString()
	{
		String result = "Words To Find: \n";
		
		for (int i = 0; i < wordList.length; i++)
			result += wordList[i] + "\n";
		
		result += "Words Found: \n";
				
		for (int i = 0; i < foundList.length; i++)
			if (foundList[i] != null)
				result += foundList[i] + "\n";
		
		result += "\n" + myGrid.toString();
		
		return result;
	}
}
