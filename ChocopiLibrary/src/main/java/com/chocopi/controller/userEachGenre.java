package com.chocopi.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

public class userEachGenre {

    @FXML
    private TextField searchField; // TextField để tìm kiếm

    @FXML
    private Button advancedButton; // Nút "Advanced"

    @FXML
    private Button storiesButton; // Nút chuyển đến Stories

    @FXML
    private GridPane booksGridPane; // GridPane hiển thị danh sách sách theo thể loại

    @FXML
    private Button previousButton; // Nút chuyển đến trang trước

    @FXML
    private Button nextButton; // Nút chuyển đến trang sau

    @FXML
    private Button homeButton; // Nút "Home"

    @FXML
    private Button personalButton; // Nút "Personal"

    @FXML
    private Button historyButton; // Nút "History"

    @FXML
    private Button logoutButton; // Nút "Log out"

    // Biến lưu trạng thái
    private int currentPage = 1; // Trang hiện tại
    private final int itemsPerPage = 15; // Số lượng sách trên mỗi trang

    /**
     * Phương thức khởi tạo, được gọi sau khi FXML được tải.
     */
    @FXML
    private void initialize() {
        // Cấu hình sự kiện nút
        advancedButton.setOnAction(event -> handleAdvancedSearch());
        storiesButton.setOnAction(event -> moveToStories());
        previousButton.setOnAction(event -> loadPreviousPage());
        nextButton.setOnAction(event -> loadNextPage());
        homeButton.setOnAction(event -> moveToHome());
        personalButton.setOnAction(event -> moveToPersonal());
        historyButton.setOnAction(event -> moveToHistory());
        logoutButton.setOnAction(event -> handleLogout());

        // Tải dữ liệu sách mặc định
        loadBooksForGenre();
    }

    /**
     * Xử lý sự kiện tìm kiếm nâng cao.
     */
    private void handleAdvancedSearch() {
        // Logic tìm kiếm nâng cao
        String query = searchField.getText();
        System.out.println("Advanced search with query: " + query);
    }

    /**
     * Di chuyển đến giao diện Stories.
     */
    private void moveToStories() {
        System.out.println("Moving to Stories...");
        // Logic chuyển sang màn hình Stories
    }

    /**
     * Tải danh sách sách theo thể loại.
     */
    private void loadBooksForGenre() {
        System.out.println("Loading books for the current genre...");
        // Logic lấy sách từ service và hiển thị lên `booksGridPane`
        // Ví dụ: gọi BookService để lấy danh sách sách
    }

    /**
     * Chuyển đến trang trước.
     */
    private void loadPreviousPage() {
        if (currentPage > 1) {
            currentPage--;
            loadBooksForGenre();
        }
    }

    /**
     * Chuyển đến trang sau.
     */
    private void loadNextPage() {
        currentPage++;
        loadBooksForGenre();
    }

    /**
     * Chuyển đến giao diện Home.
     */
    private void moveToHome() {
        System.out.println("Moving to Home...");
        // Logic chuyển về màn hình Home
    }

    /**
     * Chuyển đến giao diện Personal.
     */
    private void moveToPersonal() {
        System.out.println("Moving to Personal...");
        // Logic chuyển đến màn hình cá nhân
    }

    /**
     * Chuyển đến giao diện History.
     */
    private void moveToHistory() {
        System.out.println("Moving to History...");
        // Logic chuyển đến màn hình lịch sử
    }

    /**
     * Xử lý đăng xuất.
     */
    private void handleLogout() {
        System.out.println("Logging out...");
        // Logic đăng xuất
    }
}
