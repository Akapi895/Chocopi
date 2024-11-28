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
    requires java.desktop;

    opens com.chocopi.util to javafx.fxml;
    exports com.chocopi.util to javafx.fxml;

    opens com.chocopi.model to javafx.base;
    exports com.chocopi.model to javafx.base;

    opens com.chocopi.dao to javafx.fxml;
    exports com.chocopi.dao to javafx.fxml;

    opens com.chocopi.service to javafx.fxml;
    exports com.chocopi.service to javafx.fxml;

    opens com.chocopi.controller to javafx.fxml;
    exports com.chocopi.controller to javafx.fxml;

    opens com.chocopi to javafx.fxml;
    exports com.chocopi;

    exports com.chocopi.controller.admin;
    opens com.chocopi.controller.admin to javafx.fxml;

    exports com.chocopi.controller.user to javafx.fxml;
    opens com.chocopi.controller.user to javafx.fxml;
}