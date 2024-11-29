package com.chocopi;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import static com.chocopi.dao.BookManagementDAO.getTotalBorrow;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            // Load file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/Login.fxml"));
            Scene scene = new Scene(loader.load());

            // Cấu hình Stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("Choco.Book");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
