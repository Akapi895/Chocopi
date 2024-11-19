module chocopi.example.chocopilibrary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;
    requires java.net.http;
    requires com.google.gson;
    requires okhttp3;
    requires org.json;

    opens com.chocopi.util to javafx.fxml;
    exports com.chocopi.util to javafx.fxml;

    opens com.chocopi.model to javafx.fxml;
    exports com.chocopi.model to javafx.fxml;

    opens com.chocopi.dao to javafx.fxml;
    exports com.chocopi.dao to javafx.fxml;

    exports com.chocopi.service;
}