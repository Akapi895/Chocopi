package com.chocopi.controller.user;

import com.chocopi.util.SessionManager;
import javafx.event.ActionEvent; // Sử dụng javafx.event.ActionEvent
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Optional;

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
        // Hiển thị hộp thoại xác nhận
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        // Xử lý phản hồi từ người dùng
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            SessionManager.clearSession();

            switchScene(event, "/com/chocopi/fxml/Login.fxml");
        }
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