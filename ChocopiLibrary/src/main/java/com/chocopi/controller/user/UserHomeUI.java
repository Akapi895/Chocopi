package com.chocopi.controller.user;

import com.chocopi.util.SessionManager;
import com.chocopi.dao.BookDAO;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UserHomeUI {
    @FXML
    private TextField searchField;

    @FXML
    private Button advancedSearchButton;

    @FXML
    private Button helpButton;

    @FXML
    private Label usernameLabel;

    @FXML
    private Label userRoleLabel;

    @FXML
    private Button logoutButton;

    @FXML
    private void handleSearch() {
        String keyword = searchField.getText();
        if (keyword.isEmpty()) {
            System.out.println("Please enter a search term.");
            return;
        }
        System.out.println("Searching for: " + keyword);
        // Thêm logic tìm kiếm sách tại đây
    }

    @FXML
    private void handleAdvancedSearch(MouseEvent event) {
        System.out.println("Advanced search clicked!");
        // Hiển thị giao diện tìm kiếm nâng cao
    }

    @FXML
    private void handleHelp(MouseEvent event) {
        System.out.println("Help button clicked!");
        // Hiển thị giao diện trợ giúp
    }

    @FXML private AnchorPane page1;
    @FXML private Button button1, button2, button3, button4, button5;
    @FXML private Label genreLabel;
    @FXML private Button page1Button, page2Button;

    private String[] genrePage1 = {"Computer", "Education", "Business & Economies"};
    private String[] genrePage2 = {"Self-help", "Fiction", "Others"};

    private List<String> bookImagesPage1 = new ArrayList<>();

    private List<String> bookImagesPage2 = new ArrayList<>();

    @FXML
    public void initialize() {
        for (String genre : genrePage1) {
            bookImagesPage1.addAll(BookDAO.getBookImagesByGenre(genre));
        }
        for (String genre : genrePage2) {
            bookImagesPage2.addAll(BookDAO.getBookImagesByGenre(genre));
        }
        setPage(1);
    }

    @FXML
    private void onPage1Clicked() {
        setPage(1);
    }

    @FXML
    private void onPage2Clicked() {
        setPage(2);
    }

    private void setPage(int page) {
        String[] genres;
        List<String> bookImages;

        if (page == 1) {
            genres = genrePage1;
            bookImages = bookImagesPage1;
        } else {
            genres = genrePage2;
            bookImages = bookImagesPage2;
        }

        // Update the genre labels
        genreLabel.setText(String.join(", ", genres));

        // Assign books to buttons
        Button[] buttons = {button1, button2, button3, button4, button5};
        for (int i = 0; i < buttons.length; i++) {
            Button button = buttons[i];
            String bookImagePath = bookImages.get(i);

            // Set book image as button background
            File file = new File(bookImagePath);
            if (file.exists()) {
                Image image = new Image(file.toURI().toString());
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(button.getPrefWidth());
                imageView.setFitHeight(button.getPrefHeight());
                button.setGraphic(imageView);
            }
        }
    }
}
