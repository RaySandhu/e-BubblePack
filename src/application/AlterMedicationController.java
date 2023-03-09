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
import javafx.stage.Stage;

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

	private Medication medicationToEdit;
	private boolean isEditing;

	public void setMedicationToEdit(Medication medicationToEdit) {
	    this.medicationToEdit = medicationToEdit;
	    this.isEditing = true;
	    populateFields();
	}

	private void populateFields() {
	    nameOfMedTextField.setText(medicationToEdit.getName());
	    dosageOfMedTextField.setText(String.valueOf(medicationToEdit.getDosage()));
	    dosageUnitChoiceBox.setValue(medicationToEdit.getDosageUnit());
	    dailyScheduleTextField.setText(String.valueOf(medicationToEdit.getDaysDue()));
	    timelyScheduleTextField.setText(medicationToEdit.getTimelyScheduleString());
	}

	private boolean isValidInput() {
	    String errorMessage = "";
	    if (nameOfMedTextField.getText() == null || nameOfMedTextField.getText().isEmpty()) {
	        errorMessage += "Name of medication is required\n";
	    }
	    if (dosageOfMedTextField.getText() == null || dosageOfMedTextField.getText().isEmpty()) {
	        errorMessage += "Dosage of medication is required\n";
	    } else {
	        try {
	            Double.parseDouble(dosageOfMedTextField.getText());
	        } catch (NumberFormatException e) {
	            errorMessage += "Invalid dosage input, must be a number\n";
	        }
	    }
	    if (dosageUnitChoiceBox.getSelectionModel().isEmpty()) {
	        errorMessage += "Dosage unit is required\n";
	    }
	    if (dailyScheduleTextField.getText() == null || dailyScheduleTextField.getText().isEmpty()) {
	        errorMessage += "Days due is required\n";
	    } else {
	        try {
	            int daysDue = Integer.parseInt(dailyScheduleTextField.getText());
	            if (daysDue < 0 || daysDue > 6) {
	                errorMessage += "Invalid days due input, must be between 0 and 6\n";
	            }
	        } catch (NumberFormatException e) {
	            errorMessage += "Invalid days due input, must be a number\n";
	        }
	    }
	    if (timelyScheduleTextField.getText() == null || timelyScheduleTextField.getText().isEmpty()) {
	        errorMessage += "Timely schedule is required\n";
	    } else {
	        try {
	            String[] times = timelyScheduleTextField.getText().split(",");
	            for (String time : times) {
	                LocalTime.parse(time.trim(), DateTimeFormatter.ofPattern("HH:mm"));
	            }
	        } catch (Exception e) {
	            errorMessage += "Invalid timely schedule input, must be in 24-hour format (HH:mm) separated by commas\n";
	        }
	    }

	    if (errorMessage.isEmpty()) {
	        return true;
	    } else {
	        Alert alert = new Alert(Alert.AlertType.ERROR);
	        alert.setTitle("Invalid Input");
	        alert.setHeaderText("Please correct invalid input");
	        alert.setContentText(errorMessage);
	        alert.showAndWait();
	        return false;
	    	}
		}

	@FXML
	private void handleSubmit(ActionEvent event) {
	    if (isValidInput()) {
	        String name = nameOfMedTextField.getText();
	        double dosage = Double.parseDouble(dosageOfMedTextField.getText());
	        String dosageUnit = dosageUnitChoiceBox.getValue();
	        // convert dailySchedule to integers
	        int daysDue = Integer.parseInt(dailyScheduleTextField.getText());
	        String timelySchedule = timelyScheduleTextField.getText();
	        // convert timelySchedule to integers
	        String[] times = timelySchedule.split(",");
	        int[] schedule = new int[times.length];
	        for (int i = 0; i < times.length; i++) {
	            LocalTime time = LocalTime.parse(times[i].trim(), DateTimeFormatter.ofPattern("HH:mm"));
	            int timeInt = time.getHour() * 60 + time.getMinute();
	            schedule[i] = timeInt;
	        }
	        
	        if (isEditing) {
	            medicationToEdit.setName(name);
	            medicationToEdit.setDosage(dosage);
	            medicationToEdit.setDosageUnit(dosageUnit);
	            medicationToEdit.setDaysDue(daysDue);
	            medicationToEdit.setTimelySchedule(schedule);
	        } else {
	            Medication newMedication = new Medication(name, dosage, dosageUnit, daysDue, schedule);
	            MedicationList.getInstance().addMedication(newMedication);
	        }
	        
	        // close scene
	        formHeadingLabel.getScene().getWindow().hide();
	    }
	}
}
