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
    private Button borrowBtn, returnBtn;

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
        System.out.println(imageUrl);
        if (imageUrl != null) {
            image.setImage(new Image(getClass().getResource(imageUrl).toExternalForm()));
        } else {
            System.out.println("Error finding image");
            image.setImage(new Image(Objects.requireNonNull(getClass().getResource("/com/chocopi/images/book/0.jpg")).toExternalForm()));
        }

        int userId = SessionManager.getUserId();
        if (BookManagementDAO.checkBorrowed(userId, bookId)) {
            borrowBtn.setDisable(true);
            borrowBtn.setVisible(false);
            returnBtn.setDisable(false);
            returnBtn.setVisible(true);
        }
        if (LikeDAO.isBookLikedByUser(userId, bookId)) {
            likeButton.setDisable(true);
            likeButton.setVisible(false);
            dislikeButton.setDisable(false);
            dislikeButton.setVisible(true);
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

        borrowBtn.setDisable(true);
        borrowBtn.setVisible(false);
        returnBtn.setDisable(false);
        returnBtn.setVisible(true);

        BookManagementDAO bookManagementDAO = new BookManagementDAO();
        bookManagementDAO.addRecord(record);
    }

    @FXML
    private void handleReturnClick() {
        int userId = SessionManager.getUserId();
        int bookId = BookSessionManager.getBookId();
        int recordId = BookManagementDAO.getRecordIdByUserIdAndBookId(userId, bookId);
        boolean del = BookManagementDAO.deleteRecord(recordId);
        if (del) {
            borrowBtn.setDisable(false);
            borrowBtn.setVisible(true);

            returnBtn.setDisable(true);
            returnBtn.setVisible(false);
        }
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
