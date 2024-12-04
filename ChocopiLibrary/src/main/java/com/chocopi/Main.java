package com.chocopi;

import com.chocopi.dao.BookDAO;
import com.chocopi.dao.UserDAO;
import com.chocopi.model.Book;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        try {
            // Load file FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/Login.fxml"));
            Scene scene = new Scene(loader.load());
            scene.getStylesheets().add(getClass().getResource("/com/chocopi/css/login.css").toExternalForm());

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
