package com.chocopi.controller;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class    UserHome extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        try{
            Parent root = FXMLLoader.load(getClass().getResource("/com/chocopi/fxml/user/UserHome.fxml"));
            Scene homeScene = new Scene(root);
            if (getClass().getResource("view.css") != null)
                homeScene.getStylesheets().add(getClass().getResource("view.css").toExternalForm());

            primaryStage.setScene(homeScene);
            primaryStage.show();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
