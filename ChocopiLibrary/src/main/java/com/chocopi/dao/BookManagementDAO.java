package com.chocopi.dao;

import com.chocopi.model.BookManagement;
import com.chocopi.util.DatabaseConnection;

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

    public boolean updateRecord(BookManagement record) {
        String sql = "UPDATE BookManagement SET user_id = ?, book_id = ?, borrow_date = ?, return_date = ? WHERE record_id = ?";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, record.getUserId());
            pstmt.setInt(2, record.getBookId());
            pstmt.setDate(3, record.getBorrowDate());
            pstmt.setDate(4, record.getReturnDate());
            pstmt.setInt(5, record.getRecordId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteRecord(int recordId) {
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
}
