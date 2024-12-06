package com.chocopi.controller.admin;

import com.chocopi.model.Book;
import com.chocopi.service.BookService;
import javafx.application.Platform;

import java.util.List;
import java.util.function.Consumer;

public class FetchBooksThread extends Thread {
    private final String query;
    private final Consumer<List<Book>> onSuccess;
    private final Consumer<Exception> onFailure;

    public FetchBooksThread(String query, Consumer<List<Book>> onSuccess, Consumer<Exception> onFailure) {
        this.query = query;
        this.onSuccess = onSuccess;
        this.onFailure = onFailure;
    }

    @Override
    public void run() {
        try {
            List<Book> books = BookService.fetchBooksAdmin(query);
            Platform.runLater(() -> onSuccess.accept(books));
        } catch (Exception e) {
            Platform.runLater(() -> onFailure.accept(e)); 
        }
    }
}
