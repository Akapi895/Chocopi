package com.chocopi.model;

import java.sql.Timestamp;

public class UserComment {
    private int commentId;
    private int bookId;
    private int userId;
    private String comment;
    private Timestamp createdAt;

    public UserComment() {
    }

    public UserComment(int commentId, int bookId, int userId, String comment, Timestamp createdAt) {
        this.commentId = commentId;
        this.bookId = bookId;
        this.userId = userId;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    public UserComment(int bookId, int userId, String comment, Timestamp createdAt) {
        this.bookId = bookId;
        this.userId = userId;
        this.comment = comment;
        this.createdAt = createdAt;
    }

    // Getters và Setters
    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    // Phương thức toString để hiển thị thông tin đối tượng
    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", bookId=" + bookId +
                ", userId=" + userId +
                ", comment='" + comment + '\'' +
                ", createdAt=" + createdAt +
                '}';
    }
}