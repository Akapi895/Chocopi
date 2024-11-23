package com.chocopi.controller;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import javafx.event.ActionEvent;

import java.io.IOException;


public class UserController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void moveToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/chocopi/fxml/user/UserHome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToPersonalScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/chocopi/fxml/user/UserPersonal.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToBM(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/chocopi/fxml/UserB&M.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToStories(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/chocopi/fxml/user/userEachGenre.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToHistory(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/chocopi/fxml/user/UserHistory.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
