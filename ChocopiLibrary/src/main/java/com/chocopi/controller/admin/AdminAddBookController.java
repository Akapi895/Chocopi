package com.chocopi.controller.admin;

import com.chocopi.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import com.chocopi.dao.BookDAO;
import java.io.File;

public class AdminAddBookController extends abstractAdminSideBar {
    @FXML
    private TextField bookname, author, gerne, publisher, publishyear, rating;

    @FXML
    private TextArea description;

    @FXML
    private Button btnAddPhoto, btnSave, getBtnAddPhoto, btnBack, btnSelectFile;

    @FXML
    private ImageView photo, file;

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

        try {
            int book_id = 0;
            String book_image = photo.getImage().getUrl();
            int book_availableQuantity = 1000;
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
                bookDao.addBook(book);

                Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
                successAlert.setTitle("Success");
                successAlert.setHeaderText("Book Added");
                successAlert.setContentText("The book has been successfully added to the library.");
                successAlert.showAndWait();
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
    private void addPhoto(ActionEvent event) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Photo");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        File selectedFile = fileChooser.showOpenDialog(btnAddPhoto.getScene().getWindow());

        if (selectedFile != null) {
            Image image = new Image(selectedFile.toURI().toString());
            photo.setImage(image);
        }
    }
}