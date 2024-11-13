package com.chocopi.model;

public class Book {
    private int bookId;
    private String title;
    private String description;
    private String image;
    private String genre;
    private int rating;
    private int availableQuantity;
    private String author;
    private int publishYear;
    private String publisher;

    // Constructor
    public Book() {}

    public Book(int bookId, String title, String description, String image, String genre, int rating, int availableQuantity, String author, int publishYear, String publisher) {
        this.bookId = bookId;
        this.title = title;
        this.description = description;
        this.image = image;
        this.genre = genre;
        this.rating = rating;
        this.availableQuantity = availableQuantity;
        this.author = author;
        this.publishYear = publishYear;
        this.publisher = publisher;
    }

    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public int getAvailableQuantity() {
        return availableQuantity;
    }

    public void setAvailableQuantity(int availableQuantity) {
        this.availableQuantity = availableQuantity;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getPublishYear() {
        return publishYear;
    }

    public void setPublishYear(int publishYear) {
        this.publishYear = publishYear;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
}
