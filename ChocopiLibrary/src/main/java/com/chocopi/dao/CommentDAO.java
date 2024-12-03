package com.chocopi.dao;

import com.chocopi.model.UserComment;
import com.chocopi.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CommentDAO {
    public static List<UserComment> getCommentsByBookId(int bookId) {
        List<UserComment> comments = new ArrayList<>();
        String query = "SELECT * FROM comments WHERE book_id = ?";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, bookId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                UserComment comment = new UserComment(
                        resultSet.getInt("comment_id"),
                        resultSet.getInt("book_id"),
                        resultSet.getInt("user_id"),
                        resultSet.getString("comment"),
                        resultSet.getTimestamp("created_at")
                );
                comments.add(comment);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return comments;
    }

    public static boolean addComment(UserComment comment) {
        String query = "INSERT INTO comments (book_id, user_id, comment, created_at) VALUES (?, ?, ?, ?)";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, comment.getBookId());
            statement.setInt(2, comment.getUserId());
            statement.setString(3, comment.getComment());
            statement.setTimestamp(4, comment.getCreatedAt());

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Xóa bình luận
    public boolean deleteComment(int commentId) {
        String query = "DELETE FROM comments WHERE comment_id = ?";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, commentId);

            return statement.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
