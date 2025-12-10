package com.michael.horrorcharacterjavafx.controller;

import com.michael.horrorcharacterjavafx.Main;
import com.michael.horrorcharacterjavafx.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Controller class for the primary view of the Horror Character Manager application.
 */
public class PrimaryController {

    /** FXML UI components */

    @FXML
    private AnchorPane sceneOneMainAnchorPane;
    @FXML
    private HBox createHBox;

    @FXML
    private VBox createDobVBox, createHealthVBox, createNameVBox, createSubtypeVBox, createVulnerabilitiesVBox, finalButtonVBox;

    @FXML
    private Label infoLabel, createDobLabel, createHealthLabel, createSubtypeLabel, createVulnerabilitiesLabel, createNameLabel;

    @FXML
    private TextField createEntryNameTextField, createHealthTextField;

    @FXML
    private DatePicker createDobDatePicker;

    @FXML
    private ComboBox<String> createSubtypeComboBox;

    @FXML
    private RadioButton createVulnerabilitiesFireRadioButton, createVulnerabilitiesPoisonRadioButton, createVulnerabilitiesSilverRadioButton, createVulnerabilitiesHolyWaterRadioButton, createVulnerabilitiesSunlightRadioButton;

    @FXML
    private Button createEntryButton, deleteEntryButton, updateEntryButton;

    @FXML
    private TableView<HorrorCharacter> mainTableView;

    @FXML
    private TableColumn<HorrorCharacter, LocalDate> dateofbirthTableColumn;

    @FXML
    private TableColumn<HorrorCharacter, Integer> healthTableColumn;


    @FXML
    private TableColumn<HorrorCharacter, String> nameTableColumn;

    @FXML
    private TableColumn<HorrorCharacter, String> subtypeTableColumn;

    @FXML
    private TableColumn<HorrorCharacter, Vulnerability[]> vulnerabilitiesTableColumn;

    /** Observable list to hold HorrorCharacter items */
    private ObservableList<HorrorCharacter> items = FXCollections.observableArrayList();

    /** Handles the action of creating a new HorrorCharacter entry. */
    public void CreateEntryButtonOnAction() {
        String name = null, subtype = null, healthText = null;
        LocalDate dateOfBirth = null;
        Vulnerability[] vulnerabilities;
        boolean valid = true;

        // Validate and retrieve name
        if (!createEntryNameTextField.getText().isBlank()) {
            name = createEntryNameTextField.getText();
        } else {
            infoLabel.setText("Name is required.");
            valid = false;
        }

        // Validate and retrieve health
        try {
            Integer.parseInt(createHealthTextField.getText());
            if (!createHealthTextField.getText().isBlank()) {
                healthText = createHealthTextField.getText();
            } else {
                infoLabel.setText("Health is required.");
                valid = false;
            }
        } catch (NumberFormatException e) {
            infoLabel.setText("Health must be an integer.");
            valid = false;
        }

        // Validate and retrieve date of birth
        if (createDobDatePicker.getValue() != null) {
            dateOfBirth = createDobDatePicker.getValue();
        } else {
            infoLabel.setText("Date of Birth is required.");
            valid = false;
        }
        // Validate and retrieve subtype
        if (createSubtypeComboBox.getValue() != null) {
            subtype = createSubtypeComboBox.getValue();
        } else {
            infoLabel.setText("Subtype is required.");
            valid = false;
        }

        // Retrieve vulnerabilities
        ObservableList<Vulnerability> vulnerabilitiesList = FXCollections.observableArrayList();
        if (createVulnerabilitiesFireRadioButton.isSelected()) {
            vulnerabilitiesList.add(Vulnerability.FIRE);
        }
        if (createVulnerabilitiesPoisonRadioButton.isSelected()) {
            vulnerabilitiesList.add(Vulnerability.POISON);
        }
        if (createVulnerabilitiesSilverRadioButton.isSelected()) {
            vulnerabilitiesList.add(Vulnerability.SILVER);
        }
        if (createVulnerabilitiesHolyWaterRadioButton.isSelected()) {
            vulnerabilitiesList.add(Vulnerability.HOLY_WATER);
        }
        if (createVulnerabilitiesSunlightRadioButton.isSelected()) {
            vulnerabilitiesList.add(Vulnerability.SUNLIGHT);
        }
        vulnerabilities = vulnerabilitiesList.toArray(new Vulnerability[0]);

        // If all inputs are valid, create the new HorrorCharacter
        if (valid) {
            int health = Integer.parseInt(healthText);
            HorrorCharacter newCharacter;
            switch (subtype) {
                case "Vampire" -> newCharacter = new Vampire(name, health, vulnerabilities, dateOfBirth);
                case "Werewolf" -> newCharacter = new Werewolf(name, health, vulnerabilities, dateOfBirth);
                case "Zombie" -> newCharacter = new Zombie(name, health, vulnerabilities, dateOfBirth);
                default -> {
                    infoLabel.setText("Invalid subtype.");
                    return;
                }
            }
            // Insert the new character into the database
            Main.executeCustomQuery("INSERT INTO horrorcharacters (name, health, vulnerabilities, dateofbirth, subtype) VALUES ('" + name + "', " + health + ", '" + Arrays.stream(vulnerabilities).map(Vulnerability::toString).collect(Collectors.joining(", ")) + "', '" + dateOfBirth.toString() + "', '" + subtype + "');");
            mainTableView.getItems().add(newCharacter); // Client side add just in case the tableview doesn't get updated from db

            infoLabel.setText("Created new " + subtype + ": " + name + "!");
        }
    }

