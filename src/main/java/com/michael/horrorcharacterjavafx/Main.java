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

/**
 * Main class for the Horror Character Manager JavaFX application.
 */
public class Main extends Application {

    /**
     * Starts the JavaFX application.
     *
     * @param stage the primary stage for this application
     * @throws IOException if the FXML file cannot be loaded
     */
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/michael/horrorcharacterjavafx/view/primary-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 800, 700);
        stage.setTitle("Horror Character Manager");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * Loads an FXML file by name.
     *
     * @param name the name of the FXML file (without the .fxml extension)
     * @return the FXMLLoader for the specified FXML file
     */
    public FXMLLoader loadFXML(String name) {
        return new FXMLLoader(getClass().getResource(name + ".fxml"));
    }

    /**
     * The main method to launch the JavaFX application.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {
        launch();
    }

    private static final String URL = "jdbc:mysql://localhost:3306/horrorcharactermvc";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    /**
     * Establishes and returns a connection to the database.
     *
     * @return a Connection object to the database
     */

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

    /**
     * Retrieves a list of HorrorCharacterEntry objects from the database.
     *
     * @return a list of HorrorCharacterEntry objects
     */
    public static List<HorrorCharacterEntry> getHorrorCharacterEntries()
    {
        List<HorrorCharacterEntry> horrorCharacterEntries = new ArrayList<HorrorCharacterEntry>();
        try (Connection conn = getConnection()) // Try with resources for the auto-close
        {

            String query = "SELECT * FROM horrorcharacters";
            PreparedStatement ps = conn.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            // Loop through the result set and create HorrorCharacterEntry objects
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

    /**
     * Executes a custom SQL query on the database.
     *
     * @param query the SQL query to execute
     */
    public static void executeCustomQuery(String query)
    {
        try (Connection conn = getConnection())
        {

            PreparedStatement ps = conn.prepareStatement(query);
            ps.executeUpdate();

        } catch (SQLException e)
        {
            e.printStackTrace();
        }
    }
}