import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class WordSearch 
{
	private static final int SMALL_NUM_WORDS = 5;
	private static final int MED_NUM_WORDS = 7;
	private static final int LARGE_NUM_WORDS = 10;
	
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
		this.filename = filename;
		numWordsFound = 0;
	}
	
	public GridSize getSize()
	{
		return myGrid.getSize();
	}
	
	public void readWords()
	{
		
		try (Scanner filescan = new Scanner(new File(filename)))
		{
			for (int i = 0; i < numWords && filescan.hasNext(); i++)
			{
				wordList[i] = filescan.next().toUpperCase();
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
		String word = myGrid.getWord(row1, col1, row2, col2);
		
		if (wordListContains(word) && !foundListContains(word))
			addToFoundList(word);
		else
			word = null;
		
		return word;
	}
	
	public String[] getWordList()
	{
		return wordList;
	}
	
	public String[] getFoundList()
	{
		return foundList;
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
