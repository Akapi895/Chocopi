package com.chocopi.controller.admin;

import com.chocopi.model.Book;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import com.chocopi.dao.BookDAO;
import java.io.File;
import java.util.Random;

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
        Random rand = new Random();
        int book_id = 0;
        String book_image = new String();
        int book_availableQuantity = 1000;
        String book_title = bookname.getText();
        String book_author = author.getText();
        String book_description = description.getText();
        String book_gerne = gerne.getText();
        String book_publisher = publisher.getText();
        int book_publisheryear = Integer.parseInt(publishyear.getText());
        int book_rating = Integer.parseInt(rating.getText());

        Book book = new Book(book_id , book_title, book_description, book_image, book_gerne, book_rating, book_availableQuantity, book_author, book_publisheryear, book_publisher);
        BookDAO bookDao = new BookDAO();
        if (!bookDao.isBookExists(book.getTitle())) {
            bookDao.addBook(book);
        }
    }

    @FXML
    private void selectFile(ActionEvent event) {
//        FileChooser fileChooser = new FileChooser();
//        fileChooser.setTitle("Select File");
//
//        fileChooser.getExtensionFilters().addAll(
//                new FileChooser.ExtensionFilter("Files", "*.docx", "*.jpg", "*.txt")
//        );
//
//        File selectedFile = fileChooser.showOpenDialog(btnSelectFile.getScene().getWindow());
//
//        if (selectedFile != null) {
//            File image = new Image(selectedFile.toURI().toString());
//            file.setImage(image);
//        }
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
