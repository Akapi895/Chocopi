package com.chocopi.controller.user;

import com.chocopi.dao.BookDAO;
import com.chocopi.model.Book;
import com.chocopi.util.BookSessionManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class UserHomeUI {
    @FXML
    private TextField searchField;

    @FXML
    private Label detailGenre1, detailGenre2, detailGenre3;

    @FXML
    private Label genre1, genre2, genre3;

    @FXML
    private ImageView imageView1, imageView2, imageView3, imageView4, imageView5, imageView6;
    @FXML
    private ImageView imageView7, imageView8, imageView9, imageView10, imageView11, imageView12;
    @FXML
    private ImageView imageView13, imageView14, imageView15, imageView16, imageView17, imageView18;

    private List<ImageView> imageViewList = new ArrayList<>();

    @FXML
    private void onPage1Clicked() {
        BookSessionManager.clearBookSession();
        BookSessionManager.setPage(1);
        genre1.setText("Computers");
        genre2.setText("Business");
        genre3.setText("Education");

        initialize();
    }

    private void Page1() {
        BookSessionManager.clearBookSession();
        BookSessionManager.setPage(1);
        genre1.setText("Computers");
        genre2.setText("Business");
        genre3.setText("Education");
    }

    @FXML
    private void onPage2Clicked() {
        BookSessionManager.clearBookSession();
        BookSessionManager.setPage(2);
        genre1.setText("Self - help");
        genre2.setText("Fiction");
        genre3.setText("Others");

        initialize();
    }

    private void Page2() {
        BookSessionManager.clearBookSession();
        BookSessionManager.setPage(2);
        genre1.setText("Self - help");
        genre2.setText("Fiction");
        genre3.setText("Others");
    }


    private void showDetailGenre(String genre) {
        try {
            BookSessionManager.clearBookSession();
            BookSessionManager.setGenre(genre);
            BookSessionManager.setLastPage("/com/chocopi/fxml/user/UserHome.fxml");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/userEachGenre.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) detailGenre1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    private void initialize() {
        if (BookSessionManager.getPage() == 1) {
            Page1();
        } else {
            Page2();
        }

        imageViewList = Arrays.asList(
                imageView1, imageView2, imageView3, imageView4, imageView5, imageView6,
                imageView7, imageView8, imageView9, imageView10, imageView11, imageView12,
                imageView13, imageView14, imageView15, imageView16, imageView17, imageView18
        );

        detailGenre1.setOnMouseClicked(event -> showDetailGenre(genre1.getText()));
        detailGenre2.setOnMouseClicked(event -> showDetailGenre(genre2.getText()));
        detailGenre3.setOnMouseClicked(event -> showDetailGenre(genre3.getText()));

        List<Book> books = new ArrayList<>();
        if (genre1 != null) books.addAll(BookDAO.get6BookImagesByGenre(genre1.getText()));
        if (genre2 != null) books.addAll(BookDAO.get6BookImagesByGenre(genre2.getText()));
        if (genre3 != null) books.addAll(BookDAO.get6BookImagesByGenre(genre3.getText()));

        int cnt = 0;
        for (Book book : books) {
            String imagePath;
            try {
                if (book.getImage() != null && !book.getImage().isEmpty()) {
                    imagePath = getClass().getResource(book.getImage()).toExternalForm();
                } else {
                    imagePath = getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm();
                }
            } catch (Exception e) {
                imagePath = getClass().getResource("/com/chocopi/images/book/0.jpg").toExternalForm();
            }
            imageViewList.get(cnt).setImage(new Image(imagePath));
            imageViewList.get(cnt).setOnMouseClicked(mouseEvent -> {
                if (book != null) {
                    showDetailBook(book);
                }
            });
            cnt++;
        }
    }

    private void showDetailBook(Book book) {
        try {
            BookSessionManager.setBookId(book.getBookId());
            BookSessionManager.setGenre(book.getGenre());
            BookSessionManager.setLastPage("/com/chocopi/fxml/user/UserHome.fxml");

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/UserBook.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) detailGenre1.getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
