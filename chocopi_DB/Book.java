package org.example.oop_btl14;
import java.util.ArrayList;

public class Book {
    private boolean state;
    private date borrowDate;
    private date returnDate;
    private int book_id;
    private int publishYear;
    private int quantity;
    private int quantityOfBorrowed;
    private String cover;
    private String genre;
    private String name;
    private String description;
    private String publisher;
    private ArrayList<String> writers = new ArrayList<>();
    
    public Book() {

    }

    public Book(boolean state, date borrowDate, date returnDate, int book_id,
                int publishYear, int quantity, int quantityOfBorrowed, String cover, String genre,
                String name, String description, String publisher, ArrayList<String> writers) {
        this.state = state;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.book_id = book_id;
        if (publishYear > 0) {
            this.publishYear = publishYear;
        }
        if (quantity >= 0) {
            this.quantity = quantity;
        }
        if (quantityOfBorrowed >= 0 && quantityOfBorrowed <= quantity) {
            this.quantityOfBorrowed = quantityOfBorrowed;
        }
        this.cover = cover;
        this.genre = genre;
        this.name = name;
        this.description = description;
        this.publisher = publisher;
        this.writers = writers;
    }

    //Getter setter
    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }

    public date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(date returnDate) {
        this.returnDate = returnDate;
    }

    public int getBook_id() {
        return book_id;
    }

    public void setBook_id(int book_id) {
        this.book_id = book_id;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        if (publishYear > 0) {
            this.publishYear = publishYear;
        }
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if (quantity >= 0) {
            this.quantity = quantity;
        }
    }

    public int getQuantityOfBorrowed() {
        return quantityOfBorrowed;
    }

    public void setQuantityOfBorrowed(int quantityOfBorrowed) {
        if (quantityOfBorrowed >= 0 && quantityOfBorrowed <= quantity) {
            this.quantityOfBorrowed = quantityOfBorrowed;
        }
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String gerne) {
        this.genre = genre;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public ArrayList<String> getWriters() {
        return writers;
    }

    public void setWriters(ArrayList<String> writers) {
        this.writers = writers;
    }

    //function
    public void addWriter(String writer) {
        boolean exist = false;
        for (String s: writers) {
            if (s.equals(writer)) {
                exist = true;
                break;
            }
        }
        if (!exist) {
            writers.add(writer);
        }
    }

    public void removeWriter(String writer) {
        boolean exist = false;
        for (String s: writers) {
            if (s.equals(writer)) {
                exist = true;
                writers.remove(writer);
                break;
            }
        }
    }

    public int remainingDays() {
        return returnDate.dateDiff();
    }
}
