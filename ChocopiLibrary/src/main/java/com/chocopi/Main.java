package com.chocopi;

import com.chocopi.util.DatabaseConnection;
import java.sql.Connection;
<<<<<<< Updated upstream
=======
import java.sql.SQLException;
>>>>>>> Stashed changes

public class Main {
    public static void main(String[] args) {
        // Lấy kết nối đến database
        Connection connection = DatabaseConnection.getConnection();

        // Kiểm tra nếu connection không null thì kết nối thành công
        if (connection != null) {
            System.out.println("Connection is established and working.");
        } else {
            System.out.println("Connection failed.");
        }
    }
}
