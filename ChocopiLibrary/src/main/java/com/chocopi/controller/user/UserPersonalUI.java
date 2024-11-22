package com.chocopi.controller.user;

import com.chocopi.dao.UserDAO;
import com.chocopi.model.User;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import javafx.stage.FileChooser;

public class UserPersonalUI {
    @FXML
    private ImageView userImage;

    @FXML
    private Label personalInfoLabel;

    @FXML
    private Label documentInfoLabel;

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
    private Button editButton;

    @FXML
    private Button saveButton;

    @FXML
    private Button addPhotoButton;

    @FXML
    public void initialize() {
        // Lấy thông tin người dùng từ UserDAO
        int userId = Integer.parseInt(userIdLabel.getText());  // Lấy userId từ Label đã có
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(userId);  // Lấy thông tin người dùng từ cơ sở dữ liệu

        if (user != null) {
            // Điền thông tin vào các trường giao diện
            userNameLabel.setText(user.getUsername());
            fullNameField.setText(user.getName());
            ageField.setText(String.valueOf(user.getAge()));
            emailField.setText(user.getEmail());
            phoneField.setText(user.getPhone());
            favorField.setText(user.getFavor());
            totalBooksBorrowedLabel.setText(String.valueOf(user.getTotalBorrowed()));
            totalBooksAvailableLabel.setText(String.valueOf(user.maxBorrowed - user.getTotalBorrowed()));
            String avatarPath = user.getAvatar();
            if (avatarPath != null && !avatarPath.isEmpty()) {
                Image avatarImage = new Image("file:" + avatarPath);  // Dùng "file:" để chỉ ra đường dẫn đầy đủ
                userImage.setImage(avatarImage);
            }

            saveButton.setVisible(false);
            addPhotoButton.setVisible(false);
            editButton.setVisible(true);
        } else {
            System.out.println("User not found!");
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

    @FXML
    private void handleSaveButtonClick() {
        int userId = Integer.parseInt(userIdLabel.getText());
        UserDAO userDAO = new UserDAO();
        User user = userDAO.getUserById(userId);

        String fullName = (fullNameField.getText() != "") ? fullNameField.getText() : user.getName();
        int age = (Integer.parseInt(ageField.getText()) != 0) ? Integer.parseInt(ageField.getText()) : user.getAge();
        String email = (emailField.getText() != "") ? emailField.getText() : user.getEmail();
        String phone = (phoneField.getText() != "") ? phoneField.getText() : user.getPhone();
        String favor = (favorField.getText() != "") ? favorField.getText() : user.getFavor();
        String password = (passwordField.getText() != "") ? passwordField.getText() : user.getPassword();
        String avatar = user.getAvatar();
        int totalBorrowed = user.getTotalBorrowed();

        // Cập nhật thông tin người dùng vào cơ sở dữ liệu
        user = new User(userId, userNameLabel.getText(), password, fullName, avatar, age, phone, favor, email, totalBorrowed, "user");
        boolean isUpdated = userDAO.updateUser(user);

        if (isUpdated) {
            System.out.println("User information updated successfully!");
            // Kiểm tra nếu có ảnh mới được chọn
            if (selectedAvatarPath != null) {
                String fileName = userId + ".jpg";
                String directoryPath = "src/main/resources/com/chocopi/images/avatar";
                File directory = new File(directoryPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                Path targetPath = Path.of(directoryPath, fileName);

                try {
                    // Sao chép ảnh vào thư mục đích với tên mới
                    Files.copy(Path.of(selectedAvatarPath), targetPath, StandardCopyOption.REPLACE_EXISTING);
                    selectedAvatarPath = null;
                    System.out.println("Image successfully saved at: " + targetPath);

                    user.setAvatar(targetPath.toString());
                    isUpdated = userDAO.updateUser(user);

                    if (isUpdated) {
                        System.out.println("User's photo updated successfully!");
                    } else {
                        System.out.println("Failed to update user's photo.");
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    System.out.println("Error occurred while saving the image.");
                }
            }

            saveButton.setVisible(false);
            editButton.setVisible(true);

            fullNameField.setEditable(false);
            ageField.setEditable(false);
            emailField.setEditable(false);
            phoneField.setEditable(false);
            favorField.setEditable(false);
            passwordField.setEditable(false);
            addPhotoButton.setVisible(false);
        } else {
            System.out.println("Failed to update user information.");
        }
    }

    @FXML
    private void handleEditButtonClick() {
        fullNameField.setEditable(true);
        ageField.setEditable(true);
        emailField.setEditable(true);
        phoneField.setEditable(true);
        favorField.setEditable(true);
        passwordField.setEditable(true);
        addPhotoButton.setVisible(true);

        saveButton.setVisible(true);
        editButton.setVisible(false);
    }
}
