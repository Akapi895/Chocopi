package com.chocopi.util;

public class ApiConfig {
    public static final String GOOGLE_BOOKS_API_KEY = "AIzaSyD1mN1IDTAPkW7CtLUqhWI7YA4_BMJw248";
    public static final String GOOGLE_BOOKS_URL = "https://www.googleapis.com/books/v1/volumes?q=";
    public static final String SERPER_API_KEY = "be769d2a482d1fa7e3fb3d99e912d8806f69fd27";
    public static final String SERPER_API_URL = "https://google.serper.dev/search";
    public static final String GEMINI_API_KEY = "AIzaSyCsv-v02K1RRwDAnKzJkUmYKGgnfyGnLwU";
    public static final String GEMINI_URL = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent";

    public static String getGoogleBooksApiKey() {
        return GOOGLE_BOOKS_API_KEY;
    }

    public static String getGoogleBooksURL() {
        return GOOGLE_BOOKS_URL;
    }

    public static String getSerperApiKey() {
        return SERPER_API_KEY;
    }

    public static String getSerperApiURL() {
        return SERPER_API_URL;
    }

    public static String getGeminiApiUrl() {
        return GEMINI_URL;
    }

    public static String getGeminiApiKey() {
        return GEMINI_API_KEY;
    }
}
