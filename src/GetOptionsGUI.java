package src;
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;


public class GetOptionsGUI extends JFrame 
{
	private static final int WIDTH = 600;
	private static final int HEIGHT = 250;
	
	private JRadioButton animals;
	private JRadioButton harryPotter;
	private JRadioButton halo;
	private JRadioButton borderlands;
	private JRadioButton small;
	private JRadioButton medium;
	private JRadioButton large;
	private JButton createButton;
	
	public static void main(String[] args) 
	{
		GetOptionsGUI myOptions = new GetOptionsGUI();
		myOptions.setVisible(true);
	}
	
	public GetOptionsGUI()
	{
		super("Word Search Options");
		setSize(WIDTH, HEIGHT);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setResizable(false);
		
		// create panels and labels
		Font myFont = new Font("Arial Black", Font.BOLD, 15);
		createButton = new JButton("Create Game");
		ChoiceListener myListener = new ChoiceListener();
		createButton.setFont(myFont);
		createButton.addActionListener(myListener);
		JPanel themePanel = new JPanel(new BorderLayout());
		themePanel.setBackground(Color.DARK_GRAY);
		JPanel themeLabelPanel = new JPanel();
		themeLabelPanel.setBackground(Color.DARK_GRAY);
		JPanel themeChoicePanel = new JPanel();
		themeChoicePanel.setBackground(Color.DARK_GRAY);
		JPanel sizePanel = new JPanel(new BorderLayout());
		sizePanel.setBackground(Color.DARK_GRAY);
		JPanel sizeLabelPanel = new JPanel();
		sizeLabelPanel.setBackground(Color.DARK_GRAY);
		JPanel sizeChoicePanel = new JPanel(); 
		sizeChoicePanel.setBackground(Color.DARK_GRAY);
		JPanel submitPanel = new JPanel();
		submitPanel.setBackground(Color.DARK_GRAY);
		JLabel themeLabel = new JLabel("Choose a Theme");
		themeLabel.setForeground(Color.WHITE);
		themeLabel.setFont(myFont);
		JLabel sizeLabel = new JLabel("Choose a Size");
		sizeLabel.setForeground(Color.WHITE);
		sizeLabel.setFont(myFont);
		
		// add panels to frame
		add(themePanel, BorderLayout.NORTH);
		add(sizePanel, BorderLayout.CENTER);
		add(submitPanel, BorderLayout.SOUTH);
		
		// create theme RadioButtons
		ButtonGroup themeGroup = new ButtonGroup();
		animals = new JRadioButton("Animals");
		animals.setBackground(Color.DARK_GRAY);
		animals.setForeground(Color.WHITE);
		animals.setFont(myFont);
		harryPotter = new JRadioButton("Harry Potter");
		harryPotter.setBackground(Color.DARK_GRAY);
		harryPotter.setForeground(Color.WHITE);
		harryPotter.setFont(myFont);
		halo = new JRadioButton("Halo");
		halo.setBackground(Color.DARK_GRAY);
		halo.setForeground(Color.WHITE);
		halo.setFont(myFont);
		borderlands = new JRadioButton("Borderlands");
		borderlands.setBackground(Color.DARK_GRAY);
		borderlands.setForeground(Color.WHITE);
		borderlands.setFont(myFont);
		themeGroup.add(animals);
		themeGroup.add(harryPotter);
		themeGroup.add(halo);
		themeGroup.add(borderlands);
		
		// create theme RadioButtons
		ButtonGroup sizeGroup = new ButtonGroup();
		small = new JRadioButton("Small");
		small.setBackground(Color.DARK_GRAY);
		small.setForeground(Color.WHITE);
		small.setFont(myFont);
		medium = new JRadioButton("Medium");
		medium.setBackground(Color.DARK_GRAY);
		medium.setForeground(Color.WHITE);
		medium.setFont(myFont);
		large = new JRadioButton("Large");
		large.setBackground(Color.DARK_GRAY);
		large.setForeground(Color.WHITE);
		large.setFont(myFont);
		sizeGroup.add(small);
		sizeGroup.add(medium);
		sizeGroup.add(large);
		
		// add all components to theme panel
		themePanel.add(themeLabelPanel, BorderLayout.NORTH);
		themePanel.add(themeChoicePanel, BorderLayout.CENTER);
		themeLabelPanel.add(themeLabel, JLabel.CENTER);
		themeChoicePanel.add(animals);
		themeChoicePanel.add(harryPotter);
		themeChoicePanel.add(halo);
		themeChoicePanel.add(borderlands);
		
		// add all components to size panel
		sizePanel.add(sizeLabelPanel, BorderLayout.NORTH);
		sizePanel.add(sizeChoicePanel, BorderLayout.CENTER);
		sizeLabelPanel.add(sizeLabel, JLabel.CENTER);
		sizeChoicePanel.add(small);
		sizeChoicePanel.add(medium);
		sizeChoicePanel.add(large);
		
		// add submit button to panel
		submitPanel.add(createButton);
	}
	
	private class ChoiceListener implements ActionListener
	{
		public void actionPerformed(ActionEvent event)
		{
			boolean themeSelected = false;
			boolean sizeSelected = false;
			String filename = "";
			GridSize mySize = GridSize.SMALL;
			
			if (animals.isSelected())
			{
				filename = "resources/animals.txt";
				themeSelected = true;
			}
			else if (harryPotter.isSelected())
			{
				filename= "resources/harryPotter.txt";
				themeSelected = true;
			}
			else if (halo.isSelected())
			{
				filename= "resources/halo.txt";
				themeSelected = true;
			}
			else if (borderlands.isSelected())
			{
				filename = "resources/borderlands.txt";
				themeSelected = true;
			}
			
			
			if (small.isSelected())
			{
				mySize = GridSize.SMALL;
				sizeSelected = true;
			}
			else if (medium.isSelected())
			{
				mySize = GridSize.MEDIUM;
				sizeSelected = true;
			}
			else if (large.isSelected())
			{
				mySize = GridSize.LARGE;
				sizeSelected = true;
			}
			
			if (!themeSelected || !sizeSelected)
			{
				JOptionPane.showMessageDialog(null, "Please select the required options",
						"Options Not Selected", JOptionPane.ERROR_MESSAGE);
			}
			else
			{
				WordSearch myWordSearch = new WordSearch(mySize, filename);
				PlayWordSearch myGame = new PlayWordSearch(myWordSearch);
				myGame.setVisible(true);
				setVisible(false);
				dispose();
			}

		}
	}
}


