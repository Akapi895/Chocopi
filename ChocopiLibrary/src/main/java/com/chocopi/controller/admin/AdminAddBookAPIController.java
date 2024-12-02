package com.chocopi.controller.admin;

import com.chocopi.dao.BookDAO;
import com.chocopi.dao.BookManagementDAO;
import com.chocopi.dao.LikeDAO;
import com.chocopi.model.Book;
import com.chocopi.service.BookService;
import com.chocopi.util.BookSessionManager;
import com.chocopi.util.SessionManager;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AdminAddBookAPIController {
    @FXML
    private AnchorPane mainPane;

    @FXML
    private TextField searchField;

    @FXML
    private Label titleLabel;

    @FXML
    private ImageView image1, image2, image3, image4, image5, image6, image7, image8, image9, image10, image11, image12, image13, image14, image15, image16, image17, image18;
    private List<ImageView> images;

    @FXML
    private Button nameBtn1, nameBtn2, nameBtn3, nameBtn4, nameBtn5, nameBtn6, nameBtn7, nameBtn8, nameBtn9, nameBtn10, nameBtn11, nameBtn12, nameBtn13, nameBtn14, nameBtn15, nameBtn16, nameBtn17, nameBtn18;
    private List<Button> nameBtns;

    private final int ITEMS_PER_PAGE = 18;

    private List<Book> genBooks;

    @FXML
    public void initialize() {
        titleLabel.setText("Searching for books...");

        String query = BookSessionManager.getSearch();
        if (query == null || query.trim().isEmpty()) {
            return;
        }

        // Tạo một Task để gọi API trong background thread
        Task<List<Book>> fetchBooksTask = new Task<>() {
            @Override
            protected List<Book> call() throws Exception {
                return BookService.fetchBooksAdmin(query);
            }
        };

        // Xử lý kết quả sau khi Task hoàn thành
        fetchBooksTask.setOnSucceeded(event -> {
            genBooks = fetchBooksTask.getValue();

            if (genBooks == null || genBooks.isEmpty()) {
                titleLabel.setText("No books found for: " + query);
                return;
            }

            titleLabel.setText("Searching for: " + query);

            // Gắn các thuộc tính giao diện
            images = Arrays.asList(
                    image1, image2, image3, image4, image5, image6, image7, image8, image9,
                    image10, image11, image12, image13, image14, image15, image16, image17, image18
            );

            nameBtns = Arrays.asList(
                    nameBtn1, nameBtn2, nameBtn3, nameBtn4, nameBtn5, nameBtn6, nameBtn7,
                    nameBtn8, nameBtn9, nameBtn10, nameBtn11, nameBtn12, nameBtn13,
                    nameBtn14, nameBtn15, nameBtn16, nameBtn17, nameBtn18
            );

            for (int i = 0; i < ITEMS_PER_PAGE; ++i) {
                if (images != null && images.size() > i && images.get(i) != null) {
                    images.get(i).setVisible(false);
                    images.get(i).setDisable(true);
                }
                if (nameBtns != null && nameBtns.size() > i && nameBtns.get(i) != null) {
                    nameBtns.get(i).setVisible(false);
                    nameBtns.get(i).setDisable(true);
                }
            }

            updatePage();
        });

        // Xử lý lỗi nếu xảy ra
        fetchBooksTask.setOnFailed(event -> {
            titleLabel.setText("An error occurred while searching for books.");
            fetchBooksTask.getException().printStackTrace();
        });

        // Chạy Task trong một luồng riêng
        Thread thread = new Thread(fetchBooksTask);
        thread.setDaemon(true); // Đảm bảo thread sẽ dừng khi ứng dụng kết thúc
        thread.start();

        searchField.setOnAction(this::handleSearch);
    }


    private void updatePage() {
        if (genBooks == null || genBooks.isEmpty()) {
            return;
        }

        for (int i = 0; i < genBooks.size(); ++i) {
            if (images != null && images.size() > i && images.get(i) != null) {
                images.get(i).setVisible(true);
                images.get(i).setDisable(false);
            }
            if (nameBtns != null && nameBtns.size() > i && nameBtns.get(i) != null) {
                nameBtns.get(i).setVisible(true);
                nameBtns.get(i).setDisable(false);
            }

            Book book = genBooks.get(i);

            String imagePath = (book.getImage() != null && !book.getImage().isEmpty())
                    ? book.getImage()
                    : "/com/chocopi/images/book/0.jpg";
//            System.out.println(i + ". " + imagePath);

            URL resourceUrl = getClass().getResource(imagePath);
            if (resourceUrl != null) {
                images.get(i).setImage(new Image(resourceUrl.toExternalForm()));
            } else {
                images.get(i).setImage(new Image(getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm()));
            }

            nameBtns.get(i).setText(book.getTitle());
            nameBtns.get(i).setOnMouseClicked(event -> pickBook(book));
            images.get(i).setOnMouseClicked(event -> pickBook(book));
        }
    }

    private void pickBook(Book selectedBook) {
        BookSessionManager.setBook(selectedBook);
        handleMethod1();
    }

    @FXML
    public void handleSearch(ActionEvent event) {
        String searchText = searchField.getText();

        if (searchText != null && !searchText.trim().isEmpty()) {
            BookSessionManager.setSearch(searchText);
            initialize();
        } else {
            System.out.println("Vui lòng nhập từ khóa tìm kiếm.");
        }
    }

    @FXML
    private void handleMethod1() {
        BookSessionManager.setSearch(null);
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminAddBook.fxml"));

        try {
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/admin/AdminAddBook.css").toExternalForm());

            Stage stage = (Stage) titleLabel.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void handleMethod2() {

    }

    @FXML
    private void handleBackClick() {
        handleMethod1();
    }
}
