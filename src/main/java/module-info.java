module com.michael.horrorcharacterjavafx {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.michael.horrorcharacterjavafx to javafx.fxml;
    exports com.michael.horrorcharacterjavafx;
    exports com.michael.horrorcharacterjavafx.controller;
    opens com.michael.horrorcharacterjavafx.controller to javafx.fxml;
}