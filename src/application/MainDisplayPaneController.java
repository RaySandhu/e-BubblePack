package application;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.util.Duration;

import java.util.ArrayList;
import java.util.ResourceBundle;

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

    private Button medDisplayButton(String medName, String dosage, String singleDoseTime) {
    	Button medDisplayButton = new Button();
    	medDisplayButton.setPrefSize(700, 75);
    	medDisplayButton.setFont(new Font(18));

    	HBox hbox = new HBox();
    	hbox.setAlignment(Pos.CENTER_LEFT);
    	hbox.setPrefSize(800, 75);

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

    	Label medDoseTimeDisplay = new Label("" + singleDoseTime);
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

    @FXML
    private Label medDosageDisplay;

    @FXML
    private Label medDoseTimeDisplay;
    
    @FXML
    private VBox dailyMedsDisplay;
    
    
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
    
    public void renderMedList(int selectedDay) {

    	ArrayList<Medication> listToRender = Display.dailyMedicationList(selectedDay);
    	ArrayList<Integer> timeTracker = new ArrayList<Integer>();
    	timeTracker.add(2400);

    	dailyMedsDisplay.getChildren().clear();

		for (Medication i : listToRender) {

			i.updateMissedMeds();
    		ArrayList<Integer> timesDue = i.getSchedule().get(1);

    		for(int j=0; j<timesDue.size(); j++) {
    			Boolean adminStatus = i.getAdministrationRecord().get(j).get(0);
    			Boolean missedStatus = i.getAdministrationRecord().get(j).get(1);
    			String displayStatus = "";
    			int indexCounter = 0;
    			
//    			linear sort on timesDue for the entire day
    			for (int t : timeTracker) {
    				if(t > timesDue.get(j)) {
						Button outputButton = medDisplayButton(i.getTradeName(),i.getDosage(), Schedule.getTimeAsString(timesDue.get(j)));
						
						if(adminStatus) {
							displayStatus = "Taken";
							outputButton.setStyle("-fx-background-color: rgba(0, 255, 0, 0.25);");
						} else if (missedStatus) {
							displayStatus = "Missed";
							outputButton.setStyle("-fx-background-color: rgba(255, 0, 0, 0.5);");
						} else if(!adminStatus && !missedStatus) {
							displayStatus = "Not due yet";
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
	void initialize() {
		//using a time-based animation to display the current time
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			LocalTime time = LocalTime.now();
//			int militaryTime = time.getHour() > 12 ? time.getHour() -12 : time.getHour();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hh:mm");
			String formattedTime = time.format(formatter);
			currentTime.setText(formattedTime);
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
}
