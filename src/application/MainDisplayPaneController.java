package application;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.util.Duration;

import java.util.ArrayList;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

public class MainDisplayPaneController {


	@FXML
	private Label currentTime;

	@FXML
	private Label medNameDisplay;

	@FXML
	private Label medDosageDisplay;

	@FXML
	private Label medDoseTimeDisplay;

	@FXML
	private VBox dailyMedsDisplay;

	/**
	 * Generates a styled button displaying all the parameters to the users for necessary information.
	 * Also allows for the toggling of administration status by clicking anywhere on the button and also has a menu button for edit and delete
	 * medication functions
	 * @param keyId unique identifier for the medication so that it can be retrieved on click
	 * @param medName medication name to be displayed
	 * @param dosage dosage to be displayed including dosing units
	 * @param singleDoseTime the exact time of this particular dose of medication to be displayed.
	 * @return the complete styled button that will populate the scrollpane in the main scene
	 */
	private Button medDisplayButtonGenerator(int keyId, String medName, String dosage, int singleDoseTime) {
		Button medDisplayButton = new Button();
		medDisplayButton.setPrefSize(700, 75);
		medDisplayButton.setFont(new Font(18));
		HBox hbox = new HBox();
		hbox.setAlignment(Pos.CENTER_LEFT);
		hbox.setPrefSize(800, 75);

		medDisplayButton.setOnMouseClicked(event -> {
			Medication clickedMed = MedList.retrieveMedById(keyId);

			int doseIndex = clickedMed.getSchedule().get(1).indexOf(singleDoseTime);
			clickedMed.toggleMedicationAdministered(doseIndex);

			//color toggle per administration status
			if (clickedMed.checkAdminStatusPerDose(doseIndex) == "Taken") {
				medDisplayButton.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);");
				medDisplayButton.setOnMouseEntered(e -> medDisplayButton.setStyle("-fx-background-color: rgba(0, 255, 0, 0.15);"));
				medDisplayButton.setOnMouseExited(e -> medDisplayButton.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);"));
			} else if (clickedMed.checkAdminStatusPerDose(doseIndex) == "Missed") {
				medDisplayButton.setStyle("-fx-background-color: rgba(255, 0, 0, 0.25);");
				medDisplayButton.setOnMouseEntered(e -> medDisplayButton.setStyle("-fx-background-color: rgba(255, 0, 0, 0.15);"));
				medDisplayButton.setOnMouseExited(e -> medDisplayButton.setStyle("-fx-background-color: rgba(255, 0, 0, 0.25);"));
			} else if (clickedMed.checkAdminStatusPerDose(doseIndex)=="Not due yet") {
				medDisplayButton.setStyle("-fx-background-color: transparent;-fx-border-color: black; -fx-border-width: 0.5px;");
				medDisplayButton.setOnMouseEntered(e -> medDisplayButton.setStyle("-fx-background-color: rgba(192, 192, 192, 0.5)\r\n"
						+ "; -fx-border-color: grey;-fx-border-width: 1px"));
				medDisplayButton.setOnMouseExited(e -> medDisplayButton.setStyle("-fx-background-color: rgba(192, 192, 192, 1)\r\n"
						+ "; -fx-border-color: black;-fx-border-width: 0.5px"));
			}
		});

		Label medNameDisplay = new Label(medName);
		medNameDisplay.setPrefWidth(150);
		medNameDisplay.setFont(new Font(18));
		medNameDisplay.setWrapText(true);
		HBox.setMargin(medNameDisplay, new Insets(0, 0, 0, 15));

		Label medDosageDisplay = new Label(dosage);
		medDosageDisplay.setPrefWidth(100);
		medDosageDisplay.setFont(new Font(18));
		HBox.setMargin(medDosageDisplay, new Insets(0, 0, 0, 10));

		Pane spacer = new Pane();
		spacer.setPrefSize(200, 75);

		Label medDoseTimeDisplay = new Label(Schedule.getTimeAsString(singleDoseTime));
		medDoseTimeDisplay.setPrefWidth(100);
		medDoseTimeDisplay.setFont(new Font(18));
		HBox.setMargin(medDoseTimeDisplay, new Insets(0, 10, 0, 0));

