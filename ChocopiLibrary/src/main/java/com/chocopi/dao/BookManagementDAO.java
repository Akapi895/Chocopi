package com.chocopi.dao;

import com.chocopi.model.Book;
import com.chocopi.model.BookManagement;
import com.chocopi.util.DatabaseConnection;
import com.chocopi.util.SessionManager;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookManagementDAO {
    public List<BookManagement> getAllRecords() {
        List<BookManagement> records = new ArrayList<>();
        String sql = "SELECT * FROM BookManagement";

        try (Connection conn = DatabaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                BookManagement record = new BookManagement();
                record.setRecordId(rs.getInt("record_id"));
                record.setUserId(rs.getInt("user_id"));
                record.setBookId(rs.getInt("book_id"));
                record.setBorrowDate(rs.getDate("borrow_date"));
                record.setReturnDate(rs.getDate("return_date"));
                records.add(record);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return records;
    }

    public boolean addRecord(BookManagement record) {
        String sql = "INSERT INTO BookManagement (user_id, book_id, borrow_date, return_date) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, record.getUserId());
            pstmt.setInt(2, record.getBookId());
            pstmt.setDate(3, record.getBorrowDate());
            pstmt.setDate(4, record.getReturnDate());
            // Set các thuộc tính khác nếu cần

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean deleteRecord(int recordId) {
        String sql = "DELETE FROM BookManagement WHERE record_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, recordId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<String> getFrequentGenresByUser(int userId) {
        List<String> genres = new ArrayList<>();
        String sql = "SELECT b.genre, COUNT(b.genre) AS genre_count " +
                "FROM BookManagement bm " +
                "JOIN Books b ON bm.book_id = b.book_id " +
                "WHERE bm.user_id = ? " +
                "GROUP BY b.genre " +
                "ORDER BY genre_count DESC " +
                "LIMIT 5";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                genres.add(rs.getString("genre"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return genres;
    }

    public static int getRecordIdByUserIdAndBookId(int userId, int bookId) {
        String sql = "SELECT * FROM BookManagement WHERE user_id = ? AND book_id = ?";
        int record = 0;
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                record = rs.getInt("record_id");
                break;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return record;
    }

    public static List<Integer> getBookIdByUserId(int userId) {
        List<Integer> books = new ArrayList<>();
        String sql = "SELECT * FROM bookmanagement WHERE user_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int book = rs.getInt("book_id");
                books.add(book);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public static List<Book> getBorrowedBookByUserId(int userId) {
        List<Integer> bookIds = new ArrayList<>();

        String sql = "SELECT book_id FROM bookmanagement WHERE user_id = ? AND return_date > ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                int BookId = rs.getInt("book_id");
                bookIds.add(BookId);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        List<Book> books = new ArrayList<>();
        for (int bookId : bookIds) {
            Book book = BookDAO.getBookById(bookId);
            if (book != null) {
                books.add(book);
            }
        }
        return books;
    }

    public static int getTotalBorrow(int userId) {
        String sql = "SELECT book_id FROM bookmanagement WHERE user_id = ? AND return_date > ?";

        int cnt = 0;

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setDate(2, new java.sql.Date(System.currentTimeMillis()));

            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                cnt++;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return cnt;
    }

    public static boolean checkBorrowed(int userId, int bookId) {
        String sql = "SELECT * FROM BookManagement WHERE user_id = ? AND book_id = ?";
        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setInt(2, bookId);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) return true; // Tìm được thì trả về đúng
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return false;
    }
}
