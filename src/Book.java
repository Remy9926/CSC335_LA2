package src;

// File: Book.java
// Author(s): Mandy Jiang (mandyjiang), Ethan Huang (ehuang68)
// Purpose: Book object with book info that will be in library 


/*
* Creates a new Book with a title and author.
* The rating is set to unrated and read is set to false.
* 
* Encapsulation: private instance variables, public setters/getters
*
* @param title  The title of the book.
* @param author The author of the book.
*/

public class Book {

    private String title;
    private String author;
    private int rating;
    private boolean haveRead;

    public Book(String title, String author) {

        this.title = title;
        this.author = author;
        this.rating = 0;  // 0 means unrated default
        this.haveRead= false;
    }

    // copy constructor for encapsulation
    public Book(Book other) {
        this.title = other.title;
        this.author = other.author;
        this.haveRead = other.haveRead;
        this.rating = other.rating;
    }
    
    //gets the title of the book.
    // @return  title of the book.
    public String getTitle() {
        return title;
    }



    // gets the rating of the book.
    //@return  rating of the book,between 1 and 5, or 0 if unrated.
    public boolean haveRead() {
        return haveRead ;
    }


    //gets the rating of the book.
    //@return  rating of the book.
    public int getRating() {
        return rating;
    }


    
     //gets the author of the book.
     //@return  author of the book.
    public String getAuthor() {
        return author;
    }
    

    
     //gets the read status of the book.
     //@return  read status of the book.
    public void setToRead() {
        this.haveRead = true;  
    }

    
    //sets the rating for the book
    //@return  what rating is of the book.
    public void setRating(int rating) {
        if (rating > 0 && rating < 6) {
            this.rating = rating;
        } else {
            System.out.println("Rating must be between 1 and 5");
        }
    }

    
    // tostring method for string rep of book
    //@return  string representation of book
    public String toString() {
        return title + " by " + author + '\n'+ " - Rating: " +
        		rating + " |" + (haveRead ? "Read" : "Unread") + "|";
    }
}