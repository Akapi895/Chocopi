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
import javafx.scene.chart.PieChart;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class AdminHomeController implements Initializable {
    @FXML
    private Label noBooks, noGenres, noStudents, noIssuedBooks, pieChartDetails;

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

    @FXML
    private PieChart pieChart;

    private ObservableList<Book> bookList;

    private BookDAO bookDao = new BookDAO();

    private ObservableList<User> userList;

    private UserDAO userDao = new UserDAO();

    private BookManagementDAO bookManagementDao = new BookManagementDAO();

    public void initialize(URL location, ResourceBundle resources) {
        List<User> studentList = userDao.getAllUsers();
        List<Book> bookListTemp = bookDao.getAllBooks();
        List<BookManagement> bookManagementList = bookManagementDao.getAllRecords();

        studentList = studentList.stream()
                .filter(user -> !"admin".equals(user.getRole()))
                .collect(Collectors.toList());
        Set<String> uniqueGenres = bookListTemp.stream()
                .map(Book::getGenre)
                .filter(genre -> genre != null && !genre.isEmpty())
                .collect(Collectors.toSet());
        int uniqueGenresSize = uniqueGenres.size();

        Map<Integer, Long> borrowCountMap = bookManagementList.stream()
                .collect(Collectors.groupingBy(BookManagement::getBookId, Collectors.counting()));
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList(
                borrowCountMap.entrySet().stream()
                        .map(entry -> {
                            int bookId = entry.getKey();
                            long borrowCount = entry.getValue();
                            String bookTitle = bookListTemp.stream()
                                    .filter(book -> book.getBookId() == bookId)
                                    .map(Book::getTitle)
                                    .findFirst()
                                    .orElse("Unknown");
                            return new PieChart.Data(bookTitle, borrowCount);
                        })
                        .collect(Collectors.toList())
        );
        pieChart.setData(pieChartData);
        pieChartData.forEach(data -> {
            data.getNode().setOnMouseEntered(event -> {
                pieChartDetails.setText(String.format("%s, %.0f Times", data.getName(), data.getPieValue()));
            });

            data.getNode().setOnMouseExited(event -> {
                pieChartDetails.setText("");
            });
        });

        noBooks.setText(String.valueOf(bookListTemp.size()));
        noGenres.setText(String.valueOf(uniqueGenresSize));
        noStudents.setText(String.valueOf(studentList.size()));
        noIssuedBooks.setText(String.valueOf(bookManagementList.size()));

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