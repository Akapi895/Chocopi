package com.chocopi.controller.user;

import com.chocopi.dao.BookDAO;
import com.chocopi.dao.BookManagementDAO;
import com.chocopi.dao.LikeDAO;
import com.chocopi.model.Book;
import com.chocopi.model.BookManagement;
import com.chocopi.util.BookSessionManager;
import com.chocopi.util.SessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Date;
import java.time.LocalDate;
import java.util.Objects;

public class UserBookUI {
    @FXML
    private ImageView image;

    @FXML
    private Label titleLabel;

    @FXML
    private Label authorLabel;

    @FXML
    private Label genreLabel;

    @FXML
    private Label ratingLabel;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label publisherLabel;

    @FXML
    private Label publishYearLabel;

    @FXML
    private Button borrowButton;

    @FXML
    private Button likeButton;

    @FXML
    private Button dislikeButton;

    @FXML
    public void initialize() {
        int bookId = BookSessionManager.getBookId();
        Book book = BookDAO.getBookById(bookId);
        assert book != null;
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        genreLabel.setText(book.getGenre());
        ratingLabel.setText(ratingStar(book.getRating()));
        descriptionLabel.setText(book.getDescription());
        publisherLabel.setText(book.getPublisher());
        publishYearLabel.setText(String.valueOf(book.getPublishYear()));

        String imageUrl = book.getImage();
        if (imageUrl != null) {
            Image newImage = new Image(Objects.requireNonNull(getClass().getResource(imageUrl)).toExternalForm());
            image.setImage(newImage);
        } else {
            image.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/chocopi/images/book/0.jpg")).toExternalForm()));
        }
    }

    private String ratingStar(int rating) {
        String rate = "";
        for (int i = 0; i < rating; i++) {
            rate += "⭐";
        }
        return rate;
    }

    @FXML
    private void handleBorrowClick() {
        BookManagement record = new BookManagement();
        int bookId = BookSessionManager.getBookId();
        int userId = SessionManager.getUserId();
        LocalDate borrowDate = LocalDate.now();
        LocalDate returnDate = borrowDate.plusDays(30);
        record.setBookId(bookId);
        record.setUserId(userId);
        record.setBorrowDate(Date.valueOf(borrowDate));
        record.setReturnDate(Date.valueOf(returnDate));

        BookManagementDAO bookManagementDAO = new BookManagementDAO();
        bookManagementDAO.addRecord(record);
    }

    @FXML
    private void handleLikeClick() {
        int userId = SessionManager.getUserId();
        int bookId = BookSessionManager.getBookId();
        boolean handleLike = LikeDAO.addLike(userId, bookId);

        likeButton.setDisable(handleLike);
        likeButton.setVisible(!handleLike);

        dislikeButton.setDisable(!handleLike);
        dislikeButton.setVisible(handleLike);
    }

    @FXML
    private void handleDislikeClick() {
        int userId = SessionManager.getUserId();
        int bookId = BookSessionManager.getBookId();
        boolean handleDislike = LikeDAO.removeLike(userId, bookId);

        likeButton.setDisable(!handleDislike);
        likeButton.setVisible(handleDislike);

        dislikeButton.setDisable(handleDislike);
        dislikeButton.setVisible(!handleDislike);
    }

    /**
     * Handles the Help button click.
     */
    @FXML
    private void handleHelpClick() {
        System.out.println("Help button clicked.");
        // Add logic to show help or FAQs
    }

    @FXML
    private void handleBackClick() {
        try {
            String lastPage = BookSessionManager.getLastPage();
            if (lastPage == null || lastPage.isEmpty()) {
                throw new IllegalStateException("Last page not found in session.");
            }
            BookSessionManager.setLastPage(null);

            FXMLLoader loader = new FXMLLoader(getClass().getResource(lastPage));
            Scene scene = new Scene(loader.load());

            Stage stage = (Stage) image.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Error handling back click: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
