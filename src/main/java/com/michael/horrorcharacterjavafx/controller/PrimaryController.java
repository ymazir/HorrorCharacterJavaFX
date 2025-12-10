package com.michael.horrorcharacterjavafx.controller;

import com.michael.horrorcharacterjavafx.model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PrimaryController {


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

    public void CreateEntryButtonOnAction() {
        String name = null, subtype = null, healthText = null;
        LocalDate dateOfBirth = null;
        Vulnerability[] vulnerabilities;
        boolean valid = true;

        if (!createEntryNameTextField.getText().isBlank()) {
            name = createEntryNameTextField.getText();
        } else {
            System.out.println("Name is required.");
            valid = false;
        }

        if (!createHealthTextField.getText().isBlank()) {
            healthText = createHealthTextField.getText();
        } else {
            System.out.println("Health is required.");
            valid = false;
        }

        if (createDobDatePicker.getValue() != null) {
            dateOfBirth = createDobDatePicker.getValue();
        } else {
            System.out.println("Date of Birth is required.");
            valid = false;
        }

        if (createSubtypeComboBox.getValue() != null) {
            subtype = createSubtypeComboBox.getValue();
        } else {
            System.out.println("Subtype is required.");
            valid = false;
        }

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
        //System.out.println(vulnerabilities.length);

        if (valid) {
            int health = Integer.parseInt(healthText);
            HorrorCharacter newCharacter;
            switch (subtype) {
                case "Vampire" -> newCharacter = new Vampire(name, health, vulnerabilities, dateOfBirth);
                case "Werewolf" -> newCharacter = new Werewolf(name, health, vulnerabilities, dateOfBirth);
                case "Zombie" -> newCharacter = new Zombie(name, health, vulnerabilities, dateOfBirth);
                default -> {
                    System.out.println("Invalid subtype.");
                    return;
                }
            }
            mainTableView.getItems().add(newCharacter);
            infoLabel.setText("Created new " + subtype + ": " + name);
        }
    }

    @FXML
    public void initialize() {
        infoLabel.setText(" ");



        // Example data for the TableView
        ObservableList<HorrorCharacter> data = FXCollections.observableArrayList(
                new Vampire("Vampire bob", 80, new Vulnerability[]{Vulnerability.SUNLIGHT, Vulnerability.SILVER}, LocalDate.of(1800, 1, 1)),
                new Zombie("Zombie mike", 50, new Vulnerability[]{Vulnerability.FIRE}, LocalDate.of(2000, 5, 15)),
                new Werewolf("jon", 100, new Vulnerability[]{Vulnerability.SILVER}, LocalDate.of(1900, 10, 31))
        );

        // Set up the columns to display the data
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        healthTableColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
        subtypeTableColumn.setCellValueFactory(cellData -> {
            HorrorCharacter character = cellData.getValue();
            // Return the class name as a String
            return new javafx.beans.property.SimpleStringProperty(character.getClass().getSimpleName());
        });  // side note spent 2 hours debugging because the "T" in subtype was capitalized.
        dateofbirthTableColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfBirth")); // Set CellValueFactory

        // Custom rendering for dateofbirthTableColumn
        dateofbirthTableColumn.setCellFactory(column -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                if (empty || date == null) {
                    setText(null);
                } else {
                    // Format LocalDate to a string (e.g., dd-MM-yyyy)
                    setText(date.format(java.time.format.DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                }
            }
        });

        vulnerabilitiesTableColumn.setCellValueFactory(new PropertyValueFactory<>("vulnerabilities"));
        vulnerabilitiesTableColumn.setCellFactory(column -> new javafx.scene.control.TableCell<>() {
            @Override
            protected void updateItem(Vulnerability[] vulnerabilities, boolean empty) {
                super.updateItem(vulnerabilities, empty);
                if (empty || vulnerabilities == null) {
                    setText(null);
                } else {
                    // Convert Vulnerability[] to a comma-separated string
                    String vulnerabilitiesString = Arrays.stream(vulnerabilities)
                            .map(Vulnerability::toString)
                            .collect(Collectors.joining(", "));
                    setText(vulnerabilitiesString);
                }
            }
        });

        mainTableView.setItems(data);


        ObservableList<String> subtypeOptions = FXCollections.observableArrayList(
                "Vampire",
                "Werewolf",
                "Zombie"
        );

        createSubtypeComboBox.setItems(subtypeOptions);
    }

}
