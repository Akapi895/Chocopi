package com.chocopi.controller.admin;

import com.chocopi.controller.admin.abstractAdminSideBar;
import com.chocopi.dao.UserDAO;
import com.chocopi.model.User;
import com.chocopi.util.SessionManager;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.stage.FileChooser;
import org.controlsfx.control.tableview2.filter.filtereditor.SouthFilter;

import javax.imageio.ImageIO;

public class AdminEditUserController extends abstractAdminSideBar {
    @FXML
    private ImageView userImage;

    @FXML
    private Label userIdLabel;

    @FXML
    private Label userNameLabel;

    @FXML
    private TextField fullNameField;

    @FXML
    private TextField ageField;

    @FXML
    private TextField emailField;

    @FXML
    private TextField phoneField;

    @FXML
    private TextArea favorField;

    @FXML
    private TextField passwordField;

    @FXML
    private Label totalBooksBorrowedLabel;

    @FXML
    private Label totalBooksAvailableLabel;

    @FXML
    private Label nameLabel;

    @FXML
    private Label ageLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private Label phoneLabel;

    @FXML
    private Label favorLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private Button editButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    @FXML
    private Button addPhotoButton;

    private String avatarPath;

    @FXML
    public void initialize() {
        User user = UserDAO.getUserById(SessionManager.getUserId());

        userIdLabel.setText(String.valueOf(user.getUserId()));
        totalBooksAvailableLabel.setText(String.valueOf(User.maxBorrowed - user.getTotalBorrowed()));
        totalBooksBorrowedLabel.setText(String.valueOf(user.getTotalBorrowed()));
        userNameLabel.setText(user.getUsername());
        nameLabel.setText(user.getName());
        emailLabel.setText(user.getEmail());
        phoneLabel.setText(user.getPhone());
        ageLabel.setText(String.valueOf(user.getAge()));
        favorLabel.setText(String.valueOf(user.getFavor()));

        avatarPath = user.getAvatar();

        try {
            if (avatarPath != null && !avatarPath.isEmpty()) {
                Image newImage = new Image(getClass().getResource(avatarPath).toExternalForm());
                userImage.setImage(newImage);
            } else {
                userImage.setImage(new Image(getClass().getResource("/com/chocopi/images/avatar/0.png").toExternalForm()));
            }
        } catch (Exception e) {
            userImage.setImage(new Image(getClass().getResource("/com/chocopi/images/avatar/0.png").toExternalForm()));
        }

        userImage.setOnMouseClicked(null);
    }

    @FXML
    private void handleAddPhotoButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choose an Image");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            try {
                avatarPath = selectedFile.getAbsolutePath();

                Image newImage = new Image("file:" + avatarPath, true);
                userImage.setImage(newImage);
            } catch (Exception e) {
                System.err.println("Error loading image: " + e.getMessage());
            }
        } else {
            System.out.println("No file selected!");
        }
    }

    private void clearTextFields() {
        fullNameField.clear();
        ageField.clear();
        emailField.clear();
        phoneField.clear();
        favorField.clear();
        passwordField.clear();
    }

    @FXML
    private void handleCancelButtonClick() {
        SessionManager.update();
        initialize();

        fullNameField.setEditable(false);
        fullNameField.setVisible(false);

        ageField.setEditable(false);
        ageField.setVisible(false);

        emailField.setEditable(false);
        emailField.setVisible(false);

        phoneField.setEditable(false);
        phoneField.setVisible(false);

        favorField.setEditable(false);
        favorField.setVisible(false);

        passwordField.setEditable(false);
        passwordField.setVisible(false);
        passwordLabel.setVisible(false);

        addPhotoButton.setVisible(false);
        userImage.setOnMouseClicked(null);

        nameLabel.setVisible(true);
        userImage.setVisible(true);
        ageLabel.setVisible(true);
        emailLabel.setVisible(true);
        phoneLabel.setVisible(true);
        favorLabel.setVisible(true);

        clearTextFields();
        cancelButton.setVisible(false);
        editButton.setVisible(true);
        saveButton.setVisible(false);
        userImage.setOnMouseClicked(null);
    }

    @FXML
    private void handleSaveButtonClick() {
        try {
            int userId = SessionManager.getUserId();
            String fullName = fullNameField.getText();
            String email = emailField.getText();
            String phone = phoneField.getText();
            int age = Integer.parseInt(ageField.getText());
            String favor = favorField.getText();
            String password = passwordField.getText();

            User updatedUser = new User();
            updatedUser.setUsername(SessionManager.getUsername());
            updatedUser.setRole(SessionManager.getRole());
            updatedUser.setTotalBorrowed(SessionManager.getTotalBorrowed());
            updatedUser.setUserId(userId);
            updatedUser.setName(fullName);
            updatedUser.setEmail(email);
            updatedUser.setPhone(phone);
            updatedUser.setAge(age);
            updatedUser.setFavor(favor);
            updatedUser.setPassword(password);
            String temp = userImage.getImage().getUrl();
            temp.replace("\\", "/");
            int index = temp.indexOf("/com");
            String resultPath = temp.substring(index);
            updatedUser.setAvatar(resultPath);

            boolean isUpdated = UserDAO.updateUser(updatedUser);

            if (isUpdated) {
                SessionManager.setName(fullName);
                SessionManager.setEmail(email);
                SessionManager.setPhone(phone);
                SessionManager.setAge(age);
                SessionManager.setFavor(favor);
                SessionManager.setPassword(password);

                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText(null);
                alert.setContentText("User information updated successfully!");
                alert.showAndWait();

                handleCancelButtonClick();
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Failed to update user information. Please try again.");
                alert.showAndWait();
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Invalid input! Please check your information.");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    @FXML
    private void handleEditButtonClick() {
        userImage.setOnMouseClicked(event -> handleAddPhotoButtonClick());

        fullNameField.setEditable(true);
        fullNameField.setVisible(true);
        fullNameField.setText(SessionManager.getName());

        ageField.setEditable(true);
        ageField.setVisible(true);
        ageField.setText(String.valueOf(SessionManager.getAge()));

        emailField.setEditable(true);
        emailField.setVisible(true);
        emailField.setText(SessionManager.getEmail());

        phoneField.setEditable(true);
        phoneField.setVisible(true);
        phoneField.setText(String.valueOf(SessionManager.getPhone()));

        favorField.setEditable(true);
        favorField.setVisible(true);
        favorField.setText(String.valueOf(SessionManager.getFavor()));

        passwordLabel.setVisible(true);
        passwordField.setEditable(true);
        passwordField.setVisible(true);
        passwordField.setText(String.valueOf(SessionManager.getPassword()));

        if (passwordField.getText() != "") {
            passwordLabel.setVisible(true);
        }

        addPhotoButton.setVisible(false);

        userImage.setVisible(true);
        userImage.setOnMouseClicked(event -> handleAddPhotoButtonClick());

        ageLabel.setVisible(false);
        emailLabel.setVisible(false);
        phoneLabel.setVisible(false);
        favorLabel.setVisible(false);
        nameLabel.setVisible(false);

        saveButton.setVisible(true);
        editButton.setVisible(false);
        cancelButton.setVisible(true);
    }
}

