package com.chocopi.controller;

import com.chocopi.dao.UserDAO;
import com.chocopi.dao.BookDAO;
import com.chocopi.dao.BookManagementDAO;
import com.chocopi.model.Book;
import java.util.ArrayList;
import java.util.List;

public class RecommendationController {
    private UserDAO userDao = new UserDAO();
    private BookDAO bookDao = new BookDAO();
    private BookManagementDAO bookManagementDao = new BookManagementDAO();

    public List<Book> getRecommendations(int userId) {
        List<Book> recommendations = new ArrayList<>();

        // Gợi ý theo sở thích cá nhân
        String userFavor = userDao.getUserFavor(userId);
        if (userFavor != null) {
            recommendations.addAll(bookDao.getBooksByGenre(userFavor));
        }

        // Gợi ý theo lịch sử mượn sách
        List<String> frequentGenres = bookManagementDao.getFrequentGenresByUser(userId);
        for (String genre : frequentGenres) {
            recommendations.addAll(bookDao.getBooksByGenre(genre));
        }

        return recommendations;
    }
}
