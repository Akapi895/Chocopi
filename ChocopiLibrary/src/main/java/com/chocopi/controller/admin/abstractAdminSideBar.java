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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminHum.fxml"));
        try {
            Scene newScene = new Scene(loader.load());
            String cssLoad = "/com/chocopi/css/admin/AdminHum.css";

            newScene.getStylesheets().add(getClass().getResource("/com/chocopi/css/admin/AdminHum.css").toExternalForm());
            newScene.getStylesheets().add(getClass().getResource(cssLoad).toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + "/com/chocopi/fxml/admin/AdminHum.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleStudentClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminStudent.fxml"));
        try {
            Scene newScene = new Scene(loader.load());
            String cssLoad = "/com/chocopi/css/admin/AdminStudent.css";

            newScene.getStylesheets().add(getClass().getResource("/com/chocopi/css/admin/AdminStudent.css").toExternalForm());
            newScene.getStylesheets().add(getClass().getResource(cssLoad).toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + "/com/chocopi/fxml/admin/AdminStudent.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleBookClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminBook.fxml"));
        try {
            Scene newScene = new Scene(loader.load());
            String cssLoad = "/com/chocopi/css/admin/AdminBook.css";

            newScene.getStylesheets().add(getClass().getResource("/com/chocopi/css/admin/AdminBook.css").toExternalForm());
            newScene.getStylesheets().add(getClass().getResource(cssLoad).toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + "/com/chocopi/fxml/admin/AdminBook.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    protected void handleIssuedBookClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminIssuedBook.fxml"));
        try {
            Scene newScene = new Scene(loader.load());
            String cssLoad = "/com/chocopi/css/admin/AdminIssuedBook.css";

            newScene.getStylesheets().add(getClass().getResource("/com/chocopi/css/admin/AdminIssuedBook.css").toExternalForm());
            newScene.getStylesheets().add(getClass().getResource(cssLoad).toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + "/com/chocopi/fxml/admin/AdminIssuedBook.fxml");
            e.printStackTrace();
        }
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
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/login.fxml"));
            try {
                Scene newScene = new Scene(loader.load());
                String cssLoad = "/com/chocopi/css/login.css";

                newScene.getStylesheets().add(getClass().getResource("/com/chocopi/css/user/SideBar.css").toExternalForm());
                newScene.getStylesheets().add(getClass().getResource(cssLoad).toExternalForm());

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                stage.setScene(newScene);
                stage.show();
            } catch (IOException e) {
                System.err.println("Error loading FXML file: " + "/com/chocopi/fxml/login.fxml");
                e.printStackTrace();
            }
        }
    }
}