package com.chocopi.controller.admin;

import com.chocopi.dao.BookDAO;
import com.chocopi.dao.BookManagementDAO;
import com.chocopi.dao.UserDAO;
import com.chocopi.model.Book;
import com.chocopi.model.BookManagement;
import com.chocopi.model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class AdminHomeController implements Initializable {
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
    private TableColumn<Book, String> bookNameColumn, authorColumn, gerneColumn;

    private ObservableList<Book> bookList;

    private BookDAO bookDao = new BookDAO();

    private ObservableList<User> userList;

    private UserDAO userDao = new UserDAO();

    public void initialize(URL location, ResourceBundle resources) {
        List<User> studentList = userDao.getAllUsers();
        studentList = studentList.stream()
                .filter(user -> !"admin".equals(user.getRole()))
                .collect(Collectors.toList());

        userList = FXCollections.observableList(studentList);
        bookList = FXCollections.observableList(bookDao.getAllBooks());

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        userNameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        borrowedBooksColumn.setCellValueFactory(new PropertyValueFactory<>("borrowedBooks"));

        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        bookNameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        gerneColumn.setCellValueFactory(new PropertyValueFactory<>("genre"));

        studentTableView.setItems(userList);
        bookTableView.setItems(bookList);
    }

}
