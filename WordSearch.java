import java.io.File;
import java.util.Scanner;
import java.io.FileNotFoundException;

public class WordSearch 
{
	private String[] wordList;
	private String[] foundList;
	private WordGrid myGrid;
	private int numWordsFound;
	
	public WordSearch(GridSize size, int numWords)
	{
		myGrid = new WordGrid(size);
		wordList = new String[numWords];
		foundList = new String[numWords];
		numWordsFound = 0;
	}
	
	public void readWords(String filename)
	{
		
		try (Scanner filescan = new Scanner(new File(filename)))
		{
			for (int i = 0; filescan.hasNext(); i++)
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
		
		System.out.println(myGrid);
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
		String result = "Words To Find: \n" + getWordList();
		
		result += "Words Found: \n";
				
		for (int i = 0; i < foundList.length; i++)
			if (foundList[i] != null)
				result += foundList[i] + "\n";
		
		result += "\n" + myGrid.toString();
		
		return result;
	}
}
