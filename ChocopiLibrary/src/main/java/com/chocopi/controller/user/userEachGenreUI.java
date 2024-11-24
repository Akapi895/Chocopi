package com.chocopi.controller.user;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class userEachGenreUI extends abstractUserSideBar {

    @FXML
    private TextField searchField;

    @FXML
    private Button advancedSearchButton;

    @FXML
    private Button storyButton;

    @FXML
    protected void handleSearch(ActionEvent event) {
        String query = searchField.getText();
        System.out.println("Searching for: " + query);
        // TODO: Thêm logic tìm kiếm sách theo thể loại.
    }

    @FXML
    protected void handleAdvancedSearch(ActionEvent event) {
        System.out.println("Advanced search clicked");
        // TODO: Thêm logic xử lý tìm kiếm nâng cao.
    }

    @FXML
    protected void moveToStories(ActionEvent event) {
        System.out.println("Move to stories clicked");
        // TODO: Thêm logic chuyển tới danh sách truyện.
    }

    // Các phương thức từ abstractUserSideBar sẽ tự động khả dụng
}