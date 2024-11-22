package com.chocopi.controller;

import com.chocopi.model.Book;
import com.chocopi.dao.BookDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.util.List;

public class userEachGenreUI {

    @FXML
    private TextField searchField;

    @FXML
    private Button advancedSearchButton;

    @FXML
    private GridPane bookGridPane;

    // Danh sách sách sẽ hiển thị
    private List<Book> books;

    @FXML
    public void initialize() {
        // Tải sách mặc định (ví dụ thể loại "Fiction")
        loadBooksForGenre("Fiction");
        displayBooks();
    }

    // Hàm tải sách từ cơ sở dữ liệu theo thể loại
    private void loadBooksForGenre(String genre) {
        BookDAO bookDAO = new BookDAO();
        books = bookDAO.getBooksByGenre(genre);
    }

    // Hiển thị danh sách sách trên GridPane
    private void displayBooks() {
        bookGridPane.getChildren().clear(); // Xóa các nút hiện tại
        int row = 0, col = 0;

        for (Book book : books) {
            // Tạo một nút đại diện cho mỗi sách
            Button bookButton = new Button(book.getTitle());
            bookButton.setPrefWidth(90);
            bookButton.setPrefHeight(70);
            bookButton.setOnAction(e -> handleBookClick(book));

            // Thêm nút vào GridPane
            bookGridPane.add(bookButton, col, row);

            // Cập nhật vị trí cột và dòng
            col++;
            if (col == 5) { // 5 cột mỗi hàng
                col = 0;
                row++;
            }
        }
    }

    // Hàm xử lý khi người dùng click vào sách
    private void handleBookClick(Book book) {
        System.out.println("Clicked on: " + book.getTitle());
        // TODO: Mở chi tiết sách hoặc thực hiện hành động khác
    }

    @FXML
    private void moveToStories() {
        System.out.println("Navigating to Stories...");
        // TODO: Điều hướng tới trang Stories
    }
}