    /** Handles the action of deleting a selected HorrorCharacter entry. */
    public void DeleteEntryButtonOnAction() {
        // Get the selected character from the table view
        HorrorCharacter selectedCharacter = mainTableView.getSelectionModel().getSelectedItem();
        if (selectedCharacter != null) {
            // Delete the character from the database
            Main.executeCustomQuery("DELETE FROM horrorcharacters WHERE name = '" + selectedCharacter.getName() + "';");
            mainTableView.getItems().remove(selectedCharacter); // Again client side just in case the change is not immediately shown in the tableview
            infoLabel.setText("Deleted " + selectedCharacter.getName() + " from the list.");
        } else {
            infoLabel.setText("No character selected for deletion.");
        }
    }

    /**
     * Handles the action of updating a selected HorrorCharacter entry.
     * Loads the secondary view for updating the character.
     * @throws IOException if the FXML file cannot be loaded
     */
    public void UpdateEntryButtonOnAction() throws IOException {
        // Get the selected character from the table view
        HorrorCharacter selectedCharacter = mainTableView.getSelectionModel().getSelectedItem();
        if (selectedCharacter != null) {
            // Store the selected character in AppState for access in the secondary view
            AppState.setSelectedCharacter(selectedCharacter);
            System.out.println("added selected char to appstate");

            // Load the secondary view for managing the entire update process
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/michael/horrorcharacterjavafx/view/secondary-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load());
            Stage primaryStage = (Stage) sceneOneMainAnchorPane.getScene().getWindow();
            primaryStage.setScene(scene);
            primaryStage.show();
        } else {
            infoLabel.setText("No character selected for update.");
        }
    }




    /** Initializes the controller class. This method is automatically called after the FXML file has been loaded. */
    @FXML
    public void initialize() {
        infoLabel.setText(" ");
        // This is to populate the comboBox with its options (subtypes)
        ObservableList<String> subtypeOptions = FXCollections.observableArrayList(
                "Vampire",
                "Werewolf",
                "Zombie"
        );
        createSubtypeComboBox.setItems(subtypeOptions);
        mainTableView.refresh(); // This does absultuly nothing from what I gather but cant hurt to have
        List<HorrorCharacterEntry> entries = Main.getHorrorCharacterEntries(); // Get entries that were brought from db
            for (HorrorCharacterEntry e : entries) {
                String name = e.name();
                int health = e.health();
                LocalDate dateOfBirth = LocalDate.parse((CharSequence) e.dateOfBirth()); // Parse dateOfBirth from String to LocalDate
                Vulnerability[] vulnerabilities = Arrays.stream(e.vulnerabilities().split(", "))  // Split the vulnerabilities string into an array
                        .map(String::trim)                                                              // Trim whitespace
                        .map(Vulnerability::valueOf)                                                    // Convert each string to a Vulnerability enum
                        .toArray(Vulnerability[]::new);                                                 // Collect into an array of vulnerabilities
                HorrorCharacter character; // Temporary variable to hold the created character to later be set to a new character object
                switch (e.subtype()) {
                    case "Vampire" -> character = new Vampire(name, health, vulnerabilities, dateOfBirth);
                    case "Werewolf" -> character = new Werewolf(name, health, vulnerabilities, dateOfBirth);
                    case "Zombie" -> character = new Zombie(name, health, vulnerabilities, dateOfBirth);
                    default -> {
                        System.out.println("Unknown subtype: " + e.subtype());
                        continue; // Skip unknown subtypes
                    }
                }
                items.add(character); // Add the created character to the observable list
            }
            mainTableView.setItems(items);


        // Set up the columns to display the data
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name")); // Set CellValueFactory for name
        healthTableColumn.setCellValueFactory(new PropertyValueFactory<>("health")); // Set CellValueFactory for health
        subtypeTableColumn.setCellValueFactory(cellData -> {                                                // Custom CellValueFactory for subtype
            HorrorCharacter character = cellData.getValue();                                                // Get the HorrorCharacter object
            // Return the class name as a String
            return new javafx.beans.property.SimpleStringProperty(character.getClass().getSimpleName());    // Return the class name
        });  // side note spent 2 hours debugging because the "T" in subtype was capitalized.
        dateofbirthTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth")); // Set CellValueFactory for dateOfBirth

        dateofbirthTableColumn.setCellFactory(column -> new javafx.scene.control.TableCell<>() { // Custom CellFactory for dateOfBirth
            @Override
            protected void updateItem(LocalDate date, boolean empty) {                                // Override updateItem method
                super.updateItem(date, empty);                                                       // Call super method
                if (empty || date == null) {                                                        // If empty or date is null
                    setText(null);                                                                 // Set text to null
                } else {
                    setText(date.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd"))); // Format date as year-month-day
                }
            }
        });

        vulnerabilitiesTableColumn.setCellValueFactory(new PropertyValueFactory<>("vulnerabilities")); // Set CellValueFactory for vulnerabilities
        vulnerabilitiesTableColumn.setCellFactory(column -> new javafx.scene.control.TableCell<>() {  // Custom CellFactory for vulnerabilities
            @Override
            protected void updateItem(Vulnerability[] vulnerabilities, boolean empty) { // Override updateItem method
                super.updateItem(vulnerabilities, empty); // Call super method
                if (empty || vulnerabilities == null) {  // If empty or vulnerabilities is null
                    setText(null);
                } else {             // Else, join the vulnerabilities into a comma-separated string
                    String vulnerabilitiesString = Arrays.stream(vulnerabilities) // Stream the vulnerabilities array
                            .map(Vulnerability::toString)                        // Map each vulnerability to its string representation
                            .collect(Collectors.joining(", "));         // Join them with a comma and space
                    setText(vulnerabilitiesString);
                }
            }
        });
    }
}
