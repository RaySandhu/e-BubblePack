package application;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HandleMedInfoController {
	
	@FXML
	private Label medNameLabel;
	
	@FXML
	private Label dosageLabel;
	
	@FXML
	private Label dosageUnitLabel;
	
	@FXML
	private Label timesDueLabel;
	@FXML
	private Label daysDueLabel;
	
	@FXML
	private TextField nameOfMedTextField;

	@FXML
	private TextField dosageOfMedTextField;

	@FXML
	private ChoiceBox<String> dosageUnitChoiceBox;

	@FXML
	private TextField dailyScheduleTextField;

	@FXML
	private VBox choseTimesDisplay;

	@FXML
	private Label formHeadingLabel;
	
	@FXML
	private Spinner<Integer> hourSpinnerValue;
	
	@FXML 
	private Button finalSubmitButton;
	
	@FXML
	private Spinner<Integer> minuteSpinnerValue;
	
	private ArrayList<String> dailyScheduleInput = new ArrayList<String>();
	
	private ArrayList<Integer> weeklyScheduleInput = new ArrayList<Integer>();
	private String weeklySchedInputString = "";
	
	private float dosageValue = 0;
	
	private String finalDailyScheduleInput = "";
	
	private String nameOfMed = "";
	
	private String dosageUnit = "";
	
	@FXML
	public void toggleMainView(ActionEvent e) throws IOException {
		if(submitMedInfo()) {
			Parent root = FXMLLoader.load(getClass().getResource("MainDisplayPane.fxml"));
			Stage addMedWindow = (Stage)((Node)e.getSource()).getScene().getWindow() ;
			Scene addMedView = new Scene(root);
			addMedWindow.setScene(addMedView) ;
			addMedWindow.show();
		} else System.out.println("Medication not added");
	}
	
	@FXML
	public void generateDaySelectionAsString(ActionEvent e) {
		String dayAddedToSchedule = ((RadioButton) e.getSource()).getText();
		
		if (((RadioButton) e.getSource()).isSelected()) {
			switch (dayAddedToSchedule) {
			case("Sun"):
				weeklyScheduleInput.add(0);
			break;
			case("Mon"):
				weeklyScheduleInput.add(1);
			break;
			case("Tues"):
				weeklyScheduleInput.add(2);
			break;
			case("Wed"):
				weeklyScheduleInput.add(3);
			break;
			case("Thurs"):
				weeklyScheduleInput.add(4);
			break;
			case("Fri"):
				weeklyScheduleInput.add(5);
			break;
			case("Sat"):
				weeklyScheduleInput.add(6);
			break;
			}
		} else {
			switch (dayAddedToSchedule) {
			case("Sun"):
				weeklyScheduleInput.removeIf(n -> n==0);
			break;
			case("Mon"):
				weeklyScheduleInput.removeIf(n -> n==1);
			break;
			case("Tues"):
				weeklyScheduleInput.removeIf(n -> n==2);
			break;
			case("Wed"):
				weeklyScheduleInput.removeIf(n -> n==3);
			break;
			case("Thurs"):
				weeklyScheduleInput.removeIf(n -> n==4);
			break;
			case("Fri"):
				weeklyScheduleInput.removeIf(n -> n==5);
			break;
			case("Sat"):
				weeklyScheduleInput.removeIf(n -> n==6);
			break;
			}
		}
		
		Collections.sort(weeklyScheduleInput);
		weeklySchedInputString = "";
		
		for (Integer s : weeklyScheduleInput) {
			weeklySchedInputString += s;
		}
		System.out.println(weeklySchedInputString);
	}
	
	public void generateChosenTimeDisplay() {
		int hoursToAdd = hourSpinnerValue.getValue();
		int minutesToAdd = minuteSpinnerValue.getValue();
		String hourDisplay;
		String minutesDisplay;
		
		if (hoursToAdd < 10) {
			hourDisplay = "0" + hoursToAdd;
		} else hourDisplay = "" + hoursToAdd;

		if (minutesToAdd < 10) {
			minutesDisplay = "0" + minutesToAdd;
		} else minutesDisplay = "" + minutesToAdd;
		
		dailyScheduleInput.add(hourDisplay + minutesDisplay);
		
		Button timeChoice = new Button(hourDisplay + ":" + minutesDisplay);
		timeChoice.setPrefSize(245, 25);
		timeChoice.setOnMouseClicked(e -> {
			int timesIndex = 0;
        	for(String s : dailyScheduleInput) {
//    			System.out.println(s + " equals " + hourDisplay + minutesDisplay + (s.equals(hourDisplay + minutesDisplay)));
    			
        		if(s.equals(hourDisplay + minutesDisplay)){
        			System.out.println("Deleted");
        			choseTimesDisplay.getChildren().remove(timesIndex);
        			dailyScheduleInput.remove(hourDisplay+minutesDisplay);
        			break;
        		} else timesIndex++;
        	}
        });
		choseTimesDisplay.getChildren().add(timeChoice);
	}
	
	public Boolean checkValidInput() {
		
		StringBuilder sb = new StringBuilder();
		
		ArrayList<Integer> manipulatingDailyScheduleInput = new ArrayList<>();
		for (String s : dailyScheduleInput) {
			manipulatingDailyScheduleInput.add(Integer.parseInt(s));
		}
		Collections.sort(manipulatingDailyScheduleInput);
		
		//adding relevant data
		nameOfMed = nameOfMedTextField.getText();
		try {
			dosageValue = Float.parseFloat(dosageOfMedTextField.getText());
		} catch (Exception e) {
			System.out.println("Handle incorrect input for float");
		}
		dosageUnit = dosageUnitChoiceBox.getValue();
		
		for (int i = 0; i < manipulatingDailyScheduleInput.size(); i++) {
		    sb.append(manipulatingDailyScheduleInput.get(i));
		    if (i < manipulatingDailyScheduleInput.size() - 1) {
		        sb.append(",");
		    }
		}
        finalDailyScheduleInput = sb.toString();
        
        if(!nameOfMed.equals("")) {
        	medNameLabel.setText("Name of Medication");
        }
        if(dosageValue != 0) {
        	dosageLabel.setText("Dosage of Medication");
        }
        if(dosageUnit != null) {
        	dosageUnitLabel.setText("Dosage Unit");
        }
        if(!weeklySchedInputString.equals("")) {
        	daysDueLabel.setText("Days to take medication");
        }
        if(!finalDailyScheduleInput.equals("")) {
        	timesDueLabel.setText("Time(s) due on each day");
        }
        
        if(!nameOfMed.equals("") && dosageValue != 0 && (!dosageUnit.equals("") || dosageUnit != null) && !weeklySchedInputString.equals("") && !finalDailyScheduleInput.equals("")) {
        	finalSubmitButton.setDisable(false);
        	return true;
        } else {
        	finalSubmitButton.setDisable(true);
        	if(nameOfMed.equals("")) {
        		medNameLabel.setText("Name of Medication"+" **");
        	}
        	if(dosageValue == 0) {
        		dosageLabel.setText("Dosage of Medication"+" **");
        	}
        	if(!dosageUnit.equals("") && dosageUnit != null) {
        		dosageUnitLabel.setText("Dosage Unit"+" **");
        	}
        	if(weeklySchedInputString.equals("")) {
        		daysDueLabel.setText("Days to take medication"+" **");
        	}
        	if(finalDailyScheduleInput.equals("")) {
        		timesDueLabel.setText("Time(s) due on each day"+" **");
        	}
        	return false;
        }
        
	}
	
	public Boolean submitMedInfo() {
		if (checkValidInput()) {
			MedList.addMedications(nameOfMed, dosageValue, dosageUnit, weeklySchedInputString, finalDailyScheduleInput);
			return true;
		} else return false;
	}

}
