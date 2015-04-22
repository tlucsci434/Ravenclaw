import java.util.Scanner;


public class TestMain
{

	public static void main(String[] args)
	{
		Scanner scanner = new Scanner(System.in);
		int row1;
		int col1;
		int row2;
		int col2;
		int numWords = 5;
		String filename = "testWords.txt";
		
		WordSearch myGame = new WordSearch("medium", numWords);
		myGame.readWords(filename);
		
		do
		{
			System.out.println("Words To Find:");
			System.out.println(myGame.getWordList());
			myGame.placeWords();
			System.out.println(myGame + "\n\n");
		
			System.out.println("Enter the row or first letter: ");
			row1 = scanner.nextInt();
			
			System.out.println("Enter the col or first letter: ");
			col1 = scanner.nextInt();
	
			System.out.println("Enter the row or last letter: ");
			row2 = scanner.nextInt();
			
			System.out.println("Enter the col or last letter: ");
			col2 = scanner.nextInt();
			
			myGame.checkWord(row1, col1, row2, col2);
			
		} while (myGame.getNumWordsLeft() > 0);
	}

}
