package com.chocopi.controller.user;

import com.chocopi.dao.BookDAO;
import com.chocopi.dao.BookManagementDAO;
import com.chocopi.dao.LikeDAO;
import com.chocopi.model.Book;
import com.chocopi.service.OpenAIChatClient;
import com.chocopi.util.BookSessionManager;
import com.chocopi.util.SessionManager;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserHistoryUI extends UserSideBarController {
    // Borrowed books
    @FXML
    private ImageView borrow1, borrow2, borrow3, borrow4, borrow5;
    private List<ImageView> brwImages = new ArrayList<>();
    @FXML
    private Button brwBtn1, brwBtn2, brwBtn3, brwBtn4, brwBtn5;
    private List<Button> brwBtns = new ArrayList<Button>();

    // Interest
    @FXML
    private ImageView interest1, interest2, interest3, interest4, interest5;
    private List<ImageView> interestBtns = new ArrayList<>();
    @FXML
    private Button favor1, favor2, favor3, favor4, favor5;
    private List<Button> favors = new ArrayList<Button>();

    @FXML
    private Button moreBrw, moreInterest;

    // Question section
    @FXML
    private TextArea questionInput, questionOutput;
    @FXML
    private Button sendRequestButton;

    //TODO
    @FXML
    private void handleSendRequest() {
        String question = questionInput.getText().trim();
        if (!question.isEmpty()) {
            questionOutput.setText("Loading...");

            // Tạo một Task để gọi OpenAI API trong luồng riêng
            Task<String> task = new Task<>() {
                @Override
                protected String call() throws Exception {
                    return OpenAIChatClient.handleUserQuestion(question);
                }
            };

            task.setOnSucceeded(event -> {
                questionOutput.setText(task.getValue());
            });

            task.setOnFailed(event -> {
                questionOutput.setText("An error occurred while processing the request.");
                task.getException().printStackTrace();
            });

            Thread thread = new Thread(task);
            thread.setDaemon(true);
            thread.start();
        } else {
            questionOutput.setText("Please type a question before sending.");
        }
    }


    @FXML
    private void handleBorrowMore() {
        try {
            BookSessionManager.setPage(0);
            BookSessionManager.setLabelTitle("Borrowed Books");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserAddition.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) interest1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleInterestMore() {
        try {
            BookSessionManager.setPage(0);
            BookSessionManager.setLabelTitle("Interest");
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserAddition.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) interest1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {
        List<Integer> brwBookId = BookManagementDAO.getBookIdByUserId(SessionManager.getUserId());
        if (brwBookId.isEmpty()) {
            moreBrw.setDisable(true);
        }
        brwImages = Arrays.asList(borrow1, borrow2, borrow3, borrow4, borrow5);
        brwBtns = Arrays.asList(brwBtn1, brwBtn2, brwBtn3, brwBtn4, brwBtn5);

        for (int i = 0 ; i < 5; ++i) {
            if (i >= brwBookId.size()) {
                brwImages.get(i).setVisible(false);
                brwBtns.get(i).setVisible(false);
                brwBtns.get(i).setDisable(true);
            } else {
                Integer bookId = brwBookId.get(i);
                Book book = BookDAO.getBookById(bookId);
                String imagePath = book.getImage();
                try {
                    brwImages.get(i).setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
                } catch (Exception e) {
                    brwImages.get(i).setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                }
                brwBtns.get(i).setDisable(false);
                brwBtns.get(i).setVisible(true);
                brwBtns.get(i).setText(book.getTitle());
                brwImages.get(i).setOnMouseClicked(event -> showBookDetails(book));
                brwBtns.get(i).setOnMouseClicked(event -> showBookDetails(book));
            }

        }

        List<Integer> likeBookId = LikeDAO.getLikedBooksByUser(SessionManager.getUserId());
        if (likeBookId.isEmpty()) {
            moreInterest.setDisable(true);
        }
        interestBtns = Arrays.asList(interest1, interest2, interest3, interest4, interest5);
        favors = Arrays.asList(favor1, favor2, favor3, favor4, favor5);

        for (int i = 0 ; i < 5; ++i) {
            if (i >= likeBookId.size()) {
                interestBtns.get(i).setVisible(false);
                favors.get(i).setVisible(false);
                favors.get(i).setDisable(true);
            } else {
                Integer bookId = likeBookId.get(i);
                Book book = BookDAO.getBookById(bookId);
                String imagePath = book.getImage();
                try {
                    interestBtns.get(i).setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
                } catch (Exception e) {
                    interestBtns.get(i).setImage(new Image(getClass().getResource("/com/chocopi/images/book/1.jpg").toExternalForm()));
                }
                interestBtns.get(i).setDisable(false);
                interestBtns.get(i).setVisible(true);
                favors.get(i).setDisable(false);
                favors.get(i).setVisible(true);
                favors.get(i).setText(book.getTitle());
                favors.get(i).setOnMouseClicked(event -> showBookDetails(book));
                interestBtns.get(i).setOnMouseClicked(event -> showBookDetails(book));
            }
        }
    }

    private void showBookDetails(Book selectedBook) {
        try {
            BookSessionManager.setBookId(selectedBook.getBookId());
            BookSessionManager.setGenre(selectedBook.getGenre());
            BookSessionManager.setLastPage("/com/chocopi/fxml/user/UserHistory.fxml");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserBook.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) interest1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
