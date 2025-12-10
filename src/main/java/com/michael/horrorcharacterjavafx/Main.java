package com.michael.horrorcharacterjavafx;

import com.michael.horrorcharacterjavafx.model.HorrorCharacterEntry;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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
    private static final String URL = "jdbc:mysql://localhost:3306/horrorcharactermvc";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static Connection getConnection() {
        try
        {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        }
        catch (SQLException e)
        {
            System.out.println("Database connection failed!");
            e.printStackTrace();
            return null;
        }
    }


    public static List<HorrorCharacterEntry> getHorrorCharacterEntries()
    {
        List<HorrorCharacterEntry> horrorCharacterEntries = new ArrayList<HorrorCharacterEntry>();
        try (Connection conn = getConnection())
        {

            String query = "SELECT * FROM horrorcharacters";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                horrorCharacterEntries.add(new HorrorCharacterEntry(rs.getInt("pid"), rs.getString("name"), rs.getInt("health"), rs.getString("vulnerabilities"), rs.getString("dateofbirth"), rs.getString("subtype")));
            }

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return horrorCharacterEntries;
    }

    public static boolean executeCustomQuery(String query)
    {
        try (Connection conn = getConnection())
        {

            PreparedStatement ps = conn.prepareStatement(query);
            ps.executeUpdate();
            return true;

        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }
}