package com.chocopi.controller.user;

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

public class UserPersonalUI extends UserSideBarController {
//    @FXML
//    private UserSideBarController sideBar;

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

        String avatarPath = user.getAvatar();

        if (avatarPath != null && !avatarPath.isEmpty()) {
            Image newImage = new Image("file:" + avatarPath, true);
            userImage.setImage(newImage);
            try {
                Image image = new Image(getClass().getResource(avatarPath).toExternalForm());
                userImage.setImage(image);
            } catch (Exception e) {
                userImage.setImage(new Image(getClass().getResource("/com/chocopi/images/avatar/0.png").toExternalForm()));
            }
        } else {
            userImage.setImage(new Image(getClass().getResource("/com/chocopi/images/avatar/0.png").toExternalForm()));
        }
    }

    private String selectedAvatarPath = null;  // Biến lưu tạm thời đường dẫn ảnh

    @FXML
    private void handleAddPhotoButtonClick() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg"));

        // Hiển thị hộp thoại chọn tệp và lấy tệp đã chọn
        File selectedFile = fileChooser.showOpenDialog(null);

        if (selectedFile != null) {
            selectedAvatarPath = selectedFile.getAbsolutePath();
            System.out.println("Selected file: " + selectedAvatarPath);
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
    }

    @FXML
    private void handleSaveButtonClick() {
        int userId = SessionManager.getUserId();
        User user = UserDAO.getUserById(SessionManager.getUserId());

        String fullName = (fullNameField.getText() != "") ? String.valueOf(fullNameField.getText()) : SessionManager.getName();
        int age;
        try {
            age = !ageField.getText().isBlank() ? Integer.parseInt(ageField.getText()) : SessionManager.getAge();
        } catch (NumberFormatException e) {
            age = SessionManager.getAge();
        }
        String email = (emailField.getText() != "") ? String.valueOf(emailField.getText()) : SessionManager.getEmail();
        String phone = (phoneField.getText() != "") ? String.valueOf(phoneField.getText()) : SessionManager.getPhone();
        String favor = !favorField.getText().isBlank() ? favorField.getText() : SessionManager.getFavor();
        String password = (passwordField.getText() != "") ? passwordField.getText() : SessionManager.getPassword();

        user.setAge(age);
        user.setEmail(email);
        user.setPhone(phone);
        user.setFavor(favor);
        user.setPassword(password);
        user.setName(fullName);
        if (selectedAvatarPath != null) {
            String fileName = userId + ".png";
            String directoryPath = "src/main/resources/com/chocopi/images/avatar";
            File directory = new File(directoryPath);
            if (!directory.exists() && !directory.mkdirs()) {
                return;
            }
            Path targetPath = Path.of(directoryPath, fileName);

            try {
                File sourceFile = new File(selectedAvatarPath);
                BufferedImage originalImage = ImageIO.read(sourceFile);

                if (originalImage != null) {
                    ImageIO.write(originalImage, "png", targetPath.toFile());

                    user.setAvatar(targetPath.toString());

                    String avatarPath = SessionManager.getAvatar();
                    if (avatarPath != null) {
                        Image newImage = new Image("file:" + avatarPath, true);
                        userImage.setImage(newImage);
                    }

                    Platform.runLater(() -> {
                        userImage.setImage(null);
                        Image newImage = new Image("file:" + targetPath.toString());
                        userImage.setImage(newImage);
                    });
                } else {
                    System.err.println("Unable to read the selected image file.");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            selectedAvatarPath = null;
        }

        boolean isUpdated = UserDAO.updateUser(user);

        if (isUpdated) {
            fullNameField.setEditable(false);
            ageField.setEditable(false);
            emailField.setEditable(false);
            phoneField.setEditable(false);
            favorField.setEditable(false);
            passwordField.setEditable(false);
            fullNameField.setVisible(false);
            ageField.setVisible(false);
            emailField.setVisible(false);
            phoneField.setVisible(false);
            favorField.setVisible(false);
            passwordField.setVisible(false);
            passwordLabel.setVisible(false);
            addPhotoButton.setVisible(false);

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
        }
        // Cập nhật lại session
        SessionManager.update();
        SessionManager.ssInfo();
        initialize();

    }

    @FXML
    private void handleEditButtonClick() {
        fullNameField.setEditable(true);
        fullNameField.setVisible(true);

        ageField.setEditable(true);
        ageField.setVisible(true);

        emailField.setEditable(true);
        emailField.setVisible(true);

        phoneField.setEditable(true);
        phoneField.setVisible(true);

        favorField.setEditable(true);
        favorField.setVisible(true);

        passwordLabel.setVisible(true);
        passwordField.setEditable(true);
        passwordField.setVisible(true);
        if (passwordField.getText() != "") {
            passwordLabel.setVisible(true);
        }

        addPhotoButton.setVisible(true);

        userImage.setVisible(false);

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
