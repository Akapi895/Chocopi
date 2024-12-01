package com.chocopi.controller.admin;

import com.chocopi.util.SessionManager;
import javafx.event.ActionEvent;
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

public class abstractAdminSideBar {
    @FXML
    protected Button home;

    @FXML
    protected Button student;

    @FXML
    protected Button book;

    @FXML
    protected Button logout;

    @FXML
    protected Button issuedBook;

    @FXML
    protected void handleHomeClick(ActionEvent event) {
        switchScene(event, "/com/chocopi/fxml/admin/AdminHum.fxml");
    }

    @FXML
    protected void handleStudentClick(ActionEvent event) {
        switchScene(event, "/com/chocopi/fxml/admin/AdminStudent.fxml");
    }

    @FXML
    protected void handleBookClick(ActionEvent event) {
        switchScene(event, "/com/chocopi/fxml/admin/AdminBook.fxml");
    }

    @FXML
    protected void handleIssuedBookClick(ActionEvent event) {
        switchScene(event, "/com/chocopi/fxml/admin/AdminIssuedBook.fxml");
    }

    @FXML
    protected void handleLogoutClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            SessionManager.clearSession();
            switchScene(event, "/com/chocopi/fxml/login.fxml");
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