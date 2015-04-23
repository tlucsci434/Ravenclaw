import java.util.Scanner;


public class TestMain
{

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		GridSize mySize = GridSize.MEDIUM;
		int row1;
		int col1;
		int row2;
		int col2;
		String word;
		int numWords = 6;
		String filename = "testWords.txt";
		
		WordSearch myGame = new WordSearch(mySize, numWords);
		myGame.readWords(filename);
		myGame.placeWords();
		
		do
		{
			System.out.println(myGame);
			System.out.println("Enter the row of first letter: ");
			row1 = scanner.nextInt();
			
			System.out.println("Enter the col of first letter: ");
			col1 = scanner.nextInt();
	
			System.out.println("Enter the row of last letter: ");
			row2 = scanner.nextInt();
			
			System.out.println("Enter the col of last letter: ");
			col2 = scanner.nextInt();
			
			word = myGame.checkWord(row1, col1, row2, col2);
			System.out.println("You found " + word);
			
		} while (myGame.getNumWordsLeft() > 0);
	}

}
