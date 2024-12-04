package com.chocopi.controller.admin;

import com.chocopi.dao.BookManagementDAO;
import com.chocopi.dao.UserDAO;
import com.chocopi.model.BookManagement;
import com.chocopi.model.User;
import com.chocopi.util.SessionManager;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.List;
import java.util.ResourceBundle;

import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

public class AdminStudentController implements Initializable{
    @FXML
    private TextField userId;

    @FXML
    private TableColumn<User, Integer> userIdColumn;

    @FXML
    private TableColumn<User, Integer> ageColumn, totalBorrowedColumn;

    @FXML
    private TableColumn<User, String> nameColumn, phoneColumn, emailColumn;

    @FXML
    private TableView<User> userTableView;

    private ObservableList<User> userObservableList;
    private UserDAO userDao = new UserDAO();

    public void initialize(URL location, ResourceBundle resources) {
        SessionManager.clearSession();
        userIdColumn.setCellValueFactory(new PropertyValueFactory<>("userId"));
        ageColumn.setCellValueFactory(new PropertyValueFactory<>("age"));
        totalBorrowedColumn.setCellValueFactory(new PropertyValueFactory<>("totalBorrowed"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        phoneColumn.setCellValueFactory(new PropertyValueFactory<>("phone"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        List<User> users = userDao.getAllUsers();
        userObservableList = FXCollections.observableArrayList(users);
        FilteredList<User> filteredUsers = new FilteredList<>(userObservableList, user -> "user".equals(user.getRole()));

        userId.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredUsers.setPredicate(user -> {
                if (newValue == null || newValue.isEmpty()) {
                    return "user".equals(user.getRole());
                }
                try {
                    int inputId = Integer.parseInt(newValue);
                    return "user".equals(user.getRole()) && user.getUserId() == inputId;
                } catch (NumberFormatException e) {
                    return false;
                }
            });
        });

        userTableView.setItems(filteredUsers);
    }

    @FXML
    public void handleDeleteClick(ActionEvent e) {
        User selected = userTableView.getSelectionModel().getSelectedItem();
        userObservableList.remove(selected);
        userDao.deleteUser(selected.getUserId());
    }

    @FXML
    public void handleEditClick(ActionEvent e) {
        User selected = userTableView.getSelectionModel().getSelectedItem();

        if (selected != null) {
            userObservableList.remove(selected);
            SessionManager.setUser(selected);

            // Tạo loader cho trang chỉnh sửa người dùng
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminEditUser.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/admin/AdminEditUser.css").toExternalForm());
                scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/admin/SideBar.css").toExternalForm());

                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Error loading the edit user page", ex);
            }
        } else {
            System.out.println("No user selected for editing.");
        }
    }

}