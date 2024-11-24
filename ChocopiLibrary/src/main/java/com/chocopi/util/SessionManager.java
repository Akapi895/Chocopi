package com.chocopi.util;

public class SessionManager {
    private static int userId;
    private static String username;
    private static String role;

    // Lưu thông tin vào session
    public static void setUser(int userId, String username, String role) {
        SessionManager.userId = userId;
        SessionManager.username = username;
        SessionManager.role = role;
    }

    // Lấy thông tin từ session
    public static int getUserId() {
        return userId;
    }

    public static String getUsername() {
        return username;
    }

    public static String getRole() {
        return role;
    }

    // Xóa session khi đăng xuất
    public static void clearSession() {
        userId = 0;
        username = null;
        role = null;
    }
}

