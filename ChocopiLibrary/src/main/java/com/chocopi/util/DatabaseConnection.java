package com.chocopi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    // Khai báo các thuộc tính cấu hình cơ sở dữ liệu
    private static final String URL = "jdbc:mysql://localhost:3306/chocopi";
    private static final String USER = "root";
    private static final String PASSWORD = "123123";

    // Biến giữ instance Singleton
    private static volatile DatabaseConnection instance = null;

    // Biến kết nối
    private Connection connection;

    // Constructor private để ngăn tạo instance từ bên ngoài
    private DatabaseConnection() {
        try {
            this.connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error initializing database connection.", e);
        }
    }

    // Phương thức để lấy instance Singleton
    public static DatabaseConnection getInstance() {
        if (instance == null) {
            synchronized (DatabaseConnection.class) {
                if (instance == null) {
                    instance = new DatabaseConnection();
                }
            }
        }
        return instance;
    }

    // Phương thức để lấy Connection
    public Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                synchronized (DatabaseConnection.class) {
                    if (connection == null || connection.isClosed()) {
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error getting database connection.", e);
        }
        return connection;
    }

    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}