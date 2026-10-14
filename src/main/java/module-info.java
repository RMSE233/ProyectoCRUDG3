module com.example.proyectocrudg3 {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires org.postgresql.jdbc;

    opens com.example.proyectocrudg3 to javafx.fxml;
    opens com.example.proyectocrudg3.controller to javafx.fxml;

    exports com.example.proyectocrudg3;
    exports com.example.proyectocrudg3.controller;
}