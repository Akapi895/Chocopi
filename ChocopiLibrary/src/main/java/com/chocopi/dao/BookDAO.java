package com.chocopi.dao;

import com.chocopi.model.Book;
import com.chocopi.util.DatabaseConnection;
import com.chocopi.service.BookService;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDAO {
    public List<Book> getAllBooks() {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

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
                // Gán các thuộc tính khác của Book
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public boolean addBook(Book book) {
        String sql = "INSERT INTO Books (title, description, author, publisher, publishYear," +
                " genre, rating, available_quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getDescription());
            pstmt.setString(3, book.getAuthor());
            pstmt.setString(4, book.getPublisher());
            pstmt.setInt(5, book.getPublishYear());
            pstmt.setString(6, book.getGenre());
            pstmt.setInt(7, book.getRating());
            pstmt.setInt(8, book.getAvailableQuantity());
            // Set các thuộc tính khác nếu cần

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateBook(Book book) {
        String sql = "UPDATE Books SET title = ?, description = ?, author = ?, publisher = ?," +
                " publishYear = ?, genre = ?, rating = ?, available_quantity = ? WHERE book_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
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

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, bookId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Book> searchBooks(String keyword) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE title LIKE ? OR author LIKE ? OR genre LIKE ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            String searchKeyword = "%" + keyword + "%";
            pstmt.setString(1, searchKeyword);
            pstmt.setString(2, searchKeyword);
            pstmt.setString(3, searchKeyword);

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
                    // Gán các thuộc tính khác của Book
                    books.add(book);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public List<Book> getBooksByGenre(String genre) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT * FROM Books WHERE genre = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, genre);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setBookId(rs.getInt("book_id"));
                book.setTitle(rs.getString("title"));
                book.setAuthor(rs.getString("author"));
                book.setGenre(rs.getString("genre"));
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void addBooksByQuery(String query) {
        List<Book> books = BookService.fetchBooksInfo(query);

        if (books == null || books.isEmpty()) {
            System.err.println("Không thể lấy thông tin sách từ Google Books API.");
            return;
        }

        String sql = "INSERT INTO Books (title, description, author, publisher, publishYear, genre," +
                " rating, available_quantity) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            for (Book book : books) {
                pstmt.setString(1, book.getTitle());
                pstmt.setString(2, book.getDescription());
                pstmt.setString(3, book.getAuthor());
                pstmt.setString(4, book.getPublisher());
                pstmt.setInt(5, book.getPublishYear());
                pstmt.setString(6, book.getGenre());
                pstmt.setInt(7, book.getRating());
                pstmt.setInt(8, book.getAvailableQuantity());

                int rowsAffected = pstmt.executeUpdate();

                if (rowsAffected > 0) {
                    System.out.println("Thêm sách mới thành công: " + book.getTitle());
                } else {
                    System.out.println("Thêm sách không thành công: " + book.getTitle());
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getBooksWithoutImage() throws Exception {
        List<Book> books = new ArrayList<>();
        String query = "SELECT book_id, title, description, image, genre, rating, available_quantity, author, publishYear, publisher " +
                "FROM Books WHERE image IS NULL OR image = ''";

        try (Connection conn = DatabaseConnection.getConnection();
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

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, book.getImage());
            pstmt.setInt(2, book.getBookId());
            pstmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("Lỗi khi cập nhật đường dẫn ảnh: " + e.getMessage());
        }
    }

}
