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
	private Spinner<Integer> minuteSpinnerValue;
	
	private ArrayList<String> dailyScheduleInput = new ArrayList<String>();
	
	private ArrayList<Integer> weeklyScheduleInput = new ArrayList<Integer>();
	private String weeklySchedInputString = "";
	
	@FXML
	public void toggleMainView(ActionEvent e) throws IOException {
		submitMedInfo();
		Parent root = FXMLLoader.load(getClass().getResource("MainDisplayPane.fxml"));
		Stage addMedWindow = (Stage)((Node)e.getSource()).getScene().getWindow() ;
		Scene addMedView = new Scene(root);
		addMedWindow.setScene(addMedView) ;
		addMedWindow.show();
	}
	
	@FXML
	public void generateDaySelectionAsString(ActionEvent e) {
		String dayAddedToSchedule = ((RadioButton) e.getSource()).getText();
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
		
		Collections.sort(weeklyScheduleInput);
		weeklySchedInputString = "";
		
		for (Integer s : weeklyScheduleInput) {
			weeklySchedInputString += s;
		}
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
	
	public void submitMedInfo() {
		float dosageValue = 0;
		String finalDailyScheduleInput = "";
		StringBuilder sb = new StringBuilder();
		
		ArrayList<Integer> manipulatingDailyScheduleInput = new ArrayList<>();
		for (String s : dailyScheduleInput) {
			manipulatingDailyScheduleInput.add(Integer.parseInt(s));
		}
		Collections.sort(manipulatingDailyScheduleInput);
		
		//adding relevant data
		String nameOfMed = nameOfMedTextField.getText();
		try {
			dosageValue = Float.parseFloat(dosageOfMedTextField.getText());
		} catch (Exception e) {
			System.out.println("Handle incorrect input for float");
		}
		String dosageUnit = dosageUnitChoiceBox.getValue();
		
		for (int i = 0; i < manipulatingDailyScheduleInput.size(); i++) {
		    sb.append(manipulatingDailyScheduleInput.get(i));
		    if (i < manipulatingDailyScheduleInput.size() - 1) {
		        sb.append(",");
		    }
		}

        finalDailyScheduleInput = sb.toString();
        System.out.println(finalDailyScheduleInput);
		
		MedList.addMedications(nameOfMed, dosageValue, dosageUnit, weeklySchedInputString, finalDailyScheduleInput);
	}

}
