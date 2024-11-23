package com.chocopi.controller.user;

import javafx.event.ActionEvent; // Sử dụng javafx.event.ActionEvent
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public abstract class abstractUserSideBar {
    @FXML
    protected Button btnHome;

    @FXML
    protected Button btnProfile;

    @FXML
    protected Button btnHistory;

    @FXML
    protected Button btnLogout;

    @FXML
    protected void handleHomeClick(ActionEvent event) {
        switchScene(event, "/com/chocopi/fxml/user/UserHome.fxml");
    }

    @FXML
    protected void handlePersonalClick(ActionEvent event) {
        switchScene(event, "/com/chocopi/fxml/user/UserPersonal.fxml");
    }

    @FXML
    protected void handleHistoryClick(ActionEvent event) {
        switchScene(event, "/com/chocopi/fxml/user/UserHistory.fxml");
    }

    @FXML
    protected void handleLogoutClick(ActionEvent event) {
        System.out.println("Logout clicked. Implement logout logic.");
        // TODO: Thêm logic xử lý đăng xuất
    }

    protected void switchScene(ActionEvent event, String fxmlPath) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        try {
            Scene newScene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + fxmlPath);
            e.printStackTrace();
        }
    }
}