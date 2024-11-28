package com.chocopi.controller.admin;

import com.chocopi.dao.BookDAO;
import com.chocopi.dao.UserDAO;
import com.chocopi.model.Book;
import com.chocopi.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminHomeController implements Initializable {
    @FXML
    private Label noBooks, noGenres, noStudents, noIssuedBooks;

    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableView<User> studentTableView;

    @FXML
    private TableColumn<User, Integer> userIdColumn, borrowedBooksColumn;

    @FXML
    private TableColumn<User, String> userNameColumn, emailColumn;

    @FXML
    private TableColumn<Book, Integer> bookIdColumn;

    @FXML
    private TableColumn<Book, String> bookNameColumn, authorColumn, genreColumn;

    private ObservableList<Book> bookList;

    private BookDAO bookDao = new BookDAO();

    private ObservableList<User> userList;

    private UserDAO userDao = new UserDAO();

    public void initialize(URL location, ResourceBundle resources) {
        List<User> studentList = userDao.getAllUsers();
        List<Book> bookListTemp = bookDao.getAllBooks();
        studentList = studentList.stream()
                .filter(user -> !"admin".equals(user.getRole()))
                .collect(Collectors.toList());
        Set<String> uniqueGenres = bookListTemp.stream()
                .map(Book::getGenre)
                .filter(genre -> genre != null && !genre.isEmpty())
                .collect(Collectors.toSet());
        int uniqueGenresSize = uniqueGenres.size();

        noBooks.setText(String.valueOf(bookListTemp.size()));
        noGenres.setText(String.valueOf(uniqueGenresSize));
        noStudents.setText(String.valueOf(studentList.size()));
        noIssuedBooks.setText(String.valueOf(studentList.stream()
                .mapToInt(User::getTotalBorrowed)
                .sum()));

        userList = FXCollections.observableList(studentList);
        bookList = FXCollections.observableList(bookListTemp);

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        borrowedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("totalBorrowed"));

        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        genreColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        bookTableView.setItems(bookList);
        studentTableView.setItems(userList);
    }

}