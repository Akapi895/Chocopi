package com.chocopi.controller;

import com.chocopi.dao.UserDAO;
import com.chocopi.model.User;
import com.chocopi.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private Button loginButton;

    @FXML
    private Button registerButton;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void handleLogin() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
//        System.out.printf("username = %s, password = %s\n", username, password);

        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }
        User user = userDAO.authenticate(username, password);
        if (user != null) {
            SessionManager.setUser(user);
            if (user.getRole().equals("admin")) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminHum.fxml"));
                    Scene adminHomeScene = new Scene(loader.load());
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(adminHomeScene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserPersonal.fxml"));
                    Scene userHomeScene = new Scene(loader.load());
                    Stage stage = (Stage) usernameField.getScene().getWindow();
                    stage.setScene(userHomeScene);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        } else {
            showAlert("Login Failed", "Invalid username or password.");
        }
    }



    @FXML
    public void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/register.fxml"));
            Scene registerScene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(registerScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
