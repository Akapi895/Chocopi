package com.chocopi.util;

public class ApiConfig {
    private static final String GOOGLE_BOOKS_API_KEY = "";
    private static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private static final String OPENAI_API_KEY = "";
    private static final String OPENAI_API_URL = "https://api.openai.com/v1/chat/completions";

    public static String getOpenaiApiKey() {
        return OPENAI_API_KEY;
    }

    public static String getOpenaiApiUrl() {
        return OPENAI_API_URL;
    }

    public static String getGoogleBooksApiKey() {
        return GOOGLE_BOOKS_API_KEY;
    }

    public static String getGoogleBooksURL() {
        return GOOGLE_BOOKS_URL;
    }
}
