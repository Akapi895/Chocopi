package com.chocopi.controller.admin;

import com.chocopi.dao.BookManagementDAO;
import com.chocopi.dao.UserDAO;
import com.chocopi.model.BookManagement;
import com.chocopi.model.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import java.net.URL;
import java.sql.Date;
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
    private UserDAO userDao = new UserDAO();

    public void initialize(URL location, ResourceBundle resources) {
        List<BookManagement> records = bookManagementDao.getAllRecords();
        List<User> userList = userDao.getAllUsers();
        bookManagementsList = FXCollections.observableList(records);

        FilteredList<BookManagement> filteredList = new FilteredList<>(bookManagementsList, b -> true);

        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        borrowDateColumn.setCellValueFactory(new PropertyValueFactory<>("borrowDate"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("returnDate"));
        nameColumn.setCellValueFactory(cellData -> {
            BookManagement record = cellData.getValue();
            int userId = record.getUserId();

            User user = userList.stream()
                    .filter(u -> u.getUserId() == userId)
                    .findFirst()
                    .orElse(null);

            return new SimpleStringProperty(user != null ? user.getName() : "");
        });

        recordTableView.setItems(filteredList);

        userId.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredList.setPredicate(record -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filter = newValue.toLowerCase();
                return Integer.toString(record.getUserId()).contains(filter);
            });
        });
    }

    public void handleDeleteClick(ActionEvent e) {
        BookManagement selected = recordTableView.getSelectionModel().getSelectedItem();
        bookManagementsList.remove(selected);
        bookManagementDao.deleteRecord(selected.getRecordId());
    }
}