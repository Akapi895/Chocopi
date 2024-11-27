package com.chocopi.controller.admin;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

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
    protected void handleLogoutClick(ActionEvent event) {

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
