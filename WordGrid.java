// Gavin Harrison, Joseph Hagen, Robert Pitts

import java.util.Random;

public class WordGrid 
{
	private final static int ROW_SMALL = 10;
	private final static int COLUMN_SMALL = 10;
	private final static int ROW_MEDIUM = 12;
	private final static int COLUMN_MEDIUM = 12;
	private final static int ROW_LARGE = 16;
	private final static int COLUMN_LARGE = 16;
	private final static char EMPTY_CHAR = '\u0000';

	private static char[][] wordGrid;
	private final static char[] ALPHABET = {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O',
		'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	public WordGrid(String size) 
	{	
		System.out.println(size);
		
		if (size.equals("small"))
		{
			wordGrid = new char[ROW_SMALL][COLUMN_SMALL];
		}
		else if (size.equals("medium"))
		{
			wordGrid = new char[ROW_MEDIUM][COLUMN_MEDIUM];
		}
		else 
		{
			wordGrid = new char[ROW_LARGE][COLUMN_LARGE];
		}
	}
	
	public void placeWord(String word)
	{
		Random generator = new Random();
		char letter;
		int row;
		
		do
		{
			row = generator.nextInt(wordGrid.length - 1);
		} while (wordGrid[row][0] != EMPTY_CHAR);
		
		for (int i = 0; i < word.length(); i++)
		{	
			letter = word.charAt(i);
			wordGrid[row][i] = letter;
		}
	}
	
	public void fillGrid()
	{
		Random generator = new Random();
		int index;
		char letter;
		
		for (int i = 0; i < wordGrid.length; i++)
		{
			for (int j = 0; j < wordGrid[i].length; j++)
			{
				if (wordGrid[i][j] == EMPTY_CHAR)
				{
					index = generator.nextInt(26);
					letter = ALPHABET[index];
					
					wordGrid[i][j] = letter;
				}
			}
		}
		
	}
	
	public String getWord(int row1, int col1, int row2, int col2)
	{
		String word = "";
		
		if (row1 == row2)
		{
			for (int i = col1; i <= col2; i++)
			{
				word += wordGrid[row1][i];
			}
		}
		
		return word;
	}
	
	public String toString()
	{
		String result = "";
		
		for (int i = 0; i < wordGrid.length; i++)
		{
			for (int j = 0; j < wordGrid[0].length; j++)
			{
				result += wordGrid[i][j] + " ";
			}
			result += "\n";
		}
		
		return result;
	}

}
