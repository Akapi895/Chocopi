package com.chocopi.dao;

import com.chocopi.model.Book;
import com.chocopi.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LikeDAO {
    public static boolean addLike(int userId, int bookId) {
        String sql = "INSERT INTO likes (user_id, book_id) VALUES (?, ?)";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);

            return preparedStatement.executeUpdate() > 0; // Trả về true nếu chèn thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Phương thức để xóa một bản ghi "like" khỏi bảng likes
    public static boolean removeLike(int userId, int bookId) {
        String sql = "DELETE FROM likes WHERE user_id = ? AND book_id = ?";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);

            return preparedStatement.executeUpdate() > 0; // Trả về true nếu xóa thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static List<Integer> getLikedBooksByUser(int userId) {
        List<Integer> likedBooks = new ArrayList<>();
        String sql = "SELECT book_id FROM likes WHERE user_id = ?";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    likedBooks.add(resultSet.getInt("book_id"));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likedBooks;
    }

    public static List<Book> getBooksByUser(int userId) {
        List<Book> books = new ArrayList<>();
        String sql = "SELECT book_id FROM likes WHERE user_id = ?";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        List<Integer> bookIds = new ArrayList<>();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    bookIds.add(rs.getInt("book_id"));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        for (int bookId : bookIds) {
            Book book = BookDAO.getBookById(bookId);
            if (book != null) {
                books.add(book);
            }
        }

        return books;
    }

    public static boolean isBookLikedByUser(int userId, int bookId) {
        String sql = "SELECT COUNT(*) AS count FROM likes WHERE user_id = ? AND book_id = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, userId);
            preparedStatement.setInt(2, bookId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("count") > 0; // Trả về true nếu đã like
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<String> getAllLikes() {
        List<String> likes = new ArrayList<>();
        String sql = "SELECT like_id, user_id, book_id FROM likes";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql);
             ResultSet resultSet = preparedStatement.executeQuery()) {

            while (resultSet.next()) {
                int likeId = resultSet.getInt("like_id");
                int userId = resultSet.getInt("user_id");
                int bookId = resultSet.getInt("book_id");
                likes.add("Like ID: " + likeId + ", User ID: " + userId + ", Book ID: " + bookId);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return likes;
    }
}
