package com.chocopi.util;

public class BookSessionManager {
    private static int bookId;
    private static String genre;
    private static int page;
    private static String lastPage; // lưu fxml path
    private static String search;
    private static String labelTitle;

    public static void setLabelTitle(String labelTitle) {
        BookSessionManager.labelTitle = labelTitle;
    }

    public static String getLabelTitle() {
        return labelTitle;
    }

    public static void setSearch(String search) {
        BookSessionManager.search = search;
    }

    public static String getSearch() {
        return search;
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
        search = null;
    }

    public static boolean hasBookSession() {
        return bookId > 0;
    }
}