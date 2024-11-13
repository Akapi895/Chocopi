package com.chocopi;

import com.chocopi.dao.*;
import com.chocopi.model.*;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();

        // Retrieve all users
        List<User> users = userDAO.getAllUsers();

        // Print each user's details
        for (User user : users) {

        }
    }
}