		MenuButton menuButton = new MenuButton();
		menuButton.setText("");
		menuButton.setTextAlignment(TextAlignment.CENTER);
		menuButton.setPrefWidth(0);

		MenuItem edit = new MenuItem("Edit this medication");
		MenuItem delete = new MenuItem("Delete this medication");
		menuButton.getItems().addAll(edit, delete);

		hbox.getChildren().addAll(medNameDisplay, medDosageDisplay, spacer, medDoseTimeDisplay, menuButton);

		medDisplayButton.setGraphic(hbox);
		return medDisplayButton;

	};

	/**
	 * Calls the rendering method with the appropriate parameter to display the medications due on the selected day.
	 * 
	 * @param e the event listener for the click on the day of the week button
	 */
	public void getSelectedDay(ActionEvent e) {
		String selectedDayValue = ((Button)e.getSource()).getText();
		System.out.println("Day select = " + selectedDayValue);
		switch(selectedDayValue) {
		case "Sunday":
			renderMedList(0);
			break;
		case "Monday":
			renderMedList(1);
			break;
		case "Tuesday":
			renderMedList(2);
			break;
		case "Wednesday":
			renderMedList(3);
			break;
		case "Thursday":
			renderMedList(4);
			break;
		case "Friday":
			renderMedList(5);
			break;
		case "Saturday":
			renderMedList(6);
			break;
		}

	}

	/**
	 * iterates through the medlist to correctly call the medications that are due on the selected day by the user. 
	 * Then iterates through each medication due and generates a button display for each dose within that medication.
	 * @param selectedDay the day of the week to display medications for indexed from 0-6 for Sunday through Saturday.
	 */
	public void renderMedList(int selectedDay) {

		ArrayList<Medication> listToRender = Display.dailyMedicationList(selectedDay);
		ArrayList<Integer> timeTracker = new ArrayList<Integer>();
		timeTracker.add(2400);	// only used as an outlier value to trigger the for loops for chronological sorting

		dailyMedsDisplay.getChildren().clear();

		for (Medication i : listToRender) {

			i.updateMissedMeds();
			ArrayList<Integer> timesDue = i.getSchedule().get(1);

			//iterate through each dose of each medication to render a button to display
			for(int j=0; j<timesDue.size(); j++) {
				i.checkAdminStatusPerDose(j);
				int indexCounter = 0;

				//    			linear sort on timesDue for the entire day to then organize the medlist for the day chronologically 
				for (int t : timeTracker) {
					if(t > timesDue.get(j)) {
						Button outputButton = medDisplayButtonGenerator(i.getId(), i.getTradeName(),i.getDosage(), timesDue.get(j));

						//color setting per administration status
						if (i.checkAdminStatusPerDose(j) == "Taken") {
							outputButton.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);");
							outputButton.setOnMouseEntered(e -> outputButton.setStyle("-fx-background-color: rgba(0, 255, 0, 0.15);"));
							outputButton.setOnMouseExited(e -> outputButton.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);"));
						} else if (i.checkAdminStatusPerDose(j) == "Missed") {
							outputButton.setStyle("-fx-background-color: rgba(255, 0, 0, 0.25);");
							outputButton.setOnMouseEntered(e -> outputButton.setStyle("-fx-background-color: rgba(255, 0, 0, 0.15);"));
							outputButton.setOnMouseExited(e -> outputButton.setStyle("-fx-background-color: rgba(255, 0, 0, 0.25);"));
						} else if (i.checkAdminStatusPerDose(j)=="Not due yet") {
							System.out.println("Not due yet");
						}
						timeTracker.add(indexCounter, timesDue.get(j));
						dailyMedsDisplay.getChildren().add(indexCounter, outputButton);
						break;
					} else {
						indexCounter++;
					}
				}
			}
		}
	}

	@FXML
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the FXML file has been loaded.
	 */
	void initialize() {
		//using a time-based animation to display the current time
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			LocalTime time = LocalTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm a");
			String formattedTime = time.format(formatter);
			currentTime.setText(formattedTime);
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
}
