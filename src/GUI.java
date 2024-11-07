package src;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.*;
import javax.swing.*;

public class GUI {
	public static void main(String[] args) {
		
		JFrame mainFrame = new JFrame("Library");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800,500);
        mainFrame.add(createButtonPanel());
        mainFrame.add(createTopPanel());
        mainFrame.add(new JPanel(null));
        
        mainFrame.setVisible(true);
	}
	
	private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton rateButton = new JButton("Rate");
        JButton setToReadButton = new JButton("Set To Read");
        
        buttonPanel.add(addButton);
        buttonPanel.add(rateButton);
        buttonPanel.add(setToReadButton);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        buttonPanel.setLocation(0, 425);
        buttonPanel.setSize(800, 75);
        
        return buttonPanel;
	}
	
	private static JPanel createTopPanel() {
		String[] sortByOptions = {"Title", "Author", "Rating"};
		
        JPanel topPanel = new JPanel();
        JPanel searchButtonPanel = new JPanel();
        JPanel sortByPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        JTextField searchTextField = new JTextField();
        JLabel sortByLabel = new JLabel("Sort By:");
        JComboBox<String> sortBy = new JComboBox<>(sortByOptions);
        
        sortByPanel.add(sortByLabel);
        sortByPanel.add(sortBy);
        
        searchTextField.setPreferredSize(new Dimension( 200, 24 ) );
        
        searchButtonPanel.add(searchTextField);
        searchButtonPanel.add(searchButton);

		topPanel.add(searchButtonPanel);
        topPanel.add(sortByPanel);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        topPanel.setLocation(0, 0);
        topPanel.setSize(800, 50);
        
        return topPanel;
	}
}
