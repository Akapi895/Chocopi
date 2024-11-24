package com.chocopi.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/chocopi";
    private static final String USER = "root";
    private static final String PASSWORD = "123123";
    private static Connection connection = null;

    private DatabaseConnection() {}

    public static Connection getConnection() {
        try {
            // Kiểm tra nếu kết nối đã bị đóng hoặc chưa được tạo, mở kết nối mới
            if (connection == null || connection.isClosed()) {
                synchronized (DatabaseConnection.class) {
                    if (connection == null || connection.isClosed()) {
                        connection = DriverManager.getConnection(URL, USER, PASSWORD);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Có thể ném lại ngoại lệ nếu cần hoặc thông báo lỗi.
        }
        return connection;
    }

    public static void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
                connection = null; // Đảm bảo kết nối được khởi tạo lại khi cần thiết
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
