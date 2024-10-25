package org.example.oop_btl14;

import java.util.ArrayList;

public class Student {
    private int studentId;
    private String studentName;
    private ArrayList<Book> borrowedBook = new ArrayList<>();

    public Student(int studentId, String studentName, ArrayList<Book> borrowedBook) {
        this.studentId = studentId;
        this.studentName = studentName;
        this.borrowedBook = borrowedBook;
        int n = this.borrowedBook.size();
        for (int i = 0; i < n; i++) {
            int currentQuantity = this.borrowedBook.get(i).getQuantityOfBorrowed();
            this.borrowedBook.get(i).setQuantityOfBorrowed(currentQuantity--);
        }
    }

    //Getter setter
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public ArrayList<Book> getBorrowedBook() {
        return borrowedBook;
    }

    public void setBorrowedBook(ArrayList<Book> borrowedBook) {
        int n = this.borrowedBook.size();
        for (int i = 0; i < n; i++) {
            Book book = this.borrowedBook.get(i);
            int currentQuantity = book.getQuantityOfBorrowed();
            book.setQuantityOfBorrowed(currentQuantity++);
        }
    }

    //Function
    public void addBorrowedBook(Book book) {
        this.borrowedBook.add(book);
        int currentQuantity = book.getQuantityOfBorrowed();
        book.setQuantityOfBorrowed(currentQuantity++);
    }

    public void removeBorrowedBook(Book book) {
        
    }
}
