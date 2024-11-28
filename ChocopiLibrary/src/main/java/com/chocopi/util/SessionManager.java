package com.chocopi.util;

import com.chocopi.model.User;

public class SessionManager {
    private static int userId;
    private static String username;
    private static String password;
    private static String name;
    private static String avatar;
    private static int age;
    private static String phone;
    private static String favor;
    private static String email;
    private static String role;
    private static int totalBorrowed;

    // Lưu thông tin vào session
    public static void setUser(User user) {
        SessionManager.userId = user.getUserId();
        SessionManager.username = user.getUsername();
        SessionManager.password = user.getPassword();
        SessionManager.name = user.getName();
        SessionManager.avatar = user.getAvatar();
        SessionManager.age = user.getAge();
        SessionManager.phone = user.getPhone();
        SessionManager.favor = user.getFavor();
        SessionManager.email = user.getEmail();
        SessionManager.role = user.getRole();
        SessionManager.totalBorrowed = user.getTotalBorrowed();
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

    public static String getName() {
        return name;
    }
    public static String getAvatar() {
        return avatar;
    }
    public static int getAge() {
        return age;
    }
    public static String getPhone() {
        return phone;
    }
    public static String getFavor() {
        return favor;
    }
    public static String getEmail() {
        return email;
    }
    public static int getTotalBorrowed() {
        return totalBorrowed;
    }

    public static void ssInfo() {
        System.out.println(SessionManager.getUserId() + " " + SessionManager.getUsername() + " " + SessionManager.getRole());
    }
}