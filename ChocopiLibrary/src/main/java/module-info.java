module chocopi.example.chocopilibrary {
    requires javafx.controls;
    requires javafx.fxml;
<<<<<<< Updated upstream

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.chocopi.util to javafx.fxml;
    exports com.chocopi.util to javafx.fxml;

    opens com.chocopi.model to javafx.fxml;
    exports com.chocopi.model to javafx.fxml;

    opens com.chocopi.dao to javafx.fxml;
    exports com.chocopi.dao to javafx.fxml;
=======
//    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
//    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
//    requires eu.hansolo.tilesfx;
//    requires com.almasb.fxgl.all;
    requires java.desktop;
    requires java.sql;

    exports com.chocopi.controller;
    opens com.chocopi.controller to javafx.fxml;
>>>>>>> Stashed changes
}