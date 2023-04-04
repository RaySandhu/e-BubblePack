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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * explicitly for handling the appropriate intake and transfer of data from the user to the medList storing medication information
 */
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
	
	@FXML
	private HBox weekendSelect;

	@FXML
	private HBox weekDaySelect;
	
	@FXML private RadioButton sunSelect, satSelect, monSelect, tuesSelect, wedSelect, thursSelect, friSelect;
		
	private ArrayList<String> dailyScheduleInput = new ArrayList<String>();
	
	private ArrayList<Integer> weeklyScheduleInput = new ArrayList<Integer>();
	private String weeklySchedInputString = "";
	
	private int editMedId = -1;
	
	private float dosageValue = 0;
	
	private String finalDailyScheduleInput = "";
	
	private String nameOfMed = "";
	
	private String dosageUnit = "";
	
	
	@FXML
	/**
	 * triggers the first main scene which displays medication information for the patient dependent on a boolean 
	 * from appropriate medication submission
	 * @param e represents the event that triggers this method
	 * @throws IOException captures if the method is called by an event which is targetting null
	 */
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
	/**
	 * switch statement to convert the input from radiobuttons representing days of the week 
	 * to strings in the format that is uniform throughout the application
	 * @param e represents the event that triggers this method
	 */
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
		
		for (Integer s : weeklyScheduleInput) {
			weeklySchedInputString += s;
		}
	}
	
	/**
	 * using spinners to lock user input to integers within the 24hr time clock, this creates a deletable button
	 * which displays every dose time choice that user wishes to finalize for the new medication that is created
	 */
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
		timeChoice.setPrefSize(173, 25);
		timeChoice.setOnMouseClicked(e -> {
			int timesIndex = 0;
        	for(String s : dailyScheduleInput) {    			
        		if(s.equals(hourDisplay + minutesDisplay)){
        			choseTimesDisplay.getChildren().remove(timesIndex);
        			dailyScheduleInput.remove(hourDisplay+minutesDisplay);
        			break;
        		} else timesIndex++;
        	}
        });
		choseTimesDisplay.getChildren().add(timeChoice);
	}
	
	/**
	 * Prevents invalid input from the user in any fields and mandate every field to have a value by leaving the final submit button
	 * disabled if checks not met. This method also runs again on the submit button as a final check that no values were changed to 
	 * invalid.
	 * @return
	 */
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
        
		//error message displays
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
        	if(dosageUnit == null || dosageUnit.equals("")) {
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
	
	/**
	 * runs a final check for valid inputs and add the medication information to medList appropriately. If input was changed to invalid,
	 * the button will disable and the scene change to main display will not occur.
	 * @return indicates if the scene display will change to the main scene.
	 */
	public Boolean submitMedInfo() {
		if(editMedId != -1) {
			MedList.deleteMedicationFromList(MedList.retrieveMedById(editMedId));
		}
		if (checkValidInput()) {
			MedList.addMedications(nameOfMed, dosageValue, dosageUnit, weeklySchedInputString, finalDailyScheduleInput);
			return true;
		} else return false;
	}
	
	
	public void editMedSetter(Medication target) {
		
		editMedId = target.getId();
		
		ArrayList<RadioButton> rbId = new ArrayList<RadioButton>();
		rbId.add(sunSelect);
		rbId.add(monSelect);
		rbId.add(tuesSelect);
		rbId.add(wedSelect);
		rbId.add(thursSelect);
		rbId.add(friSelect);
		rbId.add(satSelect);
		nameOfMedTextField.setText(target.getTradeName());
		dosageOfMedTextField.setText("" + Float.parseFloat(target.getDosage().split(" ")[0]));
		dosageUnitChoiceBox.setValue(target.getDosage().split(" ")[1]);
		
		for (int i = 0; i<=6; i++) {
			if(target.getSchedule().get(0).get(i) != -1) {
				rbId.get(i).setSelected(true);
				weeklySchedInputString += i;
			}
		}
		
		for (int time : target.getSchedule().get(1)) {
			// hourSpinnerValue minuteSpinnerValue
			hourSpinnerValue.getValueFactory().setValue(time/100);
			minuteSpinnerValue.getValueFactory().setValue(time%100);
			generateChosenTimeDisplay();
		}
		hourSpinnerValue.getValueFactory().setValue(0);
		minuteSpinnerValue.getValueFactory().setValue(0);
	}

}
