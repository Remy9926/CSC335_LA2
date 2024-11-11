import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

public class GUI {
    private static LibraryModel libraryModel = new LibraryModel();
    private static JPanel bookDisplayPanel;

    public static void main(String[] args) {
        JFrame mainFrame = new JFrame("Library");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        mainFrame.setSize(800, 500);
        mainFrame.setLayout(null); 
        
        JPanel buttonPanel = createButtonPanel();
        buttonPanel.setBounds(0, 425, 800, 75);
        mainFrame.add(buttonPanel);

        JPanel topPanel = createTopPanel();
        topPanel.setBounds(0, 0, 800, 50);
        mainFrame.add(topPanel);
        bookDisplayPanel = new JPanel();
        bookDisplayPanel.setLayout(new BoxLayout(bookDisplayPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(bookDisplayPanel);
        scrollPane.setBounds(0, 50, 800, 375);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Library Books"));
        mainFrame.add(scrollPane);

        mainFrame.setVisible(true);
    }

    private static JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton rateButton = new JButton("Rate");
        JButton setToReadButton = new JButton("Set To Read");
        JButton importButton = new JButton("Import Books");

        buttonPanel.add(addButton);
        buttonPanel.add(rateButton);
        buttonPanel.add(setToReadButton);
        buttonPanel.add(importButton);
        buttonPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        setupImportBooksButton(importButton); 

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

        searchTextField.setPreferredSize(new Dimension(200, 24));
        searchButtonPanel.add(searchTextField);
        searchButtonPanel.add(searchButton);

        topPanel.add(searchButtonPanel);
        topPanel.add(sortByPanel);
        topPanel.setBorder(BorderFactory.createLineBorder(Color.black));

        return topPanel;
    }

    private static void setupImportBooksButton(JButton importButton) {
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
                    JOptionPane.showMessageDialog(null, "file not found.", "not found errorr", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }

    private static void displayBooks(ArrayList<Book> books) {
        bookDisplayPanel.removeAll();
        for (Book book : books) {
            JPanel bookPanel = new JPanel(new GridLayout(1, 4));
            bookPanel.setBorder(BorderFactory.createLineBorder(Color.gray));
            bookPanel.setPreferredSize(new Dimension(750, 30));
            bookPanel.add(new JLabel(book.getTitle()));
            bookPanel.add(new JLabel("Author: " + book.getAuthor()));
            bookPanel.add(new JLabel("Status: " + (book.haveRead() ? "Read" : "Unread")));
            bookPanel.add(new JLabel("Rating: " + book.getRating()));
            bookDisplayPanel.add(bookPanel);
        }
        bookDisplayPanel.revalidate();
        bookDisplayPanel.repaint();
    }
}

