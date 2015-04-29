package src;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;
import javax.swing.plaf.FontUIResource;

import java.awt.*;
import java.awt.event.*;

public class PlayWordSearch extends JFrame 
{
	private final static int FRAME_WIDTH = 1500;
	private final static int FRAME_HEIGHT = 1000;
	private final static int PANEL_WIDTH = 300;
	private final static int PANEL_HEIGHT = 300;
	private final static int INSTRUCTION_HEIGHT = 150;
	private final static int ROW_SMALL = 10;
	private final static int COLUMN_SMALL = 10;
	private final static int ROW_MEDIUM = 12;
	private final static int COLUMN_MEDIUM = 12;
	private final static int ROW_LARGE = 16;
	private final static int COLUMN_LARGE = 16;
	private static final int SMALL_NUM_WORDS = 5;
	private static final int MED_NUM_WORDS = 7;
	private static final int LARGE_NUM_WORDS = 10;
	
	private WordSearch myGame;
	private JButton[][] buttons;
	private JLabel[] wordLabels;
	private JLabel[] foundLabels;
	private String[] wordList;
	private String[] foundList;
	private int numClicks;
	private JButton firstButtonClicked;
	private JButton secondButtonClicked;
	private WordListener myWordListener;
	
	public PlayWordSearch(WordSearch myWordSearch)
	{
		super("Word Search");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		myGame = myWordSearch;
		myGame.readWords();
		myGame.placeWords();
		Font labelFont = new Font("Arial Black", Font.BOLD, 20);
        Border panelBorder = new LineBorder(Color.BLACK, 5);
		
		// set variables based on grid size selected
		GridSize size = myGame.getSize();
		Font gridFont;
		numClicks = 0;
		int numCols = 0;
		int numRows = 0;
		int numWords = 0;
		
		switch (size)
		{
		case SMALL:		numRows = ROW_SMALL;
						numCols = COLUMN_SMALL;
						numWords = SMALL_NUM_WORDS;
						gridFont = new Font("Arial Black", Font.BOLD, 45);
						break;
		case MEDIUM:	numRows = ROW_MEDIUM;
						numCols = COLUMN_MEDIUM;
						numWords = MED_NUM_WORDS;
						gridFont = new Font("Arial Black", Font.BOLD, 40);
						break;
		case LARGE:		numRows = ROW_LARGE;
						numCols = COLUMN_LARGE;
						numWords = LARGE_NUM_WORDS;
						gridFont = new Font("Arial Black", Font.BOLD, 35);
						break;
		default:		gridFont = new Font("Arial Black", Font.BOLD, 35);
		}
		
		// get grid letters and word lists
		wordList = myGame.getWordList();
		foundList = myGame.getFoundList();
		char[][] letters = myGame.getGridLetters();
		
		// create basic panels for grid and info sections
		GridLayout myGridLayout = new GridLayout(numWords + 1, 1);
		JPanel gridPanel = new JPanel(new GridLayout(numRows, numCols));
		JPanel infoPanel = new JPanel(new BorderLayout());
		infoPanel.setBackground(Color.DARK_GRAY);
		
		// create panel for instructions
		JPanel instructionPanel = new JPanel(new BorderLayout());
		instructionPanel.setBackground(Color.DARK_GRAY);
		instructionPanel.setBorder(panelBorder);
		instructionPanel.setPreferredSize(new Dimension(PANEL_WIDTH, INSTRUCTION_HEIGHT));
		
		// create panel for word list
		JPanel wordListPanel = new JPanel(myGridLayout);
		wordListPanel.setBackground(Color.DARK_GRAY);
		wordListPanel.setBorder(panelBorder);
		wordListPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		
		// create panel for found list
		JPanel foundListPanel = new JPanel(myGridLayout);
		foundListPanel.setBorder(panelBorder);
		foundListPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		foundListPanel.setBackground(Color.DARK_GRAY);
		
		add(gridPanel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.EAST);
		addWindowListener(new WindowOpenListener());
		
		// create instruction button
		JButton instructionButton = new JButton("Instructions");
		instructionButton.setFont(labelFont);
		instructionButton.addActionListener(new InstructionListener());
		
		// create new game button
		JButton newGameButton = new JButton("New Game");
		newGameButton.setFont(labelFont);
		newGameButton.addActionListener(new NewGameListener());

		// create word labels
		JLabel wordBankLabel = new JLabel("Words To Find:", JLabel.CENTER);
		wordBankLabel.setForeground(Color.WHITE);
		wordBankLabel.setFont(labelFont);
		wordListPanel.add(wordBankLabel);
		JLabel foundBankLabel = new JLabel("Words Found:", JLabel.CENTER);
		foundBankLabel.setForeground(Color.WHITE);
		foundBankLabel.setFont(labelFont);
		foundListPanel.add(foundBankLabel);
		wordLabels = new JLabel[wordList.length];
		foundLabels = new JLabel[foundList.length];
		for (int i = 0; i < wordLabels.length; i++)
		{
			wordLabels[i] = new JLabel(wordList[i], JLabel.CENTER);
			wordLabels[i].setForeground(Color.WHITE);
			wordLabels[i].setFont(labelFont);
			wordListPanel.add(wordLabels[i]);
			foundLabels[i] = new JLabel(foundList[i], JLabel.CENTER);
			foundLabels[i].setForeground(Color.WHITE);
			foundLabels[i].setFont(labelFont);
			foundListPanel.add(foundLabels[i]);
		}
		
		// add panels to the infoPanel
		infoPanel.add(instructionPanel, BorderLayout.NORTH);
		infoPanel.add(wordListPanel, BorderLayout.CENTER);
		infoPanel.add(foundListPanel, BorderLayout.SOUTH);
		
		//create the button array
		buttons = new JButton[numRows][numCols];
		myWordListener = new WordListener();
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
			{
				buttons[i][j] = new JButton(String.valueOf(letters[i][j]));
				buttons[i][j].addActionListener(myWordListener);
				buttons[i][j].setBackground(Color.BLACK);
				buttons[i][j].setForeground(Color.WHITE);
				buttons[i][j].setFont(gridFont);
				gridPanel.add(buttons[i][j]);
			}
		}
		
