package com.chocopi.dao;

import com.chocopi.model.User;
import com.chocopi.service.EmailService;
import com.chocopi.util.DatabaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO {
    public static boolean checkValidEmail(String email) {
        String query = "SELECT COUNT(user_id) AS user_count FROM users WHERE email = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, email);

            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                int userCount = rs.getInt("user_count");
                if (userCount > 0) {
                    return true;
                } else {
                    System.out.println("Không tìm thấy người dùng với email: " + email);
                    return false;
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi truy vấn cơ sở dữ liệu", e);
        }
        return false;
    }


    public static boolean updateUserPassword(int userId, String newPassword) {
        String query = "UPDATE users SET password = ? WHERE user_id = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, newPassword);
            pstmt.setInt(2, userId);

            int rowsAffected = pstmt.executeUpdate();

            return rowsAffected > 0;

        } catch (SQLException e) {
            System.err.println("Error updating password: " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    public static int getUserIdByEmail(String email) {
        String query = "SELECT * FROM users WHERE email = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return rs.getInt("user_id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        String sql = "SELECT * FROM Users";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAge(rs.getInt("age"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setTotalBorrowed(rs.getInt("totalBorrowed"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean addUser(User user) {
        String sql = "INSERT INTO Users (username, password, name, email, favor, age, phone, role, avatar) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getEmail());
            pstmt.setString(5, user.getFavor());
            pstmt.setString(6, String.valueOf(user.getAge()));
            pstmt.setString(7, user.getPhone());
            pstmt.setString(8, user.getRole());
            pstmt.setString(9, String.valueOf(user.getAvatar()));

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean updateUser(User user) {
        String sql = "UPDATE Users SET username = ?, password = ?, name = ?, avatar = ?, email = ?, phone = ?, role = ?, age = ?, favor = ? WHERE user_id = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.setString(3, user.getName());
            pstmt.setString(4, user.getAvatar());
            pstmt.setString(5, user.getEmail());
            pstmt.setString(6, user.getPhone());
            pstmt.setString(7, user.getRole());
            pstmt.setInt(8, user.getAge());
            pstmt.setString(9, user.getFavor());
            pstmt.setInt(10, user.getUserId());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteUser(int userId) {
        String sql = "DELETE FROM Users WHERE user_id = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();

        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static User getUserById(int userId) {
        String sql = "SELECT * FROM Users WHERE user_id = ?";
        User user = null;
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setEmail(rs.getString("email"));
                user.setPhone(rs.getString("phone"));
                user.setRole(rs.getString("role"));
                user.setAge(rs.getInt("age"));
                user.setFavor(rs.getString("favor"));
                user.setAvatar(rs.getString("avatar"));
                user.setTotalBorrowed(rs.getInt("totalBorrowed"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public String getUserFavor(int userId) {
        String favor = null;
        String sql = "SELECT favor FROM Users WHERE user_id = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                favor = rs.getString("favor");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return favor;
    }

    public User authenticate(String username, String password) {
        String query = "SELECT * FROM Users WHERE username = ? AND password = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                User user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setName(rs.getString("name"));
                user.setAvatar(rs.getString("avatar"));
                user.setAge(rs.getInt("age"));
                user.setPhone(rs.getString("phone"));
                user.setFavor(rs.getString("favor"));
                user.setEmail(rs.getString("email"));
                user.setRole(rs.getString("role"));
                user.setTotalBorrowed(rs.getInt("totalBorrowed"));

                return user;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean checkUsername(String username) {
        String sql = "SELECT COUNT(*) FROM Users WHERE username = ?";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection conn = dbConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                int count = rs.getInt(1);
                if (count > 0) {
                    return true;
                }
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static int getLastUserId() throws SQLException {
        String query = "SELECT MAX(user_id) AS lastId FROM users";
        DatabaseConnection dbConnection = DatabaseConnection.getInstance();
        try (Connection connection = dbConnection.getConnection();
             PreparedStatement statement = connection.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            if (resultSet.next()) {
                return resultSet.getInt("lastId");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw e;
        }
        return -1;
    }
}
