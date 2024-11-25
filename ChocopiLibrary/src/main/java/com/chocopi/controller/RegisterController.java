package com.chocopi.controller;

import com.chocopi.dao.UserDAO;
import com.chocopi.model.User;
import com.chocopi.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.SQLException;

public class RegisterController {
    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField cfPasswordField;

    @FXML
    private TextField usernameField;

    private String selectedAvatarPath = null;
    private UserDAO userDAO = new UserDAO();

    @FXML
    public void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/login.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleRegister() throws SQLException {
        String email = emailField.getText();
        String phone = phoneField.getText();
        String password = passwordField.getText().strip();
        String username = usernameField.getText();
        String cfPassword = cfPasswordField.getText().strip();

        if (email.isEmpty() || phone.isEmpty() || username.isEmpty() || password.isEmpty() || cfPassword.isEmpty()) {
            showAlert("Error", "Please fill in all required fields.");
            return;
        }

        if (!password.equals(cfPassword)) {
            showAlert("Error", "Passwords do not match.");
            return;
        }

        if (UserDAO.checkUsername(username)) {
            showAlert("Error", "Username is already taken.");
            return;
        }

        // Parse fixed inputs
        int age = 0;
        String fullName = "Chocopi User";
        String favor = "Không có";
        int userId = UserDAO.getLastUserId() + 1;
        String avatar = "/com/chocopi/images/avatar/" + userId + ".png";

        User user = new User();
        user.setName(fullName);
        user.setAge(age);
        user.setEmail(email);
        user.setPhone(phone);
        user.setFavor(favor);
        user.setPassword(password);
        user.setUsername(username);
        user.setRole("user");
        user.setUserId(userId);
        user.setAvatar(avatar);

        boolean isRegistered = userDAO.addUser(user);
        SessionManager.setUser(user);

        if (isRegistered) {
            SessionManager.setUser(user);

            goToUserPersonal();
            System.out.println("User registered successfully!");
        } else {
            showAlert("Error", "User registration failed.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    public void goToUserPersonal() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserPersonal.fxml"));
            Scene loginScene = new Scene(loader.load());
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(loginScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
