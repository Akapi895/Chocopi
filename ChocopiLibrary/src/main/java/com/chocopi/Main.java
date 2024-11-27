package com.chocopi;

import com.chocopi.util.BookSessionManager;
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

            // Cấu hình Stage
            primaryStage.setScene(scene);
            primaryStage.setTitle("Login - Choco.Book");
            primaryStage.setResizable(false); // Không cho phép thay đổi kích thước cửa sổ
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//    @Override
//    public void start(Stage primaryStage) {
//        BookSessionManager.setGenre("Computers");
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/chocopi/fxml/user/userEachGenre.fxml"));
//            Scene scene = new Scene(loader.load());
//
//            // Cấu hình Stage
//            primaryStage.setScene(scene);
//            primaryStage.setTitle("Choco.Book");
//            primaryStage.setResizable(false); // Không cho phép thay đổi kích thước cửa sổ
//            primaryStage.show();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }



    public static void main(String[] args) {
        launch(args); // Khởi chạy ứng dụng JavaFX
    }
}
