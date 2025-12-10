package com.michael.horrorcharacterjavafx.controller;

import com.michael.horrorcharacterjavafx.Main;
import com.michael.horrorcharacterjavafx.model.AppState;
import com.michael.horrorcharacterjavafx.model.HorrorCharacter;
import com.michael.horrorcharacterjavafx.model.Vulnerability;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

public class SecondaryController {
    @FXML
    private AnchorPane updatePageAnchorPane;
    @FXML
    private HBox updatePageHBox;
    @FXML
    private VBox updatePageDobVBox, updatePageHealthVBox, updatePageNameVBox, updatePageSubtypeVBox, updatePageVulnerabilitiesVBox;
    @FXML
    private Label updatePageDobLabel, updatePageHealthLabel, updatePageNameLabel, updatePageSubtypeLabel, updatePageVulnerabilitiesLabel, infoLabel;
    @FXML
    private TextField updatePageNameTextField, updatePageHealthTextField;
    @FXML
    private DatePicker updatePageDobDatePicker;
    @FXML
    private ComboBox<String> updatePageSubtypeComboBox;
    @FXML
    private RadioButton updatePageVulnerabilitiesFireRadioButton, updatePageVulnerabilitiesPoisonRadioButton, updatePageVulnerabilitiesHolyWaterRadioButton, updatePageVulnerabilitiesSilverRadioButton, updatePageVulnerabilitiesSunlightRadioButton;
    @FXML
    private Button updatePageFinalUpdateButton;

    private HorrorCharacter curr = AppState.getSelectedCharacter();
    private HorrorCharacter updatedCurr = AppState.getUpdatedSelectedCharacter();

    public void handleUpdatePageFinalUpdateButtonAction() throws IOException {

        // Logic to handle the update action
        if (curr != null) {
            updatedCurr = curr;
            String ogName = curr.getName();
            int ogHealth = curr.getHealth();
            String name = null, subtype = null, healthText = null;
            LocalDate dateOfBirth = null;
            Vulnerability[] vulnerabilities;
            boolean valid = true;

            // Getting name
            if (!updatePageNameTextField.getText().isBlank()) {
                name = updatePageNameTextField.getText();
            } else {
                infoLabel.setText("Name is required.");
                valid = false;
            }

            // Getting health
            try {
                Integer.parseInt(updatePageHealthTextField.getText());
                if (!updatePageHealthTextField.getText().isBlank()) {
                    healthText = updatePageHealthTextField.getText();
                } else {
                    infoLabel.setText("Health is required.");
                    valid = false;
                }
            } catch (NumberFormatException e) {
                infoLabel.setText("Health must be an integer.");
                valid = false;
            }

            // Getting date of birth
            if (updatePageDobDatePicker.getValue() != null) {
                dateOfBirth = updatePageDobDatePicker.getValue();
            } else {
                infoLabel.setText("Date of Birth is required.");
                valid = false;
            }

            // Getting subtype
            if (updatePageSubtypeComboBox.getValue() != null) {
                subtype = updatePageSubtypeComboBox.getValue();
            } else {
                infoLabel.setText("Subtype is required.");
                valid = false;
            }


            // Getting vulnerabilities
            ObservableList<Vulnerability> vulnerabilitiesList = FXCollections.observableArrayList();
            if (updatePageVulnerabilitiesFireRadioButton.isSelected()) {
                vulnerabilitiesList.add(Vulnerability.FIRE);
            }
            if (updatePageVulnerabilitiesPoisonRadioButton.isSelected()) {
                vulnerabilitiesList.add(Vulnerability.POISON);
            }
            if (updatePageVulnerabilitiesSilverRadioButton.isSelected()) {
                vulnerabilitiesList.add(Vulnerability.SILVER);
            }
            if (updatePageVulnerabilitiesHolyWaterRadioButton.isSelected()) {
                vulnerabilitiesList.add(Vulnerability.HOLY_WATER);
            }
            if (updatePageVulnerabilitiesSunlightRadioButton.isSelected()) {
                vulnerabilitiesList.add(Vulnerability.SUNLIGHT);
            }
            vulnerabilities = vulnerabilitiesList.toArray(new Vulnerability[0]);


            // Checking validity and updating the character
            if (valid) {
                int health = Integer.parseInt(healthText);
                updatedCurr = AppState.getSelectedCharacter();
                updatedCurr.setName(name);
                updatedCurr.setHealth(health);
                updatedCurr.setDateOfBirth(dateOfBirth);
                updatedCurr.setVulnerabilities(vulnerabilities);


                // Update the database entry
                if (ogName.equalsIgnoreCase(updatePageNameTextField.getText().trim())) {
                    Main.executeCustomQuery("UPDATE horrorcharacters SET name = '" + name + "', health = " + updatedCurr.getHealth() + ", dateofbirth = '" + updatedCurr.getDateOfBirth() + "', vulnerabilities = '" + updatedCurr.getVulnerabilitiesAsString() + "', subtype = '" + subtype + "' WHERE name = '" + curr.getName() + "';");
                } else if (ogHealth == Integer.parseInt(updatePageHealthTextField.getText().trim())) {
                    Main.executeCustomQuery("UPDATE horrorcharacters SET name = '" + updatedCurr.getName() + "', health = " + ogHealth + ", dateofbirth = '" + updatedCurr.getDateOfBirth() + "', vulnerabilities = '" + updatedCurr.getVulnerabilitiesAsString() + "', subtype = '" + subtype + "' WHERE health = '" + curr.getHealth() + "';");
                } else {
                    System.out.println("Cannot update both name and health in the same update.");
                    infoLabel.setText("Error, you cannot change both name and health in the same update.");
                }


                // Loading and setting the primary view scene onto the stage.
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/michael/horrorcharacterjavafx/view/primary-view.fxml"));
                Parent root = loader.load();
                Scene secondaryScene = new Scene(root);
                Stage primaryStage = (Stage) updatePageAnchorPane.getScene().getWindow();
                primaryStage.setScene(secondaryScene);
            }
        }
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        infoLabel.setText(" ");

        // Auto populate the fields with the current character's data.
        if (curr != null) {
            updatePageNameTextField.setText(curr.getName());
            updatePageHealthTextField.setText(String.valueOf(curr.getHealth()));
            updatePageDobDatePicker.setValue(curr.getDateOfBirth());
            updatePageSubtypeComboBox.setItems(FXCollections.observableArrayList("Vampire", "Werewolf", "Zombie"));
            updatePageSubtypeComboBox.setValue(curr.getClass().getSimpleName());
            if (curr.getVulnerabilities() != null) {
                for (var v : curr.getVulnerabilities()) {
                    switch (v) {
                        case FIRE -> updatePageVulnerabilitiesFireRadioButton.setSelected(true);
                        case POISON -> updatePageVulnerabilitiesPoisonRadioButton.setSelected(true);
                        case HOLY_WATER -> updatePageVulnerabilitiesHolyWaterRadioButton.setSelected(true);
                        case SILVER -> updatePageVulnerabilitiesSilverRadioButton.setSelected(true);
                        case SUNLIGHT -> updatePageVulnerabilitiesSunlightRadioButton.setSelected(true);
                    }
                }
            }
        }
    }
}
