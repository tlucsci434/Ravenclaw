import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class WordSearch 
{
	private static String[] wordList;
	private static String[] foundList;
	private static WordGrid myGrid;
	private static int numWordsFound;
	
	public WordSearch(String gridSize, int numWords)
	{
		myGrid = new WordGrid(gridSize);
		wordList = new String[numWords];
		foundList = new String[numWords];
		numWordsFound = 0;
	}
	
	public void readWords(String filename)
	{
		int index = 0;
		
		try (Scanner filescan = new Scanner(new File(filename)))
		{
			while (filescan.hasNext())
			{
				wordList[index] = filescan.next().toUpperCase();
				index++;
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
	
	public void checkWord(int row1, int col1, int row2, int col2)
	{
		String word = myGrid.getWord(row1, col1, row2, col2);
		
		for (int i = 0; i < wordList.length; i++)
		{
			if (wordList[i].equals(word))
			{
				foundList[numWordsFound] = word;
				numWordsFound++;
				System.out.println("You found " + word);
			}
		}
	}
	
	public String getWordList()
	{
		String list = "";
		
		for (int i = 0; i < wordList.length; i++)
			list += wordList[i] + "\n";
		
		return list;
	}
	
	public String getFoundList()
	{
		String list = "";
		
		for (int i = 0; i < foundList.length; i++)
			list += foundList[i] + "\n";
		
		return list;	}
	
	public int getNumWordsLeft()
	{
		return wordList.length - numWordsFound;
	}
	
	public void addToFoundList(String word)
	{
		foundList[numWordsFound] = word;
		numWordsFound++;
	}
	
	public String toString()
	{
		return myGrid.toString();
	}
}
