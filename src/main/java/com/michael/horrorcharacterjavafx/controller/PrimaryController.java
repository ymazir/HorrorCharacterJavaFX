package com.michael.horrorcharacterjavafx.controller;

import com.michael.horrorcharacterjavafx.model.HorrorCharacter;
import com.michael.horrorcharacterjavafx.model.Vulnerability;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;

public class PrimaryController {

    @FXML
    private VBox createVBox;

    @FXML
    private Button createentryButton;

    @FXML
    private Label createentryLabel;

    @FXML
    private TableColumn<HorrorCharacter, LocalDate> dateofbirthTableColumn;

    @FXML
    private TableColumn<HorrorCharacter, Integer> healthTableColumn;

    @FXML
    private HBox mainHBox;

    @FXML
    private TableView<HorrorCharacter> mainTableView;

    @FXML
    private TableColumn<HorrorCharacter, String> nameTableColumn;

    @FXML
    private AnchorPane sceneOneMainAnchorPane;

    @FXML
    private TableColumn<HorrorCharacter, Vulnerability[]> vulnerabilitiesTableColumn;


    public void initialize() {
        // Example data for the TableView
        ObservableList<HorrorCharacter> data = FXCollections.observableArrayList(
                new HorrorCharacter("Vampire", 80, new Vulnerability[]{Vulnerability.SUNLIGHT, Vulnerability.SILVER}, LocalDate.of(1800, 1, 1)),
                new HorrorCharacter("Zombie", 50, new Vulnerability[]{Vulnerability.FIRE}, LocalDate.of(2000, 5, 15)),
                new HorrorCharacter("Werewolf", 100, new Vulnerability[]{Vulnerability.SILVER}, LocalDate.of(1900, 10, 31))
        );

        // Set up the columns to display the data
        nameTableColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        healthTableColumn.setCellValueFactory(new PropertyValueFactory<>("health"));
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
    }

}
