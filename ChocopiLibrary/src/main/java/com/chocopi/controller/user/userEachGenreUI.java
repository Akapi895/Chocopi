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
import java.util.List;

public class userEachGenreUI extends UserSideBarController {
    @FXML
    private GridPane gridPane;

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

    @FXML
    private Button nameBtn1, nameBtn2, nameBtn3, nameBtn4, nameBtn5, nameBtn6, nameBtn7, nameBtn8, nameBtn9, nameBtn10, nameBtn11, nameBtn12, nameBtn13, nameBtn14, nameBtn15;

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

        updatePage();
    }

    private void updatePage() {
        if (books == null || books.isEmpty()) {
            return;
        }

        int startIndex = currentPage * ITEMS_PER_PAGE;
        int endIndex = Math.min(startIndex + ITEMS_PER_PAGE, books.size());

        if (startIndex < 0) {
            startIndex = 0;
        }
        if (endIndex > books.size()) {
            endIndex = books.size();
        }

        if (startIndex >= endIndex) {
            return;
        }

        List<Book> currentItems = books.subList(startIndex, endIndex);

        int bookNum = 1;
        for (Book book : currentItems) {
            switch (bookNum) {
                case 1:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image1.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image1.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }

                    nameBtn1.setText(book.getTitle());
                    nameBtn1.setOnMouseClicked(event -> showBookDetails(book));
                    image1.setOnMouseClicked(event -> showBookDetails(book));
                    break;

                case 2:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image2.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image2.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }

                    nameBtn2.setText(book.getTitle());
                    nameBtn2.setOnMouseClicked(event -> showBookDetails(book));
                    image2.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 3:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image3.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image3.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn3.setText(book.getTitle());
                    nameBtn3.setOnMouseClicked(event -> showBookDetails(book));
                    image3.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 4:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image4.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image4.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn4.setText(book.getTitle());
                    nameBtn4.setOnMouseClicked(event -> showBookDetails(book));
                    image4.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 5:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image5.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image5.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn5.setText(book.getTitle());
                    nameBtn5.setOnMouseClicked(event -> showBookDetails(book));
                    image5.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 6:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image6.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image6.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn6.setText(book.getTitle());
                    nameBtn6.setOnMouseClicked(event -> showBookDetails(book));
                    image6.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 7:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image7.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image7.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn7.setText(book.getTitle());
                    nameBtn7.setOnMouseClicked(event -> showBookDetails(book));
                    image7.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 8:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image8.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image8.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn8.setText(book.getTitle());
                    nameBtn8.setOnMouseClicked(event -> showBookDetails(book));
                    image8.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 9:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image9.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image9.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn9.setText(book.getTitle());
                    nameBtn9.setOnMouseClicked(event -> showBookDetails(book));
                    image9.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 10:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image10.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image10.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn10.setText(book.getTitle());
                    nameBtn10.setOnMouseClicked(event -> showBookDetails(book));
                    image10.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 11:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image11.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image11.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn11.setText(book.getTitle());
                    nameBtn11.setOnMouseClicked(event -> showBookDetails(book));
                    image11.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 12:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image12.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image12.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn12.setText(book.getTitle());
                    nameBtn12.setOnMouseClicked(event -> showBookDetails(book));
                    image12.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 13:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image13.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image13.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn13.setText(book.getTitle());
                    nameBtn13.setOnMouseClicked(event -> showBookDetails(book));
                    image13.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 14:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image14.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image14.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn14.setText(book.getTitle());
                    nameBtn14.setOnMouseClicked(event -> showBookDetails(book));
                    image14.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                case 15:
                    if (book.getImage() != null && !book.getImage().isEmpty()) {
                        image15.setImage(new Image(getClass().getResource(book.getImage()).toExternalForm()));
                    } else {
                        image15.setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
                    }
                    nameBtn15.setText(book.getTitle());
                    nameBtn15.setOnMouseClicked(event -> showBookDetails(book));
                    image15.setOnMouseClicked(event -> showBookDetails(book));
                    break;
                default:
                    break;
            }
            bookNum++;
        }

        previousButton.setVisible(true);
        nextButton.setVisible(true);
        previousButton.setDisable(currentPage == 0);
        nextButton.setDisable(endIndex >= books.size());
    }

    //TODO:
    public void handleSearch(ActionEvent event) {
        String searchText = searchField.getText();

        if (searchText != null && !searchText.trim().isEmpty()) {
            searchBooks(searchText);
        } else {
            System.out.println("Vui lòng nhập từ khóa tìm kiếm.");
        }
    }

    //TODO: xử lý tìm kiếm
    private void searchBooks(String searchText) {
        System.out.println("Đang tìm kiếm sách với từ khóa: " + searchText);
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