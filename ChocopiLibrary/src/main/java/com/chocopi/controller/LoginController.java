package com.chocopi.controller;

import com.chocopi.dao.UserDAO;
import com.chocopi.model.User;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private final UserDAO userDAO = new UserDAO();

    @FXML
    public void handleLogin() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Kiểm tra nếu username hoặc password rỗng
        if (username.isEmpty() || password.isEmpty()) {
            showAlert("Error", "Please enter both username and password.");
            return;
        }

        // Gọi UserDAO để kiểm tra thông tin đăng nhập
        User user = userDAO.authenticate(username, password);

        if (user != null) {
            try {
                // Chuyển sang trang phù hợp dựa vào vai trò
                String nextPage = user.getRole().equals("admin") ? "/com/chocopi/fxml/AdminHome.fxml" : "/com/chocopi/fxml/UserHome.fxml";
                FXMLLoader loader = new FXMLLoader(getClass().getResource(nextPage));
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(new Scene(loader.load()));
                stage.setTitle(user.getRole().equals("admin") ? "Admin Home" : "Home");
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            // Thông báo lỗi nếu đăng nhập không thành công
            showAlert("Login Failed", "Invalid username or password.");
        }
    }

    @FXML
    public void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/Register.fxml"));
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
