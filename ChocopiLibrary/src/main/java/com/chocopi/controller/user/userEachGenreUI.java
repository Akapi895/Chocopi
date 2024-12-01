package com.chocopi.controller.user;

import com.chocopi.dao.BookDAO;
import com.chocopi.model.Book;
import com.chocopi.util.BookSessionManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class userEachGenreUI extends UserSideBarController {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    private Button previousButton;

    @FXML
    private Button nextButton;

    @FXML
    private Label genreLabel;

    @FXML
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12, image13, image14, image15;
    private List<ImageView> images;

    @FXML
    private Button nameBtn1, nameBtn2, nameBtn3, nameBtn4, nameBtn5, nameBtn6, nameBtn7, nameBtn8, nameBtn9, nameBtn10, nameBtn11, nameBtn12, nameBtn13, nameBtn14, nameBtn15;
    private List<Button> buttons;

    private List<Book> books;
    private int currentPage = 0;
    private final int ITEMS_PER_PAGE = 15;

    @FXML
    public void initialize() {
        String currentGenre = BookSessionManager.getGenre();
        genreLabel.setText(currentGenre);
        currentPage = BookSessionManager.getPage();
        previousButton.setDisable(currentPage == 0);

        searchField.setOnAction(this::handleSearch);

        books = BookDAO.getBooksByGenre(currentGenre);

        images = Arrays.asList(
                image1, image2, image3, image4, image5, image6, image7, image8,
                image9, image10, image11, image12, image13, image14, image15
        );

        buttons = Arrays.asList(
                nameBtn1, nameBtn2, nameBtn3, nameBtn4, nameBtn5, nameBtn6, nameBtn7,
                nameBtn8, nameBtn9, nameBtn10, nameBtn11, nameBtn12, nameBtn13, nameBtn14, nameBtn15
        );

        updatePage();
    }


    private void updatePage() {
        for (int i = 0 ; i < ITEMS_PER_PAGE; ++i) {
            images.get(i).setVisible(false);
            images.get(i).setDisable(true);
            buttons.get(i).setVisible(false);
            buttons.get(i).setDisable(true);
        }

        if (books == null || books.isEmpty()) {
            return;
        }

        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, books.size());

        if (startIndex < 0) {
            startIndex = 0;
        }

        if (startIndex >= endIndex) {
            return;
        }

        List<Book> currentItems = books.subList(startIndex, endIndex);

        for (int i = 0; i < ITEMS_PER_PAGE; i++) {
            if (i >= currentItems.size()) {
                images.get(i).setVisible(false);
                images.get(i).setDisable(true);
                buttons.get(i).setVisible(false);
                buttons.get(i).setDisable(true);
            } else {
                images.get(i).setVisible(true);
                images.get(i).setDisable(false);
                buttons.get(i).setVisible(true);
                buttons.get(i).setDisable(false);

                Book book = currentItems.get(i);

                if (book.getImage() != null && !book.getImage().isEmpty()) {
                    images.get(i).setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                } else {
                    images.get(i).setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                }
                buttons.get(i).setText(book.getTitle());

                buttons.get(i).setOnMouseClicked(event -> showBookDetails(book));
                images.get(i).setOnMouseClicked(event -> showBookDetails(book));
            }
        }

        previousButton.setVisible(true);
        nextButton.setVisible(true);
        previousButton.setDisable(currentPage == 0);
        nextButton.setDisable(endIndex >= books.size());
    }

    public void handleSearch(ActionEvent event) {
        String searchText = searchField.getText();
        BookSessionManager.setPage(0);

        if (searchText != null && !searchText.trim().isEmpty()) {
            searchBooks(searchText);
        } else {
            System.out.println("Vui lòng nhập từ khóa tìm kiếm.");
        }
    }

    private void searchBooks(String searchText) {
        BookSessionManager.setSearch(searchText);
        BookSessionManager.setLastPage("/com/chocopi/fxml/userEachGenreUI.fxml");
        BookSessionManager.setLabelTitle("Searching");
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserAddition.fxml"));
            Parent root = fxmlLoader.load();

            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void showBookDetails(Book selectedBook) {
        try {
            BookSessionManager.setBookId(selectedBook.getBookId());
            BookSessionManager.setGenre(selectedBook.getGenre());
            BookSessionManager.setPage(currentPage);
            BookSessionManager.setLastPage("/com/chocopi/fxml/user/userEachGenre.fxml");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserBook.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) mainPane.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void showPreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            BookSessionManager.setPage(currentPage);
            previousButton.setDisable(currentPage == 0);
            updatePage();
        }
    }

    @FXML
    private void showNextPage() {
        if ((currentPage + 1) * ITEMS_PER_PAGE < books.size()) {
            BookSessionManager.setPage(currentPage);
            currentPage++;
            nextButton.setDisable((currentPage + 1) * ITEMS_PER_PAGE >= books.size());
            updatePage();
        }
    }
}