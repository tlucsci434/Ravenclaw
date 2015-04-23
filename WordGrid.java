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
	private final static int HORIZONTAL = 0;
	private final static int VERTICAL = 1;
	private final static int TOP_FIRST = 0;
	private final static int LEFT_TO_RIGHT_DOWN = 0;

	private char[][] wordGrid;
	private GridSize mySize;
	
	public WordGrid(GridSize size) 
	{
		
		mySize = size;
		
		switch (mySize)
		{
		case SMALL:		wordGrid = new char[ROW_SMALL][COLUMN_SMALL];
						break;
		case MEDIUM:	wordGrid = new char[ROW_MEDIUM][COLUMN_MEDIUM];
						break;
		case LARGE:		wordGrid = new char[ROW_LARGE][COLUMN_LARGE];
						break;
		}
	}
	
	public GridSize getSize()
	{
		return mySize;
	}
	
	public char[][] getGridLetters()
	{
		return wordGrid;
	}
	
	public void placeWord(String word)
	{
		System.out.println(word);
		Random generator = new Random();
		int placement = generator.nextInt(3);
		
		// Place Horizontally
		if (placement == HORIZONTAL)
		{
			placeHorizontal(word);
		}
		// Place Vertical
		else if (placement == VERTICAL)
		{
			placeVertical(word);
		}
		// Place Diagonally
		else
		{
			placeDiagonal(word);
		}
	}
	
	private void placeHorizontal(String word)
	{
		Random generator = new Random();
		char letter;
		int row = 0;
		int col = 0;
		boolean available;
		int i;

		do 
		{
			available = true;
			row = generator.nextInt(wordGrid.length);
			col = generator.nextInt(wordGrid.length - word.length());
			
			for (i = col; i < word.length() + col && available; i++)
				if (wordGrid[row][i] != EMPTY_CHAR)
					available = false;
			
		} while (!available);
			
		int nextLetter = 0;
		for (i = col; i < word.length() + col; i++)
		{	
			letter = word.charAt(nextLetter);
			wordGrid[row][i] = letter;
			nextLetter++;
		}
	}
	
	private void placeVertical(String word)
	{
		Random generator = new Random();
		char letter;
		int row = 0;
		int col = 0;
		int i;
		int upOrDown;
		boolean available;
		

		do 
		{
			available = true;
			row = generator.nextInt(wordGrid.length - word.length());
			col = generator.nextInt(wordGrid.length);
			
			for (i = row; i < word.length() + row && available; i++)
				if (wordGrid[i][col] != EMPTY_CHAR)
					available = false;
			
		} while (!available);
			
		upOrDown = generator.nextInt(2);
		if (upOrDown == TOP_FIRST) // place first letter on top going down
		{
			int nextLetter = 0;
			for (i = row; i < word.length() + row; i++)
			{	
				letter = word.charAt(nextLetter);
				wordGrid[i][col] = letter;
				nextLetter++;
			}
		}
		else		// place first letter on bottom going up
		{
			int nextLetter = 0;
			for (i = row + word.length() - 1; i >= row; i--)
			{	
				letter = word.charAt(nextLetter);
				wordGrid[i][col] = letter;
				nextLetter++;
			}
		}
	}
	
	private void placeDiagonal(String word)
	{
		Random generator = new Random();
		char letter;
		int row = 0;
		int col = 0;
		int rowIndex;
		int colIndex;
		int i;
		int upOrDown;
		int diagonalChoice = generator.nextInt(2);
		boolean available;
		
		if (diagonalChoice == LEFT_TO_RIGHT_DOWN) // Word start up on left side goes down to right side \
		{
			do 
			{
				available = true;
				row = generator.nextInt(wordGrid.length - word.length());
				col = generator.nextInt(wordGrid.length - word.length());
				rowIndex = row;
				colIndex = col;
			
				for (i = 0; i < word.length() && available; i++)
				{
					if (wordGrid[rowIndex][colIndex] != EMPTY_CHAR)
					{
						available = false;
					}
					else
					{
						rowIndex++;
						colIndex++;
					}
				}
				
			} while (!available);
			
			upOrDown = generator.nextInt(2);
			int nextLetter = 0;
			
			if (upOrDown == TOP_FIRST) 		// Place starting upper left to lower right
			{
				rowIndex = row;
				colIndex = col;
				for (i = 0; i < word.length(); i++)
				{
					letter = word.charAt(nextLetter);
					wordGrid[rowIndex][colIndex] = letter;
					nextLetter++;
					rowIndex++;
					colIndex++;
				}
			}
			else				// Place starting in lower right to upper left
			{
				rowIndex = row + word.length() - 1;
				colIndex = col + word.length() - 1;
				for (i = 0; i < word.length(); i++)
				{
					letter = word.charAt(nextLetter);
					wordGrid[rowIndex][colIndex] = letter;
					nextLetter++;
					rowIndex--;
					colIndex--;
				}
			}
		}
		else // Word starts in lower left and goes upper right /
		{
			do 
			{
				available = true;
				row = (word.length() - 1) + generator.nextInt(wordGrid.length - word.length());
				col = generator.nextInt(wordGrid.length - word.length());
				rowIndex = row;
				colIndex = col;
			
				for (i = 0; i < word.length() && available; i++)
				{
					if (wordGrid[rowIndex][colIndex] != EMPTY_CHAR)
					{
						available = false;
					}
					else
					{
						rowIndex--;
						colIndex++;
					}
				}
				
			} while (!available);
			
			upOrDown = generator.nextInt(2);
			int nextLetter = 0;
			
			if (upOrDown == TOP_FIRST) 		// Place starting upper right to lower left
			{
				rowIndex = row - word.length() + 1;
				colIndex = col + word.length() - 1;
				for (i = 0; i < word.length(); i++)
				{
					letter = word.charAt(nextLetter);
					wordGrid[rowIndex][colIndex] = letter;
					nextLetter++;
					rowIndex++;
					colIndex--;
				}
			}
			else				// Place starting in lower left to upper right
			{
				rowIndex = row;
				colIndex = col;
				for (i = 0; i < word.length(); i++)
				{
					letter = word.charAt(nextLetter);
					wordGrid[rowIndex][colIndex] = letter;
					nextLetter++;
					rowIndex--;
					colIndex++;
				}
			}
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
					letter = (char) ('A' + index);
					wordGrid[i][j] = letter;
				}
			}
		}
		
	}
	
	public String getWord(int row1, int col1, int row2, int col2)
	{
		String word = "";
		int i;
		int rowIndex;
		int colIndex;
		
		// Word is horizontal
		if (row1 == row2)
		{
			for (i = col1; i <= col2; i++)
			{
				word += wordGrid[row1][i];
			}
		}
		else if (col1 == col2)	// Word is vertical
		{
			if (row1 < row2)
			{
				for (i = row1; i <= row2; i++)
				{
					word += wordGrid[i][col1];
				}
			}
			else
			{
				for (i = row1; i >= row2; i--)
				{
					word += wordGrid[i][col1];
				}
			}
		}
		// Word is diagonal
		else if (Math.abs(row1 - row2) == Math.abs(col1 - col2))
		{
	
			if (row1 < row2 && col1 > col2) 	// Word starts upper right to lower left
			{
				rowIndex = row1;
				colIndex = col1;
				for (i = row1; i <= row2; i++)
				{
					word += wordGrid[rowIndex][colIndex];
					rowIndex++;
					colIndex--;
				}
			}
			else if (row1 > row2 && col1 < col2)	// Word starts lower left to upper right
			{
				rowIndex = row1;
				colIndex = col1;
				for (i = row1; i >= row2; i--)
				{
					word += wordGrid[rowIndex][colIndex];
					rowIndex--;
					colIndex++;
				}
			}
			else if (row1 < row2 && col1 < col2)	// Word starts upper left to lower right 
			{
				rowIndex = row1;
				colIndex = col1;
				for (i = row1; i <= row2; i++)
				{
					word += wordGrid[rowIndex][colIndex];
					rowIndex++;
					colIndex++;
				}
			}
			else if (row1 > row2 && col1 > col2)	// Word starts lower right to upper left
			{
				rowIndex = row1;
				colIndex = col1;
				for (i = row1; i >= row2; i--)
				{
					word += wordGrid[rowIndex][colIndex];
					rowIndex--;
					colIndex--;
				}
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
