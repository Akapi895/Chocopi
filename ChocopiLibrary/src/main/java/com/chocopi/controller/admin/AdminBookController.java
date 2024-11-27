package com.chocopi.controller.admin;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.chocopi.dao.BookDAO;
import com.chocopi.model.Book;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.IntBinaryOperator;

public class AdminBookController extends abstractAdminSideBar{
    @FXML
    private TableColumn<Book, Integer> bookIdColumn;

    @FXML
    private TableColumn<Book, String> nameColumn;

    @FXML
    private TextField addBook;

    @FXML
    private Button btnAddBook, btnDelete;

    private BookDAO bookDAO = new BookDAO();

    private ObservableList<Book> bookList;
    public void initialize(URL location, ResourceBundle resources) {
//        bookList = FXCollections.observableList(bookDAO.getAllBooks());
//        bookIdColumn.setCellFactory(new PropertyValueFactory<Book, Integer>("bookId"));
//        nameColumn.setCellFactory(new PropertyValueFactory<Book, String>("title"));
    }

    @FXML
    private void handleAddBookClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminAddBook.fxml"));
        try {
            Scene newScene = new Scene(loader.load());
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + "/com/chocopi/fxml/admin/AdminAddBook.fxml");
            e.printStackTrace();
        }
    }
}
