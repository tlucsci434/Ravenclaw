import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.*;

public class PlayWordSearch extends JFrame 
{
	private final static int FRAME_WIDTH = 1500;
	private final static int FRAME_HEIGHT = 1000;
	private final static int PANEL_WIDTH = 300;
	private final static int PANEL_HEIGHT = 300;
	private final static int INSTRUCTION_HEIGHT = 100;
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
	private String[] gridLetters;
	
	public PlayWordSearch(WordSearch myWordSearch)
	{
		super("Word Search");
		setSize(FRAME_WIDTH, FRAME_HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		myGame = myWordSearch;
		myGame.readWords();
		myGame.placeWords();
		Font myFont = new Font("Arial Black", Font.BOLD, 15);
		
		
		// determine how large to make the button array
		GridSize size = myGame.getSize();
		int numCols = 0;
		int numRows = 0;
		int numWords = 0;
		
		switch (size)
		{
		case SMALL:		numRows = ROW_SMALL;
						numCols = COLUMN_SMALL;
						numWords = SMALL_NUM_WORDS;
						break;
		case MEDIUM:	numRows = ROW_MEDIUM;
						numCols = COLUMN_MEDIUM;
						numWords = MED_NUM_WORDS;
						break;
		case LARGE:		numRows = ROW_LARGE;
						numCols = COLUMN_LARGE;
						numWords = LARGE_NUM_WORDS;
		}
		
		// get grid letters and word lists
		wordList = myGame.getWordList();
		foundList = myGame.getFoundList();
		char[][] letters = myGame.getGridLetters();
		
		// create panels
		GridLayout myGridLayout = new GridLayout(numWords + 1, 1);
		JPanel gridPanel = new JPanel(new GridLayout(numRows, numCols));
		JPanel infoPanel = new JPanel(new BorderLayout());
		infoPanel.setBackground(Color.DARK_GRAY);
		JPanel instructionPanel = new JPanel();
		instructionPanel.setBackground(Color.DARK_GRAY);
		instructionPanel.setPreferredSize(new Dimension(PANEL_WIDTH, INSTRUCTION_HEIGHT));
		JPanel wordListPanel = new JPanel(myGridLayout);
		wordListPanel.setBackground(Color.DARK_GRAY);
		wordListPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		JPanel foundListPanel = new JPanel(myGridLayout);
		foundListPanel.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
		foundListPanel.setBackground(Color.DARK_GRAY);
		add(gridPanel, BorderLayout.CENTER);
		add(infoPanel, BorderLayout.EAST);
		
		// create instruction button
		JButton instructionButton = new JButton("Instructions");
		instructionButton.setFont(myFont);
		instructionButton.addActionListener(new InstructionListener());
		
		// create word labels
		JLabel wordBankLabel = new JLabel("Words To Find:", JLabel.CENTER);
		wordBankLabel.setForeground(Color.WHITE);
		wordBankLabel.setFont(myFont);
		wordListPanel.add(wordBankLabel);
		JLabel foundBankLabel = new JLabel("Words Found:", JLabel.CENTER);
		foundBankLabel.setForeground(Color.WHITE);
		foundBankLabel.setFont(myFont);
		foundListPanel.add(foundBankLabel);
		wordLabels = new JLabel[wordList.length];
		foundLabels = new JLabel[foundList.length];
		for (int i = 0; i < wordLabels.length; i++)
		{
			wordLabels[i] = new JLabel(wordList[i], JLabel.CENTER);
			wordLabels[i].setForeground(Color.WHITE);
			wordLabels[i].setFont(myFont);
			wordListPanel.add(wordLabels[i]);
			foundLabels[i] = new JLabel(foundList[i], JLabel.CENTER);
			foundLabels[i].setForeground(Color.WHITE);
			foundLabels[i].setFont(myFont);
			foundListPanel.add(foundLabels[i]);
		}
		
		// add panels to the infoPanel
		infoPanel.add(instructionPanel, BorderLayout.NORTH);
		infoPanel.add(wordListPanel, BorderLayout.CENTER);
		infoPanel.add(foundListPanel, BorderLayout.SOUTH);
		
		//create the button array
		buttons = new JButton[numRows][numCols];
		for (int i = 0; i < numRows; i++)
		{
			for (int j = 0; j < numCols; j++)
			{
				buttons[i][j] = new JButton(String.valueOf(letters[i][j]));
				buttons[i][j].setBackground(Color.BLACK);
				buttons[i][j].setForeground(Color.WHITE);
				buttons[i][j].setFont(myFont);
				gridPanel.add(buttons[i][j]);
			}
		}
		
		// add button to instruction panel
		instructionPanel.add(instructionButton);
	}
	
	private class InstructionListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			String instructions = "Welcome to Word Search!\n";
			instructions += "\nClick the first letter and the last letter of the word that"
					+ "you would like to select.";
			
			Font myFont = new Font("Arial Black", Font.BOLD, 15);
			
			javax.swing.UIManager.put("OptionPane.messageFont", new FontUIResource(myFont));
			JOptionPane.showMessageDialog(null, instructions, "Instructions", 
					JOptionPane.INFORMATION_MESSAGE);
		}
	}
}
