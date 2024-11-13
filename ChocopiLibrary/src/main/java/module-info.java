module chocopi.example.chocopilibrary {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens chocopi.example.chocopilibrary to javafx.fxml;
    exports chocopi.example.chocopilibrary;
}