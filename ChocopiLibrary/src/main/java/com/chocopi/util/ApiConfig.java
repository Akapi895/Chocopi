package com.chocopi.util;

public class ApiConfig {
    private static final String GOOGLE_BOOKS_API_KEY = "AIzaSyD1mN1IDTAPkW7CtLUqhWI7YA4_BMJw248";
    private static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String OPENAI_API_KEY = "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";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    private static ApiConfig instance;

    // Constructor private để ngăn việc tạo đối tượng bên ngoài
    private ApiConfig() {}

    // Phương thức để lấy instance duy nhất
    public static ApiConfig getInstance() {
        if (instance == null) {
            synchronized (ApiConfig.class) {
                if (instance == null) {
                    instance = new ApiConfig();
                }
            }
        }
        return instance;
    }

    // Các phương thức getter
    public String getOpenaiApiKey() {
        return OPENAI_API_KEY;
    }

    public String getOpenaiApiUrl() {
        return OPENAI_API_URL;
    }

    public String getGoogleBooksApiKey() {
        return GOOGLE_BOOKS_API_KEY;
    }

    public String getGoogleBooksURL() {
        return GOOGLE_BOOKS_URL;
    }
}
