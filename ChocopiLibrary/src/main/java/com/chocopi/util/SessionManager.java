package com.chocopi.util;

import com.chocopi.dao.UserDAO;
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

    public static void update() {
        User user = UserDAO.getUserById(SessionManager.getUserId());

        if (user != null) {
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
        } else {
            System.err.println("Failed to fetch user information for SessionManager.");
        }
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
        password = null;
        name = null;
        avatar = null;
        age = 0;
        phone = null;
        favor = null;
        email = null;
        totalBorrowed = 0;
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
        System.out.println(SessionManager.getUserId() + " " + SessionManager.getUsername() + " " + SessionManager.getRole()
                + " " + SessionManager.getAge() + " " + SessionManager.getPhone() + " " + SessionManager.getFavor()
                + " " + SessionManager.getEmail() + " " + SessionManager.getTotalBorrowed());
    }

    public static String getPassword() {
        return password;
    }

    public static void setUserId(int userId) {
        SessionManager.userId = userId;
    }

    public static void setUsername(String username) {
        SessionManager.username = username;
    }

    public static void setPassword(String password) {
        SessionManager.password = password;
    }

    public static void setName(String name) {
        SessionManager.name = name;
    }

    public static void setAvatar(String avatar) {
        SessionManager.avatar = avatar;
    }

    public static void setAge(int age) {
        SessionManager.age = age;
    }

    public static void setPhone(String phone) {
        SessionManager.phone = phone;
    }

    public static void setFavor(String favor) {
        SessionManager.favor = favor;
    }

    public static void setEmail(String email) {
        SessionManager.email = email;
    }

    public static void setRole(String role) {
        SessionManager.role = role;
    }

    public static void setTotalBorrowed(int totalBorrowed) {
        SessionManager.totalBorrowed = totalBorrowed;
    }
}

