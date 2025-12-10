package com.michael.horrorcharacterjavafx;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/michael/horrorcharacterjavafx/view/primary-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        stage.setTitle("Horror Character Manager");
        stage.setScene(scene);
        stage.show();
    }

    public FXMLLoader loadFXML(String name) {
        return new FXMLLoader(getClass().getResource(name + ".fxml"));
    }

    public static void main(String[] args) {
        launch();
    }
}