package application;

import java.net.URL;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javafx.util.Duration;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.*;

public class MainDisplayPaneController {


	@FXML
	private Label currentTime;
	
    @FXML
    private Label medNameDisplay;

    @FXML
    private Button medDisplayButton;

    @FXML
    private Label medDosageDisplay;

    @FXML
    private Label medDoseTimeDisplay;

	@FXML
	void initialize() {
		//using a time-based animation to display the current time
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
			LocalTime time = LocalTime.now();
			int militaryTime = time.getHour() > 12 ? time.getHour() -12 : time.getHour();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("hhmm");
			String formattedTime = time.format(formatter);
			currentTime.setText(formattedTime);
		}));
		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
}
