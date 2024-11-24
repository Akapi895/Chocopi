package com.chocopi.model;

public class User {
    public static final int maxBorrowed = 30;
    private int userId;
    private String username;
    private String password;
    private String name;
    private String avatar;
    private int age;
    private String phone;
    private String favor;
    private String email;
    private String role;
    private int totalBorrowed;

    public User() {}

    public User(String username, String password, String name, String avatar,
                int age, String phone, String favor, String email, int totalBorrowed, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
        this.age = age;
        this.phone = phone;
        this.favor = favor;
        this.email = email;
        this.totalBorrowed = totalBorrowed;
        this.role = role;
    }

    public User(int userId, String username, String password, String name, String avatar,
                int age, String phone, String favor, String email, int totalBorrowed, String role) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
        this.age = age;
        this.phone = phone;
        this.favor = favor;
        this.email = email;
        this.totalBorrowed = totalBorrowed;
        this.role = role;
    }

    // Getters và Setters
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFavor() {
        return favor;
    }

    public void setFavor(String favor) {
        this.favor = favor;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getTotalBorrowed() {
        return totalBorrowed;
    }

    public void setTotalBorrowed(int totalBorrowed) {
        this.totalBorrowed = totalBorrowed;
    }
}
