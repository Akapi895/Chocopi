package com.chocopi.dao;

import com.chocopi.model.ChatHistory;
import com.chocopi.util.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ChatHistoryDAO {
    public static void saveChatHistory(ChatHistory chatHistory) {
        String sql = "INSERT INTO chathistory (user_id, message, response, timestamp) VALUES (?, ?, ?, ?)";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, chatHistory.getUserId());
            pstmt.setString(2, chatHistory.getMessage());
            pstmt.setString(3, chatHistory.getResponse());
            pstmt.setTimestamp(4, chatHistory.getTimestamp());

            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static List<ChatHistory> getChatHistoryByUserId(int userId) {
        String sql = "SELECT * FROM chathistory WHERE user_id = ? ORDER BY timestamp DESC";
        List<ChatHistory> chatHistoryList = new ArrayList<>();
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                ChatHistory chatHistory = new ChatHistory();
                chatHistory.setMessageId(rs.getInt("message_id"));
                chatHistory.setUserId(rs.getInt("user_id"));
                chatHistory.setMessage(rs.getString("message"));
                chatHistory.setResponse(rs.getString("response"));
                chatHistory.setTimestamp(rs.getTimestamp("timestamp"));
                chatHistoryList.add(chatHistory);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return chatHistoryList;
    }

    public static void deleteChatHistoryByUserId(int userId) {
        String sql = "DELETE FROM chathistory WHERE user_id = ?";

        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
