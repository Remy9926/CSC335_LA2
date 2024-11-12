package src;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI implements ActionListener {
	private LibraryModel libraryModel;
    private JPanel bookDisplayPanel;
    private JTextField bookTitle;
    private JTextField bookAuthor;
    private JTextField bookRating;
    private Parser parser;
    
    public GUI(LibraryModel libraryModel, Parser parser) {
    	this.libraryModel = libraryModel;
    	this.parser = parser;
    	setUp();
    }

    private void setUp() {
        JFrame mainFrame = new JFrame("Library");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(1000, 500);
        mainFrame.setLayout(null); 
        
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(0, 425, 1000, 75);
        mainFrame.add(buttonPanel);

        JPanel topPanel = createTopPanel();
        topPanel.setBounds(0, 0, 1000, 50);
        mainFrame.add(topPanel);
        
        this.bookDisplayPanel = new JPanel();
        this.bookDisplayPanel.setLayout(new BoxLayout(this.bookDisplayPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(this.bookDisplayPanel);
        scrollPane.setBounds(0, 50, 1000, 375);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Library Books"));
        mainFrame.add(scrollPane);
        
        mainFrame.setVisible(true);
    }

    private JPanel createButtonPanel() {
    	JTextField bookTitle = new JTextField();
    	JTextField bookAuthor = new JTextField();
    	JTextField bookRating = new JTextField();
    	JLabel bookLabel = new JLabel("Title:");
    	JLabel authorLabel = new JLabel("Author:");
    	JLabel ratingLabel = new JLabel("Rating (1-5): ");
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add Book");
        JButton rateButton = new JButton("Rate Book");
        JButton setToReadButton = new JButton("Set To Read");
        JButton importButton = new JButton("Import Books");

        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.bookRating = bookRating;
        
        bookTitle.setPreferredSize(new Dimension(100, 24));
        bookAuthor.setPreferredSize(new Dimension(100, 24));
        bookRating.setPreferredSize(new Dimension(30, 24));
        
        buttonPanel.add(bookLabel);
        buttonPanel.add(bookTitle);
        buttonPanel.add(authorLabel);
        buttonPanel.add(bookAuthor);
        buttonPanel.add(addButton);
        buttonPanel.add(ratingLabel);
        buttonPanel.add(bookRating);
        buttonPanel.add(rateButton);
        buttonPanel.add(setToReadButton);
        buttonPanel.add(importButton);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        addButton.addActionListener(this);
        addButton.setName("addBook");
        
        rateButton.addActionListener(this);
        rateButton.setName("rate");
        
        setToReadButton.addActionListener(this);
        setToReadButton.setName("setToRead");
        
        setupImportBooksButton(importButton); 

        return buttonPanel;
    }

    private JPanel createTopPanel() {
        String[] sortByOptions = {"Title", "Author", "Rating"};

        JPanel topPanel = new JPanel();
        JPanel searchButtonPanel = new JPanel();
        JPanel sortByPanel = new JPanel();
        JButton searchButton = new JButton("Search");
        JButton suggestButton = new JButton("Suggest Book");
        JButton helpButton = new JButton("Help");
        JTextField searchTextField = new JTextField();
        JLabel sortByLabel = new JLabel("Sort By:");
        JComboBox<String> sortBy = new JComboBox<>(sortByOptions);

        sortByPanel.add(sortByLabel);
        sortByPanel.add(sortBy);

        searchTextField.setPreferredSize(new Dimension(200, 24));
        searchButtonPanel.add(searchTextField);
        searchButtonPanel.add(searchButton);

        suggestButton.addActionListener(this);
        suggestButton.setName("suggestRead");
        
        helpButton.addActionListener(this);
        helpButton.setName("help");
        
        topPanel.add(searchButtonPanel);
        topPanel.add(sortByPanel);
        topPanel.add(suggestButton);
        topPanel.add(helpButton);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black));
        

        return topPanel;
    }

    private void setupImportBooksButton(JButton importButton) {
        importButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Text Files", "txt"));

            int result = fileChooser.showOpenDialog(null);
            if (result == JFileChooser.APPROVE_OPTION) {
            	
                File file = fileChooser.getSelectedFile();
                try (Scanner scanner = new Scanner(file)) {
                    int count = 0;
                    while (scanner.hasNextLine()) {
                        String line = scanner.nextLine();
                        String[] bookData = line.split(";");
                        if (bookData.length ==   2) {
                            String title = bookData[0 ].trim();
                            String author = bookData[1].trim();
                            Book newBook = new Book(title, author);
                            if (libraryModel.addBookToLibrary(newBook)) {
                                count++;
                            }
                        }
                    }
                    
                    JOptionPane.showMessageDialog(null, count + " books imported from your file" + file.getName());
                    displayBooks(libraryModel.getBooks("title"));
                } catch (FileNotFoundException x) {
                    JOptionPane.showMessageDialog(null, "file not found.", "not found error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    public void displayBooks(ArrayList<Book> books) {
        this.bookDisplayPanel.removeAll();
        for (Book book : books) {
            JPanel bookPanel = new JPanel(new GridLayout(1, 4));
            bookPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
            bookPanel.setPreferredSize(new Dimension(950, 30));
            bookPanel.add(new JLabel(book.getTitle()));
            bookPanel.add(new JLabel("Author: " + book.getAuthor()));
            bookPanel.add(new JLabel("Status: " + (book.haveRead() ? "Read" : "Unread")));
            bookPanel.add(new JLabel("Rating: " + book.getRating()));
            this.bookDisplayPanel.add(bookPanel);
        }
        this.bookDisplayPanel.revalidate();
        this.bookDisplayPanel.repaint();
    }

	@Override
	public void actionPerformed(ActionEvent e) {
		boolean validEvent = false;
		String title = this.bookTitle.getText();
		String author = this.bookAuthor.getText();
		String rating = this.bookRating.getText();
		String command = ((JButton) e.getSource()).getName();
		
		if (command.equals("help") || command.equals("suggestRead")) {
			validEvent = true;
		} else if (title.equals("") || author.equals("") || (command.equals("rate") && rating.equals(""))) {
			JOptionPane.showMessageDialog(null, "Fields cannot be empty!");
		}
		
		if (validEvent) {
			try {
				parser.setCommand(((JButton) e.getSource()).getName());
				System.out.println(((JButton) e.getSource()).getName());
				String[] args = {title, author, rating};
				System.out.println(title + " " + author + " " + rating);
				String returnMessage = parser.executeCommandGUI(args);
				JOptionPane.showMessageDialog(null, returnMessage);
				//complete the executeCommandGUI method to implement
				//each functionality but via gui
			} catch (NullCommandException exception) {
				System.out.println(exception);
			}
		}
	}
}