		// add button to instruction panel
		instructionPanel.add(instructionButton, BorderLayout.NORTH);
		instructionPanel.add(newGameButton, BorderLayout.SOUTH);
		
	}
	
	private void showInstructions()
	{
		String instructions = "Welcome to Word Search!\n";
		instructions += "\nClick the first letter and the last letter of the word that"
				+ " you would like to select.\n";
		instructions += "Press the New Game button at any time to start a new game.";
		Font myFont = new Font("Arial Black", Font.BOLD, 15);
		javax.swing.UIManager.put("OptionPane.messageFont", new FontUIResource(myFont));
		JOptionPane.showMessageDialog(null, instructions, "Instructions", 
				JOptionPane.INFORMATION_MESSAGE);
	}
	
	private class WindowOpenListener extends WindowAdapter
	{
		public void windowOpened(WindowEvent w)
		{
			showInstructions();
		}
		
		public void windowClosing(WindowEvent w)
		{
			String objButtons[] = {"Yes", "No"};
			String quitGameMess = "Are you sure you want to quit the game?";
			
			int answer = JOptionPane.showOptionDialog(null, quitGameMess, "Quit Game",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
					objButtons, objButtons[1]);
			if (answer == 0)
				System.exit(0);
		}
	}
	
	private class InstructionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			showInstructions();
		}
	}
	
	private class NewGameListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String objButtons[] = {"Yes", "No"};
			String newGameMess = "Are you sure you want to start a new game?";
			
			int answer = JOptionPane.showOptionDialog(null, newGameMess, "Start New Game",
					JOptionPane.DEFAULT_OPTION, JOptionPane.WARNING_MESSAGE, null, 
					objButtons, objButtons[1]);
			if (answer == 0)
			{
				GetOptionsGUI myOptions = new GetOptionsGUI();
				myOptions.setVisible(true);
				dispose();
			}
		}
	}
	
	private class WordListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			numClicks++;
			int firstButtonRow = 0;
			int firstButtonCol = 0;
			int secondButtonRow = 0;
			int secondButtonCol = 0;
			Font myFont = new Font("Arial Black", Font.BOLD, 15);
			javax.swing.UIManager.put("OptionPane.messageFont", new FontUIResource(myFont));
			
			// if it is the first click by user
			if (numClicks % 2 == 1)
			{
				firstButtonClicked = (JButton) event.getSource();
				firstButtonClicked.setForeground(Color.YELLOW);
			}
			// if it is the users second click
			else if (numClicks % 2 == 0)
			{
				secondButtonClicked = (JButton) event.getSource();
				secondButtonClicked.setForeground(Color.YELLOW);
				
				for (int i = 0; i < buttons.length; i++)
				{
					for (int j = 0; j < buttons[i].length; j++)
					{
						// get index of both buttons clicked
						if (buttons[i][j] == firstButtonClicked)
						{
							firstButtonRow = i;
							firstButtonCol = j;
						}
						else if (buttons[i][j] == secondButtonClicked)
						{
							secondButtonRow = i;
							secondButtonCol = j;
						}
					}
				}
				
				// determine if the word selected is a valid word
				String selectedWord = myGame.checkWord(firstButtonRow, firstButtonCol, secondButtonRow,
						secondButtonCol);
				
				if (selectedWord == null)
				{
					// reset color of letters selected
					firstButtonClicked.setForeground(Color.WHITE);
					secondButtonClicked.setForeground(Color.WHITE);
					
					// display error message
					String notWordMess = "The word you selected is not a valid word.\n";
					notWordMess += "Please try again.";
					JOptionPane.showMessageDialog(null, notWordMess, "Not A Valid Word", 
							JOptionPane.ERROR_MESSAGE);
				}
				else
				{

					// highlight appropriate letters of word found
					
					// if word selected is horizontal
					if (firstButtonRow == secondButtonRow)
					{
						// if word is has forward placement in row
						if (firstButtonCol < secondButtonCol)
						{
							for (int i = firstButtonCol; i <= secondButtonCol; i++)
							{
								buttons[firstButtonRow][i].setForeground(Color.YELLOW);
							}
						}
						// if word has backward placement in row
						else
						{
							for (int i = secondButtonCol; i <= firstButtonCol; i++)
							{
								buttons[secondButtonRow][i].setForeground(Color.YELLOW);
							}
						}
					}
					// if word selected is vertical
					else if (firstButtonCol == secondButtonCol)
					{
						// selected word starting at top going to bottom
						if (firstButtonRow < secondButtonRow)
						{
							for (int i = firstButtonRow; i <= secondButtonRow; i++)
							{
								buttons[i][firstButtonCol].setForeground(Color.YELLOW);
							}
						}
						// selected word is starting at bottom going to top
						else
						{
							for (int i = secondButtonRow; i <= firstButtonRow; i++)
							{
								buttons[i][secondButtonCol].setForeground(Color.YELLOW);
							}
						}
					}
					// if selected word is diagonal
					else if (Math.abs(firstButtonRow - secondButtonRow) == Math.abs(
							firstButtonCol - secondButtonCol))
					{
						int rowIndex = firstButtonRow;
						int colIndex = firstButtonCol;
						
						// from top right to bottom left /
						if (firstButtonRow < secondButtonRow && firstButtonCol > secondButtonCol)
						{
							for (int i = firstButtonRow; i <= secondButtonRow; i++)
							{
								buttons[rowIndex][colIndex].setForeground(Color.YELLOW);
								rowIndex++;
								colIndex--;
							}
						}
						// from bottom left to top right /
						else if (firstButtonRow > secondButtonRow && firstButtonCol < secondButtonCol)
						{
							for (int i = firstButtonRow; i >= secondButtonRow; i--)
							{
								buttons[rowIndex][colIndex].setForeground(Color.YELLOW);
								rowIndex--;
								colIndex++;
							}
						}
						// from top left to bottom right \
						else if (firstButtonRow < secondButtonRow && firstButtonCol < secondButtonCol)
						{
							for (int i = firstButtonRow; i <= secondButtonRow; i++)
							{
								buttons[rowIndex][colIndex].setForeground(Color.YELLOW);
								rowIndex++;
								colIndex++;
							}
						}
						// from bottom right to top left \
						else if (firstButtonRow > secondButtonRow && firstButtonCol > secondButtonCol)
						{
							for (int i = firstButtonRow; i >= secondButtonRow; i--)
							{
								buttons[rowIndex][colIndex].setForeground(Color.YELLOW);
								rowIndex--;
								colIndex--;
							}
						}
					}
					
					// print message with word found
					String foundWordMess = "Congratulations!\n";
					foundWordMess += "You have found " + selectedWord;
					JOptionPane.showMessageDialog(null, foundWordMess, "Word Found", 
							JOptionPane.INFORMATION_MESSAGE);
					
					// update word labels
					for (int i = 0; i < wordLabels.length;i++)
					{
						if (wordList[i].equals(selectedWord))
						{
							wordLabels[i].setText("");
							foundLabels[i].setText(selectedWord);
						}
					}
				}
				
				// if all words found
				if (myGame.getNumWordsLeft() == 0)
				{
					for (int i = 0; i < buttons.length; i++)
					{
						for (int j = 0; j < buttons[i].length; j++)
						{
							buttons[i][j].removeActionListener(myWordListener);
						}
					}
					
					// display game completion message
					String gameCompletedMess = "Congratulations! You have found all the words!\n";
					gameCompletedMess += "Thanks for playing!\n";
					gameCompletedMess += "Click the New Game button to play again.";
					
					JOptionPane.showMessageDialog(null, gameCompletedMess, "Game Completed", 
							JOptionPane.INFORMATION_MESSAGE);
					
				}
			}
		}
	}
}
