package com.chocopi.controller;

import com.chocopi.dao.UserDAO;
import com.chocopi.service.EmailService;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class ForgetPassword {
    @FXML
    private TextField emailField;

    @FXML
    public void handleRessetPassword() {
        String email = emailField.getText().trim();

        if (email.isEmpty()) {
            showAlert("Error", "Email cannot be empty.");
            return;
        }
        boolean isMailValid = UserDAO.checkValidEmail(email);

        if (!isMailValid) {
            showAlert("Error", "Please enter a valid email.");
            return;
        }

        Task<Boolean> resetPasswordTask = new Task<>() {
            @Override
            protected Boolean call() throws Exception {
                return EmailService.resetPassword(email);
            }
        };
        goToLogin();

        resetPasswordTask.setOnSucceeded(event -> {
            boolean isReset = resetPasswordTask.getValue();

            if (!isReset) {
                showAlert("Error sending email", "Please enter a valid email address");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Password Reset");
                alert.setContentText("A password reset email has been sent to your email address.");
                alert.showAndWait();
            }
        });

        resetPasswordTask.setOnFailed(event -> {
            showAlert("Error", "An error occurred while resetting the password.");
            resetPasswordTask.getException().printStackTrace();
        });

        // Chạy Task trong một luồng riêng
        Thread thread = new Thread(resetPasswordTask);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    public void goToLogin() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/login.fxml"));
            Scene registerScene = new Scene(loader.load());
            registerScene.getStylesheets().add(getClass().getResource("/com/chocopi/css/login.css").toExternalForm());

            Stage stage = (Stage) emailField.getScene().getWindow();
            stage.setScene(registerScene);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void goToRegister() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/register.fxml"));
            Scene registerScene = new Scene(loader.load());
            registerScene.getStylesheets().add(getClass().getResource("/com/chocopi/css/register.css").toExternalForm());

            Stage stage = (Stage) emailField.getScene().getWindow();
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
