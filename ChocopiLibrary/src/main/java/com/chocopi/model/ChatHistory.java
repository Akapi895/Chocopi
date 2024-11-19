package com.chocopi.model;

import java.sql.Timestamp;

public class ChatHistory {
    private int messageId;
    private int userId;
    private String message;
    private String response;
    private Timestamp timestamp;

    public ChatHistory() {
    }

    public ChatHistory(int messageId, int userId, String message, String response, Timestamp timestamp) {
        this.messageId = messageId;
        this.userId = userId;
        this.message = message;
        this.response = response;
        this.timestamp = timestamp;
    }
    public ChatHistory(int userId, String message, String response, Timestamp timestamp) {
        this.userId = userId;
        this.message = message;
        this.response = response;
        this.timestamp = timestamp;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "ChatHistory{" +
                "messageId=" + messageId +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", response='" + response + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
