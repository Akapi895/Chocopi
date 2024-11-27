package com.chocopi.controller.admin;

import com.chocopi.dao.BookManagementDAO;
import com.chocopi.model.BookManagement;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.control.cell.PropertyValueFactory;

public class AdminStudentController implements Initializable{
    @FXML
    private TextField userId;

    @FXML
    private TableColumn<BookManagement, Integer> userIdColumn;

    @FXML
    private TableColumn<BookManagement, Integer> bookIdColumn;

    @FXML
    private TableColumn<BookManagement, String> nameColumn;

    @FXML
    private TableColumn<BookManagement, Date> borrowDateColumn;

    @FXML
    private TableColumn<BookManagement, Date> returnDateColumn;

    @FXML
    private TableView<BookManagement> recordTableView;

    private ObservableList<BookManagement> bookManagementsList;

    private BookManagementDAO bookManagementDao = new BookManagementDAO();

    public void initialize(URL location, ResourceBundle resources) {
        List<BookManagement> records = bookManagementDao.getAllRecords();

        bookManagementsList = FXCollections.observableList(records);

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
//        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));

        recordTableView.setItems(bookManagementsList);
    }
}
