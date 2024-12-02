package com.chocopi.controller.user;

import com.chocopi.util.BookSessionManager;
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

public class UserSideBarController {
    @FXML
    Button personalBtn;

    @FXML
    Button homeBtn;

    @FXML
    Button historyBtn;

    @FXML
    Button logoutBtn;

    @FXML
    private void handleHomeClick(ActionEvent event) {
        BookSessionManager.setPage(0);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserHome.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/user/UserHome.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/user/SideBar.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handlePersonalClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserPersonal.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/user/UserPersonal.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/user/SideBar.css").toExternalForm());
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleHistoryClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserHistory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(loader.load());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/user/UserHistory.css").toExternalForm());
        scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/user/SideBar.css").toExternalForm());

        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
    }

    @FXML
    private void handleLogoutClick(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText(null);
        alert.setContentText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            SessionManager.clearSession();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/login.fxml"));
            Scene scene = null;
            try {
                scene = new Scene(loader.load());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/login.css").toExternalForm());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
        }
    }
}
