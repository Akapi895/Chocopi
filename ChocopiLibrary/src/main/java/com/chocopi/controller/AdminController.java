package com.chocopi.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    public void moveToHomeScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/chocopi/fxml/AdminHome.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToBookScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/chocopi/fxml/admin/AdminBook.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void moveToStudentScene(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/com/chocopi/fxml/admin/AdminStudent.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
