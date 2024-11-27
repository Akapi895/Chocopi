package com.chocopi.util;

public class BookSessionManager {
    private static int bookId;
    private static String genre;
    private static int page;
    private static String lastPage; // lưu fxml path

    public static void setBookSession(int bookId, String genre) {
        BookSessionManager.bookId = bookId;
        BookSessionManager.genre = genre;
    }

    public static void setBookId(int bookId) {
        BookSessionManager.bookId = bookId;
    }

    public static void setGenre(String genre) {
        BookSessionManager.genre = genre;
    }

    public static void setPage(int page) {
        BookSessionManager.page = page;
    }

    public static void setLastPage(String lastPage) {
        BookSessionManager.lastPage = lastPage;
    }

    public static String getLastPage() {
        return lastPage;
    }

    public static int getBookId() {
        return bookId;
    }

    public static String getGenre() {
        return genre;
    }

    public static int getPage() {
        return page;
    }

    public static void clearBookSession() {
        bookId = 0;
        genre = null;
        page = 1;
        lastPage = null;
    }

    public static boolean hasBookSession() {
        return bookId > 0;
    }
}