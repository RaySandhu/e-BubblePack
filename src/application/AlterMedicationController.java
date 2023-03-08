package application;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.util.Duration;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

public class AlterMedicationController {

    @FXML
    private TextField nameOfMedTextField;
    
    @FXML
    private TextField dosageOfMedTextField;
    
    @FXML
    private ChoiceBox <String> dosageUnitChoiceBox;
    
    @FXML
    private TextField dailyScheduleTextField;
    
    @FXML
    private TextField timelyScheduleTextField;
    
    @FXML
    private Label formHeadingLabel;
    
}