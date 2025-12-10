package com.michael.horrorcharacterjavafx.controller;

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

    public void handleUpdatePageFinalUpdateButtonAction() throws IOException {

        // Logic to handle the update action
        if (curr != null) {
            String name = null, subtype = null, healthText = null;
            LocalDate dateOfBirth = null;
            Vulnerability[] vulnerabilities;
            boolean valid = true;

            if (!updatePageNameTextField.getText().isBlank()) {
                name = updatePageNameTextField.getText();
            } else {
                infoLabel.setText("Name is required.");
                valid = false;
            }

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

            if (updatePageDobDatePicker.getValue() != null) {
                dateOfBirth = updatePageDobDatePicker.getValue();
            } else {
                infoLabel.setText("Date of Birth is required.");
                valid = false;
            }

            if (updatePageSubtypeComboBox.getValue() != null) {
                subtype = updatePageSubtypeComboBox.getValue();
            } else {
                infoLabel.setText("Subtype is required.");
                valid = false;
            }


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


            if (valid) {
                int health = Integer.parseInt(healthText);
                curr.setName(name);
                curr.setHealth(health);
                curr.setDateOfBirth(dateOfBirth);
                curr.setVulnerabilities(vulnerabilities);
                AppState.setUpdatedSelectedCharacter(curr);
                infoLabel.setText("Character updated successfully.");
                AppState.safeToSwitch = true;







                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/michael/horrorcharacterjavafx/view/primary-view.fxml"));
                Parent root = loader.load();
                Scene secondaryScene = new Scene(root);
                Stage primaryStage = (Stage) updatePageAnchorPane.getScene().getWindow();
                primaryStage.setScene(secondaryScene);
            }
        }



    }


    @FXML
    public void initialize() {
        infoLabel.setText(" ");

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
