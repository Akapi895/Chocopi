package com.chocopi.controller.admin;

import com.chocopi.dao.BookManagementDAO;
import com.chocopi.model.BookManagement;
import com.chocopi.model.User;
import com.chocopi.util.BookSessionManager;
import com.chocopi.util.SessionManager;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import com.chocopi.dao.BookDAO;
import com.chocopi.model.Book;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class AdminBookController implements Initializable {
    @FXML
    private TableView<Book> bookTableView;

    @FXML
    private TableColumn<Book, Integer> bookIdColumn, timesBorrowedColumn;

    @FXML
    private TableColumn<Book, String> nameColumn, statusColumn;

    @FXML
    private TextField bookId;

    @FXML
    private Button btnAddBook, btnDelete;

    private BookDAO bookDao = new BookDAO();
    private BookManagementDAO bookManagementDao = new BookManagementDAO();
    private ObservableList<Book> bookList;
    LocalDate localDate = LocalDate.now();

    public void initialize(URL location, ResourceBundle resources) {
        BookSessionManager.clearBookSession();
        bookList = FXCollections.observableList(bookDao.getAllBooks());
        List<BookManagement> bookManagementList = new ArrayList<>();
        bookManagementList = bookManagementDao.getAllRecords();
        List<BookManagement> finalBookManagementList = bookManagementList;

        bookIdColumn.setCellValueFactory(new PropertyValueFactory<>("bookId"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        List<BookManagement> finalBookManagementList1 = bookManagementList;
        statusColumn.setCellValueFactory(cellData -> {
            Book book = cellData.getValue();

            boolean status = finalBookManagementList1.stream()
                    .filter(record -> record.getBookId() == book.getBookId())
                    .anyMatch(record -> record.getReturnDate() != null && record.getReturnDate().after(Date.valueOf(localDate)));

            return new ReadOnlyObjectWrapper<>(status ? "Borrowed" : "No record currently");
        });

        timesBorrowedColumn.setCellValueFactory(cellData -> {
            Book book = cellData.getValue();

            long borrowedCount = finalBookManagementList.stream()
                    .filter(record -> record.getBookId() == book.getBookId())
                    .count();

            return new ReadOnlyObjectWrapper<>((int) borrowedCount);
        });

        FilteredList<Book> filteredBooks = new FilteredList<>(bookList, b -> true);
        bookId.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredBooks.setPredicate(book -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                try {
                    int enteredBookId = Integer.parseInt(newValue);
                    return book.getBookId() == enteredBookId;
                } catch (NumberFormatException e) {
                    return false;
                }
            });
        });

        bookTableView.setItems(filteredBooks);
    }

    @FXML
    private void handleAddBookClick(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminAddBook.fxml"));
        try {
            Scene newScene = new Scene(loader.load());
            newScene.getStylesheets().add(getClass().getResource("/com/chocopi/css/admin/AdminAddBook.css").toExternalForm());

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(newScene);
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading FXML file: " + "/com/chocopi/fxml/admin/AdminAddBook.fxml");
            e.printStackTrace();
        }
    }

    @FXML
    private void handleDeleteClick(ActionEvent e) {
        Book selectedBook = bookTableView.getSelectionModel().getSelectedItem();
        bookList.remove(selectedBook);
        bookDao.deleteBook(selectedBook.getBookId());
    }


    @FXML
    public void handleEditClick(ActionEvent e) {
        Book selected = bookTableView.getSelectionModel().getSelectedItem();
        selected = BookDAO.getBookByTitle(selected.getTitle());

        if (selected != null) {
            bookList.remove(selected);
            BookSessionManager.setBook(selected);

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/admin/AdminEditBook.fxml"));
            try {
                Parent root = loader.load();
                Stage stage = (Stage) ((Node) e.getSource()).getScene().getWindow();

                Scene scene = new Scene(root);
                scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/admin/AdminEditBook.css").toExternalForm());
                scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/admin/SideBar.css").toExternalForm());

                stage.setScene(scene);
                stage.show();
            } catch (IOException ex) {
                ex.printStackTrace();
                throw new RuntimeException("Error loading the edit book page", ex);
            }
        } else {
            System.out.println("No book selected for editing.");
        }
    }
}