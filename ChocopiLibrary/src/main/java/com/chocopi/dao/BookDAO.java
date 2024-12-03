package com.chocopi.dao;

import com.chocopi.model.Book;
import com.chocopi.util.DatabaseConnection;
import com.chocopi.service.BookService;
import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id")); // Cột trong DB: book_id
                book.setTitle(rs.getString("title"));
                book.setDescription(rs.getString("description"));
                book.setAuthor(rs.getString("author"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishYear(rs.getInt("publishYear")); // Cột trong DB: publish_year
                book.setGenre(rs.getString("genre"));
                book.setRating(rs.getInt("rating"));
                book.setAvailableQuantity(rs.getInt("available_quantity")); // Cột trong DB: available_quantity

                books.add(book);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public boolean isBookExists(String bookTitle) {
        String sql = "SELECT COUNT(*) FROM books WHERE title = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, bookTitle);

            try (ResultSet resultSet = pstmt.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while checking if book exists.", e);
        }
        return false;
    }

    public static String getAllBookIdandTitle() {
        String sql = "SELECT book_id, title FROM Books";
        StringBuilder books = new StringBuilder();
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String book = rs.getInt("book_id") + ". " + rs.getString("title");
                if (books.length() > 0) books.append("; ");
                books.append(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching book IDs and titles.", e);
        }

        return books.toString();
    }

    public static int lastBookId() {
        String query = "SELECT MAX(book_id) FROM books";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            if (resultSet.next()) {
                return resultSet.getInt(1);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error while fetching the last book ID.", e);
        }
        return 0; // Trường hợp không tìm thấy giá trị
    }

    public static Book getBookById(int bookId) {
        String sql = "SELECT * FROM Books WHERE book_id = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setDescription(rs.getString("description"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setPublishYear(rs.getInt("publishYear"));
                    book.setGenre(rs.getString("genre"));
                    book.setRating(rs.getInt("rating"));
                    book.setAvailableQuantity(rs.getInt("available_quantity"));
                    book.setImage(rs.getString("image"));
                    return book;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean addBook(Book book) {
        String sql = "INSERT INTO Books (book_id, title, description, author, publisher, publishYear, " +
                "genre, rating, available_quantity, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, book.getBookId());
            pstmt.setString(2, book.getTitle());
            pstmt.setString(3, book.getDescription());
            pstmt.setString(4, book.getAuthor());
            pstmt.setString(5, book.getPublisher());
            pstmt.setInt(6, book.getPublishYear());
            pstmt.setString(7, book.getGenre());
            pstmt.setInt(8, book.getRating());
            pstmt.setInt(9, book.getAvailableQuantity());
            pstmt.setString(10, book.getImage());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateBook(Book book) {
        String sql = "UPDATE Books SET title = ?, description = ?, author = ?, publisher = ?," +
                " publishYear = ?, genre = ?, rating = ?, available_quantity = ? WHERE book_id = ?";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getDescription());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getPublishYear());
            pstmt.setString(6, book.getGenre());
            pstmt.setInt(7, book.getRating());
            pstmt.setInt(8, book.getAvailableQuantity());
            pstmt.setInt(9, book.getBookId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteBook(int bookId) {
        String sql = "DELETE FROM Books WHERE book_id = ?";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    Book book = new Book();
                    book.setBookId(rs.getInt("book_id"));
                    book.setTitle(rs.getString("title"));
                    book.setDescription(rs.getString("description"));
                    book.setAuthor(rs.getString("author"));
                    book.setPublisher(rs.getString("publisher"));
                    book.setPublishYear(rs.getInt("publishYear"));
                    book.setGenre(rs.getString("genre"));
                    book.setRating(rs.getInt("rating"));
                    book.setAvailableQuantity(rs.getInt("available_quantity"));
                    book.setImage(rs.getString("image"));

                    // Kiểm tra từ khóa
                    if (matchesKeyword(book, keyword)) {
                        books.add(book);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    // Phương thức kiểm tra từ khóa
    private static boolean matchesKeyword(Book book, String keyword) {
        String lowerKeyword = keyword.toLowerCase();
        return (book.getTitle() != null && book.getTitle().toLowerCase().contains(lowerKeyword)) ||
                (book.getAuthor() != null && book.getAuthor().toLowerCase().contains(lowerKeyword)) ||
                (book.getGenre() != null && book.getGenre().toLowerCase().contains(lowerKeyword));
    }

    public static List<Book> getBooksByGenre(String genre) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE genre = ?";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, genre);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                book.setImage(rs.getString("image"));
                book.setDescription(rs.getString("description"));
                book.setPublisher(rs.getString("publisher"));
                book.setPublishYear(rs.getInt("publishYear"));
                book.setRating(rs.getInt("rating"));
                book.setAvailableQuantity(rs.getInt("available_quantity"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public static List<Book> get6BookImagesByGenre(String genre) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE genre = ? LIMIT 6";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, genre);
            ResultSet resultSet = pstmt.executeQuery();

            while (resultSet.next()) {
                Book book = new Book();
                book.setBookId(resultSet.getInt("book_id"));
                book.setTitle(resultSet.getString("title"));
                book.setAuthor(resultSet.getString("author"));
                book.setGenre(resultSet.getString("genre"));
                book.setImage(resultSet.getString("image"));
                book.setDescription(resultSet.getString("description"));
                book.setPublisher(resultSet.getString("publisher"));
                book.setPublishYear(resultSet.getInt("publishYear"));
                book.setRating(resultSet.getInt("rating"));
                book.setAvailableQuantity(resultSet.getInt("available_quantity"));

                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    public static void addBooksByQuery(String query) {
        List<Book> books = BookService.fetchBooksInfo(query);

        if (books.isEmpty()) {
            System.out.println("Không tìm thấy sách nào với từ khóa: " + query);
        } else {
            BookDAO bookDAO = new BookDAO();
            for (Book book : books) {
                bookDAO.addBook(book);
            }
        }
    }

    public List<Book> getBooksWithoutImage() throws Exception {
        List<Book> books = new ArrayList<>();
        String query = "SELECT book_id, title, description, image, genre, rating, available_quantity, author, publishYear, publisher " +
                "FROM Books WHERE image IS NULL OR image = ''";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String image = rs.getString("image");
                String genre = rs.getString("genre");
                int rating = rs.getInt("rating");
                int availableQuantity = rs.getInt("available_quantity");
                String author = rs.getString("author");
                int publishYear = rs.getInt("publishYear");
                String publisher = rs.getString("publisher");

                Book book = new Book(bookId, title, description, image, genre, rating, availableQuantity, author, publishYear, publisher);
                books.add(book);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Lỗi khi lấy danh sách sách chưa có ảnh: " + e.getMessage());
        }

        return books;
    }

    public void updateBookImage(Book book) throws Exception {
        String query = "UPDATE Books SET image = ? WHERE book_id = ?";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, book.getImage());
            pstmt.setInt(2, book.getBookId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Lỗi khi cập nhật đường dẫn ảnh: " + e.getMessage());
        }
    }

    public static JSONArray getBooksJson() {
        JSONArray booksJsonArray = new JSONArray(); // Mảng JSON để chứa các sách

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        // Kết nối tới cơ sở dữ liệu
        try (Connection conn = dbConnection.getConnection()) {
            // Truy vấn SQL lấy ID và tên sách
            String query = "SELECT book_id, title FROM books"; // Giả sử bảng sách có tên là "books"
            PreparedStatement stmt = conn.prepareStatement(query);
            ResultSet rs = stmt.executeQuery();

            // Duyệt qua kết quả và tạo các đối tượng JSON
            while (rs.next()) {
                int bookId = rs.getInt("book_id");
                String title = rs.getString("title");

                // Tạo đối tượng JSON cho mỗi sách
                JSONObject bookJson = new JSONObject();
                bookJson.put("bookId", bookId);
                bookJson.put("title", title);

                // Thêm đối tượng JSON vào mảng JSON
                booksJsonArray.put(bookJson);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return booksJsonArray; // Trả về mảng JSON chứa danh sách sách
    }

}
