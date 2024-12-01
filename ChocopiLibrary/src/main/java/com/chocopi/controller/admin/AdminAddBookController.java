package com.chocopi.controller.admin;

import com.chocopi.model.Book;
import com.chocopi.util.BookSessionManager;
import com.chocopi.util.SessionManager;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import com.chocopi.dao.BookDAO;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Path;

public class AdminAddBookController {
    @FXML
    private TextField bookname, author, gerne, publisher, publishyear, rating;

    @FXML
    private TextArea description;

    @FXML
    private Button btnAddPhoto, btnSave, getBtnAddPhoto, btnBack, btnSelectFile;

    @FXML
    private ImageView photo;

    private String selectedImage;

    @FXML
    private void initialize() {
        Book book = BookSessionManager.getBook();

        if (book != null) {
            bookname.setText(book.getTitle());
            author.setText(book.getAuthor());
            gerne.setText(book.getGenre());
            publisher.setText(book.getPublisher());
            publishyear.setText(String.valueOf(book.getPublishYear()));
            rating.setText(String.valueOf(book.getRating()));
            description.setText(book.getDescription());

            selectedImage = getClass().getResource(book.getImage()).toExternalForm();
            selectedImage = selectedImage.substring(6);

            Image image = new Image(getClass().getResource(book.getImage()).toExternalForm());
            photo.setImage(image);
            btnAddPhoto.setDisable(true);
            btnAddPhoto.setVisible(false);
            photo.setOnMouseClicked(event -> addPhoto());
        }

    }

    @FXML
    private void handleSaveClick(ActionEvent event) {
        if (bookname.getText() == null || bookname.getText().isEmpty() ||
                author.getText() == null || author.getText().isEmpty() ||
                description.getText() == null || description.getText().isEmpty() ||
                gerne.getText() == null || gerne.getText().isEmpty() ||
                publisher.getText() == null || publisher.getText().isEmpty() ||
                publishyear.getText() == null || publishyear.getText().isEmpty() ||
                rating.getText() == null || rating.getText().isEmpty() ||
                !photo.isVisible()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Missing Information");
            alert.setContentText("Please fill in all fields before saving the book.");
            alert.showAndWait();
            return;
        }
        photo.setOnMouseClicked(null);
        try {
            int book_id = BookDAO.lastBookId() + 1;
            String book_image = "/com/chocopi/images/book/" + book_id + ".jpg";
            int book_availableQuantity = 1000000;
            String book_title = bookname.getText();
            String book_author = author.getText();
            String book_description = description.getText();
            String book_gerne = gerne.getText();
            String book_publisher = publisher.getText();
            int book_publisheryear = Integer.parseInt(publishyear.getText());
            int book_rating = Integer.parseInt(rating.getText());

            Book book = new Book(book_id, book_title, book_description, book_image, book_gerne, book_rating, book_availableQuantity, book_author, book_publisheryear, book_publisher);

            BookDAO bookDao = new BookDAO();
            if (!bookDao.isBookExists(book.getTitle())) {
                if (selectedImage != null) {
                    String fileName = book_id + ".jpg";
                    String directoryPath = "src/main/resources/com/chocopi/images/book";
                    File directory = new File(directoryPath);
                    if (!directory.exists() && !directory.mkdirs()) {
                        return;
                    }
                    Path targetPath = Path.of(directoryPath, fileName);

                    try {
                        File sourceFile = new File(selectedImage);
                        BufferedImage originalImage = ImageIO.read(sourceFile);

                        if (originalImage != null) {
                            ImageIO.write(originalImage, "jpg", targetPath.toFile());
                        } else {
                            System.err.println("Unable to read the selected image file.");
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    selectedImage = null;
                }
                bookDao.addBook(book);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Book Added");
                successAlert.setContentText("The book has been successfully added to the library.");
                successAlert.showAndWait();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminBook.fxml"));
                try {
                    Scene scene = new Scene(loader.load());
                    Stage stage = (Stage) bookname.getScene().getWindow();
                    stage.setScene(scene);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                Alert existsAlert = new Alert(Alert.AlertType.WARNING);
                existsAlert.setTitle("Duplicate Book");
                existsAlert.setHeaderText("Book Already Exists");
                existsAlert.setContentText("A book with this title already exists in the library.");
                existsAlert.showAndWait();
            }
        } catch (NumberFormatException e) {
            Alert numberAlert = new Alert(Alert.AlertType.ERROR);
            numberAlert.setTitle("Invalid Input");
            numberAlert.setHeaderText("Invalid Number Format");
            numberAlert.setContentText("Please ensure 'Publish Year' and 'Rating' are valid numbers.");
            numberAlert.showAndWait();
        }
    }

    @FXML
    private void selectFile(ActionEvent event) {

    }

    @FXML
    private void addPhoto() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(btnAddPhoto.getScene().getWindow());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            selectedImage = selectedFile.toURI().toString();
            if (selectedImage.charAt(0) == 'f') {
                selectedImage = selectedImage.substring(6);
            }

            photo.setImage(image);
            btnAddPhoto.setVisible(false);
        }
    }

    @FXML
    private void handleMethod1() {
//        BookSessionManager.setPage(0);
    }

    @FXML
    private void handleMethod2() {
//        BookSessionManager.setPage(1);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminAddBookAPI.fxml"));

        try {
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) bookname.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleBackClick() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminBook.fxml"));
        BookSessionManager.clearBookSession();
        try {
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) bookname.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}